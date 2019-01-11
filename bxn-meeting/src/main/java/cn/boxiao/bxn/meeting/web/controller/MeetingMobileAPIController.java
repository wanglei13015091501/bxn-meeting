package cn.boxiao.bxn.meeting.web.controller;

import java.util.Calendar;
import java.util.Date;
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
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceStatService;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1/mobile")
public class MeetingMobileAPIController implements MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceStatService attendanceStatService;

	@Autowired
	private AttendanceUserService attendanceUserService;

	/**
	 * 查询会议状态
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/attendances", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getAttendanceMeetings(@RequestParam String beginDate,
			@RequestParam String endDate) {
		logger.debug("START:Mobile API call getAttendanceMeetings, parameter: [beginDate={}, endDate={}]", beginDate,
				endDate);

		List<Map<String, Object>> attendanceMeetingMapList = Lists.newArrayList();

		LoggedUser loggedUser = SecurityUtil.getCurrentUser();
		List<AttendanceMeetingVo> attendanceMeetingVoList = attendanceMeetingService.getAttendanceMeetings(loggedUser,
				beginDate, endDate);
		for (AttendanceMeetingVo attendanceMeetingVo : attendanceMeetingVoList) {

			Map<String, Object> attendanceMeetingMap = convertAttendanceMeetingMap(attendanceMeetingVo);
			attendanceMeetingMapList.add(attendanceMeetingMap);
		}
		logger.debug("END: Mobile API call getAttendanceMeetings, return: [{}] ", attendanceMeetingMapList);
		return new ResponseEntity<List<Map<String, Object>>>(attendanceMeetingMapList, HttpStatus.OK);
	}

	/**
	 * 根据会议ID查询会议明细以及人员状态明细
	 * 
	 * @param meetingId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAttendanceUsers(@RequestParam String meetingId) {
		logger.debug("START:Mobile API call getAttendanceUsers, parameter: [meetingId={}]", meetingId);

		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);

		Map<String, Object> attendanceMeetingMap = convertAttendanceMeetingMap(attendanceMeetingVo);
		attendanceMeetingMap.put("signTime", attendanceMeetingVo.getSignTime()); // 会议的开始签到时间
		attendanceMeetingMap.put("logoutTime", attendanceMeetingVo.getLogoutTime()); // 会议的签退时间

		// 查询参会人员状态
		List<AttendanceUserVo> attendanceUserList = attendanceUserService.getAttendanceUsers(meetingId);
		Map<String, Object> userStatusMap = attendanceUserService.countAttendanceStatus(attendanceMeetingVo,
				attendanceUserList);
		attendanceMeetingMap.put("userStatusMap", userStatusMap);

		List<Map<String, String>> attendanceMapList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : attendanceUserList) {
			Map<String, String> attendanceUserMap = Maps.newHashMap();
			attendanceUserMap.put("id", attendanceUserVo.getId());
			attendanceUserMap.put("userName", attendanceUserVo.getUserName());
			attendanceUserMap.put("fullName", attendanceUserVo.getFullName());
			attendanceUserMap.put("signTime", attendanceUserVo.getSignTime());
			attendanceUserMap.put("logoutTime", attendanceUserVo.getLogoutTime());
			attendanceUserMap.put("status", attendanceUserVo.getStatus());
			attendanceUserMap.put("uniqueNo", attendanceUserVo.getUniqueNo());
			attendanceUserMap.put("description", attendanceUserVo.getDescription());

			attendanceMapList.add(attendanceUserMap);
		}

		attendanceMeetingMap.put("attendanceMapList", attendanceMapList);

		logger.debug("END: Mobile API call getAttendanceUsers, return: [{}] ", attendanceMeetingMap);
		return new ResponseEntity<Map<String, Object>>(attendanceMeetingMap, HttpStatus.OK);

	}

	/**
	 * 会议明细以及人员状态明细(普通参会人员查询)
	 * 
	 * @param meetingId
	 * @param lastRefreshTime
	 * @return
	 */
	@Secured({ "PERM_USER" })
	@RequestMapping(value = "/attendance-users/count", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getAttendanceUsersCount(@RequestParam String meetingId,
			@RequestParam(required = false) String lastRefreshTime) {
		logger.debug("START:Mobile API call getAttendanceUsersCount, parameter: [meetingId={},lastRefreshTime]",
				meetingId, lastRefreshTime);

		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);

		Map<String, Object> attendanceMeetingMap = convertAttendanceMeetingMap(attendanceMeetingVo);
		attendanceMeetingMap.put("signTime", attendanceMeetingVo.getSignTime()); // 会议的开始签到时间
		attendanceMeetingMap.put("logoutTime", attendanceMeetingVo.getLogoutTime()); // 会议的签退时间

		Map<String, Object> userStatusMap = attendanceUserService.countAttendanceStatus(attendanceMeetingVo,
				lastRefreshTime);
		attendanceMeetingMap.put("userStatusMap", userStatusMap);

		logger.debug("END: Mobile API call getAttendanceUsersCount, return: [{}] ", attendanceMeetingMap);
		return new ResponseEntity<Map<String, Object>>(attendanceMeetingMap, HttpStatus.OK);
	}

	/**
	 * 根据状态查询参会人员考勤明细
	 * 
	 * @param meetingId
	 * @param status
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-users/status", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getUserAttendanceByStatus(@RequestParam String meetingId,
			@RequestParam String status) {
		logger.debug("START:Mobile API call getUserAttendanceByStatus, parameter: [meetingId={},status]", meetingId,
				status);

		// 查询参会人员状态
		List<AttendanceUserVo> attendanceUserList = attendanceUserService.getAttendanceUsers(meetingId);

		// 根据状态帅选
		List<AttendanceUserVo> filterAttendanceUserList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : attendanceUserList) {
			if (StringUtils.equals(status, MEETING_USER_STATUS_ABSENCE_LEAVE)) { // 请假的状态匹配公假、私假、病假
				if (StringUtils.equals(attendanceUserVo.getStatus(), MEETING_USER_STATUS_ABSENCE_PUBLIC)
						|| StringUtils.equals(attendanceUserVo.getStatus(), MEETING_USER_STATUS_ABSENCE_PRIVATE)
						|| StringUtils.equals(attendanceUserVo.getStatus(), MEETING_USER_STATUS_ABSENCE_SICK)) {
					filterAttendanceUserList.add(attendanceUserVo);
				} else {
					continue;
				}
			} else {
				if (!StringUtils.equals(attendanceUserVo.getStatus(), status)) {
					continue;
				} else {
					filterAttendanceUserList.add(attendanceUserVo);
				}
			}

		}

		List<Map<String, String>> attendanceMapList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : filterAttendanceUserList) {
			Map<String, String> attendanceUserMap = Maps.newHashMap();
			attendanceUserMap.put("id", attendanceUserVo.getId());
			attendanceUserMap.put("userName", attendanceUserVo.getUserName());
			attendanceUserMap.put("fullName", attendanceUserVo.getFullName());
			attendanceUserMap.put("signTime", attendanceUserVo.getSignTime());
			attendanceUserMap.put("logoutTime", attendanceUserVo.getLogoutTime());
			attendanceUserMap.put("status", attendanceUserVo.getStatus());
			attendanceUserMap.put("uniqueNo", attendanceUserVo.getUniqueNo());
			attendanceUserMap.put("description", attendanceUserVo.getDescription());

			attendanceMapList.add(attendanceUserMap);
		}

		logger.debug("END: Mobile API call getUserAttendanceByStatus, return: [{}] ", attendanceMapList);
		return new ResponseEntity<List<Map<String, String>>>(attendanceMapList, HttpStatus.OK);

	}

	/**
	 * 根据全名查询参会人员考勤明细
	 * 
	 * @param meetingId
	 * @param fullName
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-users/fullName", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getUserAttendanceByFullName(@RequestParam String meetingId,
			@RequestParam String fullName) {
		logger.debug("START:Mobile API call getUserAttendanceByFullName, parameter: [meetingId={},fullName]", meetingId,
				fullName);

		// 查询参会人员状态
		List<AttendanceUserVo> attendanceUserList = attendanceUserService.getAttendanceUsers(meetingId);

		// 根据用户名称筛选
		List<AttendanceUserVo> filterAttendanceUserList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : attendanceUserList) {
			if (!StringUtils.contains(attendanceUserVo.getFullName(), fullName)) {
				continue;
			} else {
				filterAttendanceUserList.add(attendanceUserVo);
			}
		}

		List<Map<String, String>> attendanceMapList = Lists.newArrayList();
		for (AttendanceUserVo attendanceUserVo : filterAttendanceUserList) {
			Map<String, String> attendanceUserMap = Maps.newHashMap();
			attendanceUserMap.put("id", attendanceUserVo.getId());
			attendanceUserMap.put("userName", attendanceUserVo.getUserName());
			attendanceUserMap.put("fullName", attendanceUserVo.getFullName());
			attendanceUserMap.put("signTime", attendanceUserVo.getSignTime());
			attendanceUserMap.put("logoutTime", attendanceUserVo.getLogoutTime());
			attendanceUserMap.put("status", attendanceUserVo.getStatus());
			attendanceUserMap.put("uniqueNo", attendanceUserVo.getUniqueNo());
			attendanceUserMap.put("description", attendanceUserVo.getDescription());

			attendanceMapList.add(attendanceUserMap);
		}

		logger.debug("END: Mobile API call getUserAttendanceByFullName, return: [{}] ", attendanceMapList);
		return new ResponseEntity<List<Map<String, String>>>(attendanceMapList, HttpStatus.OK);
	}

	/**
	 * 更新参会人员的考勤信息
	 * 
	 * @param attendanceUserVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-users", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUserAttendance(@RequestParam String id, @RequestParam String modifySignTime,
			@RequestParam String modifyLogoutTime, @RequestParam String modifyStatus,
			@RequestParam String description) {
		logger.debug(
				"START: API call updateUserAttendance, parameter: [id={},modifySignTime={},modifyLogoutTime={},modifyStatus={},description={}]",
				id, modifySignTime, modifyLogoutTime, modifyStatus, description);

		// 如果没有做任何修改直接返回
		if (StringUtils.isBlank(modifySignTime) && StringUtils.isBlank(modifyLogoutTime)
				&& StringUtils.isBlank(modifyStatus) && StringUtils.isBlank(description)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		AttendanceUserVo attendanceUserVo = new AttendanceUserVo();
		attendanceUserVo.setId(id);
		attendanceUserVo.setModifySignTime(modifySignTime);
		attendanceUserVo.setModifyLogoutTime(modifyLogoutTime);
		attendanceUserVo.setModifyStatus(modifyStatus);
		attendanceUserVo.setDescription(description);
		// 更新操作
		attendanceUserService.updateAttendanceUser(attendanceUserVo);

		AuditLog.audit("手机测更新参会人员考勤信息成功,更新内容:" + attendanceUserVo.toString());

		logger.debug("END: API call updateUserAttendance, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 重置单条考勤记录
	 * 
	 * @param meetingId
	 * @param userName
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-reset-users", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> resetUserAttendance(@RequestParam String meetingId, @RequestParam String userName) {
		logger.debug("START: API call resetUserAttendance, parameter: [meetingId={},userName={}]", meetingId, userName);

		// 如果没有做任何修改直接返回
		if (StringUtils.isBlank(meetingId) || StringUtils.isBlank(userName)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		// 重置操作
		attendanceUserService.resetAttendanceUser(meetingId, userName);

		AuditLog.audit("重置参会人员考勤信息成功,会议ID:" + meetingId + "用户名称:" + userName);

		logger.debug("END: API call resetUserAttendance, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 查询会议统计列表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/attendances-stat", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getMeetingAttendanceStat(@RequestParam String userId,
			@RequestParam String beginDate, @RequestParam String endDate) {
		logger.debug("START:Mobile API call getMeetingAttendanceStat, parameter: [beginDate={}, endDate={}]", beginDate,
				endDate);

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
		AttendanceUserStatVo userStatVo = attendanceStatService.getAttendanceStatByUserId(userId, beginDate, endDate);

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("userStat", userStatVo);

		List<AttendanceUserVo> attendanceUserListVo = attendanceStatService.getAttendanceUserDetail("", userId,
				beginDate, endDate, true);
		resultMap.put("attendanceUserListVo", attendanceUserListVo);

		logger.debug("END: Mobile API call getMeetingAttendanceStat, return: [{}] ", resultMap);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

	/**
	 * 会议状态以及人员状态
	 * 
	 * @param attendanceMeetingVo
	 * @return
	 */
	private Map<String, Object> convertAttendanceMeetingMap(AttendanceMeetingVo attendanceMeetingVo) {
		Map<String, Object> attendanceMeetingMap = Maps.newHashMap();
		attendanceMeetingMap.put("meetingId", attendanceMeetingVo.getId());
		attendanceMeetingMap.put("meetingName", attendanceMeetingVo.getMeetingName());
		attendanceMeetingMap.put("status", attendanceMeetingVo.getStatus());
		attendanceMeetingMap.put("beginTime", attendanceMeetingVo.getBeginTime());
		attendanceMeetingMap.put("endTime", attendanceMeetingVo.getEndTime());
		attendanceMeetingMap.put("placeName", attendanceMeetingVo.getPlaceName());
		attendanceMeetingMap.put("userNum", attendanceMeetingVo.getUserNum());
		attendanceMeetingMap.put("organizerIds", attendanceMeetingVo.getOrganizerIds());

		return attendanceMeetingMap;
	}

}
