package cn.boxiao.bxn.meeting.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.AttendanceMeetingService;
import cn.boxiao.bxn.meeting.service.AttendanceRuleService;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;

@Component
public class MeetingCache implements MeetingConstants {

	private static Map<String, AttendanceMeetingVo> meetingMap = Maps.newHashMap(); // 保存最近的会议信息

	private static Map<String, AttendanceRuleVo> attendanceRuleMap = Maps.newHashMap(); // 保存会议考勤规则

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private AttendanceRuleService attendanceRuleService;

	/**
	 * 获取会议信息
	 * 
	 * @return
	 */
	public Map<String, AttendanceMeetingVo> getMeeting() {
		if (meetingMap.isEmpty()) {
			refreshMeeting();
		} else if (!isTodayDayMeeting()) {
			refreshMeeting();
		}
		return meetingMap;
	}

	/**
	 * 刷新会议信息，只获取当天的会议信息，刷新时直接从数据库中获取即可
	 */
	public void refreshMeeting() {
		meetingMap.clear();

		List<AttendanceMeetingVo> meetingList = attendanceMeetingService.getTodayMeeting();

		for (AttendanceMeetingVo attendanceMeetingVo : meetingList) {
			meetingMap.put(attendanceMeetingVo.getId(), attendanceMeetingVo);
		}
	}

	/**
	 * 判断是否是当天的会议数据
	 * 
	 * @return
	 */
	private boolean isTodayDayMeeting() {

		if (MapUtils.isEmpty(meetingMap)) {
			return false;
		}

		AttendanceMeetingVo attendanceMeetingVo = meetingMap.values().iterator().next();

		// 判断是否是当天的数据，如果不是，清除掉重新获取
		String today = DateUtil.toString_YYYY_MM_DD(new Date());

		String meetingDay = DateUtil
				.toString_YYYY_MM_DD(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(attendanceMeetingVo.getBeginTime()));

		return StringUtils.equals(today, meetingDay);

	}

	/**
	 * 获取考勤规则
	 * 
	 * @return
	 */
	public Map<String, AttendanceRuleVo> getAttendanceRule() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		return attendanceRuleMap;
	}

	/**
	 * 更新考勤规则
	 */
	public void refreshAttendanceRule() {
		attendanceRuleMap.clear();
		attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
	}

	public long getNormalSignSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 获取正常签到定义，在会议开始前XX分钟内打卡
		return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_NORMAL_SIGN).getRuleValue()) * 60 * 1000;
	}

	public long getNormalLogoutSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 获取正常签退定义，在会议结束后XX分钟分钟内打卡
		return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_NORMAL_LOGOUT).getRuleValue()) * 60 * 1000;
	}

	public long getLateSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 迟到时间在XX分钟内不计
		return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_LATE).getRuleValue()) * 60 * 1000;
	}

	public long getLeaveSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 早退时间在XX分钟内不计
		return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_LEAVE).getRuleValue()) * 60 * 1000;
	}

	public long getSignInterval() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 缺勤定义,签到——签退时间范围打卡，但签到签退有效时间间隔在XX分钟内
		if (StringUtils.equals(STATUS_OPEN, attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_TIME_CLOCK).getStatus())) {
			return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_TIME_CLOCK).getRuleValue()) * 60
					* 1000;
		}
		return 0l;
	}

	public long getMaxLateSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 缺勤定义,迟到XX分钟以上
		if (StringUtils.equals(STATUS_OPEN, attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LATE).getStatus())) {
			return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LATE).getRuleValue()) * 60 * 1000;
		}
		return 0l;
	}

	public long getMaxLeaveSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 缺勤定义,早退XX分钟以上
		if (StringUtils.equals(STATUS_OPEN, attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LEAVE).getStatus())) {
			return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_ABSENCE_LEAVE).getRuleValue()) * 60 * 1000;
		}
		return 0l;
	}

	public long getNoticeSecond() {
		if (attendanceRuleMap.isEmpty()) {
			attendanceRuleMap = attendanceRuleService.queryAttendanceRules();
		}
		// 发送会议提醒短信
		if (StringUtils.equals(STATUS_OPEN, attendanceRuleMap.get(ATTENDANCE_RULE_NOTICE).getStatus())) {
			return Integer.parseInt(attendanceRuleMap.get(ATTENDANCE_RULE_NOTICE).getRuleValue()) * 60 * 1000;
		}
		return 0l;
	}

}
