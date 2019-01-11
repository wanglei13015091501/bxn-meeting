package cn.boxiao.bxn.meeting.service;

import java.util.List;

import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;

public interface MeetingTypeService {

	/**
	 * 查询会议类型信息
	 * 
	 * @param content
	 * @return
	 */
	List<MeetingTypeVo> queryMeetingTypes();
	List<MeetingTypeVo> queryMeetingTypeProfiles();

	/**
	 * 查询一条会议类型信息
	 * 
	 * @param id
	 * @return
	 */
	MeetingTypeVo queryMeetingType(String id);

	/**
	 * 通过会议类型名称获取会议类型信息
	 * 
	 * @param typeName
	 * @return
	 */
	MeetingTypeVo queryMeetingTypeByTypeName(String typeName);

	/**
	 * 保存一条会议类型信息
	 * 
	 * @param meetingTypeVo
	 * @return
	 */
	MeetingTypeVo createMeetingType(MeetingTypeVo meetingTypeVo);

	/**
	 * 更新会议类型信息
	 * 
	 * @param meetingTypeVo
	 * @return
	 */
	int updateMeetingType(MeetingTypeVo meetingTypeVo);

	/**
	 * 删除一条会议类型信息
	 * 
	 * @param id
	 * @return
	 */
	int deleteMeetingType(String id);

}
