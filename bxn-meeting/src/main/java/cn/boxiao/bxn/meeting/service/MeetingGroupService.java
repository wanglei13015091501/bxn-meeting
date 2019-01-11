package cn.boxiao.bxn.meeting.service;

import java.util.List;

import cn.boxiao.bxn.meeting.vo.MeetingGroupUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

public interface MeetingGroupService {

	/**
	 * 根据会议ID查询会议部门
	 * 
	 * @param meetingId
	 * @return
	 */
	List<MeetingGroupVo> queryMeetingGroupsByMeetingId(String meetingId);

	/**
	 * 查询部门信息
	 * 
	 * @param parentId
	 * @return
	 */
	List<MeetingGroupVo> queryMeetingGroupsByParent(String parentId);

	List<MeetingGroupVo> queryMeetingGroupProfilesByParent(String parentId);

	/**
	 * 查询一条部门信息
	 * 
	 * @param groupId
	 * @return
	 */
	MeetingGroupVo queryMeetingGroup(String groupId);

	/**
	 * 保存一条部门信息
	 * 
	 * @param meetingTypeVo
	 * @return
	 */
	MeetingGroupVo createMeetingGroup(MeetingGroupVo meetingGroupVo);

	/**
	 * 更新部门信息
	 * 
	 * @param meetingTypeVo
	 * @return
	 */
	int updateMeetingGroup(MeetingGroupVo meetingGroupVo);

	/**
	 * 删除一条部门
	 * 
	 * @param groupId
	 * @return
	 */
	int deleteMeetingGroup(String groupId);

	/**
	 * 查询部门下用户
	 * 
	 * @param groupId
	 * @return
	 */
	List<MeetingGroupUserVo> queryMeetingGroupUsers(String groupId);

	/**
	 * 查询一开通会议部门用户
	 * 
	 * @return
	 */
	List<MeetingGroupUserVo> queryOneCardMeetingGroupUsers();

	/**
	 * 添加部门用户
	 * 
	 * @param groupId
	 * @param userIds
	 * @return
	 */
	String createMeetingGroupUser(String groupId, String userIds, String creatorId);

	/**
	 * 删除部门用户
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	int deleteMeetingGroupUser(String groupId, String userId);

}
