package cn.boxiao.bxn.meeting.vo;

public class MeetingTypeVo {

	private String id;
	private String typeName;
	private String deleted;
	private String creatorId;
	private String creatorName;
	private String createTime;
	private String groupId; // 部门分类
	private String groupName; // 部门名称

	@Override
	public String toString() {
		return "MeetingTypeVo [id=" + id + ", typeName=" + typeName + ", deleted=" + deleted + ", creatorId="
				+ creatorId + ", creatorName=" + creatorName + ", createTime=" + createTime + ", groupId=" + groupId
				+ ", groupName=" + groupName + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
