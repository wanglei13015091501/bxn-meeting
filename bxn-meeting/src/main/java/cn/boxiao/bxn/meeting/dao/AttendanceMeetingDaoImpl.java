package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.PageImpl;
import cn.boxiao.bxn.base.model.Pageable;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceOrganizerDo;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

@Repository("attendanceMeetingDao")
public class AttendanceMeetingDaoImpl implements AttendanceMeetingDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryAttendanceMeeting(java.lang.String)
	 */
	@Override
	public AttendanceMeetingDo queryAttendanceMeeting(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" status,creator_id,deleted,create_time from ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" where id = :meetingId ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);

		List<AttendanceMeetingDo> attendanceMeetingDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceMeetingDo.class));

		return CollectionUtils.isEmpty(attendanceMeetingDoList) ? new AttendanceMeetingDo()
				: attendanceMeetingDoList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao#queryAttendanceMeetings(java.lang.String,
	 *      java.util.List, cn.boxiao.bxn.base.model.Pageable)
	 */
	@Override
	public Page<AttendanceMeetingDo> queryAttendanceMeetings(String userId, List<String> meetingIds, Pageable page) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" status,creator_id,deleted,create_time from ");
		sql.append(" meeting_attendance_meeting ");

		StringBuilder where = new StringBuilder();
		where.append(" where deleted = :deleted ");
		if (StringUtils.isNotBlank(userId)) {
			where.append(" and ( creator_id = :userId ");
			if (CollectionUtils.isNotEmpty(meetingIds)) {
				where.append(" or id in (:meetingIds) ");
			}
			where.append(" ) ");
		}

		sql.append(where);

		sql.append(" order by create_time desc ");
		sql.append(" limit ").append(page.getOffset()).append(" , ").append(page.getPageSize());

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("userId", userId);
		params.put("meetingIds", meetingIds);

		List<AttendanceMeetingDo> attendanceMeetingDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceMeetingDo.class));

		// total
		StringBuilder totalSql = new StringBuilder();
		totalSql.append("select count(id) from ");
		totalSql.append(" meeting_attendance_meeting ");
		totalSql.append(where);

		Integer total = namedParamJdbcTemplate.queryForObject(totalSql.toString(), params, Integer.class);
		return new PageImpl<AttendanceMeetingDo>(attendanceMeetingDoList, page, total);

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryAttendanceMeetingsByTime(java.lang.String)
	 */
	@Override
	public List<AttendanceMeetingDo> queryAttendanceMeetingsByTime(String queryTime) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" status,creator_id,deleted,create_time from ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" where deleted = :deleted ");

		if (StringUtils.isNotBlank(queryTime)) {
			sql.append(" and DATE_FORMAT(begin_time,'%Y-%m-%d') = :queryTime ");
		}
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("queryTime", queryTime);

		List<AttendanceMeetingDo> attendanceMeetingDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceMeetingDo.class));

		return attendanceMeetingDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao#queryAttendanceMeetingsByTime(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<AttendanceMeetingDo> queryAttendanceMeetingsByTime(String meetingTypeId, String beginTime,
			String endTime, boolean fromMobile) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" status,creator_id,deleted,create_time from ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" where deleted = :deleted ");
		if (StringUtils.isNotBlank(meetingTypeId)) {
			sql.append(" and meeting_type_id = :meetingTypeId ");
		}
		if (StringUtils.isNotBlank(beginTime)) {
			sql.append(" and DATE_FORMAT(begin_time,'%Y-%m-%d') >= :beginTime ");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and DATE_FORMAT(end_time,'%Y-%m-%d') <= :endTime ");
		}
		if (fromMobile) {
			sql.append(" order by DATE_FORMAT(begin_time,'%Y-%m-%d') desc,begin_time asc ");
		} else {
			sql.append(" order by begin_time asc ");
		}

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("meetingTypeId", meetingTypeId);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);

		List<AttendanceMeetingDo> attendanceMeetingDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceMeetingDo.class));
		return attendanceMeetingDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceMeetingDao#queryLastCyclingAttendanceMeeting(java.util.Date)
	 */
	@Override
	public List<AttendanceMeetingDo> queryLastCyclingAttendanceMeeting(Date today) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" status,creator_id,deleted,create_time from ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" where deleted = :deleted ");
		sql.append(" and ( (cycling = :cyclingByDay and DATE_FORMAT(begin_time,'%Y-%m-%d') = :today) ");
		sql.append(" or (cycling = :cyclingByWeek and DATE_FORMAT(begin_time,'%Y-%m-%d') = :lastWeekDay) ");
		sql.append(" or (cycling = :cyclingByMonth and DATE_FORMAT(begin_time,'%Y-%m-%d') = :lastMonthDay) ) ");
		sql.append(" order by begin_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("cyclingByDay", MEETING_CYCLING_BYDAY);
		params.put("cyclingByWeek", MEETING_CYCLING_BYWEEK);
		params.put("cyclingByMonth", MEETING_CYCLING_BYMONTH);
		params.put("today", DateUtil.toString_YYYY_MM_DD(DateUtil.beforeDayDate(today)));
		params.put("lastWeekDay", DateUtil.toString_YYYY_MM_DD(DateUtil.beforeWeekDate(today)));
		String lastMonthDay = DateUtil.toString_YYYY_MM_DD(DateUtil.beforeMonthDate(today));
		if (!lastMonthDay.split("-")[2].equals(DateUtil.toString_YYYY_MM_DD(today).split("-")[2])) {
			lastMonthDay = lastMonthDay.substring(0, lastMonthDay.lastIndexOf("-") + 1);
			lastMonthDay += DateUtil.toString_YYYY_MM_DD(today).split("-")[2];
		}
		params.put("lastMonthDay", lastMonthDay);

		List<AttendanceMeetingDo> attendanceMeetingDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceMeetingDo.class));
		return attendanceMeetingDoList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#createAttendanceMeeting(cn.boxiao.bxn.meeting.data.AttendanceMeetingDo)
	 */
	@Override
	public AttendanceMeetingDo createAttendanceMeeting(AttendanceMeetingDo attendanceMeetingDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" (id,meeting_name,place_id,meeting_type_id,description,begin_time,end_time,cycling,");
		sql.append(" creator_id,status,deleted,create_time) ");
		sql.append(" values(:id,:meetingName,:placeId,:meetingTypeId,:description,:beginTime,:endTime,:cycling,");
		sql.append(" :creatorId,:status,:deleted,:createTime) ");

		Map<String, Object> params = Maps.newHashMap();

		attendanceMeetingDo.setId(UUIDGenerator.generateUniqueID(""));
		attendanceMeetingDo.setDeleted(DELETED_NO);
		attendanceMeetingDo.setCreateTime(new Date());
		attendanceMeetingDo.setStatus(MEETING_STATUS_INIT);
		attendanceMeetingDo.setCreatorId(StringUtils.defaultIfBlank(attendanceMeetingDo.getCreatorId(), ""));

		params.put("id", attendanceMeetingDo.getId());
		params.put("meetingName", attendanceMeetingDo.getMeetingName());
		params.put("placeId", attendanceMeetingDo.getPlaceId());
		params.put("meetingTypeId", attendanceMeetingDo.getMeetingTypeId());
		params.put("description", attendanceMeetingDo.getDescription());
		params.put("beginTime", attendanceMeetingDo.getBeginTime());
		params.put("endTime", attendanceMeetingDo.getEndTime());
		params.put("cycling", attendanceMeetingDo.getCycling());
		params.put("status", attendanceMeetingDo.getStatus());
		params.put("creatorId", attendanceMeetingDo.getCreatorId());
		params.put("deleted", attendanceMeetingDo.getDeleted());
		params.put("createTime", attendanceMeetingDo.getCreateTime());

		namedParamJdbcTemplate.update(sql.toString(), params);

		return attendanceMeetingDo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#updateAttendanceMeeting(cn.boxiao.bxn.meeting.data.AttendanceMeetingDo)
	 */
	@Override
	public int updateAttendanceMeeting(AttendanceMeetingDo attendanceMeetingDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" set meeting_name = :meetingName,place_id = :placeId,meeting_type_id = :meetingTypeId,");
		sql.append(" description = :description,begin_time = :beginTime,end_time = :endTime,cycling = :cycling");
		sql.append(" where id =:id ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("id", attendanceMeetingDo.getId());
		params.put("meetingName", attendanceMeetingDo.getMeetingName());
		params.put("placeId", attendanceMeetingDo.getPlaceId());
		params.put("meetingTypeId", attendanceMeetingDo.getMeetingTypeId());
		params.put("description", attendanceMeetingDo.getDescription());
		params.put("beginTime", attendanceMeetingDo.getBeginTime());
		params.put("endTime", attendanceMeetingDo.getEndTime());
		params.put("cycling", attendanceMeetingDo.getCycling());
		params.put("status", attendanceMeetingDo.getStatus());

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#updateAttendanceMeetingStatus(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int updateAttendanceMeetingStatus(String meetingId, String status) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" set status = :status ");
		sql.append(" where id =:meetingId ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("meetingId", meetingId);
		params.put("status", status);

		return namedParamJdbcTemplate.update(sql.toString(), params);

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#deleteAttendanceMeeting(java.lang.String)
	 */
	@Override
	public int deleteAttendanceMeeting(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_meeting ");
		sql.append(" set deleted = :deleted ");
		sql.append(" where id =:meetingId ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_YES);

		return namedParamJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#createMeetingOrganizers(cn.boxiao.bxn.meeting.data.AttendanceOrganizerDo)
	 */
	@Override
	public List<AttendanceOrganizerDo> queryMeetingOrganizers(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select meeting_id,user_id,create_time from ");
		sql.append(" meeting_attendance_organizer ");
		sql.append(" where meeting_id = :meetingId and deleted = :deleted ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceOrganizerDo.class));

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#queryAttendanceOrganizersByUserId(java.lang.String)
	 */
	@Override
	public List<AttendanceOrganizerDo> queryAttendanceOrganizersByUserId(String userId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select meeting_id,user_id,create_time from ");
		sql.append(" meeting_attendance_organizer ");
		sql.append(" where user_id = :userId and deleted = :deleted ");
		sql.append(" group by meeting_id ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("userId", userId);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceOrganizerDo.class));
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#createMeetingOrganizers(cn.boxiao.bxn.meeting.data.AttendanceOrganizerDo)
	 */
	@Override
	public AttendanceOrganizerDo createMeetingOrganizers(AttendanceOrganizerDo attendanceOrganizerDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_organizer ");
		sql.append(" (id,meeting_id,user_id,deleted,create_time) ");
		sql.append(" values(:id,:meetingId,:userId,:deleted,:createTime) ");

		Map<String, Object> params = Maps.newHashMap();

		attendanceOrganizerDo.setId(UUIDGenerator.generateUniqueID(""));
		attendanceOrganizerDo.setDeleted(DELETED_NO);
		attendanceOrganizerDo.setCreateTime(new Date());

		params.put("id", attendanceOrganizerDo.getId());
		params.put("meetingId", attendanceOrganizerDo.getMeetingId());
		params.put("userId", attendanceOrganizerDo.getUserId());
		params.put("deleted", attendanceOrganizerDo.getDeleted());
		params.put("createTime", attendanceOrganizerDo.getCreateTime());

		namedParamJdbcTemplate.update(sql.toString(), params);

		return attendanceOrganizerDo;

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingAttendanceDao#deleteAttendanceOrganizers(java.lang.String)
	 */
	@Override
	public int deleteAttendanceOrganizers(String meetingId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_organizer ");
		sql.append(" set deleted = :deleted, ");
		sql.append(" del_time = :delTime ");
		sql.append(" where meeting_id =:meetingId ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingId", meetingId);
		params.put("delTime", new Date());
		params.put("deleted", DELETED_YES);

		return namedParamJdbcTemplate.update(sql.toString(), params);
	}

}
