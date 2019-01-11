package cn.boxiao.bxn.meeting.data;

import java.util.Date;

public class AttendanceLogDo {
	private String id;

	private String userName; // 用户名称

	private String meetingId; // 会议ID

	private String deviceId; // 打卡设备ID

	private Date clockTime; // 打卡时间

	private Date createTime; // 创建时间

	@Override
	public String toString() {
		return "AttendanceLogDo [id=" + id + ", userName=" + userName + ", meetingId=" + meetingId + ", deviceId="
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

	public Date getClockTime() {
		return clockTime;
	}

	public void setClockTime(Date clockTime) {
		this.clockTime = clockTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
