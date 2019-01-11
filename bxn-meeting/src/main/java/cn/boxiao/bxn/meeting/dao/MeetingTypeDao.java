package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.MeetingTypeDo;

public interface MeetingTypeDao {

	/**
	 * 查询会议类型信息
	 * 
	 * @param content
	 * @return
	 */
	List<MeetingTypeDo> queryMeetingTypes();

	/**
	 * 根据groupId查询会议类型信息
	 * 
	 * @param groupId
	 * @return
	 */
	List<MeetingTypeDo> queryMeetingTypesByGroupId(String groupId);

	/**
	 * 查询一条会议类型
	 * 
	 * @param id
	 * @return
	 */
	MeetingTypeDo queryMeetingType(String id);

	/**
	 * 通过会议类型名称获取会议类型
	 * 
	 * @param typeName
	 * @return
	 */
	MeetingTypeDo queryMeetingTypeByTypeName(String typeName);

	/**
	 * 添加会议类型信息
	 * 
	 * @param meetingTypeDo
	 * @return
	 */
	MeetingTypeDo createMeetingType(MeetingTypeDo meetingTypeDo);

	/**
	 * 更新会议类型信息
	 * 
	 * @param meetingTypeDo
	 * @return
	 */
	int updateMeetingType(MeetingTypeDo meetingTypeDo);

	/**
	 * 删除一条会议类型信息
	 * 
	 * @param id
	 * @return
	 */
	int deleteMeetingType(String id);

}
