package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.AttendanceUserStatDo;

public interface AttendanceUserStatDao {

	/**
	 * 查询考勤统计信息
	 * 
	 * @param meetingIds
	 * @return
	 */
	List<AttendanceUserStatDo> queryAttendanceUserStat(List<String> meetingIds);

	/**
	 * 查询个人考勤统计信息
	 * 
	 * @param meetingIds
	 * @param userId
	 * @return
	 */
	AttendanceUserStatDo queryAttendanceUserStatByUserId(List<String> meetingIds, String userId);
	
	/**
	 * 查询考勤统计信息，只查询应参加会议数和正常会议数
	 * 
	 * @param meetingIds
	 * @return
	 */
	List<AttendanceUserStatDo> queryAttendanceNormalUserStat(List<String> meetingIds);
}
