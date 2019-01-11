package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.AttendanceRuleDo;

public interface AttendanceRuleDao {

	/**
	 * 查询所有会议考勤规则
	 * 
	 * @return
	 */
	List<AttendanceRuleDo> queryAttendanceRules();

	/**
	 * 更新会议考勤规则
	 * 
	 * @param attendanceRuleDo
	 * @return
	 */
	int updateAttendanceRule(AttendanceRuleDo attendanceRuleDo);

}
