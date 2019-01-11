package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.MeetingGroupDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupUserDo;

public interface MeetingGroupDao {

	List<MeetingGroupDo> queryMeetingGroupsByParent(String parentId);

	MeetingGroupDo queryMeetingGroup(String groupId);

	MeetingGroupDo queryMeetingGroup(String parentId, String groupName);

	MeetingGroupDo saveMeetingGroup(MeetingGroupDo meetingGroupDo);

	int updateMeetingGroup(MeetingGroupDo meetingGroupDo);

	int deleteMeetingGroup(String groupId);
	
	List<MeetingGroupUserDo> queryMeetingGroupUsers(String groupId);
	
	List<MeetingGroupUserDo> queryMeetingGroupUsersByParent(String parentId);
	
	MeetingGroupUserDo queryMeetingGroupUserInCategory(String userId, List<String> sameParGroupIdList);
	
	MeetingGroupUserDo saveMeetingGroupUser(MeetingGroupUserDo meetingGroupUserDo);
	
	int deleteMeetingGroupUser(String groupId, String userId);

}
