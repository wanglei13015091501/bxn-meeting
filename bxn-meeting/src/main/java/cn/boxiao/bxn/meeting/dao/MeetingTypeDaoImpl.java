package cn.boxiao.bxn.meeting.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.data.MeetingTypeDo;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

@Component
public class MeetingTypeDaoImpl implements MeetingTypeDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#queryMeetingTypes()
	 */
	@Override
	public List<MeetingTypeDo> queryMeetingTypes() {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,type_name,group_id,creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_type ");
		sql.append(" where ");
		sql.append(" deleted=:deleted ");
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("deleted", DELETED_NO);

		List<MeetingTypeDo> meetingTypeDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingTypeDo.class));

		return meetingTypeDoList;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#queryMeetingTypesByGroupId(java.lang.String)
	 */
	@Override
	public List<MeetingTypeDo> queryMeetingTypesByGroupId(String groupId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,type_name,group_id,creator_id,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_type ");
		sql.append(" where ");
		sql.append(" group_id = :groupId and deleted=:deleted ");
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("groupId", groupId);
		params.put("deleted", DELETED_NO);

		List<MeetingTypeDo> meetingTypeDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingTypeDo.class));

		return meetingTypeDoList;

	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#queryMeetingType(java.lang.String)
	 */
	@Override
	public MeetingTypeDo queryMeetingType(String id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,type_name,group_id,creator_id,deleted,create_time ");
		sql.append(" from ");
		sql.append(" meeting_type ");
		sql.append(" where  ");
		sql.append(" id=:id and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("deleted", DELETED_NO);

		List<MeetingTypeDo> typeDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingTypeDo.class));

		return typeDoList.isEmpty() ? new MeetingTypeDo() : typeDoList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#queryMeetingTypeByTypeName(java.lang.String)
	 */
	@Override
	public MeetingTypeDo queryMeetingTypeByTypeName(String typeName) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,type_name,group_id,creator_id,deleted,create_time ");
		sql.append(" from ");
		sql.append(" meeting_type ");
		sql.append(" where  ");
		sql.append(" type_name = :typeName and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("typeName", typeName);
		params.put("deleted", DELETED_NO);

		List<MeetingTypeDo> typeDoList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(MeetingTypeDo.class));

		return typeDoList.isEmpty() ? new MeetingTypeDo() : typeDoList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#createMeetingType(cn.boxiao.bxn.meeting.data.MeetingTypeDo)
	 */
	@Override
	public MeetingTypeDo createMeetingType(MeetingTypeDo meetingTypeDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_type ");
		sql.append(" ( id,type_name,group_id,deleted,creator_id,create_time)");
		sql.append(" values (:id,:typeName,:groupId,:deleted,:creatorId,:createTime)");

		Map<String, Object> params = Maps.newHashMap();
		meetingTypeDo.setId(UUIDGenerator.generateUniqueID(""));
		meetingTypeDo.setDeleted(DELETED_NO);
		meetingTypeDo.setCreateTime(new Date());

		params.put("id", meetingTypeDo.getId());
		params.put("typeName", meetingTypeDo.getTypeName());
		params.put("groupId", meetingTypeDo.getGroupId());
		params.put("deleted", meetingTypeDo.getDeleted());
		params.put("creatorId", meetingTypeDo.getCreatorId());
		params.put("createTime", meetingTypeDo.getCreateTime());
		namedParameterJdbcTemplate.update(sql.toString(), params);

		return meetingTypeDo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#updateMeetingType(cn.boxiao.bxn.meeting.data.MeetingTypeDo)
	 */
	@Override
	public int updateMeetingType(MeetingTypeDo meetingTypeDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_type ");
		sql.append(" set type_name = :typeName, group_id = :groupId ");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", meetingTypeDo.getId());
		params.put("typeName", meetingTypeDo.getTypeName());
		params.put("groupId", StringUtils.defaultIfBlank(meetingTypeDo.getGroupId(), ""));

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.MeetingTypeDao#deleteMeetingType(java.lang.String)
	 */
	@Override
	public int deleteMeetingType(String id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_type ");
		sql.append(" set deleted=:deleted ");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("deleted", DELETED_YES);

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

}
