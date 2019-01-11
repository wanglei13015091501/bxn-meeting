package cn.boxiao.bxn.meeting.web.controller;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.google.common.collect.Maps;

import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;
import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.common.util.FileDownloadUtils;
import cn.boxiao.bxn.meeting.service.AttendanceGroupExportService;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class AttendanceUserWebAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceUserService attendanceUserService;

	@Autowired
	private AttendanceGroupExportService attendanceGroupExportService;

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 更新人员考勤信息
	 * 
	 * @param attendanceUserVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendances", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateUserAttendance(@RequestParam String ids, AttendanceUserVo attendanceUserVo) {
		logger.debug("START: API call updateUserAttendance, parameter: [ids={}, attendanceUserVo={}]", ids,
				attendanceUserVo);

		// 如果没有做任何修改直接返回
		if (StringUtils.isBlank(attendanceUserVo.getModifySignTime())
				&& StringUtils.isBlank(attendanceUserVo.getModifyLogoutTime())
				&& StringUtils.isBlank(attendanceUserVo.getModifyStatus())
				&& StringUtils.isBlank(attendanceUserVo.getDescription())) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		// 更新操作
		attendanceUserService.updateAttendanceUser(ids, attendanceUserVo);

		AuditLog.audit("更新参会人员考勤信息成功,更新内容: " + ids + ", " + attendanceUserVo.toString());

		logger.debug("END: API call updateUserAttendance, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 更新人员考勤信息
	 * 
	 * @param attendanceUserVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendances", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> resetUserAttendance(@RequestParam String meetingId, @RequestParam String userNames) {
		logger.debug("START: API call resetUserAttendance, parameter: [meetingId={},userNames={}]", meetingId,
				userNames);

		// 如果入参为空
		if (StringUtils.isBlank(meetingId) || StringUtils.isBlank(userNames)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "入参为空");
		}

		// 重置操作
		attendanceUserService.resetAttendanceUser(meetingId, userNames);

		AuditLog.audit("重置参会人员考勤信息成功,会议ID:" + meetingId + "用户名称:" + userNames);

		logger.debug("END: API call resetUserAttendance, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 刷新考勤
	 * 
	 * @param meetingId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-refresh", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> refreshUserAttendance(@RequestParam String meetingId) {
		logger.debug("START: API call refreshUserAttendance, parameter: [meetingId={}]", meetingId);

		attendanceUserService.refreshAttendanceUser(meetingId);

		AuditLog.audit("刷新会议考勤信息成功,会议ID:" + meetingId);

		logger.debug("END: API call refreshUserAttendance, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	/**
	 * 获取会议打卡二维码
	 * 
	 * @param meetingId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-qrCodes", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, String>> getAttendanceMeetingQRCode(@RequestParam String meetingId) {
		logger.debug("START: API call getAttendanceMeetingQRCode, parameter: [meetingId={}]", meetingId);

		if (StringUtils.isBlank(meetingId)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "会议ID为空");
		}

		String qrUrl = attendanceUserService.getAttendanceMeetingQRCode(meetingId);
		Map<String, String> qrUrlMap = Maps.newHashMap();
		qrUrlMap.put("qrUrl", qrUrl);

		logger.debug("END: API call getAttendanceMeetingQRCode, return: [{}] ", qrUrlMap);
		return new ResponseEntity<Map<String, String>>(qrUrlMap, HttpStatus.OK);
	}

	/**
	 * 发送会议通知
	 * 
	 * @param meetingId
	 * @param content
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-notices", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> sendUserAttendanceNotify(@RequestParam String meetingId,
			@RequestParam String content) {
		logger.debug("START: API call sendUserAttendanceNotify, parameter: [meetingId={},content={}]", meetingId,
				content);

		if (StringUtils.isBlank(meetingId)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "会议ID为空");
		}
		if (StringUtils.isBlank(content)) {
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALREQUESTPARAMEXCEPTION, "会议通知内容为空");
		} else if (StringUtils.length(content) > 130) {
			content = content.substring(0, 130);
		}

		attendanceUserService.sendUserAttendanceNotify(meetingId, content);

		logger.debug("END: API call sendUserAttendanceNotify, return: [HttpStatus.OK] ");
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	/**
	 * 导出部门考勤
	 * 
	 * @param response
	 * @param request
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-export", method = RequestMethod.GET)
	public void exportGroupAttendance(@RequestParam String meetingId, HttpServletResponse response,
			HttpServletRequest request) {
		logger.debug("START: API call exportGroupAttendance, parameter: [meetingId={}]", meetingId);

		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition", "attachment; filename="
				+ FileDownloadUtils.fileNameToUtf8String(request, attendanceMeetingVo.getMeetingName() + "部门考勤.xls"));
		response.resetBuffer();
		HSSFWorkbook workbook = attendanceGroupExportService.exportAttendanceGroups(attendanceMeetingVo);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("导出部门考勤失败，错误信息:", e);
		}
		logger.debug("导出部门考勤成功。");
		logger.debug("END: API call exportGroupAttendance. ");
	}

	/**
	 * 导出部门考勤明细
	 * 
	 * @param response
	 * @param request
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/attendance-export/{groupId}", method = RequestMethod.GET)
	public void exportGroupDetail(@PathVariable String groupId, @RequestParam String meetingId,
			HttpServletResponse response, HttpServletRequest request) {
		logger.debug("START: API call exportGroupDetail, parameter: [groupId={}, meetingId={}]", groupId, meetingId);

		// 获取会议详情
		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);

		// 获取会议部门
		MeetingGroupVo meetingGroupVo = meetingGroupService.queryMeetingGroup(groupId);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition",
				"attachment; filename=" + FileDownloadUtils.fileNameToUtf8String(request,
						attendanceMeetingVo.getMeetingName() + " - " + meetingGroupVo.getGroupName() + "考勤明细.xls"));
		response.resetBuffer();
		HSSFWorkbook workbook = attendanceGroupExportService.exportAttendanceUsers(attendanceMeetingVo, groupId);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("导出会议考勤部门明细失败，错误信息:", e);
		}
		logger.debug("导出会议考勤部门明细成功。");
		logger.debug("END: API call exportGroupDetail. ");
	}

}
