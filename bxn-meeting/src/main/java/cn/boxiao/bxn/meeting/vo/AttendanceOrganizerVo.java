package cn.boxiao.bxn.meeting.vo;

public class AttendanceOrganizerVo {

	private String meetingId; // 会议名称
	private String userId; // 用户ID
	private String deleted;
	private String createTime;

	@Override
	public String toString() {
		return "AttendanceOrganizerVo [meetingId=" + meetingId + ", userId=" + userId + ", deleted=" + deleted
				+ ", createTime=" + createTime + "]";
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

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
