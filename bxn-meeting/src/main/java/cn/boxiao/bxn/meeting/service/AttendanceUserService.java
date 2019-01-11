package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;

import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

public interface AttendanceUserService {

	/**
	 * 刷新考勤
	 * 
	 * @param meetingId
	 * @return
	 */
	void refreshAttendanceUser(String meetingId);

	/**
	 * 根据会议ID检查会议状态
	 * 
	 * @param meetingId
	 * @param isForceRefresh
	 *            是否强制刷新，如果是使用刷新会议考勤按钮触发，则不检查会议状态，直接检查考勤
	 */
	void checkMeetingStatus(String meetingId, boolean isForceRefresh);

	/**
	 * 统计参会人员的考勤状态
	 * 
	 * @param attendanceMeetingVo
	 * @param attendanceUserList
	 * @return
	 */
	Map<String, Object> countAttendanceStatus(AttendanceMeetingVo attendanceMeetingVo,
			List<AttendanceUserVo> attendanceUserList);

	/**
	 * 统计参会人员的考勤状态，大于1分钟从数据库中查询，小于1分钟直接从内存中获取
	 * 
	 * @param attendanceMeetingVo
	 * @param lastRefreshTime
	 * @return
	 */
	Map<String, Object> countAttendanceStatus(AttendanceMeetingVo attendanceMeetingVo, String lastRefreshTime);

	/**
	 * 根据会议ID查询会议考勤用户
	 * 
	 * @param meetingId
	 * @return
	 */
	List<AttendanceUserVo> getAttendanceUsers(String meetingId);

	/**
	 * 查询一卡通会议考勤系统用户
	 * 
	 * @return
	 */
	List<AttendanceUserVo> getAttendanceOneCardUsers();

	/**
	 * 更新人员考勤信息
	 * 
	 * @param attendanceUserVo
	 */
	void updateAttendanceUser(String ids, AttendanceUserVo attendanceUserVo);

	/**
	 * 更新人员考勤信息
	 * 
	 * @param attendanceUserVo
	 */
	void updateAttendanceUser(AttendanceUserVo attendanceUserVo);

	/**
	 * 重置单条考勤记录
	 * 
	 * @param meetingId
	 * @param userNames
	 */
	void resetAttendanceUser(String meetingId, String userNames);

	/**
	 * 获取会议打卡二维码
	 * 
	 * @param meetingId
	 * @return
	 */
	String getAttendanceMeetingQRCode(String meetingId);

	/**
	 * 发送会议通知
	 * 
	 * @param meetingId
	 * @param content
	 */
	void sendUserAttendanceNotify(String meetingId, String content);

	/**
	 * 根据会议ID获取指定会议参加人数
	 * @param meetingId
	 * @return
	 */
	int countAttendancePeople(String meetingId);

}
