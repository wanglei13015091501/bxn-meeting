package cn.boxiao.bxn.meeting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.boxiao.bxn.common.model.ImportColumn;
import cn.boxiao.bxn.dataimport.model.ExcelImportService;
import cn.boxiao.bxn.dataimport.model.ImportStat;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

/**
 * 
 * @author supercao
 * @since bxc-space 1.0 2017年3月13日
 */
@Service("excelImportPlaceService")
public class PlaceImportService implements ExcelImportService {

	@Autowired
	private PlaceService placeService;

	/**
	 * @see cn.boxiao.bxn.dataimport.model.ExcelImportService#validateExcelData(java.util.List,
	 *      java.util.List)
	 */
	@Override
	public List<String> validateExcelData(List<String> heads, List<String[]> datas) {
		List<String> strList = Lists.newArrayList();
		if (heads == null || datas == null) {
			strList.add("excel文件异常");
			return Lists.newArrayList();
		} else {
			int headsSize = heads.size();
			for (String[] data : datas) {
				if (headsSize != data.length) {
					strList.add("excel文件的标题列和内容列不相符");
					return Lists.newArrayList();
				}
			}
		}
		return null;
	}

	/**
	 * @see cn.boxiao.bxn.dataimport.model.ExcelImportService#importDescription()
	 */
	@Override
	public String[] importDescription() {
		return new String[] { "1. 请先下载Excel模板文件（点击选择框下的“下载模板”），并将其另存为一个新文件。\n" + "2. 在该文件中输入会议地点信息，注意遵守以下限制条件：\n"
				+ "\ta. 表头的第一列和第二列，即“会议地点编码”、“会议地点名称”不能为空；\n" + "\tb. excel中的所有内容都需要加上边框；\n" + "\tc. 请勿修改第一行表头中的文字；\n"
				+ "3.如果导入数据量较大，可能需要几分钟，请耐心等待系统提示；\n" + "4.特别注意：导入不支持更新操作，要修改会议地点信息，请使用页面上的修改功能。" };
	}

	/**
	 * @see cn.boxiao.bxn.dataimport.model.ExcelImportService#targetImportData(java.util.Map)
	 */
	@Override
	public List<ImportColumn> targetImportData(Map<String, String> params) {
		List<ImportColumn> result = Lists.newArrayList();
		result.add(new ImportColumn("placeNo", "会议地点编码*", ImportColumn.ColumnType.STRING, true));
		result.add(new ImportColumn("placeName", "会议地点名称*", ImportColumn.ColumnType.STRING, true));
		result.add(new ImportColumn("description", "会议地点描述", ImportColumn.ColumnType.STRING, false));
		return result;
	}

	/**
	 * @see cn.boxiao.bxn.dataimport.model.ExcelImportService#getMainColumnType()
	 */
	@Override
	public ColumnType getMainColumnType() {
		return ColumnType.EXCEL;
	}

	/**
	 * @see cn.boxiao.bxn.dataimport.model.ExcelImportService#saveData(java.util.List,
	 *      java.util.Map)
	 */
	@Override
	public ImportStat saveData(List<Map<String, Object>> successData, Map<String, String> params) {

		ImportStat is = new ImportStat();
		int err = 0;// 失败
		int suc = 0;// 成功
		List<String> errorMsg = Lists.newArrayList();

		for (Map<String, Object> placeMap : successData) {
			PlaceVo place = new PlaceVo();
			String placeNo = (String) placeMap.get("placeNo");
			place.setPlaceNo(placeNo);
			String placeName = (String) placeMap.get("placeName");
			place.setPlaceName(placeName);
			place.setDescription((String) placeMap.get("description"));
			// PlaceVo foundByPlaceNo =
			// placeService.queryPlaceByPlaceNo(placeNo);

			// if (StringUtils.isNotBlank(foundByPlaceNo.getId())) {
			// ++err;
			// errorMsg.add(String.format("会议地点编码[%s]已存在，请勿重复添加。", placeNo));
			// continue;
			// }
			// PlaceVo foundByName =
			// placeService.queryPlaceByPlaceName(placeName);
			// if (StringUtils.isNotBlank(foundByName.getId())) {
			// ++err;
			// errorMsg.add(String.format("会议地点名称[%s]已存在，请勿重复添加。", placeName));
			// continue;
			// }
			++suc;
			placeService.createPlace(place);
		}

		is.setErrored(err);
		is.setErrorMsg(errorMsg);
		is.setSuccessed(suc);
		return is;
	}

	@Override
	public String successCallBack() {
		return "meeting/place/indexPage";
	}

	@Override
	public String getPermissions() {
		return "PERM_MEETING_ADMIN";
	}
}
