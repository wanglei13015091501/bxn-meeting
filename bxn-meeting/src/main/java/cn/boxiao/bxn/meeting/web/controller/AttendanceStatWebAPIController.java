package cn.boxiao.bxn.meeting.web.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.boxiao.bxn.common.util.FileDownloadUtils;
import cn.boxiao.bxn.meeting.service.AttendanceGroupExportService;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class AttendanceStatWebAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private AttendanceGroupExportService attendanceGroupExportService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 导出所有部门全勤人员
	 * 
	 * @param response
	 * @param request
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/stat-normal-export", method = RequestMethod.GET)
	public void exportGroupNormalUsers(@RequestParam String beginDate, @RequestParam String endDate,
			@RequestParam String meetingTypeId, HttpServletResponse response, HttpServletRequest request) {
		logger.debug("START: API call exportGroupNormalUsers, parameter: [beginDate={}, endDate={}, meetingTypeId={}]",
				beginDate, endDate, meetingTypeId);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition",
				"attachment; filename=" + FileDownloadUtils.fileNameToUtf8String(request, "全勤人员.xls"));
		response.resetBuffer();
		HSSFWorkbook workbook = attendanceGroupExportService.exportAttendanceNormalUsers(meetingTypeId, beginDate, endDate);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("导出部门全勤人员失败，错误信息:", e);
		}
		logger.debug("导出部门全勤人员成功。");
		logger.debug("END: API call exportGroupNormalUsers. ");
	}

	/**
	 * 导出部门全勤人员
	 * 
	 * @param response
	 * @param request
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/stat-group-normal-export/{groupId}", method = RequestMethod.GET)
	public void exportGroupNormalUsers(@PathVariable String groupId, @RequestParam String beginDate,
			@RequestParam String endDate, @RequestParam String meetingTypeId, HttpServletResponse response,
			HttpServletRequest request) {
		logger.debug(
				"START: API call exportGroupNormalUsers, parameter: [groupId={}, beginDate={}, endDate={}, meetingTypeId={}]",
				groupId, beginDate, endDate, meetingTypeId);

		// 部门信息
		MeetingGroupVo meetingGroup = meetingGroupService.queryMeetingGroup(groupId);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition", "attachment; filename="
				+ FileDownloadUtils.fileNameToUtf8String(request, meetingGroup.getGroupName() + "全勤人员.xls"));
		response.resetBuffer();
		HSSFWorkbook workbook = attendanceGroupExportService.exportAttendanceNormalUsers(meetingTypeId, groupId, beginDate,
				endDate);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("导出部门全勤人员失败，错误信息:", e);
		}
		logger.debug("导出部门全勤人员成功。");
		logger.debug("END: API call exportGroupNormalUsers. ");
	}

}
