package cn.boxiao.bxn.meeting.service;

import java.util.List;

import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

public interface AttendanceStatService {

	/**
	 * 查询个人统计列表
	 * 
	 * @param meetingTypeId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<AttendanceUserStatVo> getAttendanceStat(String meetingTypeId, String beginDate, String endDate);

	/**
	 * 查询指定人员，指定时间段内的整体考勤情况
	 * 
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	AttendanceUserStatVo getAttendanceStatByUserId(String userId, String beginDate, String endDate);

	/**
	 * 查询指定人员，指定时间段内各会议的考勤情况
	 * 
	 * @param meetingTypeId
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @param fromMobile
	 * @return
	 */
	List<AttendanceUserVo> getAttendanceUserDetail(String meetingTypeId, String userId, String beginDate,
			String endDate, boolean fromMobile);

}
