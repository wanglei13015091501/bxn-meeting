package cn.boxiao.bxn.meeting.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.service.MeetingTypeService;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;

@Controller
@RequestMapping(value = "/meeting/type")
public class MeetingTypeWebController implements MeetingConstants {

	@Autowired
	private MeetingTypeService meetingTypeService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 进入会议类型首页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage() {

		Model model = new ExtendedModelMap();
		List<MeetingTypeVo> meetingTypes = meetingTypeService.queryMeetingTypes();
		model.addAttribute("meetingTypes", meetingTypes);

		return new ModelAndView("meeting/type/index", model.asMap());
	}

	/**
	 * 进入会议类型创建页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/createPage", method = RequestMethod.GET)
	public ModelAndView toCreatePage() {

		Model model = new ExtendedModelMap();

		List<MeetingGroupVo> meetingGroupVoList = meetingGroupService.queryMeetingGroupProfilesByParent("0");
		model.addAttribute("meetingGroupVoList", meetingGroupVoList);

		return new ModelAndView("meeting/type/_create", model.asMap());
	}

	/**
	 * 进入会议类型更新页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView toEditPage(@RequestParam String typeId) {

		Model model = new ExtendedModelMap();
		MeetingTypeVo meetingTypeVo = meetingTypeService.queryMeetingType(typeId);
		model.addAttribute("meetingType", meetingTypeVo);

		List<MeetingGroupVo> meetingGroupVoList = meetingGroupService.queryMeetingGroupProfilesByParent("0");
		model.addAttribute("meetingGroupVoList", meetingGroupVoList);

		return new ModelAndView("meeting/type/_edit", model.asMap());
	}

}
