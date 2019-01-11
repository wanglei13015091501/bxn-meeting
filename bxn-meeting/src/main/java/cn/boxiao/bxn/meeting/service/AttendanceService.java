/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;

import cn.boxiao.bxn.meeting.vo.AttendanceOneCardVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月25日
 */
public interface AttendanceService {

	/**
	 * 推送用户打卡信息
	 * 
	 * @param userNames
	 * @param meetingId
	 * @param clockTime
	 * @param deviceId
	 * @return
	 */
	Map<String, String> postEpcRecord(String userNames, String meetingId, String clockTime, String deviceId);

	/**
	 * 推送用户打卡信息
	 * 
	 * @param userNames
	 * @param attendanceMeeting
	 * @param clockTime
	 * @return
	 */
	Map<String, String> rePostEpcRecord(String userNames, AttendanceMeetingVo attendanceMeeting, String clockTime);

	/**
	 * 二维码打卡
	 * 
	 * @param userName
	 * @param meetingId
	 * @param timestamp
	 * @param token
	 * @return
	 */
	Map<String, String> postEpcRecordByQRCode(String userName, String meetingId, String timestamp, String token);
	
	/**
	 * 一开通打卡
	 * 
	 * @param attendanceList
	 * @return
	 */
	Map<String, String> postEpcRecordByOneCard(List<AttendanceOneCardVo> attendanceList);

}
