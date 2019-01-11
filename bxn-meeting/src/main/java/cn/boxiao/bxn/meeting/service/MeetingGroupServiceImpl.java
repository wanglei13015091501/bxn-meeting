package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.meeting.ErrCode;
import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.dao.MeetingGroupDao;
import cn.boxiao.bxn.meeting.dao.MeetingTypeDao;
import cn.boxiao.bxn.meeting.data.MeetingGroupDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupUserDo;
import cn.boxiao.bxn.meeting.data.MeetingTypeDo;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupUserVo;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Service
public class MeetingGroupServiceImpl implements MeetingGroupService {

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private AttendanceMeetingService attendanceMeetingService;

	@Autowired
	private MeetingGroupDao meetingGroupDao;

	@Autowired
	private MeetingTypeDao meetingTypeDao;

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryMeetingGroupsByMeetingId(java.lang.String)
	 */
	@Override
	public List<MeetingGroupVo> queryMeetingGroupsByMeetingId(String meetingId) {
		List<MeetingGroupVo> meetingGroupVoList = Lists.newArrayList();

		AttendanceMeetingVo attendanceMeetingVo = attendanceMeetingService.getAttendanceMeetingProfile(meetingId);
		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(attendanceMeetingVo.getMeetingTypeId());
		if (StringUtils.isNotBlank(meetingTypeDo.getGroupId())) {
			return queryMeetingGroupProfilesByParent(meetingTypeDo.getGroupId());
		}
		return meetingGroupVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryMeetingGroupProfilesByParent(java.lang.String)
	 */
	@Override
	public List<MeetingGroupVo> queryMeetingGroupProfilesByParent(String parentId) {
		List<MeetingGroupVo> meetingGroupVoList = Lists.newArrayList();

		List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao.queryMeetingGroupsByParent(parentId);

		for (MeetingGroupDo groupDo : meetingGroupDoList) {

			MeetingGroupVo meetingGroupVo = new MeetingGroupVo();
			BeanUtils.copyProperties(groupDo, meetingGroupVo);

			meetingGroupVoList.add(meetingGroupVo);
		}
		return meetingGroupVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryMeetingGroupsByParent(java.lang.String)
	 */
	@Override
	public List<MeetingGroupVo> queryMeetingGroupsByParent(String parentId) {
		List<MeetingGroupVo> meetingGroupVoList = Lists.newArrayList();

		List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao.queryMeetingGroupsByParent(parentId);

		Set<Long> userIdSet = Sets.newHashSet();
		for (MeetingGroupDo groupDo : meetingGroupDoList) {
			if (StringUtils.isBlank(groupDo.getCreatorId())) {
				continue;
			}
			userIdSet.add(Long.parseLong(groupDo.getCreatorId()));
		}

		Map<String, UserVo> userMap = convertUserVoToMap(userIdSet);

		for (MeetingGroupDo groupDo : meetingGroupDoList) {

			MeetingGroupVo meetingGroupVo = new MeetingGroupVo();
			BeanUtils.copyProperties(groupDo, meetingGroupVo);

			if (userMap.get(groupDo.getCreatorId()) != null) {
				meetingGroupVo.setCreatorName(userMap.get(groupDo.getCreatorId()).getFullName());
			} else {
				meetingGroupVo.setCreatorName("");
			}

			meetingGroupVo.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(groupDo.getCreateTime()));
			meetingGroupVoList.add(meetingGroupVo);
		}
		return meetingGroupVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryMeetingGroup(java.lang.String)
	 */
	@Override
	public MeetingGroupVo queryMeetingGroup(String groupId) {

		MeetingGroupDo meetingGroupDo = meetingGroupDao.queryMeetingGroup(groupId);

		MeetingGroupVo meetingGroupVo = new MeetingGroupVo();
		BeanUtils.copyProperties(meetingGroupDo, meetingGroupVo);

		return meetingGroupVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#createMeetingGroup(cn.boxiao.bxn.meeting.vo.MeetingGroupVo)
	 */
	@Override
	public MeetingGroupVo createMeetingGroup(MeetingGroupVo meetingGroupVo) {
		MeetingGroupDo meetingGroup = meetingGroupDao.queryMeetingGroup(meetingGroupVo.getParentId(),
				meetingGroupVo.getGroupName());
		if (StringUtils.isNotBlank(meetingGroup.getId())) {
			throw new BXQBusinessRuntimeException(ErrCode.DUPLICATED_OBJECT_FOUND, "会议部门已存在");
		}

		MeetingGroupDo meetingGroupDo = new MeetingGroupDo();
		BeanUtils.copyProperties(meetingGroupVo, meetingGroupDo);

		meetingGroupDo = meetingGroupDao.saveMeetingGroup(meetingGroupDo);
		meetingGroupVo.setId(meetingGroupDo.getId());

		return meetingGroupVo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#updateMeetingGroup(cn.boxiao.bxn.meeting.vo.MeetingGroupVo)
	 */
	@Override
	public int updateMeetingGroup(MeetingGroupVo meetingGroupVo) {
		MeetingGroupDo meetingGroup = meetingGroupDao.queryMeetingGroup(meetingGroupVo.getParentId(),
				meetingGroupVo.getGroupName());
		if (StringUtils.isNotBlank(meetingGroup.getId())
				&& !StringUtils.equals(meetingGroup.getId(), meetingGroupVo.getId())) {
			throw new BXQBusinessRuntimeException(ErrCode.DUPLICATED_OBJECT_FOUND, "会议部门已存在");
		}

		MeetingGroupDo meetingGroupDo = new MeetingGroupDo();
		BeanUtils.copyProperties(meetingGroupVo, meetingGroupDo);

		return meetingGroupDao.updateMeetingGroup(meetingGroupDo);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#deleteMeetingGroup(java.lang.String)
	 */
	@Override
	public int deleteMeetingGroup(String groupId) {

		List<MeetingGroupDo> meetingGroupDoList = meetingGroupDao.queryMeetingGroupsByParent(groupId);
		if (CollectionUtils.isNotEmpty(meetingGroupDoList)) {
			throw new BXQBusinessRuntimeException(ErrCode.NO_OBJECT_FOUND, "该部门分类包含下属部门，无法删除");
		}

		List<MeetingTypeDo> meetingTypeDoList = meetingTypeDao.queryMeetingTypesByGroupId(groupId);
		if (CollectionUtils.isNotEmpty(meetingTypeDoList)) {
			throw new BXQBusinessRuntimeException(ErrCode.NO_OBJECT_FOUND, "该部门分类已经关联会议类型，无法删除");
		}

		return meetingGroupDao.deleteMeetingGroup(groupId);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryMeetingGroupUsers(java.lang.String)
	 */
	@Override
	public List<MeetingGroupUserVo> queryMeetingGroupUsers(String groupId) {
		List<MeetingGroupUserVo> groupUserVoList = Lists.newArrayList();

		List<MeetingGroupUserDo> groupUserDoList = meetingGroupDao.queryMeetingGroupUsers(groupId);
		Set<Long> userIdSet = Sets.newHashSet();
		for (MeetingGroupUserDo groupUserDo : groupUserDoList) {
			userIdSet.add(Long.parseLong(groupUserDo.getUserId()));
		}

		Map<String, UserVo> userMap = convertUserVoToMap(userIdSet);

		for (MeetingGroupUserDo groupUserDo : groupUserDoList) {
			MeetingGroupUserVo groupUserVo = new MeetingGroupUserVo();
			BeanUtils.copyProperties(groupUserDo, groupUserVo);

			UserVo userVo = userMap.get(groupUserVo.getUserId());
			if (userVo != null) {
				groupUserVo.setFullName(userVo.getFullName());
				groupUserVo.setUniqueNo(userVo.getUniqueNo());
			} else {
				groupUserVo.setFullName("");
				groupUserVo.setUniqueNo("");
			}

			groupUserVo.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(groupUserDo.getCreateTime()));
			groupUserVoList.add(groupUserVo);
		}
		return groupUserVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#queryOneCardMeetingGroupUsers()
	 */
	@Override
	public List<MeetingGroupUserVo> queryOneCardMeetingGroupUsers() {
		List<MeetingGroupUserVo> groupUserVoList = Lists.newArrayList();

		// 查询一卡通会议部门ID
		List<MeetingGroupUserDo> groupUserDoList = meetingGroupDao
				.queryMeetingGroupUsersByParent(MeetingConstants.GROUP_CATEGORY_ONECARD_ID);
		Set<Long> userIdSet = Sets.newHashSet();
		for (MeetingGroupUserDo groupUserDo : groupUserDoList) {
			userIdSet.add(Long.parseLong(groupUserDo.getUserId()));
		}

		Map<String, UserVo> userMap = convertUserVoToMap(userIdSet);

		for (MeetingGroupUserDo groupUserDo : groupUserDoList) {
			MeetingGroupUserVo groupUserVo = new MeetingGroupUserVo();
			BeanUtils.copyProperties(groupUserDo, groupUserVo);

			UserVo userVo = userMap.get(groupUserVo.getUserId());
			if (userVo != null) {
				groupUserVo.setFullName(userVo.getFullName());
				groupUserVo.setUniqueNo(userVo.getUniqueNo());
				groupUserVo.setCategory(String.valueOf(userVo.getCategory()));
			} else {
				groupUserVo.setFullName("");
				groupUserVo.setUniqueNo("");
				groupUserVo.setCategory("");
			}

			groupUserVo.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(groupUserDo.getCreateTime()));
			groupUserVoList.add(groupUserVo);
		}
		return groupUserVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingGroupService#createMeetingGroupUser(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public String createMeetingGroupUser(String groupId, String userIds, String creatorId) {

		List<String> duplicateGroupUser = Lists.newArrayList();

		List<String> userIdList = CsvUtils.convertCSVToList(userIds);
		if (CollectionUtils.isEmpty(userIdList)) {
			return "";
		}

		// 查询部门对象，提取部门分类ID
		MeetingGroupDo groupDo = meetingGroupDao.queryMeetingGroup(groupId);
		if (StringUtils.isBlank(groupDo.getId())) {
			return "";
		}
		// 查询同一分类下的其他部门
		List<String> sameParGroupIdList = Lists.newArrayList();
		List<MeetingGroupDo> sameParGroupList = meetingGroupDao.queryMeetingGroupsByParent(groupDo.getParentId());
		for (MeetingGroupDo sameParGroupDo : sameParGroupList) {
			sameParGroupIdList.add(sameParGroupDo.getId());
		}

		// 查找用户信息
		Set<Long> userIdSet = Sets.newHashSet();
		for (String userId : userIdList) {
			userIdSet.add(Long.parseLong(userId));
		}
		Map<String, UserVo> userVoMap = convertUserVoToMap(userIdSet);

		// 构造并添加部门用户
		for (String userId : userIdList) {

			// 查不到用户就跳过
			UserVo userVo = userVoMap.get(userId);
			if (userVo == null) {
				continue;
			}

			// 检查用户是否已经在同一分类的部门中
			MeetingGroupUserDo duplicate = meetingGroupDao.queryMeetingGroupUserInCategory(userId, sameParGroupIdList);
			if (StringUtils.isNotBlank(duplicate.getId())) {
				duplicateGroupUser.add(userVo.getFullName());
				continue;
			}

			// 添加用户
			MeetingGroupUserDo groupUserDo = new MeetingGroupUserDo();
			groupUserDo.setGroupId(groupId);
			groupUserDo.setUserId(String.valueOf(userVo.getId()));
			groupUserDo.setUserName(userVo.getUserName());
			groupUserDo.setCreatorId(creatorId);
			meetingGroupDao.saveMeetingGroupUser(groupUserDo);
		}

		return CsvUtils.convertCollectionToCSV(duplicateGroupUser);
	}

	@Override
	public int deleteMeetingGroupUser(String groupId, String userId) {

		return meetingGroupDao.deleteMeetingGroupUser(groupId, userId);
	}

	/**
	 * 批量获取用户VO
	 * 
	 * @param userIdList
	 * @return
	 */
	private Map<String, UserVo> convertUserVoToMap(Set<Long> userIdSet) {

		List<Long> userIdList = Lists.newArrayList();
		for (Long userId : userIdSet) {
			userIdList.add(userId);
		}

		Map<String, UserVo> userMap = Maps.newHashMap();
		if (CollectionUtils.isEmpty(userIdList)) {
			return userMap;
		}
		List<UserVo> userVoList = userService.getUsersByIds(userIdList);

		for (UserVo userVo : userVoList) {
			userMap.put(String.valueOf(userVo.getId()), userVo);
		}

		return userMap;
	}

}
