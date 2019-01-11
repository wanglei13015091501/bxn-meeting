package cn.boxiao.bxn.meeting.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxi.common.client.model.DeviceVo;
import cn.boxiao.bxi.common.client.service.DeviceMgrConnectService;
import cn.boxiao.bxi.common.client.service.DeviceMonitingConnectService;
import cn.boxiao.bxn.base.client.RemoteEMBServiceInvoker;
import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;
import cn.boxiao.bxn.common.util.PropertiesAccessorUtil;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserDao;
import cn.boxiao.bxn.meeting.data.AttendanceLogDo;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.TextUtils;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Service
public class AttendanceUserServiceImpl implements AttendanceUserService, MeetingConstants {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RemoteUserCenterServiceInvoker userService;
	@Autowired
	private RemoteEMBServiceInvoker remoteEMBServiceInvoker;

	@Autowired
	private DeviceMonitingConnectService deviceMonitingConnectService;
	@Autowired
	private DeviceMgrConnectService deviceMgrConnectService;

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceService attendanceCardService;

	@Autowired
	private AttendanceGroupService attendanceGroupService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private MeetingCache meetingCache;

	@Autowired
	private AttendanceMeetingDao attendanceMeetingDao;

	@Autowired
	private AttendanceUserDao attendanceUserDao;
	
	
	

	private static Map<String, Object> userStatusMap = Maps.newHashMap(); // 人员状态

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#refreshAttendanceUser(java.lang.String)
	 */
	@Override
	public void refreshAttendanceUser(String meetingId) {

		// 初始化非人工修改参会人员的打卡状态为未打卡,清除参会人员的打卡时间
		attendanceUserDao.initAttendanceUsers(meetingId);
		// 刷新meeting缓存
		meetingCache.refreshMeeting();
		// 刷新考勤规则
		meetingCache.refreshAttendanceRule();

		AttendanceMeetingVo meetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);

		// 重新统计参会人员的打卡状态
		List<AttendanceLogDo> attendanceLogDoList = attendanceUserDao.queryAttendanceLog(meetingId);
		for (AttendanceLogDo attendanceLogDo : attendanceLogDoList) {
			String clockTime = DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceLogDo.getClockTime());
			attendanceCardService.rePostEpcRecord(attendanceLogDo.getUserName(), meetingVo, clockTime);
		}

		checkMeetingStatus(meetingId, true);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#checkMeetingStatus(java.lang.String,
	 *      boolean)
	 */
	@Override
	public synchronized void checkMeetingStatus(String meetingId, boolean isForceRefresh) {
		Long currentTime = DateUtil.toLong(new Date());

		// 查询会议是否已经到了结束时间，如果过了正常签退时间，会议状态还是进行中，则需要根据缺勤规则更新参会人员缺勤状态
		AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		if (StringUtils.isBlank(attendanceMeetingDo.getId())) {
			return;
		}

		Long beginTimeLong = DateUtil.toLong(attendanceMeetingDo.getBeginTime());
		Long endTimeLong = DateUtil.toLong(attendanceMeetingDo.getEndTime());
		Long maxEndTimeLong = endTimeLong + meetingCache.getNormalLogoutSecond(); // 会议的结束时间边界，会议结束时间加上会议结束后XX分钟分钟内打卡

		if (currentTime < beginTimeLong) {
			// 更新会议状态为未开始
			attendanceMeetingDao.updateAttendanceMeetingStatus(meetingId, MEETING_STATUS_INIT);
		} else if (currentTime >= beginTimeLong && currentTime <= maxEndTimeLong) {
			// 更新会议状态为进行中
			attendanceMeetingDao.updateAttendanceMeetingStatus(meetingId, MEETING_STATUS_PROCESS);

		} else {
			// 如果是使用刷新会议考勤按钮触发，则不检查会议状态，直接检查考勤
			if (isForceRefresh) {
				List<AttendanceUserDo> attendanceUserDoList = checkUserAbsence(meetingId, beginTimeLong, endTimeLong,
						maxEndTimeLong);

				// 刷新会议部门统计
				attendanceGroupService.createAttendanceGroup(meetingId, attendanceUserDoList);
			} else {
				// 只在会议当前状态非END时，触发考勤检查，避免刷新页面时无谓的考勤检查
				if (!StringUtils.equals(MEETING_STATUS_END, attendanceMeetingDo.getStatus())) {
					List<AttendanceUserDo> attendanceUserDoList = checkUserAbsence(meetingId, beginTimeLong,
							endTimeLong, maxEndTimeLong);

					// 刷新会议部门统计
					attendanceGroupService.createAttendanceGroup(meetingId, attendanceUserDoList);
				}
			}

			// 更新会议状态为已结束
			attendanceMeetingDao.updateAttendanceMeetingStatus(meetingId, MEETING_STATUS_END);

		}
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#countAttendanceStatus(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo,
	 *      java.util.List)
	 */
	@Override
	public Map<String, Object> countAttendanceStatus(AttendanceMeetingVo attendanceMeetingVo,
			List<AttendanceUserVo> attendanceUserList) {
		Long currentTimeLong = DateUtil.toLong(new Date());

		Map<String, Object> countMap = Maps.newHashMap();
		countMap.put("status" + MEETING_USER_STATUS_NORMAL, 0);
		countMap.put("status" + MEETING_USER_STATUS_LATE, 0);
		countMap.put("status" + MEETING_USER_STATUS_LEAVE, 0);
		countMap.put("status" + MEETING_USER_STATUS_ABSENCE, 0);
		countMap.put("status" + MEETING_USER_STATUS_MISSING, 0);
		countMap.put("status" + MEETING_USER_STATUS_ABSENCE_PUBLIC, 0);
		countMap.put("status" + MEETING_USER_STATUS_ABSENCE_PRIVATE, 0);
		countMap.put("status" + MEETING_USER_STATUS_ABSENCE_SICK, 0);
		countMap.put("status" + MEETING_USER_STATUS_LATE_LEAVE, 0);
		countMap.put("status" + MEETING_USER_STATUS_INIT, 0);

		for (AttendanceUserVo attendanceUserVo : attendanceUserList) {
			Integer value = (Integer) countMap.get("status" + attendanceUserVo.getActualStatus());
			value++;
			countMap.put("status" + attendanceUserVo.getActualStatus(), value);
		}

		// 公假/私假/病假 统计成请假
		Integer absencePublicNum = (Integer) countMap.get("status" + MEETING_USER_STATUS_ABSENCE_PUBLIC);
		Integer absencePrivateNum = (Integer) countMap.get("status" + MEETING_USER_STATUS_ABSENCE_PRIVATE);
		Integer absenceSickNum = (Integer) countMap.get("status" + MEETING_USER_STATUS_ABSENCE_SICK);
		countMap.put("status" + MEETING_USER_STATUS_ABSENCE_LEAVE,
				absencePublicNum + absencePrivateNum + absenceSickNum);

		Long beginTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime())); // 会议开始时间
		Long endTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getEndTime())); // 会议结束时间
		Long signTimeLong = beginTimeLong - meetingCache.getNormalSignSecond();// 会议签到时间
		Long lateTimeLong = beginTimeLong + meetingCache.getLateSecond(); // 会议不计迟到时间
		Long logoutTimeLong = endTimeLong + meetingCache.getNormalLogoutSecond(); // 会议签退时间

		// 会议未到签到时间,--代替0
		if (signTimeLong > currentTimeLong) {
			countMap.put("status" + MEETING_USER_STATUS_NORMAL, "--");
			countMap.put("status" + MEETING_USER_STATUS_LATE, "--");
			countMap.put("status" + MEETING_USER_STATUS_LEAVE, "--");
			countMap.put("status" + MEETING_USER_STATUS_LATE_LEAVE, "--");
		}
		// 会议正常签到时间,迟到、早退、迟到/早退 --代替0
		if (lateTimeLong >= currentTimeLong && currentTimeLong >= signTimeLong) {
			countMap.put("status" + MEETING_USER_STATUS_LATE, "--");
			countMap.put("status" + MEETING_USER_STATUS_LEAVE, "--");
			countMap.put("status" + MEETING_USER_STATUS_LATE_LEAVE, "--");
		}
		// 会议未到签退时间,缺勤 --代替0
		if (currentTimeLong <= logoutTimeLong) {
			countMap.put("status" + MEETING_USER_STATUS_ABSENCE, "--");
		}

		return countMap;

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#countAttendanceStatus(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo,
	 *      java.lang.String)
	 */
	@Override
	public Map<String, Object> countAttendanceStatus(AttendanceMeetingVo attendanceMeetingVo, String lastRefreshTime) {
		Long currentTimeLong = DateUtil.toLong(new Date());
		Long lastRefreshTimeLong = 0L;
		if (StringUtils.isNotBlank(lastRefreshTime)) {
			lastRefreshTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(lastRefreshTime));
		}
		if (userStatusMap.isEmpty() || currentTimeLong - lastRefreshTimeLong > 1 * 60 * 1000) { // 缓存中空值或者缓存时间超过1分钟，从数据中查询

			List<AttendanceUserVo> attendanceUserList = Lists.newArrayList();

			// 只取状态，不需要查询用户信息及其他
			List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao
					.queryMeetingUsers(attendanceMeetingVo.getId());
			for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
				AttendanceUserVo attendanceUserVo = new AttendanceUserVo();
				BeanUtils.copyProperties(attendanceUserDo, attendanceUserVo);

				attendanceUserList.add(attendanceUserVo);
			}

			userStatusMap = countAttendanceStatus(attendanceMeetingVo, attendanceUserList);

			userStatusMap.put("lastRefreshTime", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));
		}

		return userStatusMap;

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#getAttendanceUsers(java.lang.String)
	 */
	@Override
	public List<AttendanceUserVo> getAttendanceUsers(String meetingId) {

		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);

		// 获取用户Map
		List<Long> userIdList = Lists.newArrayList();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			if (StringUtils.isNotBlank(attendanceUserDo.getUserId())) {
				userIdList.add(Long.valueOf(attendanceUserDo.getUserId()));
			}
		}
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdList);

		List<AttendanceUserVo> attendanceUserVoList = Lists.newArrayList();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			AttendanceUserVo attendanceUserVo = new AttendanceUserVo();
			BeanUtils.copyProperties(attendanceUserDo, attendanceUserVo);

			// 获取签到时间
			if (attendanceUserDo.getModifySignTime() != null) {
				attendanceUserVo
						.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getModifySignTime()));
			} else if (attendanceUserDo.getSignTime() != null) {
				attendanceUserVo.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getSignTime()));
			}

			// 获取签退时间
			if (attendanceUserDo.getModifyLogoutTime() != null) {
				attendanceUserVo
						.setLogoutTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getModifyLogoutTime()));
			} else if (attendanceUserDo.getLogoutTime() != null) {
				attendanceUserVo.setLogoutTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getLogoutTime()));
			}

			// 显示用户手动修改的状态
			if (StringUtils.isNotBlank(attendanceUserDo.getModifyStatus())) {
				attendanceUserVo.setStatus(attendanceUserDo.getModifyStatus());
			}

			// 获取教工号/学号、全名
			UserVo userVo = userVoMap.get(attendanceUserDo.getUserName());
			if (userVo == null) {
				continue;
			}
			attendanceUserVo.setUniqueNo(StringUtils.defaultIfBlank(userVo.getUniqueNo(), ""));
			attendanceUserVo.setFullName(StringUtils.defaultIfBlank(userVo.getFullName(), ""));
			attendanceUserVo.setCategory(userVo.getCategory() == null ? UserVo.CATEGORY_OTHER : userVo.getCategory());

			attendanceUserVoList.add(attendanceUserVo);
		}

		Collections.sort(attendanceUserVoList, new Comparator<AttendanceUserVo>() {
			@Override
			public int compare(AttendanceUserVo o1, AttendanceUserVo o2) {
				return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
			}
		});

		return attendanceUserVoList;

	}

	@Override
	public List<AttendanceUserVo> getAttendanceOneCardUsers() {
		List<AttendanceUserVo> attendanceUserVoList = Lists.newArrayList();

		Collections.sort(attendanceUserVoList, new Comparator<AttendanceUserVo>() {
			@Override
			public int compare(AttendanceUserVo o1, AttendanceUserVo o2) {
				return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
			}
		});

		return attendanceUserVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#updateAttendanceUser(java.lang.String,
	 *      cn.boxiao.bxn.meeting.vo.AttendanceUserVo)
	 */
	@Override
	public void updateAttendanceUser(String ids, AttendanceUserVo attendanceUserVo) {

		List<String> idList = CsvUtils.convertCSVToList(ids);
		for (String id : idList) {
			attendanceUserVo.setId(id);
			updateAttendanceUser(attendanceUserVo);
		}
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#updateAttendanceUser(cn.boxiao.bxn.meeting.vo.AttendanceUserVo)
	 */
	@Override
	public void updateAttendanceUser(AttendanceUserVo attendanceUserVo) {
		// 先校验该用户是否被删除
		AttendanceUserDo oldAttendanceUserDo = attendanceUserDao.queryAttendanceUserById(attendanceUserVo.getId());
		if (StringUtils.isBlank(oldAttendanceUserDo.getId())) {
			throw new BXQBusinessRuntimeException(Constants.ERR_COMMON_USERNOTFOUND,
					"参会人员" + attendanceUserVo.getId() + "已经被删除，请刷新页面");
		}
		attendanceUserVo.setUserName(oldAttendanceUserDo.getUserName());

		AttendanceUserDo attendanceUserDo = new AttendanceUserDo();
		BeanUtils.copyProperties(attendanceUserVo, attendanceUserDo);

		if (StringUtils.isNotBlank(attendanceUserVo.getModifySignTime())
				&& !StringUtils.equals(attendanceUserVo.getModifySignTime(), attendanceUserVo.getSignTime())) {
			attendanceUserDo
					.setModifySignTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceUserVo.getModifySignTime()));
		}
		if (StringUtils.isNotBlank(attendanceUserVo.getModifyLogoutTime())
				&& !StringUtils.equals(attendanceUserVo.getModifyLogoutTime(), attendanceUserVo.getLogoutTime())) {
			attendanceUserDo
					.setModifyLogoutTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceUserVo.getModifyLogoutTime()));
		}
		if (!StringUtils.equals(attendanceUserVo.getStatus(), attendanceUserVo.getModifyStatus())) { // 状态不一致标识状态已经修改
			attendanceUserDo.setModifyStatus(attendanceUserVo.getModifyStatus());
		}

		int result = attendanceUserDao.updateAttendanceUser(attendanceUserDo);
		if (result > 0) {
			// 根据会议地点获取设备ID
			AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao
					.queryAttendanceMeeting(attendanceUserVo.getMeetingId());

			String placeNo = placeService.queryPlace(attendanceMeetingDo.getPlaceId()).getPlaceNo();
			if (StringUtils.isBlank(placeNo)) {
				return;
			}

			try {
				List<DeviceVo> deviceVoList = deviceMgrConnectService.getAllAvaliableDevice();
				List<String> deviceIdList = Lists.newArrayList();
				for (DeviceVo deviceVo : deviceVoList) {
					if (StringUtils.equals(placeNo, deviceVo.getPlaceId())) {
						deviceIdList.add(deviceVo.getDeviceId());
					}
				}

				// 修改某个用户的考勤状态后，需要向班牌推送消息
				Map<String, String> dataMap = Maps.newHashMap();
				dataMap.put("userName", attendanceUserVo.getUserName());
				dataMap.put("state", attendanceUserVo.getActualStatus());

				deviceMonitingConnectService.sendCmd(MEET_MEMBERSTAECHANGE, deviceIdList.toArray(new String[] {}),
						dataMap);
			} catch (Exception e) {
				logger.error("updateAttendanceUser sendCmd MEET_MEMBERSTAECHANGE error:", e);
			}

		}
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#resetAttendanceUser(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void resetAttendanceUser(String meetingId, String userNames) {

		List<String> userNameList = CsvUtils.convertCSVToList(userNames);

		for (String userName : userNameList) {
			// 先校验该用户是否被删除
			AttendanceUserDo oldAttendanceUserDo = attendanceUserDao.queryAttendanceUserByUserName(meetingId, userName);
			if (StringUtils.isBlank(oldAttendanceUserDo.getId())) {
				throw new BXQBusinessRuntimeException(Constants.ERR_COMMON_USERNOTFOUND,
						"参会人员" + userName + "已经被删除，请刷新页面");
			}

			attendanceUserDao.resetAttendanceUser(meetingId, userName);
		}

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#getAttendanceMeetingQRCode(java.lang.String)
	 */
	@Override
	public String getAttendanceMeetingQRCode(String meetingId) {
		Long currentTimeLong = DateUtil.toLong(new Date());

		// 先跳到urlRewirte触发一次登录
		String rewriteUrl = PropertiesAccessorUtil.getProperty("remote.bwxuser.urlrewrite");
		if (StringUtils.isBlank(rewriteUrl)) {
			rewriteUrl = "http://wx-s.iboxiao.com/bwx-user/web/bxnAddress/urlRewrite?originalURL=";
		}

		// 会议二维码作为原始地址
		String meetingQTUrl = PropertiesAccessorUtil.getProperty("remote.meetingQT.url");
		if (StringUtils.isBlank(meetingQTUrl)) {
			meetingQTUrl = "http://wx-s.iboxiao.com/page/meeting/bxnMeetingQT.html";
		}
		// 根据会议ID、时间戳和appkey生成token
		String appkey = StringUtils.defaultIfBlank(PropertiesAccessorUtil.getProperty("appkey"), "");
		String token = DigestUtils.md5Hex(meetingId + Long.toString(currentTimeLong) + appkey);
		String originalURL = meetingQTUrl + "?" + "meetingId=" + meetingId + "&timestamp="
				+ Long.toString(currentTimeLong) + "&token=" + token;

		try {
			originalURL = URLEncoder.encode(originalURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URL编码失败，originalURL=" + originalURL, e);
		}

		return rewriteUrl + originalURL;

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#sendUserAttendanceNotify(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendUserAttendanceNotify(String meetingId, String content) {
		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);

		// 取用户ID一次性取出多个用户
		List<String> userIdList = Lists.newArrayList();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			if (StringUtils.isNotBlank(attendanceUserDo.getUserId())) {
				userIdList.add(attendanceUserDo.getUserId());
			}
		}

		if (CollectionUtils.isEmpty(userIdList)) {
			return;
		}

		String userIds = CsvUtils.convertCollectionToCSV(userIdList);
		// 发送短信提醒
		try {
			remoteEMBServiceInvoker.sendSms(userIds, SMS_SENDERID, content, 4, MODULENAME_NOTIFY, SMS_OBJECTID);
		} catch (Exception e) {
			logger.error("sendUserAttendanceNotify sendSms error:", e);
		}

		// 发送微信提醒
		try {
			remoteEMBServiceInvoker.sendMobileNotify(MODULENAME_NOTIFY, userIds, content);
		} catch (Exception e) {
			logger.error("sendUserAttendanceNotify sendMobileNotify error:", e);
		}

	}

	/**
	 * 更新考勤
	 * 
	 * @param meetingId
	 * @param userName
	 * @param signTime
	 * @param logoutTime
	 * @param status
	 */
	private void updateUserAttendanceByUserName(String meetingId, String userName, String signTime, String logoutTime,
			String status) {
		logger.debug("updateAttendance meetingId={}, userName={}, signTime={}, logoutTime={}, status={}", meetingId,
				userName, signTime, logoutTime, status);
		attendanceUserDao.updateAttendanceUserByUserName(meetingId, userName, signTime, logoutTime, status);
	}

	/**
	 * 批量获取用户VO
	 * 
	 * @param userIdList
	 * @return
	 */
	private Map<String, UserVo> convertUserVoToMap(List<Long> userIdList) {
		List<UserVo> userVoList = userService.getUsersByIds(userIdList);

		Map<String, UserVo> userMap = Maps.newHashMap();
		for (UserVo userVo : userVoList) {
			userMap.put(userVo.getUserName(), userVo);
		}

		return userMap;
	}

	/**
	 * 检查参会人员的缺勤情况
	 * 
	 * @param meetingId
	 * @param beginTimeLong
	 * @param endTimeLong
	 * @param maxEndTimeLong
	 */
	private List<AttendanceUserDo> checkUserAbsence(String meetingId, Long beginTimeLong, Long endTimeLong,
			Long maxEndTimeLong) {
		// 获取考勤规则
		Map<String, AttendanceRuleVo> attendanceRuleMap = meetingCache.getAttendanceRule();

		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);

		// 过滤出状态为正常(并且已经正常签退)/公假/私假/病假/漏卡
		List<AttendanceUserDo> filterAttendanceUserList = Lists.newArrayList();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			if ((attendanceUserDo.getSignTime() != null && attendanceUserDo.getLogoutTime() != null
					&& StringUtils.equals(MEETING_USER_STATUS_NORMAL, attendanceUserDo.getStatus()))
					|| StringUtils.isNotBlank(attendanceUserDo.getModifyStatus())) {
				continue;
			} else {
				filterAttendanceUserList.add(attendanceUserDo);
			}
		}

		// 缺勤规则 签到——签退时间范围内未打卡
		AttendanceRuleVo noClockRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_NO_CLOCK);
		if (StringUtils.equals(STATUS_OPEN, noClockRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() == null && attendanceUserDo.getLogoutTime() == null) {
					attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
					updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
							attendanceUserDo.getStatus());
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}
			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 签到——签退时间范围打卡打卡1次
		AttendanceRuleVo oneClockRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_ONE_CLOCK);
		if (StringUtils.equals(STATUS_OPEN, oneClockRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() != null && attendanceUserDo.getLogoutTime() == null) {
					attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
					updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
							attendanceUserDo.getStatus());
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 签到——签退时间范围打卡，但签到签退有效时间间隔在XX分钟内
		AttendanceRuleVo timeClockRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_TIME_CLOCK);
		if (StringUtils.equals(STATUS_OPEN, timeClockRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() != null && attendanceUserDo.getLogoutTime() != null) {
					Long signTimeLong = DateUtil.toLong(attendanceUserDo.getSignTime());
					Long logoutTimeLong = DateUtil.toLong(attendanceUserDo.getLogoutTime());

					if (logoutTimeLong - signTimeLong < meetingCache.getSignInterval()) {
						attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
						updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
								attendanceUserDo.getStatus());
					} else {
						saveAttendanceUserList.add(attendanceUserDo);
					}
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}

			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 只在正常签到时间内打卡
		AttendanceRuleVo onlySignRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_ONLY_SIGN);
		if (StringUtils.equals(STATUS_OPEN, onlySignRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() != null && attendanceUserDo.getLogoutTime() == null
						&& StringUtils.equals(MEETING_USER_STATUS_NORMAL, attendanceUserDo.getStatus())) {
					attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
					updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
							attendanceUserDo.getStatus());
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 只在正常签退时间内打卡
		AttendanceRuleVo onlyLogoutRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_ONLY_LOGOUT);
		if (StringUtils.equals(STATUS_OPEN, onlyLogoutRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() != null && attendanceUserDo.getLogoutTime() == null
						&& StringUtils.equals(MEETING_USER_STATUS_LATE, attendanceUserDo.getStatus())) {
					Long signTimeLong = DateUtil.toLong(attendanceUserDo.getSignTime()); // 打卡时间
					if (signTimeLong >= endTimeLong - meetingCache.getLeaveSecond() && signTimeLong <= maxEndTimeLong) {
						attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
						updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
								attendanceUserDo.getStatus());
					} else {
						saveAttendanceUserList.add(attendanceUserDo);
					}

				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 迟到XX分钟以上
		AttendanceRuleVo maxMinuteLateRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LATE);
		if (StringUtils.equals(STATUS_OPEN, maxMinuteLateRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getSignTime() != null
						&& StringUtils.equals(MEETING_USER_STATUS_LATE, attendanceUserDo.getStatus())) {
					Long signTimeLong = DateUtil.toLong(attendanceUserDo.getSignTime()); // 打卡时间
					if (signTimeLong - beginTimeLong > meetingCache.getMaxLateSecond()) {
						attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
						updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
								attendanceUserDo.getStatus());
					} else {
						saveAttendanceUserList.add(attendanceUserDo);
					}
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		// 缺勤规则 早退XX分钟以上
		AttendanceRuleVo maxMinuteLeaveRule = attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LEAVE);
		if (StringUtils.equals(STATUS_OPEN, maxMinuteLeaveRule.getStatus())) {
			List<AttendanceUserDo> saveAttendanceUserList = Lists.newArrayList();
			for (AttendanceUserDo attendanceUserDo : filterAttendanceUserList) {
				if (attendanceUserDo.getLogoutTime() != null
						&& StringUtils.equals(MEETING_USER_STATUS_LEAVE, attendanceUserDo.getStatus())) {
					Long signTimeLong = DateUtil.toLong(attendanceUserDo.getSignTime()); // 打卡时间
					if (endTimeLong - signTimeLong > meetingCache.getMaxLeaveSecond()) {
						attendanceUserDo.setStatus(MEETING_USER_STATUS_ABSENCE);
						updateUserAttendanceByUserName(meetingId, attendanceUserDo.getUserName(), "", "",
								attendanceUserDo.getStatus());
					} else {
						saveAttendanceUserList.add(attendanceUserDo);
					}
				} else {
					saveAttendanceUserList.add(attendanceUserDo);
				}
			}

			filterAttendanceUserList.clear();
			filterAttendanceUserList.addAll(saveAttendanceUserList);
		}

		return attendanceUserDoList;
	}

	@Override
	public int countAttendancePeople(String meetingId) {
		
		// 获取参会人数
		int userNum = attendanceUserDao.countAttendanceUserByMeetingId(meetingId);
		
		return userNum;
	}

}
