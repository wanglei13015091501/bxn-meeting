/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;

/**
 * @author liumeng
 * @since bxc 1.0 2018年1月25日
 */
public class MeetingCyclingTask implements MeetingConstants {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	public void doTask() {
		logger.warn("MeetingCyclingTask doTask start, current: {}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));

		// 获取今天的日期对象
		Date todayTime = DateUtil.parseYYYY_MM_DDDate(DateUtil.toString_YYYY_MM_DD(new Date()));

		// 查询上一天，上一周，上一月设置为循环的会议
		List<AttendanceMeetingVo> attendanceMeetingList = attendanceMeetingService.getLastCyclingMeeting(todayTime);
		for (AttendanceMeetingVo attendanceMeetingVo : attendanceMeetingList) {

			attendanceMeetingService.copyAttendanceMeeting(attendanceMeetingVo.getId(),
					DateUtil.toString_YYYY_MM_DD(new Date()));
		}

		logger.warn("MeetingCyclingTask doTask end, current{}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));
	}
}
