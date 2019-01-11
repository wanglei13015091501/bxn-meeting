/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author supercao
 * @since bxc-space 1.0 2017年3月6日
 */
@Controller
@RequestMapping(value = "/meeting")
public class MeetingWebController {

	/**
	 * 进入考勤系统首页
	 * 
	 * @return
	 */
	@Secured({ "PERM_USER","PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage() {

		Model model = new ExtendedModelMap();
		return new ModelAndView("meeting/index", model.asMap());
	}

}
