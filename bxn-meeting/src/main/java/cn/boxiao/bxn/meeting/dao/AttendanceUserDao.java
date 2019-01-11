package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.AttendanceLogDo;
import cn.boxiao.bxn.meeting.data.AttendanceUserDo;

public interface AttendanceUserDao {

	/**
	 * 根据会议ID查询用户打卡日志
	 * 
	 * @param meetingId
	 * @return
	 */
	List<AttendanceLogDo> queryAttendanceLog(String meetingId);

	/**
	 * 批量创建会议考勤日志
	 * 
	 * @param attendanceLogDoList
	 * @return
	 */
	int[] createAttendanceLog(List<AttendanceLogDo> attendanceLogDoList);

	/**
	 * 根据会议ID查询会议考勤系统详情
	 * 
	 * @param meetingId
	 * @return
	 */
	List<AttendanceUserDo> queryMeetingUsers(String meetingId);
	
	/**
	 * 根据会议ID和人员userName查询会议考勤详情
	 * 
	 * @param meetingId
	 * @param userName
	 * @return
	 */
	AttendanceUserDo queryAttendanceUserByUserName(String meetingId, String userName);

	/**
	 * 根据ID查询考情信息
	 * 
	 * @param id
	 * @return
	 */
	AttendanceUserDo queryAttendanceUserById(String id);

	/**
	 * 查询考勤人员的考勤信息
	 * 
	 * @param meetingId
	 * @param userId
	 * @param deleted
	 * @return
	 */
	AttendanceUserDo queryAttendanceUserByUserId(String meetingId, String userId, String deleted);

	/**
	 * 根据用户ID查询用户参与的会议
	 * 
	 * @param userId
	 * @param meetingIdList
	 * @return
	 */
	List<AttendanceUserDo> queryMeetingUsersByUserId(String userId, List<String> meetingIdList);

	/**
	 * 查询指定会议的参会人员数
	 * 
	 * @param meetingId
	 * @return
	 */
	int countAttendanceUserByMeetingId(String meetingId);

	/**
	 * 初始化用户考勤，设置成未打卡状态
	 * 
	 * @param meetingId
	 * @return
	 */
	int initAttendanceUsers(String meetingId);

	/**
	 * 创建会议考勤人员明细
	 * 
	 * @param attendanceUserDo
	 * @return
	 */
	int createAttendanceUser(AttendanceUserDo attendanceUserDo);

	/**
	 * 更新人员考勤信息
	 * 
	 * @param attendanceUserDo
	 * @return
	 */
	int updateAttendanceUser(AttendanceUserDo attendanceUserDo);

	/**
	 * 重置单条考勤记录
	 * 
	 * @param meetingId
	 * @param userName
	 * @return
	 */
	int resetAttendanceUser(String meetingId, String userName);

	/**
	 * 根据userId更新人员打卡时间和状态
	 * 
	 * @param meetingId
	 * @param userName
	 * @param signTime
	 * @param logoutTime
	 * @return
	 */
	int updateAttendanceUserByUserName(String meetingId, String userName, String signTime, String logoutTime,
			String status);

	/**
	 * 删除人员考勤信息
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	int deleteAttendanceUserByUserId(String meetingId, String userId);

	/**
	 * 删除人员考勤信息
	 * 
	 * @param meeting
	 * @return
	 */
	int deleteAttendanceUsers(String meetingId);

}
