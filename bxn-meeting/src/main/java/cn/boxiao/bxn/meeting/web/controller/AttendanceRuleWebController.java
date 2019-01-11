package cn.boxiao.bxn.meeting.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.boxiao.bxn.meeting.service.AttendanceRuleService;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;

@Controller
@RequestMapping(value = "/meeting/rule")
public class AttendanceRuleWebController {

	@Autowired
	private AttendanceRuleService attendanceRuleService;

	/**
	 * 进入考勤规则页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage() {

		Model model = new ExtendedModelMap();

		Map<String, AttendanceRuleVo> ruleMap = attendanceRuleService.queryAttendanceRules();
		model.addAttribute("ruleMap", ruleMap);

		return new ModelAndView("meeting/rule/index", model.asMap());
	}

}
