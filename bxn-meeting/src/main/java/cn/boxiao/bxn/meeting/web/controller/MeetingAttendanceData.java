package cn.boxiao.bxn.meeting.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.PageRequest;
import cn.boxiao.bxn.base.model.Sort;
import cn.boxiao.bxn.base.web.controller.ApiControllerBase;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.util.Constants;
import cn.boxiao.bxn.meeting.util.ReuMsg;
import cn.boxiao.bxn.meeting.util.ToolUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

@Controller
@RequestMapping(value = "/api/meeting/attendance")
public class MeetingAttendanceData extends ApiControllerBase {

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceUserService attendanceUserService;

	/**
	 * 会议考勤详情
	 * 
	 * @param meetingId:会议ID
	 * @return
	 */
	@RequestMapping(value = "/detailsAttendance", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getDetailsAttendance(@RequestParam String meetingId) {

		ReuMsg rm = new ReuMsg(Constants.FAIL_CODE, Constants.FAIL_MSG);

		if (ToolUtil.isEmpty(meetingId)) {
			rm = new ReuMsg(Constants.FAIL_PARAM_CODE, Constants.FAIL_PARAM__MSG);
			return JSONObject.toJSONString(rm);
		}

		Map<String, Object> resultMap = Maps.newHashMap();
		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);
		resultMap.put("attendanceMeeting", attendanceMeetingVo);

		// 获取考勤列表
		List<AttendanceUserVo> attendanceUserVoList = attendanceUserService.getAttendanceUsers(meetingId);
		List<Map<String, String>> attendanceMapList = Lists.newArrayList();

		if (ToolUtil.isNotEmpty(attendanceUserVoList)) {
			for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
				Map<String, String> attendanceUserMap = Maps.newHashMap();
				attendanceUserMap.put("id", attendanceUserVo.getId());
				attendanceUserMap.put("fullName", attendanceUserVo.getFullName());
				attendanceUserMap.put("status", attendanceUserVo.getStatus());
				attendanceUserMap.put("portraitUrl", ToolUtil.buildURL(Long.valueOf(attendanceUserVo.getUserId())));
				attendanceMapList.add(attendanceUserMap);
			}

			resultMap.put("attendanceMapList", attendanceMapList);
		}

		rm = new ReuMsg(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, resultMap);

		return JSONObject.toJSONString(rm);
	}

	/**
	 * 会议考勤统计
	 * 
	 * @param meetingId:会议ID
	 * @return
	 */
	@RequestMapping(value = "/attendanceStatistics", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getAttendanceStatistics(@RequestParam String meetingId) {

		ReuMsg rm = new ReuMsg(Constants.FAIL_CODE, Constants.FAIL_MSG);
		if (ToolUtil.isEmpty(meetingId)) {
			rm = new ReuMsg(Constants.FAIL_PARAM_CODE, Constants.FAIL_PARAM__MSG);
			return JSONObject.toJSONString(rm);
		}
		
		
		Map<String, Object> resultStatistics = Maps.newHashMap();
		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);
		resultStatistics.put("attendanceMeeting", attendanceMeetingVo);	
		// 获取考勤列表
		List<AttendanceUserVo> attendanceUserVoList = attendanceUserService.getAttendanceUsers(meetingId);		
		// 获取考勤统计
		Map<String, Object> attendanceStatusMap = attendanceUserService.countAttendanceStatus(attendanceMeetingVo,
						attendanceUserVoList);
		resultStatistics.put("attendanceStatusMap", attendanceStatusMap);		
		
		//获取参会人员人数
	   int totalNumber=	attendanceUserService.countAttendancePeople(meetingId);		
	   resultStatistics.put("totalNumber", totalNumber);		
	 
	  
	   //获取请假和缺勤人数
	    if( ToolUtil.isNotEmpty(attendanceStatusMap)){
	    	Integer leaveabsenceCount=Integer.valueOf(attendanceStatusMap.get("status10").toString())+Integer.valueOf(attendanceStatusMap.get("status3").toString());
	    	  Integer leaveabsence = new Integer(leaveabsenceCount);
	    	  int absenceCount = leaveabsence.intValue();
	    	  //获取出勤人数
	      int numberAttendance= totalNumber-absenceCount;
	    	 resultStatistics.put("numberAttendance", numberAttendance);	
	    }
	      
		rm = new ReuMsg(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, resultStatistics);
		
		return JSONObject.toJSONString(rm);
	}
	
	
	/**
	 * 获取会议考勤列表
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/attendanceList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public  String toIndexPage(
			@RequestParam(value = "page.page", required = false, defaultValue = "1") int pageNumber) {

		ReuMsg rm = new ReuMsg(Constants.DATA_NOT_FOUND, Constants.DATA_NOT_FOUND_MSG);
		PageRequest page = new PageRequest(Integer.valueOf(pageNumber) - 1, 10, Sort.Direction.DESC, "create_time");
		 
		
		Page<AttendanceMeetingVo> meetingVoPage = attendanceMeetingService.getAttendanceMeetingsApi( page);		
		//判断考勤列表是否为null
		if (ToolUtil.isNotEmpty(meetingVoPage)) {
			
			rm = new ReuMsg(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, meetingVoPage);
		}
		
		return JSONObject.toJSONString(rm);
	}
	
	
	
	
	
	
	
	
	
	
	

}
