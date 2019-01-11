package cn.boxiao.bxn.meeting.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;

/**
 * @author wangfucheng
 * @version 创建时间：2017年3月10日 上午11:09:46 微信端 跳转路由
 */
@Controller
@RequestMapping(value = "/wechat/meeting")
public class MeetingMobileController {
	/**
	 * 进入考勤列表页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage() {

		Model model = new ExtendedModelMap();

		LoggedUser loggedUser = SecurityUtil.getCurrentUser();
		// 拥有管理员权限
		if (loggedUser.hasAnyAuthorities("PERM_MEETING_ADMIN")) {
			model.addAttribute("perm", "admin");
			return new ModelAndView("meeting/mobile/index", model.asMap());
		}else if (!loggedUser.hasAnyAuthorities("PERM_MEETING_ADMIN") && loggedUser.hasAnyAuthorities("PERM_MEETING_OWNER")) {// 没有管理员权限，有组织者权限
			model.addAttribute("perm", "owner");
			return new ModelAndView("meeting/mobile/index", model.asMap());
		} else {
			return new ModelAndView("meeting/mobile/user-index", model.asMap());
		}
	}
	
	/**
	 * 进入单次会议部门考勤
	 * 
	 * @return
	 */
	@RequestMapping(value = "/groupStatisticsPage", method = RequestMethod.GET)
	public ModelAndView toGroupStatisticsPage() {

		Model model = new ExtendedModelMap();

		LoggedUser loggedUser = SecurityUtil.getCurrentUser();
		// 拥有管理员权限 或 有组织者权限
		if (loggedUser.hasAnyAuthorities("PERM_MEETING_ADMIN") || loggedUser.hasAnyAuthorities("PERM_MEETING_OWNER")) {
			return new ModelAndView("meeting/mobile/groupStat", model.asMap());
		} else {
			return new ModelAndView("meeting/mobile/user-groupStat", model.asMap());
		}
	}

	/**
	 * 进入考勤详情明细页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView toDetailPage() {

		Model model = new ExtendedModelMap();
		return new ModelAndView("meeting/mobile/detail", model.asMap());
	}

	/**
	 * 进入搜索页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/searchPage", method = RequestMethod.GET)
	public ModelAndView toSearchPage() {

		Model model = new ExtendedModelMap();
		return new ModelAndView("meeting/mobile/search", model.asMap());
	}

	/**
	 * 进入考勤统计页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/statisticsPage", method = RequestMethod.GET)
	public ModelAndView toStatisticsPage() {

		Model model = new ExtendedModelMap();
		return new ModelAndView("meeting/mobile/statistics", model.asMap());
	}
}
