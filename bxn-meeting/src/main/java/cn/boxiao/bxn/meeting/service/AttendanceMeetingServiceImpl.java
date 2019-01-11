package cn.boxiao.bxn.meeting.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.boxiao.bxi.common.client.model.DeviceVo;
import cn.boxiao.bxi.common.client.service.DeviceMgrConnectService;
import cn.boxiao.bxi.common.client.service.DeviceMonitingConnectService;
import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.PageImpl;
import cn.boxiao.bxn.base.model.PageRequest;
import cn.boxiao.bxn.base.model.Pageable;
import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserDao;
import cn.boxiao.bxn.meeting.dao.PlaceDao;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceOrganizerDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.data.PlaceDo;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.TextUtils;
import cn.boxiao.bxn.meeting.util.ToolUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Service
public class AttendanceMeetingServiceImpl implements AttendanceMeetingService, MeetingConstants {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceMeetingDao attendanceMeetingDao;

	@Autowired
	private AttendanceUserDao attendanceUserDao;

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private DeviceMonitingConnectService deviceMonitingConnectService;

	@Autowired
	private DeviceMgrConnectService deviceMgrConnectService;

	@Autowired
	private PlaceDao placeDao;

	@Autowired
	private MeetingCache meetingCache;

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#getAttendanceMeetingsByTime(java.lang.String)
	 */
	@Override
	public List<AttendanceMeetingVo> getAttendanceMeetingsByTime(String queryTime) {

		List<AttendanceMeetingVo> attendanceMeetingVoList = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(queryTime);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {

			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			// 获取会议室名称
			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
				attendanceMeetingVo.setPlaceNo(placeDo.getPlaceNo());
			} else {
				attendanceMeetingVo.setPlaceName("[已删除]");
				attendanceMeetingVo.setPlaceNo("[已删除]");
			}

			attendanceMeetingVo.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			attendanceMeetingVo
					.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));

			attendanceMeetingVoList.add(attendanceMeetingVo);
		}
		return attendanceMeetingVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#getAttendanceMeetings(cn.boxiao.bxn.base.client.rest.security.LoggedUser,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceMeetingVo> getAttendanceMeetings(LoggedUser loggedUser, String beginTime, String endTime) {

		List<AttendanceMeetingDo> meetingDoList = attendanceMeetingDao.queryAttendanceMeetingsByTime("", beginTime,
				endTime, false);

		List<AttendanceMeetingVo> meetingVoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingDoList)) {
			return meetingVoList;
		}

		if (!loggedUser.hasAnyAuthorities("PERM_MEETING_ADMIN")) {
			Map<String, AttendanceMeetingDo> attendanceMeetingMap = Maps.newHashMap();
			List<String> meetingIdList = Lists.newArrayList();
			for (AttendanceMeetingDo attendanceMeetingDo : meetingDoList) {
				meetingIdList.add(attendanceMeetingDo.getId());
				attendanceMeetingMap.put(attendanceMeetingDo.getId(), attendanceMeetingDo);
			}

			// 根据会议列表获取人员考勤列表，然后筛选实际参加的会议
			List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao
					.queryMeetingUsersByUserId(String.valueOf(loggedUser.getId()), meetingIdList);
			meetingDoList.clear();
			for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
				if (attendanceMeetingMap.containsKey(attendanceUserDo.getMeetingId())) {
					meetingDoList.add(attendanceMeetingMap.get(attendanceUserDo.getMeetingId()));
					attendanceMeetingMap.remove(attendanceUserDo.getMeetingId());
				}
			}

			// 如果有组织者权限，需要查询组织者组织的会议
			if (loggedUser.hasAnyAuthorities("PERM_MEETING_OWNER")) {
				List<AttendanceOrganizerDo> attendanceOrganizerDoList = attendanceMeetingDao
						.queryAttendanceOrganizersByUserId(String.valueOf(loggedUser.getId()));
				for (AttendanceOrganizerDo attendanceOrganizerDo : attendanceOrganizerDoList) {
					AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingMap
							.get(attendanceOrganizerDo.getMeetingId());
					if (attendanceMeetingDo != null) {
						meetingDoList.add(attendanceMeetingDo);
					}
				}
			}
		}

		for (AttendanceMeetingDo attendanceMeetingDo : meetingDoList) {
			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			// 参会人数
			int userNum = attendanceUserDao.countAttendanceUserByMeetingId(attendanceMeetingDo.getId());
			attendanceMeetingVo.setUserNum(userNum);

			// 获取会议室地点
			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
			} else {
				attendanceMeetingVo.setPlaceName("[已删除]");
			}

			// 查询会议组织者
			String organizerUsers = getMeetingOrganizerUsers(attendanceMeetingDo.getId());
			attendanceMeetingVo.setOrganizerIds(organizerUsers);

			// 会议时间判断
			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			if (attendanceMeetingDo.getCreateTime() != null) {
				attendanceMeetingVo
						.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
			}

			meetingVoList.add(attendanceMeetingVo);
		}

		Collections.sort(meetingVoList, new Comparator<AttendanceMeetingVo>() {
			@Override
			public int compare(AttendanceMeetingVo o1, AttendanceMeetingVo o2) {
				return TextUtils.compare(o1.getBeginTime(), o2.getBeginTime());
			}

		});

		return meetingVoList;

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#getAttendanceMeetings(cn.boxiao.bxn.base.client.rest.security.LoggedUser,
	 *      cn.boxiao.bxn.base.model.Pageable)
	 */
	@Override
	public Page<AttendanceMeetingVo> getAttendanceMeetings(LoggedUser loggedUser, Pageable page) {

		List<AttendanceMeetingVo> attendanceMeetingVoList = Lists.newArrayList();

		Page<AttendanceMeetingDo> attendanceMeetingDoPage;
		if (loggedUser.hasAnyAuthorities("PERM_MEETING_ADMIN")) {

			attendanceMeetingDoPage = attendanceMeetingDao.queryAttendanceMeetings("", null, page);
		} else {
			List<String> meetingIdList = Lists.newArrayList();
			List<AttendanceOrganizerDo> attendanceOrganizerDoList = attendanceMeetingDao
					.queryAttendanceOrganizersByUserId(String.valueOf(loggedUser.getId()));
			for (AttendanceOrganizerDo attendanceOrganizerDo : attendanceOrganizerDoList) {
				meetingIdList.add(attendanceOrganizerDo.getMeetingId());
			}
			if (CollectionUtils.isEmpty(meetingIdList)) {
				return new PageImpl<>(attendanceMeetingVoList, page, 0);
			}
			attendanceMeetingDoPage = attendanceMeetingDao.queryAttendanceMeetings(String.valueOf(loggedUser.getId()),
					meetingIdList, page);
		}

		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoPage) {

			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			// 获取会议室名称
			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
			} else {
				attendanceMeetingVo.setPlaceName("[已删除]");
			}

			// 获取参会人数
			int userNum = attendanceUserDao.countAttendanceUserByMeetingId(attendanceMeetingDo.getId());
			attendanceMeetingVo.setUserNum(userNum);

			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			attendanceMeetingVo
					.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));

			attendanceMeetingVoList.add(attendanceMeetingVo);
		}

		Page<AttendanceMeetingVo> pageAttendanceMeetingVo = new PageImpl<>(attendanceMeetingVoList, page,
				attendanceMeetingDoPage.getTotalElements());

		return pageAttendanceMeetingVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#getTodayMeeting()
	 */
	@Override
	public List<AttendanceMeetingVo> getTodayMeeting() {
		List<AttendanceMeetingDo> meetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(DateUtil.toString_YYYY_MM_DD(new Date()));

		List<AttendanceMeetingVo> meetingVoList = Lists.newArrayList();
		for (AttendanceMeetingDo attendanceMeetingDo : meetingDoList) {
			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			if (attendanceMeetingDo.getCreateTime() != null) {
				attendanceMeetingVo
						.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
			}

			meetingVoList.add(attendanceMeetingVo);
		}

		return meetingVoList;

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#getLastCyclingMeeting(java.util.Date)
	 */
	@Override
	public List<AttendanceMeetingVo> getLastCyclingMeeting(Date today) {
		List<AttendanceMeetingDo> meetingDoList = attendanceMeetingDao.queryLastCyclingAttendanceMeeting(today);

		List<AttendanceMeetingVo> meetingVoList = Lists.newArrayList();
		for (AttendanceMeetingDo attendanceMeetingDo : meetingDoList) {
			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			if (attendanceMeetingDo.getCreateTime() != null) {
				attendanceMeetingVo
						.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
			}

			meetingVoList.add(attendanceMeetingVo);
		}

		return meetingVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#getAttendanceMeeting(java.lang.String)
	 */
	@Override
	public AttendanceMeetingVo getAttendanceMeeting(String meetingId) {

		AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
		//判断是否为null
		if(ToolUtil.isEmpty(attendanceMeetingDo)){
			return attendanceMeetingVo;
		}
		
		BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

		if (attendanceMeetingDo.getBeginTime() != null) {
			attendanceMeetingVo.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
		}
		if (attendanceMeetingDo.getEndTime() != null) {
			attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
		}
		if (attendanceMeetingDo.getCreateTime() != null) {
			attendanceMeetingVo
					.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
		}

		Long normalSignLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime()))
				- meetingCache.getNormalSignSecond();
		Long normalLogoutLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getEndTime()))
				+ meetingCache.getNormalLogoutSecond();
		attendanceMeetingVo.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(DateUtil.parseLong(normalSignLong)));
		attendanceMeetingVo.setLogoutTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(DateUtil.parseLong(normalLogoutLong)));

		// 查询会议地点
		PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
		if (StringUtils.isNotBlank(placeDo.getId())) {
			attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
		} else {
			attendanceMeetingVo.setPlaceName("[已删除]");
		}

		// 获取参会人数
		int userNum = attendanceUserDao.countAttendanceUserByMeetingId(attendanceMeetingDo.getId());
		attendanceMeetingVo.setUserNum(userNum);

		// 查询创建人
		String creatorId = attendanceMeetingDo.getCreatorId();
		if (StringUtils.isNotBlank(creatorId)) {
			UserVo userVo = userService.getUserById(Long.valueOf(creatorId));
			if (userVo != null) {
				attendanceMeetingVo.setCreatorName(userVo.getFullName());
			}
		}

		// 获取会议的组织人信息
		List<String> organizerIdList = Lists.newArrayList();
		List<Long> organizerIdLongList = Lists.newArrayList();
		List<String> organizerNameList = Lists.newArrayList();
		List<AttendanceOrganizerDo> attendanceOrganizerList = attendanceMeetingDao.queryMeetingOrganizers(meetingId);
		for (AttendanceOrganizerDo organizerDo : attendanceOrganizerList) {
			organizerIdList.add(organizerDo.getUserId());
			organizerIdLongList.add(Long.parseLong(organizerDo.getUserId()));
		}
		if (CollectionUtils.isNotEmpty(organizerIdLongList)) {
			List<UserVo> organizerList = userService.getUsersByIds(organizerIdLongList);
			if (CollectionUtils.isNotEmpty(organizerList)) {
				Map<String, UserVo> userVoMap = Maps.newHashMap();
				for (UserVo organizer : organizerList) {
					userVoMap.put(organizer.getId().toString(), organizer);
				}
				for (AttendanceOrganizerDo organizerDo : attendanceOrganizerList) {
					UserVo userVo = userVoMap.get(organizerDo.getUserId());
					if (userVo != null) {
						organizerNameList.add(userVo.getFullName());
					}
				}
			}
		}
		attendanceMeetingVo.setOrganizerIds(CsvUtils.convertCollectionToCSV(organizerIdList));
		attendanceMeetingVo.setOrganizerNames(CsvUtils.convertCollectionToCSV(organizerNameList));

		// 获取会议的参会人
		List<String> userIds = Lists.newArrayList();
		List<Long> userIdLongList = Lists.newArrayList();
		List<String> userNames = Lists.newArrayList();
		List<String> uniqueNos = Lists.newArrayList();
		List<AttendanceUserDo> attendanceUserList = attendanceUserDao.queryMeetingUsers(meetingId);
		for (AttendanceUserDo attendanceUserDo : attendanceUserList) {
			userIdLongList.add(Long.parseLong(attendanceUserDo.getUserId()));
		}
		if (CollectionUtils.isNotEmpty(userIdLongList)) {
			List<UserVo> userVoList = userService.getUsersByIds(userIdLongList);
			if (CollectionUtils.isNotEmpty(userVoList)) {
				Map<String, UserVo> userVoMap = Maps.newHashMap();
				for (UserVo userVo : userVoList) {
					userVoMap.put(userVo.getId().toString(), userVo);
				}
				for (AttendanceUserDo attendanceUserDo : attendanceUserList) {
					UserVo userVo = userVoMap.get(attendanceUserDo.getUserId());
					if (userVo != null) {
						userIds.add(Long.toString(userVo.getId()));
						userNames.add(userVo.getFullName());
						uniqueNos.add(userVo.getUniqueNo());
					}
				}
			}
		}

		attendanceMeetingVo.setUserIds(CsvUtils.convertCollectionToCSV(userIds));
		attendanceMeetingVo.setUserNames(CsvUtils.convertCollectionToCSV(userNames));
		attendanceMeetingVo.setUniqueNos(CsvUtils.convertCollectionToCSV(uniqueNos));
		return attendanceMeetingVo;

	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#getAttendanceMeetingProfile(java.lang.String)
	 */
	@Override
	public AttendanceMeetingVo getAttendanceMeetingProfile(String meetingId) {

		AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
		BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

		if (attendanceMeetingDo.getBeginTime() != null) {
			attendanceMeetingVo.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
		}
		if (attendanceMeetingDo.getEndTime() != null) {
			attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
		}
		if (attendanceMeetingDo.getCreateTime() != null) {
			attendanceMeetingVo
					.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
		}

		Long normalSignLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime()))
				- meetingCache.getNormalSignSecond();
		Long normalLogoutLong = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getEndTime()))
				+ meetingCache.getNormalLogoutSecond();
		attendanceMeetingVo.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(DateUtil.parseLong(normalSignLong)));
		attendanceMeetingVo.setLogoutTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(DateUtil.parseLong(normalLogoutLong)));

		// 查询会议地点
		PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
		if (StringUtils.isNotBlank(placeDo.getId())) {
			attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
		} else {
			attendanceMeetingVo.setPlaceName("[已删除]");
		}

		return attendanceMeetingVo;

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#createAttendanceMeeting(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo)
	 */
	@Override
	public AttendanceMeetingVo createAttendanceMeeting(AttendanceMeetingVo attendanceMeetingVo) {

		// 创建会议室基本信息
		AttendanceMeetingDo attendanceMeetingDo = new AttendanceMeetingDo();
		BeanUtils.copyProperties(attendanceMeetingVo, attendanceMeetingDo);
		attendanceMeetingDo.setBeginTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime()));
		attendanceMeetingDo.setEndTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getEndTime()));
		attendanceMeetingDo = attendanceMeetingDao.createAttendanceMeeting(attendanceMeetingDo);

		List<Long> userIdLongList = Lists.newArrayList();
		// 创建会议与会人员关系
		List<String> userIds = CsvUtils.convertCSVToList(attendanceMeetingVo.getUserIds());
		for (String userId : userIds) {
			userIdLongList.add(Long.parseLong(userId));
		}
		List<UserVo> userVoList = userService.getUsersByIds(userIdLongList);
		Map<String, UserVo> userVoMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(userVoList)) {
			for (UserVo userVo : userVoList) {
				userVoMap.put(userVo.getId().toString(), userVo);
			}
		}

		for (String userId : userIds) {

			AttendanceUserDo attendanceUserDo = new AttendanceUserDo();
			attendanceUserDo.setUserId(userId);
			attendanceUserDo.setMeetingId(attendanceMeetingDo.getId());
			attendanceUserDo
					.setUserName((userVoMap.get(userId) == null ? new UserVo() : userVoMap.get(userId)).getUserName());

			attendanceUserDao.createAttendanceUser(attendanceUserDo);
		}

		// 创建会议组织人员关系
		List<String> organizerIds = CsvUtils.convertCSVToList(attendanceMeetingVo.getOrganizerIds());
		for (String organizerId : organizerIds) {

			AttendanceOrganizerDo attendanceOrganizerDo = new AttendanceOrganizerDo();
			attendanceOrganizerDo.setUserId(organizerId);
			attendanceOrganizerDo.setMeetingId(attendanceMeetingDo.getId());

			attendanceMeetingDao.createMeetingOrganizers(attendanceOrganizerDo);
		}

		attendanceMeetingVo.setId(attendanceMeetingDo.getId());
		attendanceMeetingVo.setCreateTime(DateUtil.toString_YYYY_MM_DD(attendanceMeetingDo.getCreateTime()));

		// 刷新会议列表
		meetingCache.refreshMeeting();

		// 根据会议地点获取设备ID
		String placeNo = placeService.queryPlace(attendanceMeetingDo.getPlaceId()).getPlaceNo();
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

			dataMap.put("meetingId", attendanceMeetingDo.getId());
			deviceMonitingConnectService.sendCmd(MEET_MEETCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
			deviceMonitingConnectService.sendCmd(MEET_MEMBERCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
		} catch (Exception e) {
			logger.error("createAttendanceMeeting sendCmd error:", e);
		}

		return attendanceMeetingVo;
	}

	/**
	 * cn.boxiao.bxn.meeting.service.AttendanceUserService#copyAttendanceMeeting
	 * (java.lang.String,java.lang.String)
	 */
	@Override
	public int copyAttendanceMeeting(String meetingId, String meetingDay) {

		AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		if (StringUtils.isNotBlank(meetingDay)) {
			String beginHms = DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()).split(" ")[1];
			String beginTime = meetingDay + " " + beginHms;
			attendanceMeetingDo.setBeginTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(beginTime));

			String endHms = DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()).split(" ")[1];
			String endTime = meetingDay + " " + endHms;
			attendanceMeetingDo.setEndTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(endTime));
		}

		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);
		List<AttendanceOrganizerDo> attendanceOrganizerDoList = attendanceMeetingDao.queryMeetingOrganizers(meetingId);
		attendanceMeetingDo = attendanceMeetingDao.createAttendanceMeeting(attendanceMeetingDo);
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {

			attendanceUserDo.setMeetingId(attendanceMeetingDo.getId());
			attendanceUserDo.setStatus(MEETING_USER_STATUS_INIT);
			attendanceUserDo.setModifyStatus("");
			attendanceUserDo.setModifySignTime(null);
			attendanceUserDo.setModifyLogoutTime(null);
			attendanceUserDo.setDescription("");
			attendanceUserDo.setSignTime(null);
			attendanceUserDo.setLogoutTime(null);
			attendanceUserDao.createAttendanceUser(attendanceUserDo);
		}
		for (AttendanceOrganizerDo attendanceOrganizerDo : attendanceOrganizerDoList) {

			attendanceOrganizerDo.setMeetingId(attendanceMeetingDo.getId());
			attendanceMeetingDao.createMeetingOrganizers(attendanceOrganizerDo);
		}

		meetingCache.refreshMeeting();

		// 根据会议地点获取设备ID
		String placeNo = placeService.queryPlace(attendanceMeetingDo.getPlaceId()).getPlaceNo();
		if (StringUtils.isBlank(placeNo)) {
			return 0;
		}
		List<DeviceVo> deviceVoList = deviceMgrConnectService.getAllAvaliableDevice();
		List<String> deviceIdList = Lists.newArrayList();
		for (DeviceVo deviceVo : deviceVoList) {
			if (StringUtils.equals(placeNo, deviceVo.getPlaceId())) {
				deviceIdList.add(deviceVo.getDeviceId());
			}
		}

		// 修改某个用户的考勤状态后，需要向班牌推送消息
		Map<String, String> dataMap = Maps.newHashMap();

		dataMap.put("meetingId", attendanceMeetingDo.getId());
		deviceMonitingConnectService.sendCmd(MEET_MEETCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
		deviceMonitingConnectService.sendCmd(MEET_MEMBERCHANGE, deviceIdList.toArray(new String[] {}), dataMap);

		return 0;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceUserService#updateAttendanceMeeting(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo)
	 */
	@Override
	public int updateAttendanceMeeting(AttendanceMeetingVo attendanceMeetingVo) {

		// 更新会议室基本信息
		AttendanceMeetingDo attendanceMeetingDo = new AttendanceMeetingDo();
		BeanUtils.copyProperties(attendanceMeetingVo, attendanceMeetingDo);
		attendanceMeetingDo.setBeginTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime()));
		attendanceMeetingDo.setEndTime(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getEndTime()));
		attendanceMeetingDao.updateAttendanceMeeting(attendanceMeetingDo);

		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(attendanceMeetingVo.getId());
		Set<String> oldUserIdSet = Sets.newHashSet();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			oldUserIdSet.add(attendanceUserDo.getUserId());
		}
		List<String> currUserIdList = CsvUtils.convertCSVToList(attendanceMeetingVo.getUserIds());
		Set<String> currUserIdSet = Sets.newHashSet();
		for (String userId : currUserIdList) {
			currUserIdSet.add(userId);
		}
		Set<String> toAddCurrUserIdSet = Sets.newHashSet();
		toAddCurrUserIdSet.addAll(currUserIdSet);
		toAddCurrUserIdSet.removeAll(oldUserIdSet);
		if (CollectionUtils.isNotEmpty(toAddCurrUserIdSet)) {
			for (String userId : toAddCurrUserIdSet) {
				UserVo userVo = userService.getUserById(Long.parseLong(userId));
				AttendanceUserDo attendanceUserDo = new AttendanceUserDo();
				attendanceUserDo.setUserId(userId);
				if (userVo != null) {
					attendanceUserDo.setUserName(userVo.getUserName());
				}
				attendanceUserDo.setMeetingId(attendanceMeetingDo.getId());

				AttendanceUserDo attendanceUser = attendanceUserDao
						.queryAttendanceUserByUserId(attendanceMeetingDo.getId(), userId, null);
				if (StringUtils.isBlank(attendanceUser.getId())) {

					attendanceUserDao.createAttendanceUser(attendanceUserDo);
				} else {
					attendanceUser.setDeleted(DELETED_NO);
					attendanceUserDao.updateAttendanceUser(attendanceUser);
				}
			}
		}
		Set<String> toDeleteOldUserIdSet = Sets.newHashSet();
		toDeleteOldUserIdSet.addAll(oldUserIdSet);
		toDeleteOldUserIdSet.removeAll(currUserIdSet);
		if (CollectionUtils.isNotEmpty(toDeleteOldUserIdSet)) {
			for (String userId : toDeleteOldUserIdSet) {

				attendanceUserDao.deleteAttendanceUserByUserId(attendanceMeetingVo.getId(), userId);
			}
		}

		attendanceMeetingDao.deleteAttendanceOrganizers(attendanceMeetingVo.getId());
		// 创建会议组织人员关系
		List<String> organizerIds = CsvUtils.convertCSVToList(attendanceMeetingVo.getOrganizerIds());
		for (String organizerId : organizerIds) {

			AttendanceOrganizerDo attendanceOrganizerDo = new AttendanceOrganizerDo();
			attendanceOrganizerDo.setUserId(organizerId);
			attendanceOrganizerDo.setMeetingId(attendanceMeetingDo.getId());

			attendanceMeetingDao.createMeetingOrganizers(attendanceOrganizerDo);
		}

		meetingCache.refreshMeeting();

		// 根据会议地点获取设备ID

		String placeNo = placeService.queryPlace(attendanceMeetingDo.getPlaceId()).getPlaceNo();
		if (StringUtils.isBlank(placeNo)) {
			return 0;
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

			dataMap.put("meetingId", attendanceMeetingDo.getId());
			deviceMonitingConnectService.sendCmd(MEET_MEETCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
			if (oldUserIdSet != currUserIdSet) {
				deviceMonitingConnectService.sendCmd(MEET_MEMBERCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
			}
		} catch (Exception e) {
			logger.error("updateAttendanceMeeting sendCmd error:" + e);
		}

		return 0;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceMeetingService#deleteAttendanceMeeting(java.lang.String)
	 */
	@Override
	public int deleteAttendanceMeeting(String meetingId) {

		AttendanceMeetingDo attendanceMeetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		if (StringUtils.isBlank(attendanceMeetingDo.getId())) {
			throw new BXQBusinessRuntimeException(Constants.ERR_COMMON_USERNOTFOUND, "该会议不存在或已经被删除");
		}
		attendanceMeetingDao.deleteAttendanceMeeting(meetingId);
		attendanceMeetingDao.deleteAttendanceOrganizers(meetingId);
		attendanceUserDao.deleteAttendanceUsers(meetingId);

		meetingCache.refreshMeeting();

		// 根据会议地点获取设备ID
		String placeNo = placeService.queryPlace(attendanceMeetingDo.getPlaceId()).getPlaceNo();
		if (StringUtils.isBlank(placeNo)) {
			return 0;
		}
		List<DeviceVo> deviceVoList = deviceMgrConnectService.getAllAvaliableDevice();
		List<String> deviceIdList = Lists.newArrayList();
		for (DeviceVo deviceVo : deviceVoList) {
			if (StringUtils.equals(placeNo, deviceVo.getPlaceId())) {
				deviceIdList.add(deviceVo.getDeviceId());
			}
		}

		// 修改某个用户的考勤状态后，需要向班牌推送消息
		Map<String, String> dataMap = Maps.newHashMap();

		dataMap.put("meetingId", meetingId);
		deviceMonitingConnectService.sendCmd(MEET_MEETCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
		deviceMonitingConnectService.sendCmd(MEET_MEMBERCHANGE, deviceIdList.toArray(new String[] {}), dataMap);
		return 0;
	}

	/**
	 * 查询会议组织者
	 * 
	 * @param meetingId
	 * @return
	 */
	private String getMeetingOrganizerUsers(String meetingId) {
		// 查询会议组织者
		List<AttendanceOrganizerDo> attendanceOrganizerDoList = attendanceMeetingDao.queryMeetingOrganizers(meetingId);
		List<String> userIdList = Lists.newArrayList();
		for (AttendanceOrganizerDo attendanceOrganizerDo : attendanceOrganizerDoList) {
			userIdList.add(attendanceOrganizerDo.getUserId());
		}

		return CsvUtils.convertCollectionToCSV(userIdList);
	}

	@Override
	public List<AttendanceMeetingVo> getAttendanceMeetings(String beginTime, String endTime) {
		List<AttendanceMeetingDo> meetingDoList = attendanceMeetingDao.queryAttendanceMeetingsByTime("", beginTime,
				endTime, false);

		List<AttendanceMeetingVo> meetingVoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingDoList)) {
			return meetingVoList;
		}

		for (AttendanceMeetingDo attendanceMeetingDo : meetingDoList) {
			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			// 参会人数
			int userNum = attendanceUserDao.countAttendanceUserByMeetingId(attendanceMeetingDo.getId());
			attendanceMeetingVo.setUserNum(userNum);

			// 获取会议室地点
			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
			} else {
				attendanceMeetingVo.setPlaceName("[已删除]");
			}

			// 查询会议组织者
			String organizerUsers = getMeetingOrganizerUsers(attendanceMeetingDo.getId());
			attendanceMeetingVo.setOrganizerIds(organizerUsers);

			// 会议时间判断
			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			if (attendanceMeetingDo.getCreateTime() != null) {
				attendanceMeetingVo
						.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));
			}

			meetingVoList.add(attendanceMeetingVo);
		}

		Collections.sort(meetingVoList, new Comparator<AttendanceMeetingVo>() {
			@Override
			public int compare(AttendanceMeetingVo o1, AttendanceMeetingVo o2) {
				return TextUtils.compare(o1.getBeginTime(), o2.getBeginTime());
			}

		});

		return meetingVoList;
	}

	
	
	/**
	 * 获取接口会议考勤列表
	 */
	@Override
	public Page<AttendanceMeetingVo> getAttendanceMeetingsApi(PageRequest page) {
		
		List<AttendanceMeetingVo> attendanceMeetingVoList = Lists.newArrayList();

		Page<AttendanceMeetingDo> attendanceMeetingDoPage=attendanceMeetingDao.queryAttendanceMeetings("", null, page);
		
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoPage) {

			AttendanceMeetingVo attendanceMeetingVo = new AttendanceMeetingVo();
			BeanUtils.copyProperties(attendanceMeetingDo, attendanceMeetingVo);

			// 获取会议室名称
			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceMeetingVo.setPlaceName(placeDo.getPlaceName());
			} else {
				attendanceMeetingVo.setPlaceName("[已删除]");
			}

			// 获取参会人数
			int userNum = attendanceUserDao.countAttendanceUserByMeetingId(attendanceMeetingDo.getId());
			attendanceMeetingVo.setUserNum(userNum);

			if (attendanceMeetingDo.getBeginTime() != null) {
				attendanceMeetingVo
						.setBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			}
			if (attendanceMeetingDo.getEndTime() != null) {
				attendanceMeetingVo.setEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			}
			attendanceMeetingVo
					.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getCreateTime()));

			attendanceMeetingVoList.add(attendanceMeetingVo);
		}

		Page<AttendanceMeetingVo> pageAttendanceMeetingVo = new PageImpl<>(attendanceMeetingVoList, page,
				attendanceMeetingDoPage.getTotalElements());

		return pageAttendanceMeetingVo;
	}

}
