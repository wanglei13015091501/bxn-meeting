package cn.boxiao.bxn.meeting.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.boxiao.bxn.meeting.vo.PlaceVo;

public interface PlaceService {

	/**
	 * 查询会议地点信息
	 * 
	 * @param content
	 * @return
	 */
	List<PlaceVo> queryPlaces(String content);

	/**
	 * 查询一条会议地点信息
	 * 
	 * @param id
	 * @return
	 */
	PlaceVo queryPlace(String id);

	/**
	 * 通过编号获取会议地点信息
	 * 
	 * @param placeNo
	 * @return
	 */
	PlaceVo queryPlaceByPlaceNo(String placeNo);

	/**
	 * 通过会议地点名称获取会议地点信息
	 * 
	 * @param placeName
	 * @return
	 */
	PlaceVo queryPlaceByPlaceName(String placeName);

	/**
	 * 保存一条会议地点信息
	 * 
	 * @param placeVo
	 * @return
	 */
	PlaceVo createPlace(PlaceVo placeVo);

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
	 * @param placeVo
	 * @return
	 */
	int updatePlace(PlaceVo placeVo);

	/**
	 * 会议地点模板
	 * 
	 * @return
	 */
	HSSFWorkbook getImportTemplate();
	
	/**
	 * 考勤会议地点信息导出
	 * 
	 * @return
	 */
	HSSFWorkbook generate4Export();

}
