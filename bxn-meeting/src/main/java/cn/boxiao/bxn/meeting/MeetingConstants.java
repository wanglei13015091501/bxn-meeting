package cn.boxiao.bxn.meeting;

public interface MeetingConstants {

	/**
	 * 业务模块
	 */
	String MODULENAME_NOTIFY = "BXN_MEETING_NOTIFY";
	String MODULENAME_RECEIPT = "BXN_MEETING_RECEIPT";
	String MODULENAME_REMIND = "BXN_MEETING_REMIND";

	/**
	 * 参加会议的人员状态
	 */
	String MEETING_USER_STATUS_NORMAL = "0"; // 正常
	String MEETING_USER_STATUS_LATE = "1"; // 迟到
	String MEETING_USER_STATUS_LEAVE = "2"; // 早退
	String MEETING_USER_STATUS_ABSENCE = "3"; // 缺勤
	String MEETING_USER_STATUS_MISSING = "4"; // 漏卡
	String MEETING_USER_STATUS_ABSENCE_PUBLIC = "5"; // 公假
	String MEETING_USER_STATUS_ABSENCE_PRIVATE = "6"; // 私假
	String MEETING_USER_STATUS_ABSENCE_SICK = "7"; // 病假
	String MEETING_USER_STATUS_LATE_LEAVE = "8"; // 迟到/早退
	String MEETING_USER_STATUS_INIT = "9"; // 未打卡
	String MEETING_USER_STATUS_ABSENCE_LEAVE = "10"; // 请假
	String MEETING_USER_STATUS_INVALID_UNKNOWN = "97"; // 未知异常
	String MEETING_USER_STATUS_INVALID_TIMESTAMP = "98"; // 时间戳过期
	String MEETING_USER_STATUS_INVALID = "99"; // 无此用户

	/**
	 * 会议状态
	 */
	String MEETING_STATUS_INIT = "0"; // 未开始
	String MEETING_STATUS_PROCESS = "1"; // 进行中
	String MEETING_STATUS_END = "2"; // 已结束

	String DELETED_NO = "0"; // 正常状态（未删除）
	String DELETED_YES = "1"; // 删除
	String DELETED_CANCELED = "2"; // 取消删除

	String STATUS_OPEN = "0"; // XXX启用（未关闭）
	String STATUS_CLOSE = "1"; // XXX禁用（关闭）

	int DEFAULT_PAGE_SIZE = 10; // 每页条数
	int DEFAULT_PAGE_SIZE_SMALL = 5; // 部分业务需要每页较少的条目数量

	/**
	 * 考勤规则定义
	 */

	String ATTENDANCE_RULE_NORMAL_SIGN = "normalSign"; // 正常签到定义
	String ATTENDANCE_RULE_NORMAL_LOGOUT = "normalLogout";// 正常签退定义
	String ATTENDANCE_RULE_LATE = "late"; // 迟到定义
	String ATTENDANCE_RULE_LEAVE = "leave"; // 早退定义
	String ATTENDANCE_RULE_NOTICE = "notice"; // 发送会议提醒短信
	String ATTENDANCE_RULE_ABSENCE_NO_CLOCK = "noClock"; // 签到——签退时间范围内未打卡
	String ATTENDANCE_RULE_ABSENCE_ONE_CLOCK = "oneClock";// 签到——签退时间范围打卡打卡1次
	String ATTENDANCE_RULE_ABSENCE_TIME_CLOCK = "timeClock";// 签到——签退时间范围打卡，但签到签退有效时间间隔在XX分钟内
	String ATTENDANCE_RULE_ABSENCE_ONLY_SIGN = "onlySign"; // 只在正常签到时间内打卡
	String ATTENDANCE_RULE_ABSENCE_ONLY_LOGOUT = "onlyLogout"; // 只在正常签退时间内打卡
	String ATTENDANCE_RULE_ABSENCE_LATE = "maxMinuteLate"; // 迟到XX分钟以上
	String ATTENDANCE_RULE_ABSENCE_LEAVE = "maxMinuteLeave"; // 早退XX分钟以上

	/**
	 * 接口命令字
	 */
	String MEET_MEMBERSTAECHANGE = "MEET_MEMBERSTAECHANGE";
	String MEET_MEETCHANGE = "MEET_MEETCHANGE";
	String MEET_MEMBERCHANGE = "MEET_MEMBERCHANGE";

	/**
	 * 发送短信
	 */
	Long SMS_SENDERID = 400L;
	String SMS_OBJECTID = "MEET_MEETING_345689";// 业务名称_ObjectName_会议ID

	/**
	 * 二维码打卡开关状态
	 */
	String ATTENDANCE_QRCODE_OPEN = "0"; // 开放
	String ATTENDANCE_QRCODE_CLOSE = "1"; // 关闭

	/**
	 * 会议部门
	 */
	String GROUP_CATEGORY_PARENT = "0";
	String GROUP_ID_OTHER = "0"; // 部门（其他）取值

	/**
	 * 一开通考勤会议ID和部门分类ID
	 */
	String MEETING_TYPE_ONECARD_ID = "1";
	String GROUP_CATEGORY_ONECARD_ID = "1";

	/**
	 * 会议循环标记
	 */
	String MEETING_CYCLING_NO = "0";
	String MEETING_CYCLING_BYDAY = "1";
	String MEETING_CYCLING_BYWEEK = "2";
	String MEETING_CYCLING_BYMONTH = "3";

}
