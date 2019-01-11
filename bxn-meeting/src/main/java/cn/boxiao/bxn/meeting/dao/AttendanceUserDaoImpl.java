package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.AttendanceLogDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

@Repository("attendanceUserDao")
public class AttendanceUserDaoImpl implements AttendanceUserDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryAttendanceLog(java.lang.String)
	 */
	@Override
	public List<AttendanceLogDo> queryAttendanceLog(String meetingId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,user_name,meeting_id,device_id,clock_time ");
		sql.append(" from ");
		sql.append(" meeting_attendance_log ");
		sql.append(" where meeting_id=:meetingId ");
		sql.append(" order by clock_time asc");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);

		return namedParamJdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(AttendanceLogDo.class));

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#createAttendanceLog(java.util.List)
	 */
	@Override
	public int[] createAttendanceLog(List<AttendanceLogDo> attendanceLogDoList) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_log ");
		sql.append(" (id,user_name,meeting_id,device_id,clock_time,create_time) ");
		sql.append(" values(:id,:userName,:meetingId,:deviceId,:clockTime,:createTime) ");

		List<Map<String, Object>> paramsList = Lists.newLinkedList();
		for (AttendanceLogDo attendanceLogDo : attendanceLogDoList) {
			Map<String, Object> params = Maps.newLinkedHashMap();
			params.put("id", UUIDGenerator.generateUniqueID(""));
			params.put("userName", attendanceLogDo.getUserName());
			params.put("meetingId", attendanceLogDo.getMeetingId());
			params.put("deviceId", attendanceLogDo.getDeviceId());
			params.put("clockTime", attendanceLogDo.getClockTime());
			params.put("createTime", new Date());

			paramsList.add(params);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object>[] paramMaps = new LinkedHashMap[paramsList.size()];
		return namedParamJdbcTemplate.batchUpdate(sql.toString(), paramsList.toArray(paramMaps));

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryMeetingUsers(java.lang.String)
	 */
	@Override
	public List<AttendanceUserDo> queryMeetingUsers(String meetingId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status,deleted ");
		sql.append(" from ");
		sql.append(" meeting_attendance_user ");
		sql.append(" where meeting_id=:meetingId and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserDo.class));
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#queryAttendanceUserByUserName(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AttendanceUserDo queryAttendanceUserByUserName(String meetingId, String userName) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status ");
		sql.append(" from ");
		sql.append(" meeting_attendance_user ");
		sql.append(" where meeting_id=:meetingId and user_name=:userName and deleted=:deleted");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("userName", userName);
		params.put("deleted", DELETED_NO);

		List<AttendanceUserDo> attendanceUserDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserDo.class));
		return CollectionUtils.isEmpty(attendanceUserDoList) ? new AttendanceUserDo() : attendanceUserDoList.get(0);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#queryAttendanceUserById(java.lang.String)
	 */
	@Override
	public AttendanceUserDo queryAttendanceUserById(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status ");
		sql.append(" from ");
		sql.append(" meeting_attendance_user ");
		sql.append(" where id=:id and deleted=:deleted");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("deleted", DELETED_NO);

		List<AttendanceUserDo> attendanceUserDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserDo.class));
		return CollectionUtils.isEmpty(attendanceUserDoList) ? new AttendanceUserDo() : attendanceUserDoList.get(0);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryAttendanceUserByUserId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AttendanceUserDo queryAttendanceUserByUserId(String meetingId, String userId, String deleted) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status,deleted ");
		sql.append(" from ");
		sql.append(" meeting_attendance_user ");
		sql.append(" where meeting_id=:meetingId and user_id =:userId ");

		if (StringUtils.equals(deleted, DELETED_NO) || StringUtils.equals(deleted, DELETED_YES)) {
			sql.append(" and deleted =:deleted ");
		}

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("userId", userId);
		params.put("deleted", deleted);

		List<AttendanceUserDo> attendanceUserDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserDo.class));

		return CollectionUtils.isEmpty(attendanceUserDoList) ? new AttendanceUserDo() : attendanceUserDoList.get(0);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#queryMeetingUsersByUserId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<AttendanceUserDo> queryMeetingUsersByUserId(String userId, List<String> meetingIdList) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status,deleted ");
		sql.append(" from ");
		sql.append(" meeting_attendance_user ");
		sql.append(" where deleted=:deleted and user_id=:userId");
		if (CollectionUtils.isNotEmpty(meetingIdList)) {
			sql.append(" and meeting_id in (:meetingIdList) ");
		}

		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", userId);
		params.put("deleted", DELETED_NO);
		params.put("meetingIdList", meetingIdList);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserDo.class));

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#countAttendanceUserByMeetingId(java.lang.String)
	 */
	@Override
	public int countAttendanceUserByMeetingId(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) from");
		sql.append(" meeting_attendance_user ");
		sql.append(" where meeting_id =:meetingId and deleted = :deleted");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#initAttendanceUsers(java.lang.String)
	 */
	@Override
	public int initAttendanceUsers(String meetingId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set ");
		sql.append(" status=:status,sign_time=null,logout_time=null ");
		sql.append(" where meeting_id=:meetingId and deleted=:deleted and LENGTH(TRIM( modify_status)) < 1 ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("status", MEETING_USER_STATUS_INIT);
		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#createAttendanceUser(cn.boxiao.bxn.meeting.data.AttendanceUserDo)
	 */
	@Override
	public int createAttendanceUser(AttendanceUserDo attendanceUserDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_user ");
		sql.append(
				" (id,meeting_id,user_id,user_name,sign_time,logout_time,modify_sign_time,modify_logout_time,description,status,modify_status,deleted,create_time) ");
		sql.append(
				" values(:id,:meetingId,:userId,:userName,:signTime,:logoutTime,:modifySignTime,:modifyLogoutTime,:description,:status,:modifyStatus,:deleted,:createTime) ");

		Map<String, Object> params = Maps.newHashMap();

		attendanceUserDo.setUserName(StringUtils.defaultIfBlank(attendanceUserDo.getUserName(), ""));
		attendanceUserDo.setUserId(StringUtils.defaultIfBlank(attendanceUserDo.getUserId(), ""));
		attendanceUserDo.setDescription(StringUtils.defaultIfBlank(attendanceUserDo.getDescription(), ""));
		attendanceUserDo.setStatus(StringUtils.defaultIfBlank(attendanceUserDo.getStatus(), MEETING_USER_STATUS_INIT));
		attendanceUserDo.setModifyStatus(StringUtils.defaultIfBlank(attendanceUserDo.getModifyStatus(), ""));
		params.put("id", UUIDGenerator.generateUniqueID(""));
		params.put("meetingId", attendanceUserDo.getMeetingId());
		params.put("userId", attendanceUserDo.getUserId());
		params.put("userName", attendanceUserDo.getUserName());
		params.put("signTime", attendanceUserDo.getSignTime());
		params.put("logoutTime", attendanceUserDo.getLogoutTime());
		params.put("modifySignTime", attendanceUserDo.getModifySignTime());
		params.put("modifyLogoutTime", attendanceUserDo.getModifyLogoutTime());
		params.put("description", attendanceUserDo.getDescription());
		params.put("status", attendanceUserDo.getStatus());
		params.put("modifyStatus", attendanceUserDo.getModifyStatus());
		params.put("deleted", DELETED_NO);
		params.put("createTime", new Date());

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#updateAttendanceUser(cn.boxiao.bxn.meeting.data.AttendanceUserDo)
	 */
	@Override
	public int updateAttendanceUser(AttendanceUserDo attendanceUserDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set ");
		if (attendanceUserDo.getModifySignTime() != null) {
			sql.append(" modify_sign_time=:modifySignTime, ");
		}
		if (attendanceUserDo.getModifyLogoutTime() != null) {
			sql.append(" modify_logout_time=:modifyLogoutTime, ");
		}
		if (StringUtils.isNotBlank(attendanceUserDo.getModifyStatus())) {
			sql.append(" modify_status=:modifyStatus, ");
		}
		if (StringUtils.isNotBlank(attendanceUserDo.getDeleted())) {
			sql.append("deleted=:deleted, ");
		}
		sql.append(" description=:description ");
		sql.append(" where id=:id");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", attendanceUserDo.getId());
		params.put("modifySignTime", attendanceUserDo.getModifySignTime());
		params.put("modifyLogoutTime", attendanceUserDo.getModifyLogoutTime());
		params.put("modifyStatus", attendanceUserDo.getModifyStatus());
		params.put("description", StringUtils.defaultIfBlank(attendanceUserDo.getDescription(), ""));
		params.put("deleted", attendanceUserDo.getDeleted());

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserDao#resetAttendanceUser(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int resetAttendanceUser(String meetingId, String userName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set ");
		sql.append(
				" modify_sign_time=:modifySignTime,modify_logout_time=:modifyLogoutTime,modify_status=:modifyStatus,description=:description");
		sql.append(" where meeting_id=:meetingId and user_name=:userName");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("userName", userName);
		params.put("modifySignTime", null);
		params.put("modifyLogoutTime", null);
		params.put("modifyStatus", "");
		params.put("description", "");

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#updateAttendanceUserByUserName(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int updateAttendanceUserByUserName(String meetingId, String userName, String signTime, String logoutTime,
			String status) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set ");
		if (StringUtils.isNotBlank(signTime)) {
			sql.append(" sign_time=:signTime, ");
		}
		if (StringUtils.isNotBlank(logoutTime)) {
			sql.append(" logout_time=:logoutTime, ");
		}
		sql.append(" status=:status ");
		sql.append(" where meeting_id=:meetingId and user_name=:userName and deleted=:deleted");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("userName", userName);
		if (StringUtils.isNotBlank(signTime)) {
			params.put("signTime", DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(signTime));
		}
		if (StringUtils.isNotBlank(logoutTime)) {
			params.put("logoutTime", DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(logoutTime));
		}

		params.put("status", status);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#deleteUserAttendance(java.lang.String)
	 */
	@Override
	public int deleteAttendanceUserByUserId(String meetingId, String userId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set deleted = :deleted,status=:status,sign_time=null,logout_time=null ");
		sql.append(" where meeting_id =:meetingId and user_id =:userId ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);
		params.put("status", MEETING_USER_STATUS_INIT);
		params.put("userId", userId);
		params.put("deleted", DELETED_YES);

		return namedParamJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#deleteAttendanceUsers(java.lang.String)
	 */
	@Override
	public int deleteAttendanceUsers(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_user ");
		sql.append(" set deleted = :deleted,status=:status,sign_time=null,logout_time=null ");
		sql.append(" where meeting_id =:meetingId ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("status", MEETING_USER_STATUS_INIT);
		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_YES);

		return namedParamJdbcTemplate.update(sql.toString(), params);
	}
}
