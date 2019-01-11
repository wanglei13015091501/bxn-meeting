package cn.boxiao.bxn.meeting.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.boxiao.bxn.common.util.ThreadDeviceLocal;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.service.AttendanceService;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceRuleService;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.util.Constants;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.util.ReuMsg;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

@Controller
@RequestMapping(value = "/api-equip/meeting/v1")
public class MeetingAPIController implements MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceUserService attendanceUserService;

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceService attendanceCardService;

	@Autowired
	private MeetingCache meetingCache;
     
	
	@Autowired
	private	AttendanceRuleService attendanceRuleService;
	
	
	/**
	 * 推送参加会议人员的考勤信息
	 * 
	 * @param userNames
	 * @param meetingId
	 * @return
	 */
	@RequestMapping(value = "/attendances", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> postEpcRecord(@RequestParam String userNames,
			@RequestParam String meetingId, @RequestParam(required = false) String clockTime) {
		logger.debug("START: API call postEpcRecord, parameter: [userNames={}, meetingId={},clockTime={}]", userNames,
				meetingId, clockTime);

		if (StringUtils.isBlank(clockTime)) {
			clockTime = DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date());
		}
		Map<String, String> recordMap = attendanceCardService.postEpcRecord(userNames, meetingId, clockTime,
				ThreadDeviceLocal.getDevice());

		logger.debug("END: API call postEpcRecord, return: [{}] ", recordMap);
		return new ResponseEntity<Map<String, String>>(recordMap, HttpStatus.OK);
	}
      
	/**
	 * 根据会议ID获取用户考勤信息
	 * 
	 * @param meetingId
	 * @return
	 */
	@RequestMapping(value = "/attendance-members", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getMembers(@RequestParam String meetingId) {
		logger.debug("START: API call getMembers, parameter: [meetingId={}]", meetingId);

		List<AttendanceUserVo> attendanceUserVoList = attendanceUserService.getAttendanceUsers(meetingId);
		List<Map<String, Object>> memberMapList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
			Map<String, Object> memberMap = Maps.newHashMap();
			String state = attendanceUserVo.getStatus();
			if (StringUtils.equals(MeetingConstants.MEETING_USER_STATUS_INIT, state)) { // 考勤状态，未考勤时为null
				state = null;
			}
			memberMap.put("userName", attendanceUserVo.getUserName());
			memberMap.put("fullName", attendanceUserVo.getFullName());
			memberMap.put("uniqueNo", attendanceUserVo.getUniqueNo());
			memberMap.put("category", attendanceUserVo.getCategory());
			memberMap.put("state", state);
			memberMapList.add(memberMap);

		}
		logger.debug("END: API call getMembers, return: [{}] ", memberMapList);
		return new ResponseEntity<List<Map<String, Object>>>(memberMapList, HttpStatus.OK);
	}

	/**
	 * 获取指定时间段内的会议考勤信息
	 * 
	 * @param queryTime
	 * @return
	 */
	@RequestMapping(value = "/attendance-meetings", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getMeetings(@RequestParam String queryTime,
			@RequestParam(required = false, defaultValue = "") String placeNo) {
		logger.debug("START: API call getMeetings, parameter: [queryTime={}]");

	
		// 获取规则信息
		Map<String, Object> resultRuleMap = Maps.newHashMap();
		Map<String, AttendanceRuleVo> ruleMap = meetingCache.getAttendanceRule();
		for (AttendanceRuleVo ruleVo : ruleMap.values()) {
			if (StringUtils.equals(ruleVo.getStatus(), MeetingConstants.STATUS_OPEN)) {
				resultRuleMap.put(ruleVo.getRuleName(), ruleVo.getRuleValue());
			}
		}
		Map<String, Object> resultMap = Maps.newHashMap();	
		resultMap.put("ruleMap", resultRuleMap);

		logger.debug("END: API call getMeetings, return: [{}] ", resultMap);
		
		//获取指定考勤规则
		AttendanceRuleVo  beginAttendanceRulevo= ruleMap.get("normalSign");
		AttendanceRuleVo endAttendanceRulevo= ruleMap.get("normalLogout");	
		//获取会议开始的提前打卡时间
		String  attendanceBegins= beginAttendanceRulevo.getRuleValue();
		//获取会议结束后的延迟打卡时间
		String   attendanceEnds=endAttendanceRulevo.getRuleValue();
		
		
		List<Map<String, Object>> meetingMapList = Lists.newArrayList();
		// 获取指定时间段内的会议列表
		List<AttendanceMeetingVo> meetingVoList = attendanceMeetingService.getAttendanceMeetingsByTime(queryTime);			
		for (AttendanceMeetingVo attendanceMeeting : meetingVoList) {

			// 如果入参placeNo不为空，则根据placeNo过滤
			if (StringUtils.isNotBlank(placeNo)
					&& !StringUtils.equalsIgnoreCase(placeNo, attendanceMeeting.getPlaceNo())) {
				System.out.println(placeNo);
				System.out.println(attendanceMeeting.getPlaceNo());
				continue;
			} 

			Map<String, Object> meetingMap = Maps.newHashMap();
			meetingMap.put("id", attendanceMeeting.getId());
			meetingMap.put("meetingName", attendanceMeeting.getMeetingName());
			meetingMap.put("placeNo", attendanceMeeting.getPlaceNo());
			meetingMap.put("placeName", attendanceMeeting.getPlaceName());
			meetingMap.put("beginTime", attendanceMeeting.getBeginTime());
			meetingMap.put("endTime", attendanceMeeting.getEndTime());
			meetingMap.put("description", attendanceMeeting.getDescription());					
			meetingMap.put("attendanceBegin",DateUtil.getReductionMinutesTime(attendanceMeeting.getBeginTime(), Integer.valueOf( attendanceBegins)) );
			meetingMap.put("attendanceEnd", DateUtil.getAddMinutesTime(attendanceMeeting.getEndTime(), Integer.valueOf(  attendanceEnds)));
			
			meetingMapList.add(meetingMap);
		}
        
	
		resultMap.put("meetingList", meetingMapList);
	
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	
	
  
	
	
	
	
	
	
	
	
	
}
