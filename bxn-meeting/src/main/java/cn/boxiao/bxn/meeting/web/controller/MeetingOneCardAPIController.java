package cn.boxiao.bxn.meeting.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.service.AttendanceService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.util.JsonHelper;
import cn.boxiao.bxn.meeting.vo.AttendanceOneCardVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupUserVo;

/**
 * 一卡通考勤接口
 * 
 * @author liumeng
 * @since bxc 1.0 2017年10月19日
 */
@Controller
@RequestMapping(value = "/api/meeting/v1")
public class MeetingOneCardAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceService attendanceCardService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 一卡通同步打卡信息
	 * 
	 * @param attendanceJson
	 * @return
	 */
	@RequestMapping(value = "/attendances", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> postEpcRecord(@RequestParam String attendanceJson) {
		logger.debug("START: API call postEpcRecord, parameter: [attendanceJson={}]", attendanceJson);

		List<AttendanceOneCardVo> attendanceList = JsonHelper.deserialize(attendanceJson,
				new TypeReference<ArrayList<AttendanceOneCardVo>>() {
				});
		Map<String, String> recordMap = attendanceCardService.postEpcRecordByOneCard(attendanceList);

		logger.debug("END: API call postEpcRecord, return: [{}] ", recordMap);
		return new ResponseEntity<Map<String, String>>(recordMap, HttpStatus.OK);
	}

	/**
	 * 一卡通同步当天一卡通会议的用户
	 * 
	 * @param meetingId
	 * @return
	 */
	@RequestMapping(value = "/attendance-members", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getMembers() {
		logger.debug("START: API call getMembers, parameter: []");

		List<Map<String, Object>> memberMapList = Lists.newArrayList();

		List<MeetingGroupUserVo> meetingUsers = meetingGroupService.queryOneCardMeetingGroupUsers();
		for (MeetingGroupUserVo meetingUser : meetingUsers) {
			Map<String, Object> memberMap = Maps.newHashMap();

			memberMap.put("id", meetingUser.getUserId());

			memberMap.put("userName", meetingUser.getUserName());
			memberMap.put("fullName", meetingUser.getFullName());
			memberMap.put("uniqueNo", meetingUser.getUniqueNo());
			memberMap.put("category", meetingUser.getCategory());

			memberMapList.add(memberMap);
		}
		logger.debug("END: API call getMembers, return: [{}] ", memberMapList);
		return new ResponseEntity<List<Map<String, Object>>>(memberMapList, HttpStatus.OK);
	}
}
