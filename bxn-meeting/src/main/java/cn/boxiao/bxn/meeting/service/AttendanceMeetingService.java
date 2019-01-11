package cn.boxiao.bxn.meeting.service;

import java.util.Date;
import java.util.List;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.model.Page;
import cn.boxiao.bxn.base.model.PageRequest;
import cn.boxiao.bxn.base.model.Pageable;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;

public interface AttendanceMeetingService {

	/**
	 * 获取指定时间段内的考勤会议列表
	 * 
	 * @param queryTime
	 * @return
	 */
	List<AttendanceMeetingVo> getAttendanceMeetingsByTime(String queryTime);

	/**
	 * 根据时间段查询会议列表
	 * 
	 * @param loggedUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<AttendanceMeetingVo> getAttendanceMeetings(LoggedUser loggedUser, String beginTime, String endTime);

	/**
	 * 获取考勤会议列表
	 * 
	 * @return
	 */
	Page<AttendanceMeetingVo> getAttendanceMeetings(LoggedUser userVo, Pageable page);

	/**
	 * 查询当天的会议
	 * 
	 * @return
	 */
	List<AttendanceMeetingVo> getTodayMeeting();

	/**
	 * 查询设置为循环的会议
	 * 
	 * @return
	 */
	List<AttendanceMeetingVo> getLastCyclingMeeting(Date today);

	/**
	 * 获取会议详情
	 * 
	 * @param meetingId
	 * @return
	 */
	AttendanceMeetingVo getAttendanceMeeting(String meetingId);

	/**
	 * 获取会议详情
	 * 
	 * @param meetingId
	 * @return
	 */
	AttendanceMeetingVo getAttendanceMeetingProfile(String meetingId);

	/**
	 * 创建会议
	 * 
	 * @param attendanceMeetingVo
	 */
	AttendanceMeetingVo createAttendanceMeeting(AttendanceMeetingVo attendanceMeetingVo);

	/**
	 * 复制会议
	 * 
	 * @param meetingId
	 */
	int copyAttendanceMeeting(String meetingId, String meetingDay);

	/**
	 * 編輯会议
	 * 
	 * @param attendanceMeetingVo
	 */
	int updateAttendanceMeeting(AttendanceMeetingVo attendanceMeetingVo);

	/**
	 * 删除会议
	 * 
	 * @param meetingId
	 */
	int deleteAttendanceMeeting(String meetingId);
	
	/**
	 * 根据时间段查询会议列表
	 * 
	 * @param loggedUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<AttendanceMeetingVo> getAttendanceMeetings(String beginTime, String endTime);

	/**
	 * 接口获取会议考勤列表
	 * @param page
	 * @return
	 */
	Page<AttendanceMeetingVo> getAttendanceMeetingsApi(PageRequest page);

}
