package cn.boxiao.bxn.meeting.data;

import java.util.Date;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class AttendanceMeetingDo extends BaseDo {

	private String meetingName; // 会议名称
	private String placeId; // 会议会议室id
	private String description; // 备注
	private String meetingTypeId; // 会议类型ID
	private Date beginTime; // 开始时间
	private Date endTime; // 结束时间
	private String cycling; // 循环标记
	private String creatorId; // 创建人ID

	private String status; // 状态(0-正常,1-禁用)

	@Override
	public String toString() {
		return "AttendanceMeetingDo [meetingName=" + meetingName + ", placeId=" + placeId + ", description="
				+ description + ", meetingTypeId=" + meetingTypeId + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", cycling=" + cycling + ", creatorId=" + creatorId + ", status=" + status + "]";
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeetingTypeId() {
		return meetingTypeId;
	}

	public void setMeetingTypeId(String meetingTypeId) {
		this.meetingTypeId = meetingTypeId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCycling() {
		return cycling;
	}

	public void setCycling(String cycling) {
		this.cycling = cycling;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
