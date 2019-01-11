package cn.boxiao.bxn.meeting.data;

import java.util.Date;

public class AttendanceUserDo {
	private String id;

	private String userId; // 用户ID
	private String userName; // 用户名称

	private String meetingId; // 会议ID

	private Date signTime; // 签到时间
	private Date logoutTime; // 签退时间
	private Date modifySignTime; // 修改后签到时间
	private Date modifyLogoutTime; // 修改后签退时间

	private String description; // 备注

	private String status; // 状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)
	private String modifyStatus; // 状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)

	private String deleted; // 是否删除标识
	private Date createTime; // 创建时间
	private Date delTime; // 删除时间

	@Override
	public String toString() {
		return "AttendanceUserDo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", meetingId="
				+ meetingId + ", signTime=" + signTime + ", logoutTime=" + logoutTime + ", modifySignTime="
				+ modifySignTime + ", modifyLogoutTime=" + modifyLogoutTime + ", description=" + description
				+ ", status=" + status + ", modifyStatus=" + modifyStatus + ", deleted=" + deleted + ", createTime="
				+ createTime + ", delTime=" + delTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Date getModifySignTime() {
		return modifySignTime;
	}

	public void setModifySignTime(Date modifySignTime) {
		this.modifySignTime = modifySignTime;
	}

	public Date getModifyLogoutTime() {
		return modifyLogoutTime;
	}

	public void setModifyLogoutTime(Date modifyLogoutTime) {
		this.modifyLogoutTime = modifyLogoutTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}
