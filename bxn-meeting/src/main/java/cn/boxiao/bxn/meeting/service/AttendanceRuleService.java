package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;

import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;

public interface AttendanceRuleService {

	/**
	 * 查询考勤规则
	 * 
	 * @param page
	 * @param content
	 * @return
	 */
	Map<String, AttendanceRuleVo> queryAttendanceRules();

	/**
	 * 更新考勤规则信息
	 * 
	 * @param ruleList
	 * @return
	 */
	int updateAttendanceRule(List<AttendanceRuleVo> ruleList);

}
