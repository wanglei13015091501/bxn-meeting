package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class MeetingGroupUserDo extends BaseDo {

	private String userId;
	private String userName;
	private String groupId;
	private String creatorId;

	@Override
	public String toString() {
		return "MeetingGroupUserDo [userId=" + userId + ", userName=" + userName + ", groupId=" + groupId
				+ ", creatorId=" + creatorId + "]";
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

}
