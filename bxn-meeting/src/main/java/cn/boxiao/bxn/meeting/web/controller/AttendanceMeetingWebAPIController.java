package cn.boxiao.bxn.meeting.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;
import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.service.MeetingTypeService;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class AttendanceMeetingWebAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private MeetingTypeService meetingTypeService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 创建考勤会议信息
	 * 
	 * @param attendanceMeetingVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meetings", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createAttendanceMeeting(AttendanceMeetingVo attendanceMeetingVo) {

		logger.debug("START: API call createAttendanceMeeting, parameter: [attendanceMeetingVo={}]",
				attendanceMeetingVo);

		// 校验入参
		if (StringUtils.isBlank(attendanceMeetingVo.getMeetingName())
				|| StringUtils.isBlank(attendanceMeetingVo.getPlaceId())
				|| StringUtils.isBlank(attendanceMeetingVo.getBeginTime())
				|| StringUtils.isBlank(attendanceMeetingVo.getEndTime())) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		LoggedUser userVo = SecurityUtil.getCurrentUser();
		attendanceMeetingVo.setCreatorId(String.valueOf(userVo.getId()));
		attendanceMeetingService.createAttendanceMeeting(attendanceMeetingVo);

		AuditLog.audit("创建考勤会议信息,会议内容:" + attendanceMeetingVo.toString());
		logger.debug("END: API call createAttendanceMeeting, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 根据会议类型获取部门信息
	 * 
	 * @param meetingType
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meetings/{meetingType}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getMeetingGroupByMeetingType(@PathVariable String meetingType) {
		logger.debug("START: API call getMeetingGroupByMeetingType, parameter: [meetingType={}]", meetingType);

		MeetingTypeVo meetingTypeVo = meetingTypeService.queryMeetingType(meetingType);
		MeetingGroupVo meetingGroupVo = meetingGroupService.queryMeetingGroup(meetingTypeVo.getGroupId());

		// 获取部门信息
		List<MeetingGroupVo> meetingGroupVoList = meetingGroupService
				.queryMeetingGroupProfilesByParent(meetingGroupVo.getId());

		Map<String, Object> meetingGroupMap = Maps.newHashMap();
		meetingGroupMap.put("groupId", meetingGroupVo.getId());
		meetingGroupMap.put("groupName", meetingGroupVo.getGroupName());

		List<Map<String, String>> meetingGroupMapList = Lists.newArrayList();
		for (MeetingGroupVo childMeetingGroupVo : meetingGroupVoList) {
			Map<String, String> childMeetingGroupMap = Maps.newHashMap();
			childMeetingGroupMap.put("groupId", childMeetingGroupVo.getId());
			childMeetingGroupMap.put("groupName", childMeetingGroupVo.getGroupName());

			meetingGroupMapList.add(childMeetingGroupMap);
		}
		meetingGroupMap.put("childMeetingGroup", meetingGroupMapList);

		logger.debug("END: API call getMeetingGroupByMeetingType, return: [{}] ", meetingGroupMap);
		return new ResponseEntity<Map<String, Object>>(meetingGroupMap, HttpStatus.OK);

	}

	/**
	 * 根据部门ID获取人员
	 * 
	 * @param groupId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meetings/group-users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getMeetingGroupUsers(@RequestParam String groupId) {
		logger.debug("START: API call getMeetingGroupUsers, parameter: [groupId={}]", groupId);

		List<MeetingGroupUserVo> groupUsers = meetingGroupService.queryMeetingGroupUsers(groupId);
		List<Map<String, String>> groupUserMapList = Lists.newArrayList();
		for (MeetingGroupUserVo meetingGroupUserVo : groupUsers) {
			Map<String, String> groupUserMap = Maps.newHashMap();
			groupUserMap.put("userId", meetingGroupUserVo.getUserId());
			groupUserMap.put("fullName", meetingGroupUserVo.getFullName());
			groupUserMap.put("uniqueNo", meetingGroupUserVo.getUniqueNo());

			groupUserMapList.add(groupUserMap);
		}

		logger.debug("END: API call getMeetingGroupUsers, return: [{}] ", groupUserMapList);
		return new ResponseEntity<List<Map<String, String>>>(groupUserMapList, HttpStatus.OK);
	}

	/**
	 * 更新考勤会议信息
	 * 
	 * @param attendanceMeetingVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meetings/{meetingId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateAttendanceMeeting(@PathVariable String meetingId,
			AttendanceMeetingVo attendanceMeetingVo) {

		logger.debug("START: API call updateAttendanceMeeting, parameter: [attendanceMeetingVo={}]",
				attendanceMeetingVo);

		// 校验入参
		if (StringUtils.isBlank(attendanceMeetingVo.getMeetingName())
				|| StringUtils.isBlank(attendanceMeetingVo.getPlaceId())
				|| StringUtils.isBlank(attendanceMeetingVo.getBeginTime())
				|| StringUtils.isBlank(attendanceMeetingVo.getEndTime())) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		attendanceMeetingVo.setId(meetingId);
		attendanceMeetingService.updateAttendanceMeeting(attendanceMeetingVo);

		AuditLog.audit("更新考勤会议信息,会议内容:" + attendanceMeetingVo.toString());
		logger.debug("END: API call updateAttendanceMeeting, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 复制考勤会议信息
	 * 
	 * @param meetingId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meeting-copy/{meetingId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> copyAttendanceMeeting(@PathVariable String meetingId) {

		logger.debug("START: API call copyAttendanceMeeting, parameter: [meetingId={}]", meetingId);

		attendanceMeetingService.copyAttendanceMeeting(meetingId, "");

		AuditLog.audit("复制考勤会议信息,会议ID:" + meetingId);
		logger.debug("END: API call copyAttendanceMeeting, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 删除考勤会议信息
	 * 
	 * @param meetingId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/meetings/{meetingId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteAttendanceMeeting(@PathVariable String meetingId) {

		logger.debug("START: API call deleteAttendanceMeeting, parameter: [meetingId={}]", meetingId);

		attendanceMeetingService.deleteAttendanceMeeting(meetingId);

		AuditLog.audit("删除考勤会议信息,会议ID:" + meetingId);
		logger.debug("END: API call deleteAttendanceMeeting, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
