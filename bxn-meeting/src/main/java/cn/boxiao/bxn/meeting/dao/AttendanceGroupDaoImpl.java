/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.AttendanceGroupDo;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月24日
 */
@Component
public class AttendanceGroupDaoImpl implements AttendanceGroupDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceGroupDao#queryAttendanceGroup(java.util.List)
	 */
	@Override
	public List<AttendanceGroupDo> queryAttendanceGroup(List<String> meetingIds) {
		List<AttendanceGroupDo> attendanceGroupDoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingIds)) {
			return attendanceGroupDoList;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("select id,meeting_id,group_id,need_join_num,normal_num,late_num,leave_num,leave_late_num,");
		sql.append(" un_punch_num,absence_num,holiday_pub_num,holiday_pri_num,holiday_sick_num,missing_num,");
		sql.append(" deleted,create_time from");
		sql.append(" meeting_attendance_group ");
		sql.append(" where deleted = :deleted and meeting_id in (:meetingIds)");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("meetingIds", meetingIds);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceGroupDo.class));

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceGroupDao#queryAttendanceGroup(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public List<AttendanceGroupDo> queryAttendanceGroup(List<String> meetingIds, String groupId) {
		List<AttendanceGroupDo> attendanceGroupDoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingIds)) {
			return attendanceGroupDoList;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("select id,meeting_id,group_id,need_join_num,normal_num,late_num,leave_num,leave_late_num,");
		sql.append(" un_punch_num,absence_num,holiday_pub_num,holiday_pri_num,holiday_sick_num,missing_num,");
		sql.append(" deleted,create_time from");
		sql.append(" meeting_attendance_group ");
		sql.append(" where deleted = :deleted and group_id = :groupId and meeting_id in (:meetingIds)");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("groupId", groupId);
		params.put("meetingIds", meetingIds);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceGroupDo.class));
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceGroupDao#createOrUpdateAttendanceGroup(cn.boxiao.bxn.meeting.data.AttendanceGroupDo)
	 */
	@Override
	public int createOrUpdateAttendanceGroup(AttendanceGroupDo attendanceGroupDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_group ");
		sql.append(" set need_join_num = :needJoinNum, normal_num = :normalNum, late_num = :lateNum,");
		sql.append(" leave_num = :leaveNum, leave_late_num = :leaveLateNum, un_punch_num = :unPunchNum,");
		sql.append(" absence_num = :absenceNum, holiday_pub_num = :holidayPubNum, holiday_pri_num = :holidayPriNum,");
		sql.append(" holiday_sick_num = :holidaySickNum, missing_num = :missingNum,");
		sql.append(" deleted = :deleted, create_time = :createTime ");
		sql.append(" where meeting_id = :meetingId and group_id = :groupId");

		Map<String, Object> params = Maps.newHashMap();
		attendanceGroupDo.setDeleted(DELETED_NO);
		attendanceGroupDo.setCreateTime(new Date());
		params.put("meetingId", attendanceGroupDo.getMeetingId());
		params.put("groupId", attendanceGroupDo.getGroupId());
		params.put("needJoinNum", attendanceGroupDo.getNeedJoinNum());
		params.put("normalNum", attendanceGroupDo.getNormalNum());
		params.put("lateNum", attendanceGroupDo.getLateNum());
		params.put("leaveNum", attendanceGroupDo.getLeaveNum());
		params.put("leaveLateNum", attendanceGroupDo.getLeaveLateNum());
		params.put("unPunchNum", attendanceGroupDo.getUnPunchNum());
		params.put("absenceNum", attendanceGroupDo.getAbsenceNum());
		params.put("holidayPubNum", attendanceGroupDo.getHolidayPubNum());
		params.put("holidayPriNum", attendanceGroupDo.getHolidayPriNum());
		params.put("holidaySickNum", attendanceGroupDo.getHolidaySickNum());
		params.put("missingNum", attendanceGroupDo.getMissingNum());
		params.put("deleted", attendanceGroupDo.getDeleted());
		params.put("createTime", attendanceGroupDo.getCreateTime());

		int result = namedParamJdbcTemplate.update(sql.toString(), params);
		if (result > 0) {
			return result;
		} else {
			return createAttendanceGroup(attendanceGroupDo);
		}
	}

	/**
	 * 新增部门考勤记录
	 * 
	 * @param attendanceGroupDo
	 * @return
	 */
	private int createAttendanceGroup(AttendanceGroupDo attendanceGroupDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_group ");
		sql.append(" ( id, meeting_id, group_id, need_join_num, normal_num, late_num, leave_num, leave_late_num,");
		sql.append(" un_punch_num, absence_num, holiday_pub_num, holiday_pri_num, holiday_sick_num, missing_num,");
		sql.append(" deleted, create_time) ");
		sql.append(" values (:id, :meetingId, :groupId, :needJoinNum, :normalNum, :lateNum, :leaveNum, :leaveLateNum,");
		sql.append(" :unPunchNum, :absenceNum, :holidayPubNum, :holidayPriNum, :holidaySickNum, :missingNum,");
		sql.append(" :deleted, :createTime) ");

		Map<String, Object> params = Maps.newHashMap();
		attendanceGroupDo.setId(UUIDGenerator.generateUniqueID(""));
		attendanceGroupDo.setDeleted(DELETED_NO);
		attendanceGroupDo.setCreateTime(new Date());
		params.put("id", attendanceGroupDo.getId());
		params.put("meetingId", attendanceGroupDo.getMeetingId());
		params.put("groupId", attendanceGroupDo.getGroupId());
		params.put("needJoinNum", attendanceGroupDo.getNeedJoinNum());
		params.put("normalNum", attendanceGroupDo.getNormalNum());
		params.put("lateNum", attendanceGroupDo.getLateNum());
		params.put("leaveNum", attendanceGroupDo.getLeaveNum());
		params.put("leaveLateNum", attendanceGroupDo.getLeaveLateNum());
		params.put("unPunchNum", attendanceGroupDo.getUnPunchNum());
		params.put("absenceNum", attendanceGroupDo.getAbsenceNum());
		params.put("holidayPubNum", attendanceGroupDo.getHolidayPubNum());
		params.put("holidayPriNum", attendanceGroupDo.getHolidayPriNum());
		params.put("holidaySickNum", attendanceGroupDo.getHolidaySickNum());
		params.put("missingNum", attendanceGroupDo.getMissingNum());
		params.put("deleted", attendanceGroupDo.getDeleted());
		params.put("createTime", attendanceGroupDo.getCreateTime());

		return namedParamJdbcTemplate.update(sql.toString(), params);
	}

}
