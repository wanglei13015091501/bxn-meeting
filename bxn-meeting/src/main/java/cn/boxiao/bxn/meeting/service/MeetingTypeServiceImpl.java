package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.meeting.ErrCode;
import cn.boxiao.bxn.meeting.dao.MeetingTypeDao;
import cn.boxiao.bxn.meeting.data.MeetingTypeDo;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;
import cn.boxiao.bxn.uic.client.RemoteUserCenterServiceInvoker;
import cn.boxiao.bxn.uic.client.vo.UserVo;

@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

	@Autowired
	private MeetingTypeDao meetingTypeDao;

	@Autowired
	private RemoteUserCenterServiceInvoker userService;

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#queryMeetingTypes()
	 */
	@Override
	public List<MeetingTypeVo> queryMeetingTypes() {

		List<MeetingTypeVo> meetingTypeVoList = Lists.newArrayList();
		List<MeetingTypeDo> meetingTypeDoList = meetingTypeDao.queryMeetingTypes();

		Set<Long> userIdSet = Sets.newHashSet();
		for (MeetingTypeDo meetingTypeDo : meetingTypeDoList) {
			if (StringUtils.isBlank(meetingTypeDo.getCreatorId())) {
				continue;
			}
			userIdSet.add(Long.parseLong(meetingTypeDo.getCreatorId()));
		}

		Map<String, UserVo> userMap = convertUserVoToMap(userIdSet);
		Map<String, String> groupMap = convertGroupListToMap();

		for (MeetingTypeDo meetingTypeDo : meetingTypeDoList) {

			MeetingTypeVo meetingTypeVo = new MeetingTypeVo();
			BeanUtils.copyProperties(meetingTypeDo, meetingTypeVo);

			meetingTypeVo.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(meetingTypeDo.getCreateTime()));
			if (userMap.get(meetingTypeDo.getCreatorId()) != null) {
				meetingTypeVo.setCreatorName(userMap.get(meetingTypeDo.getCreatorId()).getFullName());
			} else {
				meetingTypeVo.setCreatorName("");
			}

			String groupName = MapUtils.getString(groupMap, meetingTypeDo.getGroupId(), "");
			meetingTypeVo.setGroupName(groupName);

			meetingTypeVoList.add(meetingTypeVo);

		}

		return meetingTypeVoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#queryMeetingTypeProfiles()
	 */
	@Override
	public List<MeetingTypeVo> queryMeetingTypeProfiles() {

		List<MeetingTypeVo> meetingTypeVoList = Lists.newArrayList();

		List<MeetingTypeDo> meetingTypeDoList = meetingTypeDao.queryMeetingTypes();
		for (MeetingTypeDo meetingTypeDo : meetingTypeDoList) {

			MeetingTypeVo meetingTypeVo = new MeetingTypeVo();
			BeanUtils.copyProperties(meetingTypeDo, meetingTypeVo);

			meetingTypeVoList.add(meetingTypeVo);
		}

		return meetingTypeVoList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#queryMeetingType(java.lang.String)
	 */
	@Override
	public MeetingTypeVo queryMeetingType(String id) {

		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingType(id);

		MeetingTypeVo meetingTypeVo = new MeetingTypeVo();
		BeanUtils.copyProperties(meetingTypeDo, meetingTypeVo);

		return meetingTypeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#queryMeetingTypeByTypeName(java.lang.String)
	 */
	@Override
	public MeetingTypeVo queryMeetingTypeByTypeName(String typeName) {

		MeetingTypeDo meetingTypeDo = meetingTypeDao.queryMeetingTypeByTypeName(typeName);

		MeetingTypeVo meetingTypeVo = new MeetingTypeVo();
		BeanUtils.copyProperties(meetingTypeDo, meetingTypeVo);

		return meetingTypeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#createMeetingType(cn.boxiao.bxn.meeting.vo.MeetingTypeVo)
	 */
	@Override
	public MeetingTypeVo createMeetingType(MeetingTypeVo meetingTypeVo) {

		MeetingTypeDo meetingType = meetingTypeDao.queryMeetingTypeByTypeName(meetingTypeVo.getTypeName());
		if (StringUtils.isNotBlank(meetingType.getId())) {
			throw new BXQBusinessRuntimeException(ErrCode.DUPLICATED_OBJECT_FOUND, "会议类型已存在");
		}

		MeetingTypeDo meetingTypeDo = new MeetingTypeDo();
		BeanUtils.copyProperties(meetingTypeVo, meetingTypeDo);

		meetingTypeDo = meetingTypeDao.createMeetingType(meetingTypeDo);
		meetingTypeVo.setId(meetingTypeDo.getId());

		return meetingTypeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#updateMeetingType(cn.boxiao.bxn.meeting.vo.MeetingTypeVo)
	 */
	@Override
	public int updateMeetingType(MeetingTypeVo meetingTypeVo) {

		MeetingTypeDo meetingType = meetingTypeDao.queryMeetingTypeByTypeName(meetingTypeVo.getTypeName());
		if (StringUtils.isNotBlank(meetingType.getId())
				&& !StringUtils.equals(meetingType.getId(), meetingTypeVo.getId())) {
			throw new BXQBusinessRuntimeException(ErrCode.DUPLICATED_OBJECT_FOUND, "会议类型已存在");
		}

		MeetingTypeDo meetingTypeDo = new MeetingTypeDo();
		BeanUtils.copyProperties(meetingTypeVo, meetingTypeDo);

		return meetingTypeDao.updateMeetingType(meetingTypeDo);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.MeetingTypeService#deleteMeetingType(java.lang.String)
	 */
	@Override
	public int deleteMeetingType(String id) {

		return meetingTypeDao.deleteMeetingType(id);
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

	/**
	 * 获取关联分组Map
	 * 
	 * @return
	 */
	private Map<String, String> convertGroupListToMap() {
		List<MeetingGroupVo> meetingGroupVoList = meetingGroupService.queryMeetingGroupProfilesByParent("0");

		Map<String, String> groupMap = Maps.newHashMap();
		for (MeetingGroupVo meetingGroupVo : meetingGroupVoList) {
			groupMap.put(meetingGroupVo.getId(), meetingGroupVo.getGroupName());
		}

		return groupMap;

	}
}
