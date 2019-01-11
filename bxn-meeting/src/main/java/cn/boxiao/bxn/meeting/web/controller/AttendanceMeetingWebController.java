package cn.boxiao.bxn.meeting.web.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.PageRequest;
import cn.boxiao.bxn.base.model.Sort;
import cn.boxiao.bxn.common.FileOperator;
import cn.boxiao.bxn.common.util.IDPhotoUtil;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.service.MeetingTypeService;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.util.ToolUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

@Controller
@RequestMapping(value = "/meeting/attendance/meeting")
public class AttendanceMeetingWebController {

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private MeetingTypeService meetingTypeService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 进入考勤会议列表页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage(
			@RequestParam(value = "page.page", required = false, defaultValue = "1") int pageNumber) {

		PageRequest page = new PageRequest(Integer.valueOf(pageNumber) - 1, 10, Sort.Direction.DESC, "create_time");
		Model model = new ExtendedModelMap();

		LoggedUser user = SecurityUtil.getCurrentUser();
		
		 
		Page<AttendanceMeetingVo> meetingVoPage = attendanceMeetingService.getAttendanceMeetings(user, page);
		model.addAttribute("meetingVoPage", meetingVoPage);

		return new ModelAndView("meeting/attendanceMeeting/index", model.asMap());
	}

	/**
	 * 进入考勤会议室创建页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/createPage", method = RequestMethod.GET)
	public ModelAndView toCreatePage() {

		Model model = new ExtendedModelMap();
		List<PlaceVo> placeVoList = placeService.queryPlaces("");
		model.addAttribute("placeVoList", placeVoList);

		List<MeetingTypeVo> meetingTypeVoList = meetingTypeService.queryMeetingTypeProfiles();
		model.addAttribute("meetingTypeVoList", meetingTypeVoList);

		return new ModelAndView("meeting/attendanceMeeting/create", model.asMap());
	}

	/**
	 * 进入考勤会议室修改页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/editPage/{meetingId}", method = RequestMethod.GET)
	public ModelAndView toEditPage(@PathVariable String meetingId) {

		Model model = new ExtendedModelMap();
		List<PlaceVo> placeVoList = placeService.queryPlaces("");
		model.addAttribute("placeVoList", placeVoList);

		List<MeetingTypeVo> meetingTypeVoList = meetingTypeService.queryMeetingTypeProfiles();
		model.addAttribute("meetingTypeVoList", meetingTypeVoList);

		// 获取考勤会议信息
		AttendanceMeetingVo meetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);
		model.addAttribute("meetingVo", meetingVo);

		// 获取部门分类信息
		MeetingTypeVo meetingTypeVo = meetingTypeService.queryMeetingType(meetingVo.getMeetingTypeId());
		MeetingGroupVo meetingGroupVo = meetingGroupService.queryMeetingGroup(meetingTypeVo.getGroupId());
		model.addAttribute("meetingGroupVo", meetingGroupVo);

		// 获取部门信息
		List<MeetingGroupVo> meetingGroupVoList = meetingGroupService
				.queryMeetingGroupProfilesByParent(meetingGroupVo.getId());
		model.addAttribute("meetingGroupVoList", meetingGroupVoList);

		return new ModelAndView("meeting/attendanceMeeting/edit", model.asMap());
	}
}
