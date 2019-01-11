/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.boxiao.bxn.common.util.DateUtil;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.dao.AttendanceGroupDao;
import cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserDao;
import cn.boxiao.bxn.meeting.dao.AttendanceUserStatDao;
import cn.boxiao.bxn.meeting.dao.MeetingGroupDao;
import cn.boxiao.bxn.meeting.dao.MeetingTypeDao;
import cn.boxiao.bxn.meeting.data.AttendanceGroupDo;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserStatDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupUserDo;
import cn.boxiao.bxn.meeting.data.MeetingTypeDo;
import cn.boxiao.bxn.meeting.util.TextUtils;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月24日
 */
@Service
public class AttendanceGroupServiceImpl implements AttendanceGroupService, MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private AttendanceMeetingDao attendanceMeetingDao;

	@Autowired
	private MeetingTypeDao meetingTypeDao;

	@Autowired
	private MeetingGroupDao meetingGroupDao;

	@Autowired
	private AttendanceGroupDao attendanceGroupDao;

	@Autowired
	private AttendanceUserDao attendanceUserDao;

	@Autowired
	private AttendanceUserStatDao attendanceUserStatDao;

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceGroupStat(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceGroupStatVo> queryAttendanceGroupStat(String meetingTypeId, String beginDate,
			String endDate) {

		List<AttendanceGroupStatVo> groupStatList = Lists.newArrayList();

		// 根据会议类型查询部门，如果会议类型不关联部门分类，则返回
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingTypeId);
		if (StringUtils.isBlank(meetingTypeDo.getGroupId())) {
			return groupStatList;
		}

		// 查询部门信息，转换为以ID为key，部门统计对象为value的map(map保序)
		Map<String, AttendanceGroupStatVo> meetingGroupStatMap = Maps.newLinkedHashMap();
		List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao
				.queryMeetingGroupsByParent(meetingTypeDo.getGroupId());
		for (MeetingGroupDo meetingGroupDo : meetingGroupDoList) {
			AttendanceGroupStatVo groupStatVo = new AttendanceGroupStatVo();
			groupStatVo.setGroupId(meetingGroupDo.getId());
			groupStatVo.setGroupName(meetingGroupDo.getGroupName());

			meetingGroupStatMap.put(meetingGroupDo.getId(), groupStatVo);
		}

		// 根据时间范围查询会议信息
		List<String> meetingIds = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
		}

		// 查询会议考勤部门记录，遍历每次会议的部门统计
		List<AttendanceGroupDo> attendanceGroupDoList = attendanceGroupDao.queryAttendanceGroup(meetingIds);
		for (AttendanceGroupDo attendanceGroupDo : attendanceGroupDoList) {

			AttendanceGroupStatVo groupStatVo = meetingGroupStatMap.get(attendanceGroupDo.getGroupId());

			// 如果统计对象的groupId未找到，则跳过
			if (groupStatVo == null) {
				continue;
			}

			// 参会次数增加
			int needJoinNum = groupStatVo.getNeedJoinNum() + attendanceGroupDo.getNeedJoinNum();
			groupStatVo.setNeedJoinNum(needJoinNum);

			// 全勤次数增加
			int normalNum = groupStatVo.getNormalNum() + attendanceGroupDo.getNormalNum();
			groupStatVo.setNormalNum(normalNum);
		}

		// 计算全勤率
		for (AttendanceGroupStatVo attendanceGroupStat : meetingGroupStatMap.values()) {
			attendanceGroupStat.setNormalRates(TextUtils.computeNormalRates(attendanceGroupStat.getNormalNum(),
					attendanceGroupStat.getNeedJoinNum()));
		}

		return Lists.newArrayList(meetingGroupStatMap.values());
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#computeAttendanceGroupStatSummary(java.util.List)
	 */
	@Override
	public AttendanceGroupStatVo computeAttendanceGroupStatSummary(List<AttendanceGroupStatVo> groupStatVoList) {
		// 计算全勤率
		int normalNumSum = 0;
		int needJoinNum = 0;
		for (AttendanceGroupStatVo attendanceGroupStat : groupStatVoList) {
			normalNumSum += attendanceGroupStat.getNormalNum();
			needJoinNum += attendanceGroupStat.getNeedJoinNum();
		}

		AttendanceGroupStatVo summaryStatVo = new AttendanceGroupStatVo();
		summaryStatVo.setNormalNum(normalNumSum);
		summaryStatVo.setNeedJoinNum(needJoinNum);

		summaryStatVo.setNormalRates(
				TextUtils.computeNormalRates(summaryStatVo.getNormalNum(), summaryStatVo.getNeedJoinNum()));

		return summaryStatVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceGroups(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceGroupVo> queryAttendanceGroups(String meetingTypeId, String meetingGroupId, String beginDate,
			String endDate) {
		List<AttendanceGroupVo> groupList = Lists.newArrayList();

		// 根据会议类型查询部门，如果会议类型不关联部门分类，则返回
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingTypeId);
		if (StringUtils.isBlank(meetingTypeDo.getGroupId())) {
			return groupList;
		}

		// 根据时间范围查询会议信息
		List<String> meetingIds = Lists.newArrayList();
		Map<String, AttendanceMeetingDo> meetingMap = Maps.newHashMap();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
			meetingMap.put(attendanceMeetingDo.getId(), attendanceMeetingDo);
		}

		// 查询会议考勤部门记录，遍历每次会议的部门统计
		List<AttendanceGroupDo> attendanceGroupDoList = attendanceGroupDao.queryAttendanceGroup(meetingIds,
				meetingGroupId);
		for (AttendanceGroupDo attendanceGroupDo : attendanceGroupDoList) {

			AttendanceGroupVo attendanceGroupVo = new AttendanceGroupVo();
			BeanUtils.copyProperties(attendanceGroupDo, attendanceGroupVo);

			// 会议名称和时间
			AttendanceMeetingDo meetingDo = meetingMap.get(attendanceGroupVo.getMeetingId());
			if (meetingDo != null) {
				attendanceGroupVo.setMeetingName(meetingDo.getMeetingName());
				attendanceGroupVo.setMeetingDate(meetingDo.getBeginTime());
			} else {
				attendanceGroupVo.setMeetingName("其他");
				attendanceGroupVo.setMeetingDate(new Date());
			}

			// 计算请假
			int holidayNum = attendanceGroupVo.getHolidayPubNum() + attendanceGroupVo.getHolidayPriNum()
					+ attendanceGroupVo.getHolidaySickNum();
			attendanceGroupVo.setHolidayNum(holidayNum);
			// 实际到会
			int actualNum = attendanceGroupVo.getNeedJoinNum() - attendanceGroupVo.getHolidayNum()
					- attendanceGroupVo.getAbsenceNum() - attendanceGroupVo.getUnPunchNum();
			attendanceGroupVo.setActualNum(actualNum);

			// 计算全勤率
			attendanceGroupVo.setNormalRates(
					TextUtils.computeNormalRates(attendanceGroupVo.getNormalNum(), attendanceGroupVo.getNeedJoinNum()));

			groupList.add(attendanceGroupVo);
		}

		// 根据会议召开时间排序
		Collections.sort(groupList, new Comparator<AttendanceGroupVo>() {

			@Override
			public int compare(AttendanceGroupVo o1, AttendanceGroupVo o2) {
				return o1.getMeetingDate().compareTo(o2.getMeetingDate());
			}

		});
		return groupList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceGroups(java.lang.String)
	 */
	@Override
	public List<AttendanceGroupVo> queryAttendanceGroups(String meetingId) {

		List<AttendanceGroupVo> attendGroupList = Lists.newArrayList();
		
		// 查询该会议的用户考勤
		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);

		// 构造部门考勤
		List<AttendanceGroupVo> attendanceGroupList = buildAttendanceGroupList(meetingId, attendanceUserDoList);
		for (AttendanceGroupVo attendanceGroupVo : attendanceGroupList) {

			// 应到人数为0的不显示
			if(attendanceGroupVo.getNeedJoinNum() == 0){
				continue;
			}
			
			// 计算请假
			int holidayNum = attendanceGroupVo.getHolidayPubNum() + attendanceGroupVo.getHolidayPriNum()
					+ attendanceGroupVo.getHolidaySickNum();
			attendanceGroupVo.setHolidayNum(holidayNum);
			// 实际到会
			int actualNum = attendanceGroupVo.getNeedJoinNum() - attendanceGroupVo.getHolidayNum()
					- attendanceGroupVo.getAbsenceNum() - attendanceGroupVo.getUnPunchNum();
			attendanceGroupVo.setActualNum(actualNum);

			// 计算全勤率
			attendanceGroupVo.setNormalRates(
					TextUtils.computeNormalRates(attendanceGroupVo.getNormalNum(), attendanceGroupVo.getNeedJoinNum()));
			attendGroupList.add(attendanceGroupVo);
		}

		return attendGroupList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceGroup(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AttendanceGroupVo queryAttendanceGroup(String meetingId, String groupId) {
		// 查询该会议的用户考勤
		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(meetingId);

		// 设置会议和部门信息
		AttendanceGroupVo attendanceGroupVo = new AttendanceGroupVo();
		attendanceGroupVo.setMeetingId(meetingId);
		attendanceGroupVo.setGroupId(groupId);

		// 取部门下的用户，以用户ID为key，部门statVo为Value建立Map
		Set<String> userIdSet = Sets.newHashSet();
		List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao.queryMeetingGroupUsers(groupId);
		for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
			userIdSet.add(meetingGroupUserDo.getUserId());
		}

		// 遍历用户考勤信息，进行部门统计
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			if (!userIdSet.contains(attendanceUserDo.getUserId())) {
				continue;
			}

			// 计算部门统计
			computeGroupStat(attendanceUserDo, attendanceGroupVo);
		}

		// 计算请假
		int holidayNum = attendanceGroupVo.getHolidayPubNum() + attendanceGroupVo.getHolidayPriNum()
				+ attendanceGroupVo.getHolidaySickNum();
		attendanceGroupVo.setHolidayNum(holidayNum);

		// 实际到会
		int actualNum = attendanceGroupVo.getNeedJoinNum() - attendanceGroupVo.getHolidayNum()
				- attendanceGroupVo.getAbsenceNum() - attendanceGroupVo.getUnPunchNum();
		attendanceGroupVo.setActualNum(actualNum);

		// 计算全勤率
		attendanceGroupVo.setNormalRates(
				TextUtils.computeNormalRates(attendanceGroupVo.getNormalNum(), attendanceGroupVo.getNeedJoinNum()));
		return attendanceGroupVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#computeAttendanceGroupSummary(java.util.List)
	 */
	@Override
	public AttendanceGroupVo computeAttendanceGroupSummary(List<AttendanceGroupVo> attendanceGroupList) {
		// 计算全勤率
		int needJoinNum = 0;
		int normalNumSum = 0;
		int lateNum = 0;
		int leaveNum = 0;
		int leaveLateNum = 0;
		int unPunchNum = 0;
		int absenceNum = 0;
		int holidayPubNum = 0;
		int holidayPriNum = 0;
		int holidaySickNum = 0;
		int missingNum = 0;
		int holidayNum = 0;
		for (AttendanceGroupVo attendanceGroup : attendanceGroupList) {
			needJoinNum += attendanceGroup.getNeedJoinNum();
			normalNumSum += attendanceGroup.getNormalNum();
			lateNum += attendanceGroup.getLateNum();
			leaveNum += attendanceGroup.getLeaveNum();
			leaveLateNum += attendanceGroup.getLeaveLateNum();
			unPunchNum += attendanceGroup.getUnPunchNum();
			absenceNum += attendanceGroup.getAbsenceNum();
			holidayPubNum += attendanceGroup.getHolidayPubNum();
			holidayPriNum += attendanceGroup.getHolidayPriNum();
			holidaySickNum += attendanceGroup.getHolidaySickNum();
			missingNum += attendanceGroup.getMissingNum();

			holidayNum = holidayNum + attendanceGroup.getHolidayPubNum() + attendanceGroup.getHolidayPriNum()
					+ attendanceGroup.getHolidaySickNum();
		}

		AttendanceGroupVo summaryVo = new AttendanceGroupVo();
		summaryVo.setNeedJoinNum(needJoinNum);
		summaryVo.setNormalNum(normalNumSum);
		summaryVo.setLateNum(lateNum);
		summaryVo.setLeaveNum(leaveNum);
		summaryVo.setLeaveLateNum(leaveLateNum);
		summaryVo.setUnPunchNum(unPunchNum);
		summaryVo.setAbsenceNum(absenceNum);
		summaryVo.setHolidayPubNum(holidayPubNum);
		summaryVo.setHolidayPriNum(holidayPriNum);
		summaryVo.setHolidaySickNum(holidaySickNum);
		summaryVo.setMissingNum(missingNum);
		summaryVo.setHolidayNum(holidayNum);

		// 实际到会
		int actualNum = summaryVo.getNeedJoinNum() - summaryVo.getHolidayNum() - summaryVo.getAbsenceNum()
				- summaryVo.getUnPunchNum();
		summaryVo.setActualNum(actualNum);

		summaryVo.setNormalRates(TextUtils.computeNormalRates(summaryVo.getNormalNum(), summaryVo.getNeedJoinNum()));

		return summaryVo;
	}

	/**
	 */
	@Override
	public List<AttendanceUserVo> queryAttendanceUsers(AttendanceMeetingVo attendanceMeetingVo, String groupId) {

		List<AttendanceUserVo> attendanceUserVoList = Lists.newArrayList();

		// 查询该会议的用户考勤
		List<AttendanceUserDo> attendanceUserDoList = attendanceUserDao.queryMeetingUsers(attendanceMeetingVo.getId());

		// 取部门下的用户，以用户ID为key，部门statVo为Value建立Map
		Set<String> userInGroupSet = Sets.newHashSet();
		if (!StringUtils.equals(groupId, GROUP_ID_OTHER)) {
			List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao.queryMeetingGroupUsers(groupId);
			for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
				userInGroupSet.add(meetingGroupUserDo.getUserId());
			}
		} else {
			// 如果选择的是其他分组，需要把所有分组的用户查出来
			MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(attendanceMeetingVo.getMeetingTypeId());
			if (StringUtils.isNotBlank(meetingTypeDo.getGroupId())) {
				MeetingGroupDo meetingGroup = meetingGroupDao.queryMeetingGroup(meetingTypeDo.getGroupId());
				List<MeetingGroupDo> meetingGroupList = meetingGroupDao
						.queryMeetingGroupsByParent(meetingGroup.getId());
				for (MeetingGroupDo groupDo : meetingGroupList) {
					List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao
							.queryMeetingGroupUsers(groupDo.getId());
					for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
						userInGroupSet.add(meetingGroupUserDo.getUserId());
					}
				}
			}
		}

		// 根据用户ID查找部门人员的会议考勤
		List<Long> userIdList = Lists.newArrayList();
		Map<String, Integer> statusUserNumMap = Maps.newHashMap();
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {

			// 如果不是其他分组，不是这个部门的人员，跳过
			if (!StringUtils.equals(groupId, GROUP_ID_OTHER)) {
				if (!userInGroupSet.contains(attendanceUserDo.getUserId())) {
					continue;
				}
			} else {
				// 如果是其他分组，是部门的内的用户，跳过
				if (userInGroupSet.contains(attendanceUserDo.getUserId())) {
					continue;
				}
			}

			userIdList.add(Long.valueOf(attendanceUserDo.getUserId()));

			AttendanceUserVo attendanceUserVo = new AttendanceUserVo();
			BeanUtils.copyProperties(attendanceUserDo, attendanceUserVo);
			if (attendanceUserDo.getSignTime() != null) {
				attendanceUserVo.setSignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getSignTime()));
			}
			if (attendanceUserDo.getLogoutTime() != null) {
				attendanceUserVo.setLogoutTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getLogoutTime()));
			}
			if (attendanceUserDo.getModifySignTime() != null) {
				attendanceUserVo
						.setModifySignTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getModifySignTime()));
			}
			if (attendanceUserDo.getModifyLogoutTime() != null) {
				attendanceUserVo.setModifyLogoutTime(
						DateUtil.toString_YYYY_MM_DD_HH_MM_SS(attendanceUserDo.getModifyLogoutTime()));
			}

			// 状态、签到签退时间，如果有手动编辑值则替换
			attendanceUserVo.setStatus(attendanceUserVo.getActualStatus());
			attendanceUserVo.setSignTime(attendanceUserVo.getActualSignTime());
			attendanceUserVo.setLogoutTime(attendanceUserVo.getActualLogoutTime());
			attendanceUserVoList.add(attendanceUserVo);

			// 计算各状态人数
			int statusUserNum = MapUtils.getInteger(statusUserNumMap, attendanceUserVo.getStatus(), 0) + 1;
			statusUserNumMap.put(attendanceUserVo.getStatus(), statusUserNum);
		}

		// 获取用户Map
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdList);

		// 用户同状态人数赋值
		for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
			UserVo userVo = userVoMap.get(attendanceUserVo.getUserId());
			if (userVo != null) {
				attendanceUserVo.setFullName(userVo.getFullName());
				attendanceUserVo.setUniqueNo(userVo.getUniqueNo());
			} else {
				attendanceUserVo.setFullName("");
				attendanceUserVo.setUniqueNo("");
			}

			attendanceUserVo.setStatusUserNum(MapUtils.getIntValue(statusUserNumMap, attendanceUserVo.getStatus(), 1));
		}

		// 排序
		Collections.sort(attendanceUserVoList, new Comparator<AttendanceUserVo>() {

			@Override
			public int compare(AttendanceUserVo o1, AttendanceUserVo o2) {

				// 优先按状态，如果状态相同，看打卡时间
				if (StringUtils.equals(o1.getStatus(), o2.getStatus())) {

					// 其次看签到时间，如果签到时间相同，看签退时间
					if (StringUtils.equals(o1.getSignTime(), o2.getSignTime())) {

						// 最后看用户编号
						if (StringUtils.equals(o1.getLogoutTime(), o2.getLogoutTime())) {

							return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
						}
						return TextUtils.compare(o1.getLogoutTime(), o2.getLogoutTime());
					}
					return TextUtils.compare(o1.getSignTime(), o2.getSignTime());
				}
				return TextUtils.compare(o1.getStatus(), o2.getStatus());
			}

		});

		return attendanceUserVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceNormalUsers(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceUserStatVo> queryAttendanceNormalUsers(String meetingTypeId, String beginDate,
			String endDate) {
		// 根据时间范围查询会议信息
		List<String> meetingIds = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
		}

		// 部门名集合
		Map<String, String> meetingGroupUserMap = Maps.newHashMap();
		// 如果会议类型不为空，并且会议类型关联的部门分类不为空，取此分类下部门作为展示
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingTypeId);
		if (StringUtils.isNotBlank(meetingTypeDo.getGroupId())) {

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

		// 查询用户考勤统计
		List<Long> userIdList = Lists.newArrayList();
		Map<String, Integer> orgUserNumMap = Maps.newHashMap();
		List<AttendanceUserStatVo> normalUserList = Lists.newArrayList();
		List<AttendanceUserStatDo> attendanceUserStatList = attendanceUserStatDao
				.queryAttendanceNormalUserStat(meetingIds);
		for (AttendanceUserStatDo userStatDo : attendanceUserStatList) {
			// 如果应参会数与正常考勤数相等，则为全勤用户
			if (userStatDo.getNeedJoinNum() == userStatDo.getNormalNum()) {
				userIdList.add(Long.valueOf(userStatDo.getUserId()));

				AttendanceUserStatVo userStatVo = new AttendanceUserStatVo();
				userStatVo.setUserId(userStatDo.getUserId());

				// 部门名称赋值
				userStatVo.setOrganizationName(MapUtils.getString(meetingGroupUserMap, userStatVo.getUserId(), "其他"));

				// 计算各部门人数
				int orgUserNum = MapUtils.getInteger(orgUserNumMap, userStatVo.getOrganizationName(), 0) + 1;
				orgUserNumMap.put(userStatVo.getOrganizationName(), orgUserNum);
				normalUserList.add(userStatVo);
			}
		}

		// 获取用户Map
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdList);
		for (AttendanceUserStatVo userStatVo : normalUserList) {

			UserVo userVo = userVoMap.get(userStatVo.getUserId());
			if (userVo != null) {
				userStatVo.setFullName(userVo.getFullName());
				userStatVo.setUniqueNo(userVo.getUniqueNo());
			} else {
				userStatVo.setFullName("");
				userStatVo.setUniqueNo("");
			}
			// 设置组内用户数
			userStatVo.setOrgUserNum(MapUtils.getInteger(orgUserNumMap, userStatVo.getOrganizationName(), 1));
		}

		// 按部门类型-用户编号进行排序
		Collections.sort(normalUserList, new Comparator<AttendanceUserStatVo>() {

			@Override
			public int compare(AttendanceUserStatVo o1, AttendanceUserStatVo o2) {
				if (StringUtils.equals(o1.getOrganizationName(), o2.getOrganizationName())) {
					return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
				}
				return TextUtils.compare(o1.getOrganizationName(), o2.getOrganizationName());

			}
		});

		return normalUserList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#queryAttendanceNormalUsers(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AttendanceUserStatVo> queryAttendanceNormalUsers(String meetingTypeId, String groupId, String beginDate,
			String endDate) {
		List<AttendanceUserStatVo> normalUserList = Lists.newArrayList();

		// 根据会议类型查询部门，如果会议类型不关联部门分类，则返回
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingTypeId);
		if (StringUtils.isBlank(meetingTypeDo.getGroupId())) {
			return normalUserList;
		}

		// 查询部门内用户信息,如果部门内用户为空，则返回
		Set<String> userInGroupIdSet = Sets.newHashSet();
		List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao.queryMeetingGroupUsers(groupId);
		for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
			userInGroupIdSet.add(meetingGroupUserDo.getUserId());
		}
		if (CollectionUtils.isEmpty(userInGroupIdSet)) {
			return normalUserList;
		}

		// 根据时间范围查询会议信息
		List<String> meetingIds = Lists.newArrayList();
		List<AttendanceMeetingDo> attendanceMeetingDoList = attendanceMeetingDao
				.queryAttendanceMeetingsByTime(meetingTypeId, beginDate, endDate, false);
		for (AttendanceMeetingDo attendanceMeetingDo : attendanceMeetingDoList) {
			meetingIds.add(attendanceMeetingDo.getId());
		}
		if (CollectionUtils.isEmpty(meetingIds)) {
			return normalUserList;
		}

		// 查询用户考勤统计
		List<Long> userIdList = Lists.newArrayList();
		List<AttendanceUserStatDo> attendanceUserStatList = attendanceUserStatDao
				.queryAttendanceNormalUserStat(meetingIds);
		if (CollectionUtils.isEmpty(attendanceUserStatList)) {
			return normalUserList;
		}

		// 遍历考勤信息寻找全勤用户（并且是查询部门的用户）
		for (AttendanceUserStatDo userStatDo : attendanceUserStatList) {
			// 如果应参会数与正常考勤数相等，则为全勤用户；且用户ID属于该部门
			if (userStatDo.getNeedJoinNum() == userStatDo.getNormalNum()
					&& userInGroupIdSet.contains(userStatDo.getUserId())) {
				userIdList.add(Long.valueOf(userStatDo.getUserId()));

				AttendanceUserStatVo userStatVo = new AttendanceUserStatVo();
				userStatVo.setUserId(userStatDo.getUserId());

				normalUserList.add(userStatVo);
			}
		}

		// 获取用户Map
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdList);
		for (AttendanceUserStatVo userStatVo : normalUserList) {

			UserVo userVo = userVoMap.get(userStatVo.getUserId());
			if (userVo != null) {
				userStatVo.setFullName(userVo.getFullName());
				userStatVo.setUniqueNo(userVo.getUniqueNo());
			} else {
				userStatVo.setFullName("");
				userStatVo.setUniqueNo("");
			}
		}

		// 根据用户编号排序
		Collections.sort(normalUserList, new Comparator<AttendanceUserStatVo>() {
			@Override
			public int compare(AttendanceUserStatVo o1, AttendanceUserStatVo o2) {
				return TextUtils.compare(o1.getUniqueNo(), o2.getUniqueNo());
			}

		});

		return normalUserList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupService#createAttendanceGroup(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void createAttendanceGroup(String meetingId, List<AttendanceUserDo> attendanceUserDoList) {

		// 构造会议统计对象
		List<AttendanceGroupVo> attendanceGroupList = buildAttendanceGroupList(meetingId, attendanceUserDoList);

		// 遍历保存部门统计
		for (AttendanceGroupVo attendanceGroupVo : attendanceGroupList) {

			AttendanceGroupDo attendanceGroupDo = new AttendanceGroupDo();
			BeanUtils.copyProperties(attendanceGroupVo, attendanceGroupDo);

			attendanceGroupDao.createOrUpdateAttendanceGroup(attendanceGroupDo);
		}
	}

	/**
	 * 统计用户考勤，根据用户部门构造部门考勤
	 * 
	 * @param meetingId
	 * @param attendanceUserDoList
	 * @return
	 */
	private List<AttendanceGroupVo> buildAttendanceGroupList(String meetingId,
			List<AttendanceUserDo> attendanceUserDoList) {

		List<AttendanceGroupVo> attendanceGroupVoList = Lists.newArrayList();

		// 查询会议信息，若会议不存在，返回
		AttendanceMeetingDo meetingDo = attendanceMeetingDao.queryAttendanceMeeting(meetingId);
		if (StringUtils.isBlank(meetingDo.getMeetingTypeId())) {
			return attendanceGroupVoList;
		}

		// 查询会议对应的部门分类，若无对应部门分类，则返回
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(meetingDo.getMeetingTypeId());
		if (StringUtils.isBlank(meetingTypeDo.getGroupId())) {
			return attendanceGroupVoList;
		}

		// 查会议部门
		Map<String, AttendanceGroupVo> userGroupStatMap = Maps.newHashMap();
		List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao
				.queryMeetingGroupsByParent(meetingTypeDo.getGroupId());
		for (MeetingGroupDo meetingGroupDo : meetingGroupDoList) {

			// 设置会议和部门信息
			AttendanceGroupVo groupStatVo = new AttendanceGroupVo();
			groupStatVo.setMeetingId(meetingId);
			groupStatVo.setGroupId(meetingGroupDo.getId());
			groupStatVo.setGroupName(meetingGroupDo.getGroupName());

			attendanceGroupVoList.add(groupStatVo);

			// 取部门下的用户，以用户ID为key，部门statVo为Value建立Map
			List<MeetingGroupUserDo> meetingGroupUserDoList = meetingGroupDao
					.queryMeetingGroupUsers(meetingGroupDo.getId());
			for (MeetingGroupUserDo meetingGroupUserDo : meetingGroupUserDoList) {
				userGroupStatMap.put(meetingGroupUserDo.getUserId(), groupStatVo);
			}
		}
		// 处理"其他"部门
		AttendanceGroupVo otherGroupStat = new AttendanceGroupVo();
		otherGroupStat.setMeetingId(meetingId);
		otherGroupStat.setGroupId(GROUP_ID_OTHER);
		otherGroupStat.setGroupName("其他");
		attendanceGroupVoList.add(otherGroupStat);
		userGroupStatMap.put(GROUP_ID_OTHER, otherGroupStat);

		// 遍历用户考勤信息，进行部门统计
		for (AttendanceUserDo attendanceUserDo : attendanceUserDoList) {
			// 取出该用户所属的部门统计对象
			AttendanceGroupVo groupStatVo = userGroupStatMap.get(attendanceUserDo.getUserId());
			if (groupStatVo == null) {
				// 如果该用户部署于任何部门，则取出“其他”部门
				groupStatVo = userGroupStatMap.get(GROUP_ID_OTHER);
			}

			// 计算部门统计
			computeGroupStat(attendanceUserDo, groupStatVo);
		}

		return attendanceGroupVoList;
	}

	/**
	 * 计算部门统计
	 * 
	 * @param attendanceUserDo
	 * @param groupStatVo
	 */
	private void computeGroupStat(AttendanceUserDo attendanceUserDo, AttendanceGroupVo groupStatVo) {
		// 不管是什么状态，该用户都算应到会人员
		int needJoinNum = groupStatVo.getNeedJoinNum() + 1;
		groupStatVo.setNeedJoinNum(needJoinNum);

		// 取出用户考勤状态
		String status = StringUtils.isBlank(attendanceUserDo.getModifyStatus()) ? attendanceUserDo.getStatus()
				: attendanceUserDo.getModifyStatus();
		if (StringUtils.equals(status, MEETING_USER_STATUS_NORMAL)) {
			int normalNum = groupStatVo.getNormalNum() + 1;
			groupStatVo.setNormalNum(normalNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_LATE)) {
			int lateNum = groupStatVo.getLateNum() + 1;
			groupStatVo.setLateNum(lateNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_LEAVE)) {
			int leaveNum = groupStatVo.getLeaveNum() + 1;
			groupStatVo.setLeaveNum(leaveNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_ABSENCE)) {
			int absenceNum = groupStatVo.getAbsenceNum() + 1;
			groupStatVo.setAbsenceNum(absenceNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_MISSING)) {
			int missingNum = groupStatVo.getMissingNum() + 1;
			groupStatVo.setMissingNum(missingNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_ABSENCE_PUBLIC)) {
			int holidayPubNum = groupStatVo.getHolidayPubNum() + 1;
			groupStatVo.setHolidayPubNum(holidayPubNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_ABSENCE_PRIVATE)) {
			int holidayPriNum = groupStatVo.getHolidayPriNum() + 1;
			groupStatVo.setHolidayPriNum(holidayPriNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_ABSENCE_SICK)) {
			int holidaySickNum = groupStatVo.getHolidaySickNum() + 1;
			groupStatVo.setHolidaySickNum(holidaySickNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_LATE_LEAVE)) {
			int leaveLateNum = groupStatVo.getLeaveLateNum() + 1;
			groupStatVo.setLeaveLateNum(leaveLateNum);

		} else if (StringUtils.equals(status, MEETING_USER_STATUS_INIT)) {
			int unPunchNum = groupStatVo.getUnPunchNum() + 1;
			groupStatVo.setUnPunchNum(unPunchNum);

		} else {
			logger.warn("无效的用户考勤状态，会议id={},用户id={}，考勤状态={}, 部门考勤={}", attendanceUserDo.getMeetingId(),
					attendanceUserDo.getUserId(), status, groupStatVo);
		}
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
