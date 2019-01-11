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
import cn.boxiao.bxn.meeting.vo.MeetingGroupUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

@Controller
@RequestMapping(value = "/meeting/group")
public class MeetingGroupWebController implements MeetingConstants {

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 进入部门类型页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/categoryPage", method = RequestMethod.GET)
	public ModelAndView toCategoryPage() {

		Model model = new ExtendedModelMap();
		List<MeetingGroupVo> meetingGroups = meetingGroupService.queryMeetingGroupsByParent(GROUP_CATEGORY_PARENT);
		model.addAttribute("meetingGroups", meetingGroups);

		return new ModelAndView("meeting/group/category", model.asMap());
	}

	/**
	 * 进入部门页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groupPage", method = RequestMethod.GET)
	public ModelAndView toGroupPage(@RequestParam String parentId) {

		Model model = new ExtendedModelMap();
		MeetingGroupVo parentVo = meetingGroupService.queryMeetingGroup(parentId);
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentName", parentVo.getGroupName());

		List<MeetingGroupVo> meetingGroups = meetingGroupService.queryMeetingGroupsByParent(parentId);
		model.addAttribute("meetingGroups", meetingGroups);

		return new ModelAndView("meeting/group/group", model.asMap());
	}

	/**
	 * 进入部门创建页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/createPage", method = RequestMethod.GET)
	public ModelAndView toCreateCategoryPage() {

		Model model = new ExtendedModelMap();

		return new ModelAndView("meeting/group/_create", model.asMap());
	}

	/**
	 * 进入部门更新页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView toEditPage(@RequestParam String groupId) {

		Model model = new ExtendedModelMap();
		MeetingGroupVo meetingGroupVo = meetingGroupService.queryMeetingGroup(groupId);
		model.addAttribute("meetingGroup", meetingGroupVo);

		return new ModelAndView("meeting/group/_edit", model.asMap());
	}
	
	/**
	 * 进入部门用户页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/userPage", method = RequestMethod.GET)
	public ModelAndView toUserPage(@RequestParam String groupId) {

		Model model = new ExtendedModelMap();
		MeetingGroupVo groupVo = meetingGroupService.queryMeetingGroup(groupId);
		model.addAttribute("groupId", groupId);
		model.addAttribute("groupName", groupVo.getGroupName());
		model.addAttribute("parentId", groupVo.getParentId());

		List<MeetingGroupUserVo> groupUsers = meetingGroupService.queryMeetingGroupUsers(groupId);
		model.addAttribute("groupUsers", groupUsers);

		return new ModelAndView("meeting/group/user", model.asMap());
	}

}
