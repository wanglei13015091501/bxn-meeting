package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class AttendanceOrganizerDo extends BaseDo {

	private String meetingId; // 会议名称
	private String userId; // 用户ID

	@Override
	public String toString() {
		return "AttendanceOrganizerDo [meetingId=" + meetingId + ", userId=" + userId + "]";
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
