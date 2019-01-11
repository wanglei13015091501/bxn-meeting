/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.AttendanceGroupDo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月24日
 */
public interface AttendanceGroupDao {

	List<AttendanceGroupDo> queryAttendanceGroup(List<String> meetingIds);
	
	List<AttendanceGroupDo> queryAttendanceGroup(List<String> meetingIds, String groupId);

	int createOrUpdateAttendanceGroup(AttendanceGroupDo attendanceGroupDo);

}
