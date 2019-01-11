package cn.boxiao.bxn.meeting.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.meeting.service.AttendanceGroupService;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceStatService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.service.MeetingTypeService;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;
import cn.boxiao.bxn.meeting.vo.PlaceVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Controller
@RequestMapping(value = "/meeting/stat")
public class AttendanceStatWebController {

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private AttendanceStatService attendanceStatService;

	@Autowired
	private AttendanceGroupService attendanceGroupService;

	@Autowired
	private MeetingTypeService meetingTypeService;

	@Autowired
	private MeetingGroupService meetingGroupService;
	@Autowired
	private AttendanceMeetingService attendanceMeetingService;
	@Autowired
	private PlaceService placeService;

	/**
	 * 进入考勤统计--人员统计列表页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage(@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate,
			@RequestParam(required = false, defaultValue = "") String meetingTypeId) {

		Model model = new ExtendedModelMap();

		List<MeetingTypeVo> meetingTypeVoList = meetingTypeService.queryMeetingTypeProfiles();
		model.addAttribute("meetingTypeVoList", meetingTypeVoList);
		
		// 如果meetingTypeId没有取值，则进行查询
		if(StringUtils.isBlank(meetingTypeId)){
			return new ModelAndView("meeting/stat/index", model.asMap()); 
		}
		
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (StringUtils.isBlank(beginDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			beginDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		if (StringUtils.isBlank(endDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			endDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		List<AttendanceUserStatVo> statListVo = attendanceStatService.getAttendanceStat(meetingTypeId, beginDate,
				endDate);
		model.addAttribute("statList", statListVo);

		return new ModelAndView("meeting/stat/index", model.asMap());
	}

	/**
	 * 进入考勤统计--人员统计详情页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/detailPage/{userId}", method = RequestMethod.GET)
	public ModelAndView toDetailPage(@PathVariable String userId,
			@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate,
			@RequestParam(required = false, defaultValue = "") String meetingTypeId) {

		Model model = new ExtendedModelMap();

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (StringUtils.isBlank(beginDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			beginDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		if (StringUtils.isBlank(endDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			endDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		List<AttendanceUserVo> statDetailList = attendanceStatService.getAttendanceUserDetail(meetingTypeId, userId,
				beginDate, endDate, false);
		model.addAttribute("statDetailList", statDetailList);

		// 获取教工号/学号、全名
		UserVo userVo = userService.getUserById(Long.parseLong(userId));
		Map<String, Object> queryMap = Maps.newHashMap();
		queryMap.put("user", userVo == null ? new UserVo() : userVo);
		queryMap.put("beginDate", beginDate);
		queryMap.put("endDate", endDate);
		queryMap.put("meetingTypeId", meetingTypeId);
		model.addAttribute("queryMap", queryMap);

		return new ModelAndView("meeting/stat/detail", model.asMap());
	}

	/**
	 * 进入考勤统计-部门列表页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/group/indexPage", method = RequestMethod.GET)
	public ModelAndView toGroupPage(@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate,
			@RequestParam(required = false, defaultValue = "") String meetingTypeId) {
		Model model = new ExtendedModelMap();

		// 查询所有部门类型
		List<MeetingTypeVo> meetingTypeVoList = meetingTypeService.queryMeetingTypeProfiles();
		model.addAttribute("meetingTypeVoList", meetingTypeVoList);
		if (StringUtils.isBlank(meetingTypeId) && meetingTypeVoList.size() > 0) {
			// 如果第一次进入，选择第一个部门分类
			meetingTypeId = meetingTypeVoList.get(0).getId();
		}

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (StringUtils.isBlank(beginDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			beginDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		if (StringUtils.isBlank(endDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			endDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}

		// 查询部门统计
		List<AttendanceGroupStatVo> statVoList = attendanceGroupService.queryAttendanceGroupStat(meetingTypeId,
				beginDate, endDate);
		model.addAttribute("statList", statVoList);

		// 计算总计
		AttendanceGroupStatVo summaryVo = attendanceGroupService.computeAttendanceGroupStatSummary(statVoList);
		model.addAttribute("summaryVo", summaryVo);

		return new ModelAndView("meeting/stat/group", model.asMap());
	}

	/**
	 * 进入考勤统计--部门统计详情页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/group/detailPage/{groupId}", method = RequestMethod.GET)
	public ModelAndView toGroupDetailPage(@PathVariable String groupId, @RequestParam String beginDate,
			@RequestParam String endDate, @RequestParam String meetingTypeId) {

		Model model = new ExtendedModelMap();

		// 部门信息
		MeetingGroupVo meetingGroup = meetingGroupService.queryMeetingGroup(groupId);
		model.addAttribute("meetingGroup", meetingGroup);

		// 查询部门考勤明细
		List<AttendanceGroupVo> attendanceGroupList = attendanceGroupService.queryAttendanceGroups(meetingTypeId,
				groupId, beginDate, endDate);
		model.addAttribute("attendanceGroupList", attendanceGroupList);

		return new ModelAndView("meeting/stat/groupDetail", model.asMap());
	}

	/**
	 * 进入考勤统计-所有部门全勤名单页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/group/normalPage", method = RequestMethod.GET)
	public ModelAndView toGroupNormalPage(@RequestParam String beginDate, @RequestParam String endDate,
			@RequestParam String meetingTypeId) {
		Model model = new ExtendedModelMap();

		List<AttendanceUserStatVo> userStatList = attendanceGroupService.queryAttendanceNormalUsers(meetingTypeId,
				beginDate, endDate);
		model.addAttribute("userStatList", userStatList);
		return new ModelAndView("meeting/stat/groupNormal", model.asMap());
	}

	/**
	 * 进入考勤统计--部门全勤详情页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/group/normalDetailPage/{groupId}", method = RequestMethod.GET)
	public ModelAndView toGroupNoramlDetailPage(@PathVariable String groupId, @RequestParam String beginDate,
			@RequestParam String endDate, @RequestParam String meetingTypeId) {

		Model model = new ExtendedModelMap();

		// 部门信息
		MeetingGroupVo meetingGroup = meetingGroupService.queryMeetingGroup(groupId);
		model.addAttribute("meetingGroup", meetingGroup);

		// 全勤名单
		List<AttendanceUserStatVo> userStatList = attendanceGroupService.queryAttendanceNormalUsers(meetingTypeId,
				groupId, beginDate, endDate);
		model.addAttribute("userStatList", userStatList);
		return new ModelAndView("meeting/stat/groupNormalDetail", model.asMap());
	}

	/**
	 * 进入考勤统计--个人统计详情页(普通会议参加者)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userDetailPage", method = RequestMethod.GET)
	public ModelAndView toUserDetailPage(@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate) {

		Model model = new ExtendedModelMap();

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (StringUtils.isBlank(beginDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			beginDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		if (StringUtils.isBlank(endDate)) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			endDate = DateUtil.toString_YYYY_MM_DD(calendar.getTime());
		}
		LoggedUser loggedUser = SecurityUtil.getCurrentUser();
		String userId = String.valueOf(loggedUser.getId());
		List<AttendanceUserVo> statDetailList = attendanceStatService.getAttendanceUserDetail("", userId, beginDate,
				endDate, false);
		model.addAttribute("statDetailList", statDetailList);

		AttendanceUserStatVo userStatVo = attendanceStatService.getAttendanceStatByUserId(userId, beginDate, endDate);
		model.addAttribute("userStatVo", userStatVo);

		// 获取教工号/学号、全名
		Map<String, Object> queryMap = Maps.newHashMap();
		queryMap.put("user", loggedUser);
		queryMap.put("beginDate", beginDate);
		queryMap.put("endDate", endDate);
		model.addAttribute("queryMap", queryMap);

		return new ModelAndView("meeting/stat/userDetail", model.asMap());
	}
	
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/place/list", method = RequestMethod.GET)
	public ModelAndView placeList(@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate) {
		Model model = new ExtendedModelMap();
		List<AttendanceMeetingVo> attendanceMeetingVos = attendanceMeetingService.getAttendanceMeetings(beginDate, endDate);
		Map<String,Integer> placeUsedCountMap = Maps.newHashMap();
		Map<String,Double> placeUsedSumMap = Maps.newHashMap();
		int totalCount = 0;
		double totalHours = 0.0;
		for(AttendanceMeetingVo attendanceMeetingVo:attendanceMeetingVos){
			Integer count = placeUsedCountMap.get(attendanceMeetingVo.getPlaceId());
			if(count!=null){
				placeUsedCountMap.put(attendanceMeetingVo.getPlaceId(), count++);
			}else{
				placeUsedCountMap.put(attendanceMeetingVo.getPlaceId(), 1);
			}
			
			Double sumHour = placeUsedSumMap.get(attendanceMeetingVo.getPlaceId());
			Double hour = getDiffHour(attendanceMeetingVo.getBeginTime(),attendanceMeetingVo.getEndTime());
			if(sumHour!=null){
				placeUsedSumMap.put(attendanceMeetingVo.getPlaceId(), sumHour+hour);
			}else{
				placeUsedSumMap.put(attendanceMeetingVo.getPlaceId(), hour);
			}
			totalCount++;
			totalHours+=hour;
		}
		List<PlaceVo> places = placeService.queryPlaces("");
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("placeUsedCountMap", placeUsedCountMap);
		model.addAttribute("placeUsedSumMap", placeUsedSumMap);
		model.addAttribute("places", places);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalHours", totalHours);
		return new ModelAndView("meeting/stat/placeList", model.asMap());
	}
	
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/place/usedDetail", method = RequestMethod.GET)
	public ModelAndView placeUsedDetail(
			@RequestParam(required = false, defaultValue = "") String placeId,
			@RequestParam(required = false, defaultValue = "") String beginDate,
			@RequestParam(required = false, defaultValue = "") String endDate) {
		Model model = new ExtendedModelMap();
		PlaceVo placeVo = placeService.queryPlace(placeId);
		List<AttendanceMeetingVo> attendanceMeetingVos = attendanceMeetingService.getAttendanceMeetings(beginDate, endDate);
		Map<String,Double> placeUsedSumMap = Maps.newHashMap();
		List<AttendanceMeetingVo> attendanceMeetingVosByPlaceId = Lists.newArrayList();
		for(AttendanceMeetingVo attendanceMeetingVo:attendanceMeetingVos){
			if(placeId!=null && placeId.equals(attendanceMeetingVo.getPlaceId())){
				attendanceMeetingVosByPlaceId.add(attendanceMeetingVo);
			}
			
			Double hour = getDiffHour(attendanceMeetingVo.getBeginTime(),attendanceMeetingVo.getEndTime());
			placeUsedSumMap.put(attendanceMeetingVo.getId(), hour);
		}
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("placeVo", placeVo);
		model.addAttribute("placeUsedSumMap", placeUsedSumMap);
		model.addAttribute("attendanceMeetingVos", attendanceMeetingVosByPlaceId);
		return new ModelAndView("meeting/stat/placeUsedDetail", model.asMap());
	}
	
	public static double getDiffHour(String startTime, String endTime) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double hour = 0.0;
		try {
			long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();// 获得两个时间的毫秒时间差异  
			hour = diff/1000/60/60.0;// 计算差多少小时  
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hour;
	}
}
