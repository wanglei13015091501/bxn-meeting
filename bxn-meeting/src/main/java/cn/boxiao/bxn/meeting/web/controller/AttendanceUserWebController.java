package cn.boxiao.bxn.meeting.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.common.util.PropertiesAccessorUtil;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.AttendanceGroupService;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

@Controller
@RequestMapping(value = "/meeting/attendance")
public class AttendanceUserWebController {

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceUserService attendanceUserService;

	@Autowired
	private AttendanceGroupService attendanceGroupService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	@Autowired
	private PlaceService placeService;

	/**
	 * 会议考勤详情
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage(@RequestParam String meetingId) {

		Model model = new ExtendedModelMap();

		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);
		model.addAttribute("attendanceMeeting", attendanceMeetingVo);

		// 获取会议地点
		PlaceVo placeVo = placeService.queryPlace(attendanceMeetingVo.getPlaceId());
		model.addAttribute("placeName", placeVo.getPlaceName());

		// 获取考勤列表
		List<AttendanceUserVo> attendanceUserVoList = attendanceUserService.getAttendanceUsers(meetingId);

		// 获取考勤统计
		Map<String, Object> attendanceStatusMap = attendanceUserService.countAttendanceStatus(attendanceMeetingVo,
				attendanceUserVoList);
		model.addAttribute("attendanceStatusMap", attendanceStatusMap);

		List<Map<String, String>> attendanceMapList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
			Map<String, String> attendanceUserMap = Maps.newHashMap();
			attendanceUserMap.put("id", attendanceUserVo.getId());
			attendanceUserMap.put("uniqueNo", attendanceUserVo.getUniqueNo());
			attendanceUserMap.put("userName", attendanceUserVo.getUserName());
			attendanceUserMap.put("fullName", attendanceUserVo.getFullName());
			attendanceUserMap.put("signTime", attendanceUserVo.getSignTime());
			attendanceUserMap.put("logoutTime", attendanceUserVo.getLogoutTime());
			attendanceUserMap.put("status", attendanceUserVo.getStatus());
			attendanceUserMap.put("description", attendanceUserVo.getDescription());

			attendanceMapList.add(attendanceUserMap);
		}
		model.addAttribute("attendanceMapList", attendanceMapList);

		// 是否显示二维码打卡开关
		String qrcodeSwitch = PropertiesAccessorUtil.getProperty("attendance.qrcode.switch");
		if (StringUtils.equals(qrcodeSwitch, MeetingConstants.ATTENDANCE_QRCODE_CLOSE)) {
			model.addAttribute("qrcodeSwitch", MeetingConstants.ATTENDANCE_QRCODE_CLOSE);
		} else {
			model.addAttribute("qrcodeSwitch", MeetingConstants.ATTENDANCE_QRCODE_OPEN);
		}

		return new ModelAndView("meeting/attendanceUser/index", model.asMap());
	}

	/**
	 * 会议考勤详情-部门统计
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/groupPage", method = RequestMethod.GET)
	public ModelAndView toGroupPage(@RequestParam String meetingId) {

		Model model = new ExtendedModelMap();

		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);
		model.addAttribute("attendanceMeeting", attendanceMeetingVo);

		// 获取会议地点
		PlaceVo placeVo = placeService.queryPlace(attendanceMeetingVo.getPlaceId());
		model.addAttribute("placeName", placeVo.getPlaceName());

		// 按部门统计考勤
		List<AttendanceGroupVo> attendanceGroupList = attendanceGroupService.queryAttendanceGroups(meetingId);
		model.addAttribute("attendanceGroupList", attendanceGroupList);

		// 按部门统计总计
		AttendanceGroupVo summaryGroup = attendanceGroupService.computeAttendanceGroupSummary(attendanceGroupList);
		model.addAttribute("summaryGroup", summaryGroup);

		// 是否显示二维码打卡开关
		String qrcodeSwitch = PropertiesAccessorUtil.getProperty("attendance.qrcode.switch");
		if (StringUtils.equals(qrcodeSwitch, MeetingConstants.ATTENDANCE_QRCODE_CLOSE)) {
			model.addAttribute("qrcodeSwitch", MeetingConstants.ATTENDANCE_QRCODE_CLOSE);
		} else {
			model.addAttribute("qrcodeSwitch", MeetingConstants.ATTENDANCE_QRCODE_OPEN);
		}
		return new ModelAndView("meeting/attendanceUser/group", model.asMap());
	}

	/**
	 * 会议考勤详情-部门明细
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/groupDetailPage/{groupId}", method = RequestMethod.GET)
	public ModelAndView toGroupDetailPage(@PathVariable String groupId, @RequestParam String meetingId) {

		Model model = new ExtendedModelMap();

		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);
		model.addAttribute("attendanceMeeting", attendanceMeetingVo);

		// 获取会议部门
		MeetingGroupVo meetingGroupVo = meetingGroupService.queryMeetingGroup(groupId);
		model.addAttribute("meetingGroup", meetingGroupVo);

		// 按部门统计考勤
		List<AttendanceUserVo> attendanceUserList = attendanceGroupService.queryAttendanceUsers(attendanceMeetingVo,groupId);
		model.addAttribute("attendanceUserList", attendanceUserList);

		return new ModelAndView("meeting/attendanceUser/groupDetail", model.asMap());
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

		return new ModelAndView("meeting/attendance/create", model.asMap());
	}

	/**
	 * 进入考勤会议室修改页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView toEditPage() {

		Model model = new ExtendedModelMap();

		return new ModelAndView("meeting/attendance/edit", model.asMap());
	}
}
