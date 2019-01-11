package cn.boxiao.bxn.meeting.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.AttendanceRuleDo;

@Component
public class AttendanceRuleDaoImpl implements AttendanceRuleDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceRuleDao#queryAttendanceRules()
	 */
	@Override
	public List<AttendanceRuleDo> queryAttendanceRules() {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,rule_name,rule_value,status,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_attendance_rule ");

		Map<String, Object> params = Maps.newHashMap();

		List<AttendanceRuleDo> ruleList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(AttendanceRuleDo.class));

		return ruleList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.AttendanceRuleDao#updateAttendanceRule(cn.boxiao.bxn.meeting.data.AttendanceRuleDo)
	 */
	@Override
	public int updateAttendanceRule(AttendanceRuleDo meetingRuleDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_rule ");
		sql.append(" set rule_value=:ruleValue,status = :status ");
		sql.append(" where rule_name=:ruleName ");

		Map<String, Object> params = Maps.newHashMap();
		meetingRuleDo.setRuleValue(StringUtils.defaultIfBlank(meetingRuleDo.getRuleValue(), ""));
		meetingRuleDo.setStatus(StringUtils.defaultIfBlank(meetingRuleDo.getStatus(), STATUS_OPEN));
		params.put("ruleName", meetingRuleDo.getRuleName());
		params.put("ruleValue", meetingRuleDo.getRuleValue());
		params.put("status", meetingRuleDo.getStatus());

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

}
