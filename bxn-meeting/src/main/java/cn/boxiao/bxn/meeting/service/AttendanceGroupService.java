package cn.boxiao.bxn.meeting.service;

import java.util.List;

import cn.boxiao.bxn.meeting.data.AttendanceUserDo;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

public interface AttendanceGroupService {

	/**
	 * 查询某一会议类型，一段时间内部门考勤统计
	 * 
	 * @param meetingTypeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<AttendanceGroupStatVo> queryAttendanceGroupStat(String meetingTypeId, String beginDate, String endDate);

	/**
	 * 计算各部门总计考勤信息
	 * 
	 * @param groupStatVoList
	 * @return
	 */
	AttendanceGroupStatVo computeAttendanceGroupStatSummary(List<AttendanceGroupStatVo> groupStatVoList);

	/**
	 * 查询某一个会议类型，某一部门，一段时间内部门考勤信息
	 * 
	 * @param meetingTypeId
	 * @param meetingGroupId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<AttendanceGroupVo> queryAttendanceGroups(String meetingTypeId, String meetingGroupId, String beginDate,
			String endDate);

	/**
	 * 根据会议ID查询各部门考勤统计
	 * 
	 * @param meetingId
	 * @return
	 */
	List<AttendanceGroupVo> queryAttendanceGroups(String meetingId);

	/**
	 * 根据会议ID查询指定部门考勤统计
	 * 
	 * @param meetingId
	 * @param groupId
	 * @return
	 */
	AttendanceGroupVo queryAttendanceGroup(String meetingId, String groupId);

	/**
	 * 计算部门考勤总计
	 * 
	 * @param attendanceGroupList
	 * @return
	 */
	AttendanceGroupVo computeAttendanceGroupSummary(List<AttendanceGroupVo> attendanceGroupList);

	/**
	 * 根据会议ID和部门ID查询用户考勤
	 * 
	 * @param attendanceMeetingVo
	 * @param groupId
	 * @return
	 */
	List<AttendanceUserVo> queryAttendanceUsers(AttendanceMeetingVo attendanceMeetingVo, String groupId);

	/**
	 * 查询某一会议类型，一段时间内全勤用户信息
	 * 
	 * @param meetingTypeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<AttendanceUserStatVo> queryAttendanceNormalUsers(String meetingTypeId, String beginDate, String endDate);

	/**
	 * 查询某一会议类型/某一部门，一段时间内全勤用户信息
	 * 
	 * @param meetingTypeId
	 * @param groupId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<AttendanceUserStatVo> queryAttendanceNormalUsers(String meetingTypeId, String groupId, String beginDate,
			String endDate);

	/**
	 * 创建部门考勤信息
	 * 
	 * @param meetingId
	 * @param attendanceUserDoList
	 */
	void createAttendanceGroup(String meetingId, List<AttendanceUserDo> attendanceUserDoList);

}
