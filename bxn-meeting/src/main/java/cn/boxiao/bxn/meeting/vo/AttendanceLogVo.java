package cn.boxiao.bxn.meeting.vo;

public class AttendanceLogVo {

	private String id;

	private String userName; // 用户名称

	private String meetingId; // 会议ID

	private String deviceId; // 打卡设备ID

	private String clockTime; // 打卡时间

	private String createTime; // 创建时间

	@Override
	public String toString() {
		return "AttendanceLogVo [id=" + id + ", userName=" + userName + ", meetingId=" + meetingId + ", deviceId="
				+ deviceId + ", clockTime=" + clockTime + ", createTime=" + createTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClockTime() {
		return clockTime;
	}

	public void setClockTime(String clockTime) {
		this.clockTime = clockTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
