package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.List;

import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.Pageable;
import cn.boxiao.bxn.meeting.data.AttendanceMeetingDo;
import cn.boxiao.bxn.meeting.data.AttendanceOrganizerDo;

public interface AttendanceMeetingDao {

	/**
	 * 查询考勤会议列表
	 * 
	 * @param userId
	 * @param meetingIds
	 * @param page
	 * @return
	 */
	Page<AttendanceMeetingDo> queryAttendanceMeetings(String userId, List<String> meetingIds, Pageable page);

	/**
	 * 查询指定时间段内的考勤会议列表
	 * 
	 * @return
	 */
	List<AttendanceMeetingDo> queryAttendanceMeetingsByTime(String queryTime);

	/**
	 * 按照时间段查询会议列表
	 * 
	 * @param meetingTypeId
	 * @param beginTime
	 * @param endTime
	 * @param fromMobile
	 * @return
	 */
	List<AttendanceMeetingDo> queryAttendanceMeetingsByTime(String meetingTypeId, String beginTime, String endTime,
			boolean fromMobile);

	/**
	 * 查询设置为循环的会议
	 * 
	 * @return
	 */
	List<AttendanceMeetingDo> queryLastCyclingAttendanceMeeting(Date today);

	/**
	 * 查询会议基础信息
	 * 
	 * @param meetingId
	 * @return
	 */
	AttendanceMeetingDo queryAttendanceMeeting(String meetingId);

	/**
	 * 创建会议信息
	 * 
	 * @param attendanceMeetingDo
	 * @return
	 */
	AttendanceMeetingDo createAttendanceMeeting(AttendanceMeetingDo attendanceMeetingDo);

	/**
	 * 更新会议信息
	 * 
	 * @param attendanceMeetingDo
	 * @return
	 */
	int updateAttendanceMeeting(AttendanceMeetingDo attendanceMeetingDo);

	/**
	 * 更新会议状态
	 * 
	 * @param meetingId
	 * @param status
	 * @return
	 */
	int updateAttendanceMeetingStatus(String meetingId, String status);

	/**
	 * 删除会议信息
	 * 
	 * @param meetingId
	 * @return
	 */
	int deleteAttendanceMeeting(String meetingId);

	/**
	 * 查询指定会议的组织者信息
	 * 
	 * @param meetingId
	 * @return
	 */
	List<AttendanceOrganizerDo> queryMeetingOrganizers(String meetingId);

	/**
	 * 查询组织人员信息
	 * 
	 * @param userId
	 * @return
	 */
	List<AttendanceOrganizerDo> queryAttendanceOrganizersByUserId(String userId);

	/**
	 * 创建会议组织者信息
	 * 
	 * @param attendanceOrganizerDo
	 * @return
	 */
	AttendanceOrganizerDo createMeetingOrganizers(AttendanceOrganizerDo attendanceOrganizerDo);

	/**
	 * 删除会议组织者信息
	 * 
	 * @param meetingId
	 * @return
	 */
	int deleteAttendanceOrganizers(String meetingId);
}
