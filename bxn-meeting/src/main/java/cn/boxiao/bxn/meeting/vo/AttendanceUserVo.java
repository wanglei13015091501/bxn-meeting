package cn.boxiao.bxn.meeting.vo;

import org.apache.commons.lang.StringUtils;

import cn.boxiao.bxn.meeting.MeetingConstants;

public class AttendanceUserVo {
	private String id;

	private String userId; // 用户ID
	private String userName; // 用户名称
	private String fullName; // 用户全名
	private String uniqueNo; // 教工号/学号
	private int category; // 用户身份

	private String meetingName; // 会议名称
	private String placeName; // 会议室名称
	private String meetingBeginTime; // 会议开始时间
	private String meetingEndTime; // 会议结束时间
	private String meetingId; // 会议ID
	private String meetingStatus; // 会议状态(0-未开始,1-进行中,2-已结束)

	private String signTime; // 签到时间
	private String logoutTime; // 签退时间
	private String modifySignTime; // 修改后签到时间
	private String modifyLogoutTime; // 修改后签退时间

	private String description; // 备注

	private String status; // 状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)
	private String modifyStatus; // 状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)

	private String createTime; // 创建时间

	private int statusUserNum; // 同状态的人数

	@Override
	public String toString() {
		return "AttendanceUserVo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", fullName=" + fullName
				+ ", uniqueNo=" + uniqueNo + ", category=" + category + ", meetingName=" + meetingName + ", placeName="
				+ placeName + ", meetingBeginTime=" + meetingBeginTime + ", meetingEndTime=" + meetingEndTime
				+ ", meetingId=" + meetingId + ", meetingStatus=" + meetingStatus + ", signTime=" + signTime
				+ ", logoutTime=" + logoutTime + ", modifySignTime=" + modifySignTime + ", modifyLogoutTime="
				+ modifyLogoutTime + ", description=" + description + ", status=" + status + ", modifyStatus="
				+ modifyStatus + ", createTime=" + createTime + ", statusUserNum=" + statusUserNum + "]";
	}

	public String getActualStatus() {
		return StringUtils.isBlank(getModifyStatus()) ? getStatus() : getModifyStatus();
	}

	public String getActualSignTime() {
		return StringUtils.isBlank(getModifySignTime()) ? getSignTime() : getModifySignTime();
	}

	public String getActualLogoutTime() {
		return StringUtils.isBlank(getModifyLogoutTime()) ? getLogoutTime() : getModifyLogoutTime();
	}

	public String getStatusText() {
		String actualStatus = getActualStatus();
		if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_NORMAL)) {
			return "正常";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_LATE)) {
			return "迟到";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_LEAVE)) {
			return "早退";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_ABSENCE)) {
			return "缺勤";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_MISSING)) {
			return "漏卡";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_ABSENCE_PUBLIC)) {
			return "公假";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_ABSENCE_PRIVATE)) {
			return "私假";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_ABSENCE_SICK)) {
			return "病假";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_LATE_LEAVE)) {
			return "迟到/早退";
		} else if (StringUtils.equals(actualStatus, MeetingConstants.MEETING_USER_STATUS_INIT)) {
			return "未打卡";
		} else {
			return "其他";
		}
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getModifySignTime() {
		return modifySignTime;
	}

	public void setModifySignTime(String modifySignTime) {
		this.modifySignTime = modifySignTime;
	}

	public String getModifyLogoutTime() {
		return modifyLogoutTime;
	}

	public void setModifyLogoutTime(String modifyLogoutTime) {
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

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getMeetingBeginTime() {
		return meetingBeginTime;
	}

	public void setMeetingBeginTime(String meetingBeginTime) {
		this.meetingBeginTime = meetingBeginTime;
	}

	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStatusUserNum() {
		return statusUserNum;
	}

	public void setStatusUserNum(int statusUserNum) {
		this.statusUserNum = statusUserNum;
	}

}
