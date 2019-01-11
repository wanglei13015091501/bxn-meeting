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
import cn.boxiao.bxn.meeting.data.PlaceDo;
import cn.boxiao.bxn.meeting.util.UUIDGenerator;

@Component
public class PlaceDaoImpl implements PlaceDao, MeetingConstants {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.PlaceDao#queryPlaces(java.lang.String)
	 */
	@Override
	public List<PlaceDo> queryPlaces(String content) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,place_no,place_name,description,status,deleted,create_time ");
		sql.append(" from  ");
		sql.append(" meeting_attendance_place ");
		sql.append(" where ");
		sql.append(" deleted=:deleted ");
		if (StringUtils.isNotBlank(content)) {
			sql.append(" and (place_no like :content or place_name like :content)");
		}
		sql.append(" order by create_time asc ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("content", content);
		params.put("deleted", DELETED_NO);
		if (StringUtils.isBlank(content)) {
			content = "";
		}
		params.put("content", "%" + content + "%");

		List<PlaceDo> placeList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(PlaceDo.class));

		return placeList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.dao.PlaceDao#queryPlace(java.lang.String)
	 */
	@Override
	public PlaceDo queryPlace(String id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,place_no,place_name,description,status,deleted,create_time ");
		sql.append(" from ");
		sql.append(" meeting_attendance_place ");
		sql.append(" where  ");
		sql.append(" id=:id and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("deleted", DELETED_NO);

		List<PlaceDo> placeList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(PlaceDo.class));

		return placeList.isEmpty() ? new PlaceDo() : placeList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.PlaceDao#queryPlaceByPlaceNo(java.lang.String)
	 */
	@Override
	public PlaceDo queryPlaceByPlaceNo(String placeNo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,place_no,place_name,description,status,deleted,create_time ");
		sql.append(" from ");
		sql.append(" meeting_attendance_place ");
		sql.append(" where  ");
		sql.append(" place_no = :placeNo and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("placeNo", placeNo);
		params.put("deleted", DELETED_NO);

		List<PlaceDo> placeList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(PlaceDo.class));

		return placeList.isEmpty() ? new PlaceDo() : placeList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.dao.PlaceDao#queryPlaceByPlaceName(java.lang.String)
	 */
	@Override
	public PlaceDo queryPlaceByPlaceName(String placeName) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select id,place_no,place_name,description,status,deleted,create_time ");
		sql.append(" from ");
		sql.append(" meeting_attendance_place ");
		sql.append(" where  ");
		sql.append(" place_name = :placeName and deleted=:deleted ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("placeName", placeName);
		params.put("deleted", DELETED_NO);

		List<PlaceDo> placeList = namedParameterJdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<>(PlaceDo.class));

		return placeList.isEmpty() ? new PlaceDo() : placeList.get(0);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.dao.PlaceDao#createPlace(cn.boxiao.bxn.meeting.place.data.PlaceDo)
	 */
	@Override
	public PlaceDo createPlace(PlaceDo placeDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ");
		sql.append(" meeting_attendance_place ");
		sql.append(" ( id,place_no,place_name,description,status,deleted,create_time)");
		sql.append(" values (:id,:placeNo,:placeName,:description,:status,:deleted,:createTime)");

		Map<String, Object> params = Maps.newHashMap();
		placeDo.setId(UUIDGenerator.generateUniqueID(""));
		placeDo.setDeleted(DELETED_NO);
		placeDo.setStatus(STATUS_OPEN);
		placeDo.setCreateTime(new Date());
		placeDo.setPlaceName(StringUtils.defaultIfBlank(placeDo.getPlaceName(), ""));
		placeDo.setPlaceNo(StringUtils.defaultIfBlank(placeDo.getPlaceNo(), ""));
		placeDo.setDescription(StringUtils.defaultIfBlank(placeDo.getDescription(), ""));

		params.put("id", placeDo.getId());
		params.put("placeNo", placeDo.getPlaceNo());
		params.put("placeName", placeDo.getPlaceName());
		params.put("description", placeDo.getDescription());
		params.put("status", placeDo.getStatus());
		params.put("deleted", placeDo.getDeleted());
		params.put("createTime", placeDo.getCreateTime());
		namedParameterJdbcTemplate.update(sql.toString(), params);

		return placeDo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.dao.PlaceDao#updatePlace(cn.boxiao.bxn.meeting.place.data.PlaceDo)
	 */
	@Override
	public int updatePlace(PlaceDo placeDo) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_place ");
		sql.append(" set place_no=:placeNo,place_name = :placeName,description=:description ");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		placeDo.setPlaceName(StringUtils.defaultIfBlank(placeDo.getPlaceName(), ""));
		placeDo.setPlaceNo(StringUtils.defaultIfBlank(placeDo.getPlaceNo(), ""));
		placeDo.setDescription(StringUtils.defaultIfBlank(placeDo.getDescription(), ""));

		params.put("id", placeDo.getId());
		params.put("placeNo", placeDo.getPlaceNo());
		params.put("placeName", placeDo.getPlaceName());
		params.put("description", placeDo.getDescription());

		return namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.dao.PlaceDao#deletePlace(java.lang.String)
	 */
	@Override
	public int deletePlace(String id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update ");
		sql.append(" meeting_attendance_place ");
		sql.append(" set deleted=:deleted ");
		sql.append(" where id=:id ");

		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("deleted", DELETED_YES);

		return namedParameterJdbcTemplate.update(sql.toString(), params);

	}

}
