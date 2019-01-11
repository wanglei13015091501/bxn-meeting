package cn.boxiao.bxn.meeting.vo;

public class MeetingGroupUserVo {

	private String id;
	private String userId;
	private String userName;
	private String fullName;
	private String uniqueNo;
	private String category;
	private String groupId;
	private String deleted;
	private String creatorId;
	private String creatorName;
	private String createTime;

	@Override
	public String toString() {
		return "MeetingGroupUserVo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", fullName="
				+ fullName + ", uniqueNo=" + uniqueNo + ", category=" + category + ", groupId=" + groupId + ", deleted="
				+ deleted + ", creatorId=" + creatorId + ", creatorName=" + creatorName + ", createTime=" + createTime
				+ "]";
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
