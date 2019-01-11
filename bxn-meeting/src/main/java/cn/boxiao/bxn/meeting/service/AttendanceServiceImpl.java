/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.base.client.RemoteEMBServiceInvoker;
import cn.boxiao.bxn.common.util.PropertiesAccessorUtil;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.dao.AttendanceUserDao;
import cn.boxiao.bxn.meeting.data.AttendanceLogDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceOneCardVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月25日
 */
@Service
public class AttendanceServiceImpl implements AttendanceService, MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RemoteEMBServiceInvoker remoteEMBServiceInvoker;

	@Autowired
	private AttendanceUserDao attendanceUserDao;

	@Autowired
	private MeetingCache meetingCache;

	private ReadWriteLock rwLock = new ReentrantReadWriteLock();

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceService#postEpcRecord(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public Map<String, String> postEpcRecord(String userNames, String meetingId, String clockTime, String deviceId) {
		logger.debug("postEpcRecord parameter: userNames={}, meetingId={}, clockTime={}", userNames, meetingId,
				clockTime);
		List<String> userNameList = CsvUtils.convertCSVToList(userNames);

		List<AttendanceLogDo> attendanceLogDoList = Lists.newArrayList();
		for (String userName : userNameList) {
			AttendanceLogDo attendanceLogDo = createAttendanceLog(userName, meetingId, clockTime, deviceId);
			attendanceLogDoList.add(attendanceLogDo);
		}
		attendanceUserDao.createAttendanceLog(attendanceLogDoList);
		logger.debug("postEpcRecord createAttendanceLog size={}", attendanceLogDoList.size());

		// 根据会议地点ID，获取所有该会议地点的信息
		Map<String, AttendanceMeetingVo> meetingMap = meetingCache.getMeeting();
		AttendanceMeetingVo attendanceMeeting = (AttendanceMeetingVo) MapUtils.getObject(meetingMap, meetingId,
				new AttendanceMeetingVo());

		// 返回结果Map
		Map<String, String> recordMap = Maps.newHashMap();

		// 如果没有查询到当天会议直接返回
		if (StringUtils.isBlank(attendanceMeeting.getId())) {
			logger.debug("postEpcRecord can not find meetingVo id={}", meetingId);
			return getRecordMap(userNameList, meetingId, recordMap);
		}

		// 如果会议已经结束，不计算状态直接返回
		// if (StringUtils.equals(MEETING_STATUS_END,
		// attendanceMeeting.getStatus())) {
		// logger.debug("postEpcRecord meeting={} is end, will not force
		// refresh", meetingId);
		// return getRecordMap(userNameList, meetingId, recordMap);
		// }

		// 允许同时打卡或刷二维码，此处为读锁，读锁相互之间不互斥
		rwLock.readLock().lock();
		try {
			recordMap = doPostEpcRecord(attendanceMeeting, clockTime, true, userNameList);
		} finally {
			rwLock.readLock().unlock();
		}
		return recordMap;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceService#rePostEpcRecord(java.lang.String,
	 *      cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo, java.lang.String)
	 */
	@Override
	public Map<String, String> rePostEpcRecord(String userNames, AttendanceMeetingVo attendanceMeeting,
			String clockTime) {
		logger.debug("rePostEpcRecord parameter: userNames={}, meetingId={}, clockTime={}", userNames,
				attendanceMeeting.getId(), clockTime);

		// 返回结果Map
		Map<String, String> recordMap = Maps.newHashMap();

		List<String> userNameList = CsvUtils.convertCSVToList(userNames);

		// 如果没有查询到会议直接返回
		if (StringUtils.isBlank(attendanceMeeting.getId())) {
			logger.debug("rePostEpcRecord can not find meetingVo id={}", attendanceMeeting.getId());
			return getRecordMap(userNameList, attendanceMeeting.getId(), recordMap);
		}

		// 不允许打卡或刷二维码时重新考勤，此处为写锁，写锁与读锁或写锁之间互斥
		rwLock.writeLock().lock();
		try {
			recordMap = doPostEpcRecord(attendanceMeeting, clockTime, false, userNameList);
		} finally {
			rwLock.writeLock().unlock();
		}
		return recordMap;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceService#postEpcRecordByQRCode(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, String> postEpcRecordByQRCode(String userName, String meetingId, String timestamp,
			String token) {
		Long clockTimeLong = DateUtil.toLong(new Date());
		// 返回结果Map
		Map<String, String> recordMap = Maps.newHashMap();
		if (StringUtils.isBlank(timestamp)) {
			logger.error("postEpcRecordByQRCode timestamp is null");
			recordMap.put(userName, MEETING_USER_STATUS_INVALID_UNKNOWN);
			return recordMap;
		}

		String appkey = StringUtils.defaultIfBlank(PropertiesAccessorUtil.getProperty("appkey"), "");
		String trueToken = DigestUtils.md5Hex(meetingId + timestamp + appkey);
		// 如果token不一致，返回无效状态
		if (!StringUtils.equals(trueToken, token)) {
			logger.error("postEpcRecordByQRCode token check fail, new token is {},param token is {}", trueToken, token);
			recordMap.put(userName, MEETING_USER_STATUS_INVALID_UNKNOWN);
			return recordMap;
		}

		// timestamp是否过期，时间戳有效时间15秒，如果大于15秒，返回无效状态
		Long timestampLong = Long.valueOf(timestamp);
		if (clockTimeLong - timestampLong > 15 * 1000L) {
			logger.error("postEpcRecordByQRCode timeout, clockTime is {},param timestamp is {}", clockTimeLong,
					timestampLong);
			recordMap.put(userName, MEETING_USER_STATUS_INVALID_TIMESTAMP);
			return recordMap;
		}

		return postEpcRecord(userName, meetingId,
				DateUtil.toString_YYYY_MM_DD_HH_MM_SS(DateUtil.parseLong(clockTimeLong)), "");
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceService#postEpcRecordByOneCard(java.util.List)
	 */
	@Override
	public Map<String, String> postEpcRecordByOneCard(List<AttendanceOneCardVo> attendanceList) {

		Map<String, String> recordMap = Maps.newHashMap();
		if (CollectionUtils.isEmpty(attendanceList)) {
			return recordMap;
		}

		// 查询一卡通考勤的当天会议
		List<AttendanceMeetingVo> todayOneCardMeetings = Lists.newArrayList();
		Map<String, AttendanceMeetingVo> todayMeetings = meetingCache.getMeeting();
		for (AttendanceMeetingVo meetingVo : todayMeetings.values()) {

			if (StringUtils.equals(meetingVo.getMeetingTypeId(), MeetingConstants.MEETING_TYPE_ONECARD_ID)) {
				todayOneCardMeetings.add(meetingVo);
			}
		}

		// 处理打卡记录
		String todayDate = DateUtil.toString_YYYY_MM_DD(new Date());
		for (AttendanceOneCardVo attendance : attendanceList) {

			// 只处理当天的打卡记录
			Date recordTime = DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendance.getRecordDate());
			if (!StringUtils.equals(DateUtil.toString_YYYY_MM_DD(recordTime), todayDate)) {
				continue;
			}

			// 根据打卡时间和用户信息，查找可能的会议
			Long recordTimeLong = DateUtil.toLong(recordTime);
			for (AttendanceMeetingVo meetingVo : todayOneCardMeetings) {

				// 判断会议时间与打卡时间是否匹配
				Long normalSignLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(meetingVo.getBeginTime()))
						- meetingCache.getNormalSignSecond();
				Long normalLogoutLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(meetingVo.getEndTime()))
						+ meetingCache.getNormalLogoutSecond();
				if (recordTimeLong.longValue() < normalSignLong.longValue()
						|| recordTimeLong.longValue() > normalLogoutLong.longValue()) {
					continue;
				}

				// 判断会议考勤范围中是否有该用户
				AttendanceUserDo attendanceUser = attendanceUserDao.queryAttendanceUserByUserName(meetingVo.getId(),
						attendance.getUserName());
				if (StringUtils.isBlank(attendanceUser.getId())) {
					continue;
				}

				// 进行打卡操作
				postEpcRecord(attendance.getUserName(), meetingVo.getId(), attendance.getRecordDate(),
						attendance.getDeviceId());
			}

		}
		return recordMap;
	}

	/**
	 * 生成打卡日志
	 * 
	 * @param userName
	 * @param meetingId
	 * @param clockTime
	 * @param deviceId
	 */
	private AttendanceLogDo createAttendanceLog(String userName, String meetingId, String clockTime, String deviceId) {
		AttendanceLogDo attendanceLogDo = new AttendanceLogDo();
		attendanceLogDo.setUserName(userName);
		attendanceLogDo.setMeetingId(meetingId);
		attendanceLogDo.setDeviceId(deviceId);
		attendanceLogDo.setClockTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(clockTime));

		return attendanceLogDo;

	}

	/**
	 * 推送用户打卡信息，更新用户打卡状态
	 * 
	 * @param attendanceMeeting
	 * @param clockTime
	 * @param needNotify
	 * @param userNameList
	 * @return
	 */
	private Map<String, String> doPostEpcRecord(AttendanceMeetingVo attendanceMeeting, String clockTime,
			boolean needNotify, List<String> userNameList) {

		Map<String, String> recordMap = Maps.newHashMap();
		Long clockTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(clockTime)); // 会议的打卡时间

		// 签到时间之后N秒刷卡，才产生早退记录
		Long leaveIntervalLong = 1000 * 60L;
		String leaveInterval = PropertiesAccessorUtil.getProperty("attendance.leave.intervalSec");
		if (StringUtils.isNotBlank(leaveInterval)) {
			leaveIntervalLong = 1000 * Long.valueOf(leaveInterval);
		}

		// 计算会议的有效打卡时间
		Long beginTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeeting.getBeginTime()));
		Long endTimeLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeeting.getEndTime()));
		Long minBeginTimeLong = beginTimeLong - meetingCache.getNormalSignSecond(); // 会议的开始时间边界，会议开始时间减去会议开始前XX分钟内打卡
		Long maxEndTimeLong = endTimeLong + meetingCache.getNormalLogoutSecond(); // 会议的结束时间边界，会议结束时间加上会议结束后XX分钟分钟内打卡

		// 发送短信参会人员列表
		Map<String, String> notifyUserMap = Maps.newHashMap();

		if (clockTimeLong < minBeginTimeLong) { // 如果签到之前打开无效
			recordMap = getRecordMap(userNameList, attendanceMeeting.getId(), recordMap);

			logger.debug("doPostEpcRecord clockTime={} is before minBeginTime={}, return {}", clockTimeLong,
					minBeginTimeLong, recordMap);
		} else if (clockTimeLong >= minBeginTimeLong && clockTimeLong <= beginTimeLong + meetingCache.getLateSecond()) { // 在正常签到时间段内
			for (String userName : userNameList) {
				// 查询用户的打卡信息，若没有打卡信息，返回无效打卡
				AttendanceUserVo attendanceUserVo = getAttendanceUserByUserName(attendanceMeeting.getId(), userName);
				if (StringUtils.isBlank(attendanceUserVo.getId())) {
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_INVALID, ""));
					continue;
				}

				if (StringUtils.equals(MEETING_USER_STATUS_INIT, attendanceUserVo.getStatus())) { // 如果用户已经打过卡，此次算无效，如果没有打过卡，此次算正常签到
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, clockTime, "",
							MEETING_USER_STATUS_NORMAL);
				}

				notifyUserMap.put(attendanceUserVo.getUserId(), "sign");
				recordMap.put(userName,
						getUserAttendanceStatus(MEETING_USER_STATUS_NORMAL, attendanceUserVo.getModifyStatus()));

			}

			logger.debug(
					"doPostEpcRecord clockTime={} is after minBeginTime={} and before beginTime+late={}, return {}",
					clockTimeLong, minBeginTimeLong, beginTimeLong + meetingCache.getLateSecond(), recordMap);

		} else if (clockTimeLong > beginTimeLong + meetingCache.getLateSecond()
				&& clockTimeLong < endTimeLong - meetingCache.getLeaveSecond()) { // 如果打卡时间在迟到之后，允许正常签退之前
			for (String userName : userNameList) {
				// 查询用户的打卡信息
				AttendanceUserVo attendanceUserVo = getAttendanceUserByUserName(attendanceMeeting.getId(), userName);
				if (StringUtils.isBlank(attendanceUserVo.getId())) {
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_INVALID, ""));
					continue;
				}

				String status = attendanceUserVo.getStatus();
				String modifyStatus = attendanceUserVo.getModifyStatus();

				if (StringUtils.equals(MEETING_USER_STATUS_INIT, attendanceUserVo.getStatus())) { // 如果是第一次打卡，此次算迟到
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, clockTime, "",
							MEETING_USER_STATUS_LATE);

					notifyUserMap.put(attendanceUserVo.getUserId(), "sign");
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LATE, modifyStatus));

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_NORMAL)) { // 如果不是第一次打卡，并且打卡状态为正常，此次为早退
					long signTimeLong = DateUtil
							.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceUserVo.getSignTime()));

					// 判断签到打卡与本次打卡是否间隔60s，如果不足，则不处理
					if (clockTimeLong < signTimeLong + leaveIntervalLong) {
						notifyUserMap.put(attendanceUserVo.getUserId(), "sign");
						recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_NORMAL, modifyStatus));
					} else {
						updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime,
								MEETING_USER_STATUS_LEAVE);
						notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
						recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LEAVE, modifyStatus));
					}

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_LATE)) { // 如果不是第一次打卡，并且打卡状态为迟到，此次为迟到/早退

					long signTimeLong = DateUtil
							.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceUserVo.getSignTime()));

					// 判断签到打卡与本次打卡是否间隔60s，如果不足，则不处理
					if (clockTimeLong < signTimeLong + leaveIntervalLong) {
						notifyUserMap.put(attendanceUserVo.getUserId(), "sign");
						recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LATE, modifyStatus));
					} else {
						updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime,
								MEETING_USER_STATUS_LATE_LEAVE);
						notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
						recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LATE_LEAVE, modifyStatus));
					}

				} else {
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime, status);
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(status, modifyStatus));
				}

			}

			logger.debug(
					"doPostEpcRecord clockTime={} is after beginTime+late={} and before endTime-leave={}, return {}",
					clockTimeLong, beginTimeLong + meetingCache.getLateSecond(),
					endTimeLong - meetingCache.getLeaveSecond(), recordMap);
		} else if (clockTimeLong >= endTimeLong - meetingCache.getLeaveSecond() && clockTimeLong <= maxEndTimeLong) { // 如果打卡时间在正常签退时间内
			for (String userName : userNameList) {
				// 查询用户的打卡信息
				AttendanceUserVo attendanceUserVo = getAttendanceUserByUserName(attendanceMeeting.getId(), userName);
				if (StringUtils.isBlank(attendanceUserVo.getId())) {
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_INVALID, ""));
					continue;
				}

				String status = attendanceUserVo.getStatus();
				String modifyStatus = attendanceUserVo.getModifyStatus();

				if (StringUtils.equals(MEETING_USER_STATUS_INIT, attendanceUserVo.getStatus())) { // 如果是第一次打卡，此次算迟到
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, clockTime, "",
							MEETING_USER_STATUS_LATE);

					notifyUserMap.put(attendanceUserVo.getUserId(), "sign");
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LATE, modifyStatus));

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_NORMAL)) { // 如果不是第一次打卡，而且状态为正常，此次为正常
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime, status);
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(status, modifyStatus));

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_LATE)) { // 如果不是第一次打卡，而且状态为迟到，此次为迟到
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime, status);
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(status, modifyStatus));

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_LEAVE)) { // 如果不是第一次打卡，并且状态为早退，此次为正常
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime,
							MEETING_USER_STATUS_NORMAL);
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_NORMAL, modifyStatus));

				} else if (StringUtils.equals(status, MEETING_USER_STATUS_LATE_LEAVE)) { // 如果不是第一次打卡，并且状态为迟到/早退，此次为迟到
					updateUserAttendanceByUserName(attendanceMeeting.getId(), userName, "", clockTime,
							MEETING_USER_STATUS_LATE);
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_LATE, modifyStatus));
				} else {
					notifyUserMap.put(attendanceUserVo.getUserId(), "logout");
					recordMap.put(userName, getUserAttendanceStatus(status, modifyStatus));
				}

				logger.debug(
						"doPostEpcRecord clockTime={} is after endTime-leave={} and before maxEndTime={}, return {}",
						clockTime, endTimeLong - meetingCache.getLeaveSecond(), maxEndTimeLong, recordMap);
			}
		} else { // 正常签退之后打卡无效
			for (String userName : userNameList) {
				AttendanceUserVo attendanceUserVo = getAttendanceUserByUserName(attendanceMeeting.getId(), userName);
				if (StringUtils.isBlank(attendanceUserVo.getId())) {
					recordMap.put(userName, getUserAttendanceStatus(MEETING_USER_STATUS_INVALID, ""));
					continue;
				}

				String status = attendanceUserVo.getStatus();
				String modifyStatus = attendanceUserVo.getModifyStatus();
				recordMap.put(userName, getUserAttendanceStatus(status, modifyStatus));
			}

			logger.debug("doPostEpcRecord clockTime={} is after maxEndTime={}", clockTime, maxEndTimeLong, recordMap);
		}

		// 发送打卡短信
		if (needNotify) {
			sendUserAttendanceReceipt(notifyUserMap, attendanceMeeting);
		}
		return recordMap;
	}

	/**
	 * 获取打卡返回值
	 * 
	 * @param userNameList
	 * @param meetingId
	 * @param recordMap
	 * @return
	 */
	private Map<String, String> getRecordMap(List<String> userNameList, String meetingId,
			Map<String, String> recordMap) {
		for (String userName : userNameList) {
			AttendanceUserVo attendanceUserVo = getAttendanceUserByUserName(meetingId, userName);
			recordMap.put(userName, attendanceUserVo.getActualStatus());
		}
		return recordMap;
	}

	/**
	 * 根据username获取人员考勤
	 * 
	 * @param meetingId
	 * @param userName
	 * @return
	 */
	private AttendanceUserVo getAttendanceUserByUserName(String meetingId, String userName) {
		AttendanceUserDo attendanceUserDo = attendanceUserDao.queryAttendanceUserByUserName(meetingId, userName);
		if (StringUtils.isBlank(attendanceUserDo.getId())) {
			return new AttendanceUserVo();
		}

		AttendanceUserVo attendanceUserVo = new AttendanceUserVo();
		BeanUtils.copyProperties(attendanceUserDo, attendanceUserVo);

		// 获取签到时间
		if (attendanceUserDo.getModifySignTime() != null) {
			attendanceUserVo.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getModifySignTime()));
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

		return attendanceUserVo;

	}

	/**
	 * 返回参会人员状态
	 * 
	 * @param attendanceUserVo
	 * @return
	 */
	private String getUserAttendanceStatus(String status, String modifyStatus) {
		return StringUtils.isNotBlank(modifyStatus) ? modifyStatus : status;
	}

	/**
	 * 发送打卡提示短信
	 * 
	 * @param notifyUserMap
	 * @param attendanceMeetingVo
	 */
	private void sendUserAttendanceReceipt(Map<String, String> notifyUserMap, AttendanceMeetingVo attendanceMeetingVo) {

		String meetingName = attendanceMeetingVo.getMeetingName();
		String beginTime = attendanceMeetingVo.getBeginTime();
		String endTime = attendanceMeetingVo.getEndTime();
		String content1 = meetingName + "(" + beginTime + "--" + endTime + ") ," + "您已经签到成功。";
		String content2 = meetingName + "(" + beginTime + "--" + endTime + ") ," + "您已经签退成功。";

		if (MapUtils.isEmpty(notifyUserMap)) {
			return;
		}

		Set<String> userIds = notifyUserMap.keySet();
		for (String userId : userIds) {

			// 根据签到/签退状态选择发送内容
			String content = StringUtils.equalsIgnoreCase(notifyUserMap.get(userId), "sign") ? content1 : content2;

			try {
				remoteEMBServiceInvoker.sendSms(userId, SMS_SENDERID, content, 4, MODULENAME_RECEIPT, SMS_OBJECTID);
			} catch (Exception e) {
				logger.error("doPostEpcRecord sendSms error:", e);
			}

			// 发送微信提醒
			try {
				remoteEMBServiceInvoker.sendMobileNotify(MODULENAME_RECEIPT, userId, content);
			} catch (Exception e) {
				logger.error("doPostEpcRecord sendMobileNotify error:", e);
			}
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

}
