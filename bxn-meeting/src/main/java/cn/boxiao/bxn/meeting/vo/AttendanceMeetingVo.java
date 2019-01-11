package cn.boxiao.bxn.meeting.vo;

public class AttendanceMeetingVo {

	private String id;
	private String meetingName; // 会议名称
	private String placeId; // 会议会议室id
	private String placeNo; // 会议会议室编号
	private String placeName; // 会议会议室名称
	private String description; // 备注
	private String meetingTypeId; // 会议类型ID
	private String beginTime; // 开始时间
	private String endTime; // 结束时间
	private String cycling; // 循环标记
	private String signTime; // 签到时间
	private String logoutTime;// 签退时间
	private String userIds; // 参会人员id
	private String userNames; // 参会人员名称
	private String uniqueNos; // 参会人员编号
	private String organizerIds; // 组织者id
	private String organizerNames; // 组织者名称
	private int userNum; // 参会人员数量
	private String creatorId; // 创建人ID
	private String creatorName; // 创建人名称

	private String status; // 状态(0-正常,1-禁用)
	private String deleted;
	private String createTime;
	
	//2018-12-14
	private String ad ;
	private String ad1 ;

	@Override
	public String toString() {
		return "AttendanceMeetingVo [id=" + id + ", meetingName=" + meetingName + ", placeId=" + placeId + ", placeNo="
				+ placeNo + ", placeName=" + placeName + ", description=" + description + ", meetingTypeId="
				+ meetingTypeId + ", beginTime=" + beginTime + ", endTime=" + endTime + ", cycling=" + cycling
				+ ", signTime=" + signTime + ", logoutTime=" + logoutTime + ", userIds=" + userIds + ", userNames="
				+ userNames + ", uniqueNos=" + uniqueNos + ", organizerIds=" + organizerIds + ", organizerNames="
				+ organizerNames + ", userNum=" + userNum + ", creatorId=" + creatorId + ", creatorName=" + creatorName
				+ ", status=" + status + ", deleted=" + deleted + ", createTime=" + createTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCycling() {
		return cycling;
	}

	public void setCycling(String cycling) {
		this.cycling = cycling;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
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

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getUniqueNos() {
		return uniqueNos;
	}

	public void setUniqueNos(String uniqueNos) {
		this.uniqueNos = uniqueNos;
	}

	public String getOrganizerIds() {
		return organizerIds;
	}

	public void setOrganizerIds(String organizerIds) {
		this.organizerIds = organizerIds;
	}

	public String getOrganizerNames() {
		return organizerNames;
	}

	public void setOrganizerNames(String organizerNames) {
		this.organizerNames = organizerNames;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
