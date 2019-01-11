package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class MeetingGroupDo extends BaseDo {

	private String orderNo;
	private String groupName;
	private String parentId;
	private String status;
	private String creatorId;

	@Override
	public String toString() {
		return "MeetingGroupDo [orderNo=" + orderNo + ", groupName=" + groupName + ", parentId=" + parentId
				+ ", status=" + status + ", creatorId=" + creatorId + "]";
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

}
