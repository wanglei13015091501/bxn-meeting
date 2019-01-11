package cn.boxiao.bxn.meeting.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.boxiao.bxn.base.client.RemoteEMBServiceInvoker;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.cache.MeetingCache;
import cn.boxiao.bxn.meeting.service.AttendanceUserService;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

public class MeetingTask implements MeetingConstants {
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 会议是否已经发送提醒短信，结构<meetingId,isSend> isSend: 1-已发送
	private static Map<String, Integer> smsMap = Maps.newHashMap();

	@Autowired
	private MeetingCache meetingCache;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private AttendanceUserService attendanceUserService;

	@Autowired
	private RemoteEMBServiceInvoker remoteEMBServiceInvoker;

	public void doTask() {
		logger.warn("MeetingTask doTask start, current: {}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));

		// 查询今天的会议
		Map<String, AttendanceMeetingVo> attendanceMeetingMap = meetingCache.getMeeting();

		// 检查当日会议会议状态
		try {
			checkMeetingStatus(attendanceMeetingMap);
		} catch (Exception e) {
			logger.error("MeetingTask checkMeetingStatus failed.", e);
		}

		// 发送提醒短信
		try {
			sendSms(attendanceMeetingMap);
		} catch (Exception e) {
			logger.error("MeetingTask sendSms failed.", e);
		}

		logger.warn("MeetingTask doTask end, current{}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));
	}

	/**
	 * 定时检查当日会议，刷新会议状态
	 */
	private void checkMeetingStatus(Map<String, AttendanceMeetingVo> attendanceMeetingMap) {
		logger.warn("checkMeetingStatus start, current: {}, meetingMap: {}",
				DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()), attendanceMeetingMap);

		for (String meetingId : attendanceMeetingMap.keySet()) {
			attendanceUserService.checkMeetingStatus(meetingId, false);
		}

		logger.warn("checkMeetingStatus doTask end, current: {}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));

	}

	/**
	 * 会前发送短信提醒
	 */
	private void sendSms(Map<String, AttendanceMeetingVo> attendanceMeetingMap) {
		logger.warn("sendSms start, current: {}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));

		// 发送会议提醒短信，获取会议开始前XX分钟
		long noticeSecond = meetingCache.getNoticeSecond();
		if (noticeSecond <= 0) {
			return;
		}

		// 获取当前时间
		Long currentTimeSecond = DateUtil.toLong(new Date());

		List<AttendanceMeetingVo> missMeetingList = Lists.newArrayList();
		for (String meetingId : attendanceMeetingMap.keySet()) { // 过滤已经发送过短信的会议
			if (smsMap.containsKey(meetingId)) {
				continue;
			} else {
				missMeetingList.add(attendanceMeetingMap.get(meetingId));
			}
		}

		for (AttendanceMeetingVo attendanceMeetingVo : missMeetingList) {
			String beginTime = attendanceMeetingVo.getBeginTime();
			if (StringUtils.isBlank(beginTime)) {
				continue;
			}

			Long beginTimeSecond = DateUtil.toLong(DateUtil.parseYYYY_MM_DD_HH_MM_SSDate(beginTime));
			if (beginTimeSecond - currentTimeSecond > 0 && beginTimeSecond - currentTimeSecond <= noticeSecond) { // 比对当前时间和会议开始时间
				List<AttendanceUserVo> attendanceUserVoList = attendanceUserService
						.getAttendanceUsers(attendanceMeetingVo.getId());

				List<String> userIdList = Lists.newArrayList(); // 用户ID 列表
				for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
					if (StringUtils.isNotBlank(attendanceUserVo.getUserId())) {
						userIdList.add(attendanceUserVo.getUserId());
					}

				}

				// 发送提醒短信
				sendUserAttendanceRemind(userIdList, attendanceMeetingVo);
				smsMap.put(attendanceMeetingVo.getId(), 1);
			}
		}

		logger.warn("sendSms end, current: {}", DateUtil.toString_YYYY_MM_DD_HH_MM_SS(new Date()));

	}

	/**
	 * 发送打卡提示短信/微信
	 * 
	 * @param userIdList
	 * @param attendanceMeetingVo
	 */
	private void sendUserAttendanceRemind(List<String> userIdList, AttendanceMeetingVo attendanceMeetingVo) {
		String userIds = CsvUtils.convertCollectionToCSV(userIdList);
		String meetingName = attendanceMeetingVo.getMeetingName();
		String beginTime = attendanceMeetingVo.getBeginTime();
		String endTime = attendanceMeetingVo.getEndTime();

		PlaceVo palceVo = placeService.queryPlace(attendanceMeetingVo.getPlaceId());
		String placeName = palceVo.getPlaceName();

		String content = "您好，" + beginTime + "--" + endTime + "，" + "在" + placeName + "，" + "召开" + meetingName + "，"
				+ "会议马上开始，请准时参加，谢谢。";

		// 发送短信提醒
		try {
			remoteEMBServiceInvoker.sendSms(userIds, SMS_SENDERID, content, 4, MODULENAME_REMIND, SMS_OBJECTID);
		} catch (Exception e) {
			logger.error("MeetingTask sendSms error:", e);
		}

		// 发送微信提示
		try {
			remoteEMBServiceInvoker.sendMobileNotify(MODULENAME_REMIND, userIds, content);
		} catch (Exception e) {
			logger.error("MeetingTask sendMobileNotify error:", e);
		}

	}

}
