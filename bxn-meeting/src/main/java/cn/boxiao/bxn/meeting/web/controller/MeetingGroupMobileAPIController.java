package cn.boxiao.bxn.meeting.web.controller;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.AttendanceGroupService;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1/mobile/group")
public class MeetingGroupMobileAPIController implements MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	@Autowired
	private AttendanceGroupService attendanceGroupService;

	/**
	 * 根据会议ID查询相关部门信息
	 * 
	 * @param meetingId
	 * @return
	 */
	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getMeetingGroups(@RequestParam String meetingId) {
		logger.debug("START:Mobile API call getMeetingGroups, parameter: [meetingId={}]", meetingId);

		List<Map<String, String>> meetingGroupMapList = Lists.newArrayList();

		List<MeetingGroupVo> meetingGroupList = meetingGroupService.queryMeetingGroupsByMeetingId(meetingId);
		for (MeetingGroupVo meetingGroup : meetingGroupList) {
			Map<String, String> meetingGroupMap = Maps.newLinkedHashMap();
			meetingGroupMap.put("id", meetingGroup.getId());
			meetingGroupMap.put("groupName", meetingGroup.getGroupName());
			meetingGroupMap.put("meetingId", meetingId);

			meetingGroupMapList.add(meetingGroupMap);
		}

		logger.debug("END: Mobile API call getMeetingGroups, return: [{}] ", meetingGroupMapList);
		return new ResponseEntity<List<Map<String, String>>>(meetingGroupMapList, HttpStatus.OK);
	}

	/**
	 * 查询部门考勤和明细
	 * 
	 * @param meetingId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/attendances", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAttendanceGroup(@RequestParam String meetingId,
			@RequestParam String groupId) {
		logger.debug("START:Mobile API call getAttendanceGroup, parameter: [meetingId={}, groupId={}]", meetingId,
				groupId);

		Map<String, Object> attendanceGroupMap = Maps.newHashMap();
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeeting(meetingId);

		// 部门整体统计
		Map<String, Object> attendanceGroupCountMap = Maps.newHashMap();
		AttendanceGroupVo attendanceGroupVo = attendanceGroupService.queryAttendanceGroup(meetingId, groupId);
		attendanceGroupCountMap.put("needJoinNum", attendanceGroupVo.getNeedJoinNum());
		attendanceGroupCountMap.put("actualNum", attendanceGroupVo.getActualNum());
		attendanceGroupCountMap.put("normalNum", attendanceGroupVo.getNormalNum());
		attendanceGroupCountMap.put("lateNum", attendanceGroupVo.getLateNum());
		attendanceGroupCountMap.put("leaveNum", attendanceGroupVo.getLeaveNum());
		attendanceGroupCountMap.put("leaveLateNum", attendanceGroupVo.getLeaveLateNum());
		attendanceGroupCountMap.put("holiday", attendanceGroupVo.getHolidayNum());
		attendanceGroupCountMap.put("absenceNum", attendanceGroupVo.getAbsenceNum());
		attendanceGroupCountMap.put("missingNum", attendanceGroupVo.getMissingNum());
		attendanceGroupCountMap.put("unPunchNum", attendanceGroupVo.getUnPunchNum());
		attendanceGroupCountMap.put("normalRates", attendanceGroupVo.getNormalRates());
		attendanceGroupMap.put("count", attendanceGroupCountMap);

		// 查询该部门用户考勤明细
		List<Map<String, Object>> attendanceGroupUserMapList = Lists.newArrayList();
		List<AttendanceUserVo> attendanceUserList = attendanceGroupService.queryAttendanceUsers(attendanceMeetingVo,
				groupId);
		for (AttendanceUserVo attendanceUser : attendanceUserList) {

			Map<String, Object> attendanceUserMap = Maps.newLinkedHashMap();
			attendanceUserMap.put("fullName", attendanceUser.getFullName());
			attendanceUserMap.put("status", attendanceUser.getStatusText());
			attendanceUserMap.put("signTime", attendanceUser.getSignTime());
			attendanceUserMap.put("logoutTime", attendanceUser.getLogoutTime());
			attendanceGroupUserMapList.add(attendanceUserMap);
		}
		attendanceGroupMap.put("users", attendanceGroupUserMapList);

		logger.debug("END: Mobile API call getAttendanceGroup, return: [{}] ", attendanceGroupMap);
		return new ResponseEntity<Map<String, Object>>(attendanceGroupMap, HttpStatus.OK);
	}

	/**
	 * 查询部门考勤和明细
	 * 
	 * @param meetingId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/attendances/count", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAttendanceGroupCount(@RequestParam String meetingId,
			@RequestParam String groupId) {
		logger.debug("START:Mobile API call getAttendanceGroupCount, parameter: [meetingId={}, groupId={}]", meetingId,
				groupId);

		// 部门整体统计
		Map<String, Object> attendanceGroupCountMap = Maps.newHashMap();
		AttendanceGroupVo attendanceGroupVo = attendanceGroupService.queryAttendanceGroup(meetingId, groupId);
		attendanceGroupCountMap.put("needJoinNum", attendanceGroupVo.getNeedJoinNum());
		attendanceGroupCountMap.put("actualNum", attendanceGroupVo.getActualNum());
		attendanceGroupCountMap.put("normalNum", attendanceGroupVo.getNormalNum());
		attendanceGroupCountMap.put("lateNum", attendanceGroupVo.getLateNum());
		attendanceGroupCountMap.put("leaveNum", attendanceGroupVo.getLeaveNum());
		attendanceGroupCountMap.put("leaveLateNum", attendanceGroupVo.getLeaveLateNum());
		attendanceGroupCountMap.put("holiday", attendanceGroupVo.getHolidayNum());
		attendanceGroupCountMap.put("absenceNum", attendanceGroupVo.getAbsenceNum());
		attendanceGroupCountMap.put("missingNum", attendanceGroupVo.getMissingNum());
		attendanceGroupCountMap.put("unPunchNum", attendanceGroupVo.getUnPunchNum());
		attendanceGroupCountMap.put("normalRates", attendanceGroupVo.getNormalRates());

		logger.debug("END: Mobile API call getAttendanceGroupCount, return: [{}] ", attendanceGroupCountMap);
		return new ResponseEntity<Map<String, Object>>(attendanceGroupCountMap, HttpStatus.OK);
	}

}
