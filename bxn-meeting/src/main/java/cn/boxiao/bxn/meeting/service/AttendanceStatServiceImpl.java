package cn.boxiao.bxn.meeting.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserStatDao;
import cn.boxiao.bxn.meeting.dao.MeetingGroupDao;
import cn.boxiao.bxn.meeting.dao.MeetingTypeDao;
import cn.boxiao.bxn.meeting.dao.PlaceDao;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserStatDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupUserDo;
import cn.boxiao.bxn.meeting.data.MeetingTypeDo;
import cn.boxiao.bxn.meeting.data.PlaceDo;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.TextUtils;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.OrganizationVo;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Service
public class AttendanceStatServiceImpl implements AttendanceStatService, MeetingConstants {

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private AttendanceMeetingDao attendanceMeetingDao;

	@Autowired
	private AttendanceUserDao attendanceUserDao;

	@Autowired
	private AttendanceUserStatDao attendanceUserStatDao;

	@Autowired
	private PlaceDao placeDao;

	@Autowired
	private MeetingTypeDao meetingTypeDao;

	@Autowired
	private MeetingGroupDao meetingGroupDao;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceStatService#getAttendanceStat(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceUserStatVo> getAttendanceStat(String meetingTypeId, String beginDate, String endDate) {

		List<AttendanceUserStatVo> attendanceUserStatVoList = Lists.newArrayList();

		// 是否使用会议考勤部门
		boolean isMeetingGroup = false;

		// 根据会议分类查询会议ID，如果没有会议则返回
		List<String> meetingIds = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
		}
		if (CollectionUtils.isEmpty(meetingIds)) {
			return attendanceUserStatVoList;
		}

		// 根据会议ID查询用户考勤记录，如果没有考勤记录则返回
		List<AttendanceUserStatDo> attendanceUserStatDoList = attendanceUserStatDao.queryAttendanceUserStat(meetingIds);
		if (CollectionUtils.isEmpty(attendanceUserStatDoList)) {
			return attendanceUserStatVoList;
		}

		// 遍历用户考勤记录取用户信息
		List<Long> userIdList = Lists.newArrayList();
		for (AttendanceUserStatDo attendanceUserStatDo : attendanceUserStatDoList) {
			if (StringUtils.isNotBlank(attendanceUserStatDo.getUserId())) {
				userIdList.add(Long.valueOf(attendanceUserStatDo.getUserId()));
			}
		}
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdList);

		// 用户-部门名称map,如果会议类型不为空，并且会议类型关联的部门分类不为空，取此分类下部门作为展示
		Map<String, String> meetingGroupUserMap = Maps.newHashMap();
		if (StringUtils.isNotBlank(meetingTypeId)) {
			MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingTypeId);
			if (StringUtils.isNotBlank(meetingTypeDo.getGroupId())) {
				// 使用考勤部门进行统计
				isMeetingGroup = true;

				// 查部门
				List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao
						.queryMeetingGroupsByParent(meetingTypeDo.getGroupId());

				// 遍历部门集合，查找部门内用户
				for (MeetingGroupDo meetingGroupDo : meetingGroupDoList) {

					// 取部门下的用户
					List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao
							.queryMeetingGroupUsers(meetingGroupDo.getId());
					for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
						meetingGroupUserMap.put(meetingGroupUserDo.getUserId(), meetingGroupDo.getGroupName());
					}
				}
			}
		}

		// 遍历用户考勤数据
		Map<String, Integer> orgUserNumMap = Maps.newHashMap();
		for (AttendanceUserStatDo attendanceUserStatDo : attendanceUserStatDoList) {
			// 获取教工号/学号、全名
			UserVo userVo = userVoMap.get(attendanceUserStatDo.getUserId());
			if (userVo == null) {
				continue;
			}

			AttendanceUserStatVo attendanceUserStatVo = new AttendanceUserStatVo();
			BeanUtils.copyProperties(attendanceUserStatDo, attendanceUserStatVo);
			attendanceUserStatVo.setUniqueNo(userVo.getUniqueNo());
			attendanceUserStatVo.setFullName(userVo.getFullName());
			attendanceUserStatVo.setCategory(userVo.getCategory());
			attendanceUserStatVo.copyNumsFromDo(attendanceUserStatDo);

			// 部门名称赋值
			if (isMeetingGroup) {
				String groupName = MapUtils.getString(meetingGroupUserMap, attendanceUserStatVo.getUserId(), "其他");
				attendanceUserStatVo.setOrganizationName(groupName);
			} else {
				// 如果不使用会议考勤部门，取用户本身的所属部门
				List<OrganizationVo> organizationVoList = userVo.getOrganizations();
				for (OrganizationVo organizationVo : organizationVoList) {
					// 排除是班级的组
					if (organizationVo.getCategory() == OrganizationVo.ORG_CATEGORY_DPT_VALUE) {
						attendanceUserStatVo.setOrganizationName(organizationVo.getName());
						break;
					}
				}
			}

			// 计算各部门人数
			int orgUserNum = MapUtils.getInteger(orgUserNumMap, attendanceUserStatVo.getOrganizationName(), 0) + 1;
			orgUserNumMap.put(attendanceUserStatVo.getOrganizationName(), orgUserNum);

			attendanceUserStatVoList.add(attendanceUserStatVo);
		}

		// 按部门-用户no进行排序
		Collections.sort(attendanceUserStatVoList, new Comparator<AttendanceUserStatVo>() {
			@Override
			public int compare(AttendanceUserStatVo o1, AttendanceUserStatVo o2) {
				if (StringUtils.equals(o1.getOrganizationName(), o2.getOrganizationName())) {
					return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
				}
				return TextUtils.compare(o1.getOrganizationName(), o2.getOrganizationName());

			}
		});

		// 部门人数赋值
		for (AttendanceUserStatVo userStat : attendanceUserStatVoList) {
			userStat.setOrgUserNum(MapUtils.getIntValue(orgUserNumMap, userStat.getOrganizationName()));
		}

		return attendanceUserStatVoList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceStatService#getAttendanceStatByUserId(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public AttendanceUserStatVo getAttendanceStatByUserId(String userId, String beginDate, String endDate) {

		List<String> meetingIds = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao.queryAttendanceMeetingsByTime("",
				beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
		}

		AttendanceUserStatDo attendanceUserStatDo = new AttendanceUserStatDo();
		if (CollectionUtils.isNotEmpty(meetingIds)) {
			attendanceUserStatDo = attendanceUserStatDao.queryAttendanceUserStatByUserId(meetingIds, userId);
		}

		AttendanceUserStatVo attendanceUserStatVo = new AttendanceUserStatVo();
		BeanUtils.copyProperties(attendanceUserStatDo, attendanceUserStatVo);
		attendanceUserStatVo.copyNumsFromDo(attendanceUserStatDo);

		UserVo userVo = userService.getUserById(Long.parseLong(userId));
		if (userVo == null) {
			return attendanceUserStatVo;
		}
		attendanceUserStatVo.setUniqueNo(userVo.getUniqueNo());
		attendanceUserStatVo.setFullName(userVo.getFullName());
		attendanceUserStatVo.setCategory(userVo.getCategory());

		return attendanceUserStatVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceStatService#getAttendanceUserDetail(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<AttendanceUserVo> getAttendanceUserDetail(String meetingTypeId, String userId, String beginDate,
			String endDate, boolean fromMobile) {

		List<AttendanceUserVo> attendanceUserVoList = Lists.newArrayList();

		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, fromMobile);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {

			AttendanceUserDo attendanceUserDo = attendanceUserDao
					.queryAttendanceUserByUserId(attendanceMeetingDo.getId(), userId, DELETED_NO);
			if (StringUtils.isBlank(attendanceUserDo.getId())) {
				continue;
			}

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
			UserVo userVo = userService.getUserById(Long.parseLong(userId));
			if (userVo != null) {
				attendanceUserVo.setUniqueNo(userVo.getUniqueNo());
				attendanceUserVo.setFullName(userVo.getFullName());
				attendanceUserVo.setCategory(userVo.getCategory());
			}

			attendanceUserVo.setMeetingName(attendanceMeetingDo.getMeetingName());
			attendanceUserVo
					.setMeetingBeginTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getBeginTime()));
			attendanceUserVo.setMeetingEndTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceMeetingDo.getEndTime()));
			attendanceUserVo.setMeetingStatus(attendanceMeetingDo.getStatus());

			PlaceDo placeDo = placeDao.queryPlace(attendanceMeetingDo.getPlaceId());
			if (StringUtils.isNotBlank(placeDo.getId())) {
				attendanceUserVo.setPlaceName(placeDo.getPlaceName());
			}else{
				attendanceUserVo.setPlaceName("[已删除]");
			}

			attendanceUserVoList.add(attendanceUserVo);
		}

		return attendanceUserVoList;
	}

	/**
	 * 批量获取用户VO
	 * 
	 * @param userIdList
	 * @return
	 */
	private Map<String, UserVo> convertUserVoToMap(List<Long> userIdList) {

		Map<String, UserVo> userMap = Maps.newHashMap();
		if (CollectionUtils.isEmpty(userIdList)) {
			return userMap;
		}
		List<UserVo> userVoList = userService.getUsersByIds(userIdList, true);
		for (UserVo userVo : userVoList) {
			userMap.put(String.valueOf(userVo.getId()), userVo);
		}

		return userMap;
	}

}
