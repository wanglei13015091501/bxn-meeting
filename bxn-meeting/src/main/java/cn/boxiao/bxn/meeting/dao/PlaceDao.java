package cn.boxiao.bxn.meeting.dao;

import java.util.List;

import cn.boxiao.bxn.meeting.data.PlaceDo;

public interface PlaceDao {

	/**
	 * 查询会议地点信息
	 * 
	 * @param content
	 * @return
	 */
	List<PlaceDo> queryPlaces(String content);

	/**
	 * 查询一条会议地点信息
	 * 
	 * @param id
	 * @return
	 */
	PlaceDo queryPlace(String id);

	/**
	 * 通过会议地点编号获取会议地点信息
	 * 
	 * @param placeNo
	 * @return
	 */
	PlaceDo queryPlaceByPlaceNo(String placeNo);

	/**
	 * 通过会议地点名称获取会议地点信息
	 * 
	 * @param placeName
	 * @return
	 */
	PlaceDo queryPlaceByPlaceName(String placeName);

	/**
	 * 添加会议地点信息
	 * 
	 * @param placeDo
	 * @return
	 */
	PlaceDo createPlace(PlaceDo placeDo);

	/**
	 * 删除一条会议地点信息
	 * 
	 * @param id
	 * @return
	 */
	int deletePlace(String id);

	/**
	 * 更新会议地点信息
	 * 
	 * @param placeDo
	 * @return
	 */
	int updatePlace(PlaceDo placeDo);

}
