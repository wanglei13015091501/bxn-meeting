package cn.boxiao.bxn.meeting.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;

public interface AttendanceGroupExportService {

	/**
	 * 导出会议考勤各部门考勤数据和部门明细
	 * @param attendanceMeetingVo
	 * @return
	 */
	HSSFWorkbook exportAttendanceGroups(AttendanceMeetingVo attendanceMeetingVo);

	/**
	 * 导出会议考勤指定部门考勤明细
	 * @param attendanceMeetingVo
	 * @param groupId
	 * @return
	 */
	HSSFWorkbook exportAttendanceUsers(AttendanceMeetingVo attendanceMeetingVo, String groupId);

	/**
	 * 导出会议考勤统计全勤用户
	 * @param meetingTypeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	HSSFWorkbook exportAttendanceNormalUsers(String meetingTypeId, String beginDate, String endDate);

	/**
	 * 导出会议考勤统计指定部门全勤用户
	 * @param meetingTypeId
	 * @param groupId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	HSSFWorkbook exportAttendanceNormalUsers(String meetingTypeId, String groupId, String beginDate, String endDate);

}
