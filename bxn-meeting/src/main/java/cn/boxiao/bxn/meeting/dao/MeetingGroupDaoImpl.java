package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.MeetingGroupDo;
import cn.boxiao.bxn.meeting.data.MeetingGroupUserDo;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

@Component
public class MeetingGroupDaoImpl implements MeetingGroupDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroupsByParent(java.lang.String)
	 */
	@Override
	public List<MeetingGroupDo> queryMeetingGroupsByParent(String parentId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id, order_no, group_name, parent_id, status, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group ");
		sql.append(" where ");
		sql.append(" deleted=:deleted and parent_id=:parentId");
		sql.append(" order by order_no+1,order_no asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("parentId", parentId);

		List<MeetingGroupDo> meetingGroupDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupDo.class));
		return meetingGroupDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroup(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public MeetingGroupDo queryMeetingGroup(String parentId, String groupName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, order_no, group_name, parent_id, status, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group ");
		sql.append(" where ");
		sql.append(" deleted=:deleted and parent_id=:parentId and group_name=:groupName");
		sql.append(" order by order_no+1,order_no asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("parentId", parentId);
		params.put("groupName", groupName);

		List<MeetingGroupDo> meetingGroupDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupDo.class));
		return CollectionUtils.isEmpty(meetingGroupDoList) ? new MeetingGroupDo() : meetingGroupDoList.get(0);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroup(java.lang.String)
	 */
	@Override
	public MeetingGroupDo queryMeetingGroup(String groupId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, order_no, group_name, parent_id, status, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group ");
		sql.append(" where ");
		sql.append(" deleted=:deleted and id=:groupId");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("groupId", groupId);

		List<MeetingGroupDo> meetingGroupDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupDo.class));
		return CollectionUtils.isEmpty(meetingGroupDoList) ? new MeetingGroupDo() : meetingGroupDoList.get(0);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#saveMeetingGroup(cn.boxiao.bxn.meeting.data.MeetingGroupDo)
	 */
	@Override
	public MeetingGroupDo saveMeetingGroup(MeetingGroupDo meetingGroupDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_group ");
		sql.append(" ( id,order_no, group_name, parent_id, status,deleted,creator_id,create_time)");
		sql.append(" values (:id,:orderNo,:groupName,:parentId,:status,:deleted,:creatorId,:createTime)");

		Map<String, Object> params = Maps.newHashMap();
		meetingGroupDo.setId(UUIDGenerator.generateUniqueID(""));
		meetingGroupDo.setDeleted(DELETED_NO);
		meetingGroupDo.setCreateTime(new Date());

		params.put("id", meetingGroupDo.getId());
		params.put("orderNo", meetingGroupDo.getOrderNo());
		params.put("groupName", meetingGroupDo.getGroupName());
		params.put("parentId", meetingGroupDo.getParentId());
		params.put("status", STATUS_OPEN);
		params.put("deleted", meetingGroupDo.getDeleted());
		params.put("creatorId", meetingGroupDo.getCreatorId());
		params.put("createTime", meetingGroupDo.getCreateTime());
		namedParameterJdbcTemplate.update(sql.toString(), params);

		return meetingGroupDo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#updateMeetingGroup(cn.boxiao.bxn.meeting.data.MeetingGroupDo)
	 */
	@Override
	public int updateMeetingGroup(MeetingGroupDo meetingGroupDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_group ");
		sql.append(" set group_name = :groupName, order_no = :orderNo");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", meetingGroupDo.getId());
		params.put("groupName", meetingGroupDo.getGroupName());
		params.put("orderNo", meetingGroupDo.getOrderNo());

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#deleteMeetingGroup(java.lang.String)
	 */
	@Override
	public int deleteMeetingGroup(String groupId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_group ");
		sql.append(" set deleted=:deleted ");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", groupId);
		params.put("deleted", DELETED_YES);

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroupUsers(java.lang.String)
	 */
	@Override
	public List<MeetingGroupUserDo> queryMeetingGroupUsers(String groupId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, user_id, user_name, group_id, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group_user ");
		sql.append(" where ");
		sql.append(" deleted=:deleted and group_id=:groupId");
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("groupId", groupId);

		List<MeetingGroupUserDo> meetingGroupUserDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupUserDo.class));
		return meetingGroupUserDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroupUsersByParent(java.lang.String)
	 */
	@Override
	public List<MeetingGroupUserDo> queryMeetingGroupUsersByParent(String parentId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, user_id, user_name, group_id, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group_user ");
		sql.append(" where deleted=:deleted and ");
		sql.append(
				" group_id in ( select id from meeting_group where deleted = :deleted and parent_id = :parentId ) ");
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("parentId", parentId);

		List<MeetingGroupUserDo> meetingGroupUserDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupUserDo.class));
		return meetingGroupUserDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#queryMeetingGroupUserInCategory(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public MeetingGroupUserDo queryMeetingGroupUserInCategory(String userId, List<String> sameParGroupIdList) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id, user_id, user_name, group_id, creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_group_user ");
		sql.append(" where ");
		sql.append(" deleted=:deleted and user_id=:userId and group_id in (:groupId)");
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);
		params.put("userId", userId);
		params.put("groupId", sameParGroupIdList);

		List<MeetingGroupUserDo> meetingGroupUserDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingGroupUserDo.class));
		return CollectionUtils.isEmpty(meetingGroupUserDoList) ? new MeetingGroupUserDo()
				: meetingGroupUserDoList.get(0);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#saveMeetingGroupUser(cn.boxiao.bxn.meeting.data.MeetingGroupUserDo)
	 */
	@Override
	public MeetingGroupUserDo saveMeetingGroupUser(MeetingGroupUserDo meetingGroupUserDo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_group_user ");
		sql.append(" ( id,user_id, user_name, group_id, deleted,creator_id,create_time)");
		sql.append(" values (:id,:userId,:userName,:groupId,:deleted,:creatorId,:createTime)");

		Map<String, Object> params = Maps.newHashMap();
		meetingGroupUserDo.setId(UUIDGenerator.generateUniqueID(""));
		meetingGroupUserDo.setDeleted(DELETED_NO);
		meetingGroupUserDo.setCreateTime(new Date());

		params.put("id", meetingGroupUserDo.getId());
		params.put("userId", meetingGroupUserDo.getUserId());
		params.put("userName", meetingGroupUserDo.getUserName());
		params.put("groupId", meetingGroupUserDo.getGroupId());
		params.put("deleted", meetingGroupUserDo.getDeleted());
		params.put("creatorId", meetingGroupUserDo.getCreatorId());
		params.put("createTime", meetingGroupUserDo.getCreateTime());
		namedParameterJdbcTemplate.update(sql.toString(), params);

		return meetingGroupUserDo;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingGroupDao#deleteMeetingGroupUser(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int deleteMeetingGroupUser(String groupId, String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_group_user ");
		sql.append(" set deleted=:deleted ");
		sql.append(" where group_id=:groupId and user_id=:userId");

		Map<String, Object> params = Maps.newHashMap();
		params.put("groupId", groupId);
		params.put("userId", userId);
		params.put("deleted", DELETED_YES);

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}
}
