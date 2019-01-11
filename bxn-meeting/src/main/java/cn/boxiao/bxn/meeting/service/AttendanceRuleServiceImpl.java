package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.dao.AttendanceRuleDao;
import cn.boxiao.bxn.meeting.data.AttendanceRuleDo;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;

@Service
public class AttendanceRuleServiceImpl implements AttendanceRuleService {

	@Autowired
	private AttendanceRuleDao attendanceRuleDao;

	@Autowired
	private MeetingCache meetingCache;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceRuleService#queryAttendanceRules()
	 */
	@Override
	public Map<String, AttendanceRuleVo> queryAttendanceRules() {

		Map<String, AttendanceRuleVo> map = Maps.newHashMap();

		//获取考勤规则列表
		List<AttendanceRuleDo> attendanceRuleList = attendanceRuleDao.queryAttendanceRules();
		
		
		for (AttendanceRuleDo attendanceRuleDo : attendanceRuleList) {

			AttendanceRuleVo attendanceRuleVo = new AttendanceRuleVo();
			BeanUtils.copyProperties(attendanceRuleDo, attendanceRuleVo);

			map.put(attendanceRuleDo.getRuleName(), attendanceRuleVo);
		}
		return map;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.AttendanceRuleService#updateAttendanceRule(java.util.List)
	 */
	@Override
	public int updateAttendanceRule(List<AttendanceRuleVo> ruleList) {

		for (AttendanceRuleVo attendanceRuleVo : ruleList) {

			AttendanceRuleDo attendanceRuleDo = new AttendanceRuleDo();
			BeanUtils.copyProperties(attendanceRuleVo, attendanceRuleDo);

			attendanceRuleDao.updateAttendanceRule(attendanceRuleDo);
		}

		meetingCache.refreshAttendanceRule();

		return 0;
	}

}
