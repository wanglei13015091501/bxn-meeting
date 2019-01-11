package cn.boxiao.bxn.meeting.dao;

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
import cn.boxiao.bxn.meeting.data.AttendanceUserStatDo;

@Component
public class AttendanceUserStatDaoImpl implements AttendanceUserStatDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;

	@Override
	public List<AttendanceUserStatDo> queryAttendanceUserStat(List<String> meetingIds) {

		List<AttendanceUserStatDo> attendanceUserStatDoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingIds)) {
			return attendanceUserStatDoList;
		}

		StringBuilder tempSql = new StringBuilder();
		tempSql.append(" (select id, user_id, IF(LENGTH(modify_status) > 0,modify_status,status) as status from ");
		tempSql.append(" meeting_attendance_user ");
		tempSql.append(" where meeting_id in (:meetingIds) ");
		tempSql.append(" and deleted = :deleted)m ");

		StringBuilder sql = new StringBuilder();
		sql.append(" select a.user_id as userId,a.needJoinNum as needJoinNum,b.normalNum as normalNum,");
		sql.append(" c.lateNum as lateNum,d.leaveNum as leaveNum,e.absenceNum as absenceNum,");
		sql.append(" f.holidayNum as holidayNum,g.missingNum as missingNum,h.leaveLateNum as leaveLateNum,");
		sql.append(" j.unPunchNum as unPunchNum from  ");

		sql.append(" (select user_id,count(m.id) as needJoinNum from ");
		sql.append(tempSql);
		sql.append(" group by m.user_id)a ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as normalNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '0' ");
		sql.append(" group by m.user_id)b on a.user_id = b.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as lateNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '1' ");
		sql.append(" group by m.user_id)c on a.user_id = c.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as leaveNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '2' ");
		sql.append(" group by m.user_id)d on a.user_id = d.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as absenceNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '3' ");
		sql.append(" group by m.user_id)e on a.user_id = e.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as holidayNum from ");
		sql.append(tempSql);
		sql.append(" where (m.status = '5' or m.status = '6' or m.status = '7') ");
		sql.append(" group by m.user_id)f on a.user_id = f.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as missingNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '4' ");
		sql.append(" group by m.user_id)g on a.user_id = g.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as leaveLateNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '8' ");
		sql.append(" group by m.user_id)h on a.user_id = h.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as unPunchNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '9' ");
		sql.append(" group by m.user_id)j on a.user_id = j.user_id ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingIds", meetingIds);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserStatDo.class));
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserStatDao#queryAttendanceUserStatByUserId(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public AttendanceUserStatDo queryAttendanceUserStatByUserId(List<String> meetingIds, String userId) {

		List<AttendanceUserStatDo> attendanceUserStatDoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingIds)) {
			return new AttendanceUserStatDo();
		}
		StringBuilder tempSql = new StringBuilder();
		tempSql.append(" (select id, user_id, IF(LENGTH(modify_status) > 0,modify_status,status) as status from ");
		tempSql.append(" meeting_attendance_user ");
		tempSql.append(" where meeting_id in (:meetingIds) and user_id = :userId ");
		tempSql.append(" and deleted = :deleted)m ");

		StringBuilder sql = new StringBuilder();
		sql.append(" select a.user_id as userId,a.needJoinNum as needJoinNum,b.normalNum as normalNum,");
		sql.append(" c.lateNum as lateNum,d.leaveNum as leaveNum,e.absenceNum as absenceNum,");
		sql.append(" f.holidayNum as holidayNum,g.missingNum as missingNum,h.leaveLateNum as leaveLateNum,");
		sql.append(" j.unPunchNum as unPunchNum from  ");

		sql.append(" (select user_id,count(m.id) as needJoinNum from ");
		sql.append(tempSql);
		sql.append(" group by m.user_id)a ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as normalNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '0' ");
		sql.append(" group by m.user_id)b on a.user_id = b.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as lateNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '1' ");
		sql.append(" group by m.user_id)c on a.user_id = c.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as leaveNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '2' ");
		sql.append(" group by m.user_id)d on a.user_id = d.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as absenceNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '3' ");
		sql.append(" group by m.user_id)e on a.user_id = e.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as holidayNum from ");
		sql.append(tempSql);
		sql.append(" where (m.status = '5' or m.status = '6' or m.status = '7') ");
		sql.append(" group by m.user_id)f on a.user_id = f.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as missingNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '4' ");
		sql.append(" group by m.user_id)g on a.user_id = g.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as leaveLateNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '8' ");
		sql.append(" group by m.user_id)h on a.user_id = h.user_id ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as unPunchNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '9' ");
		sql.append(" group by m.user_id)j on a.user_id = j.user_id ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingIds", meetingIds);
		params.put("userId", userId);
		params.put("deleted", DELETED_NO);

		attendanceUserStatDoList = namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserStatDo.class));
		return CollectionUtils.isEmpty(attendanceUserStatDoList) ? new AttendanceUserStatDo()
				: attendanceUserStatDoList.get(0);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceUserStatDao#queryAttendanceNormalUserStat(java.util.List)
	 */
	@Override
	public List<AttendanceUserStatDo> queryAttendanceNormalUserStat(List<String> meetingIds) {
		List<AttendanceUserStatDo> attendanceUserStatDoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(meetingIds)) {
			return attendanceUserStatDoList;
		}

		StringBuilder tempSql = new StringBuilder();
		tempSql.append(" (select id, user_id, IF(LENGTH(modify_status) > 0,modify_status,status) as status from ");
		tempSql.append(" meeting_attendance_user ");
		tempSql.append(" where meeting_id in (:meetingIds) ");
		tempSql.append(" and deleted = :deleted)m ");

		StringBuilder sql = new StringBuilder();
		sql.append(" select a.user_id as userId,a.needJoinNum as needJoinNum,b.normalNum as normalNum from  ");
		sql.append(" (select user_id,count(m.id) as needJoinNum from ");
		sql.append(tempSql);
		sql.append(" group by m.user_id)a ");

		sql.append(" left join ");
		sql.append(" (select user_id,count(m.id) as normalNum from ");
		sql.append(tempSql);
		sql.append(" where m.status = '0' ");
		sql.append(" group by m.user_id)b on a.user_id = b.user_id ");

		Map<String, Object> params = Maps.newHashMap();

		params.put("meetingIds", meetingIds);
		params.put("deleted", DELETED_NO);

		return namedParamJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceUserStatDo.class));
	}

}
