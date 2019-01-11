package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class MeetingTypeDo extends BaseDo {

	private String typeName;
	private String creatorId;
	private String groupId; // 部门分类

	@Override
	public String toString() {
		return "MeetingTypeDo [typeName=" + typeName + ", creatorId=" + creatorId + ", groupId=" + groupId + "]";
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
