package cn.boxiao.bxn.meeting.vo;

public class MeetingGroupVo {

	private String id;
	private String orderNo;
	private String groupName;
	private String parentId;
	private String status;
	private String deleted;
	private String creatorId;
	private String creatorName;
	private String createTime;

	@Override
	public String toString() {
		return "MeetingGroupVo [id=" + id + ", orderNo=" + orderNo + ", groupName=" + groupName + ", parentId="
				+ parentId + ", status=" + status + ", deleted=" + deleted + ", creatorId=" + creatorId
				+ ", creatorName=" + creatorName + ", createTime=" + createTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
