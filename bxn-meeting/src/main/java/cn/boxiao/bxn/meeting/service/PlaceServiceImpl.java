package cn.boxiao.bxn.meeting.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.boxiao.bxn.meeting.dao.PlaceDao;
import cn.boxiao.bxn.meeting.data.PlaceDo;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.util.DateUtil;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private PlaceDao placeDao;

	String TEMPLATE_HEADER = "会议地点编码*,会议地点名称*,会议地点描述";
	
	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.PlaceService#queryPlaces(java.lang.String)
	 */
	@Override
	public List<PlaceVo> queryPlaces(String content) {

		List<PlaceVo> placeVoList = Lists.newArrayList();
		List<PlaceDo> placeDoList = placeDao.queryPlaces(content);

		for (PlaceDo placeDo : placeDoList) {

			PlaceVo placeVo = new PlaceVo();
			BeanUtils.copyProperties(placeDo, placeVo);

			placeVo.setCreateTime(DateUtil.toString_YYYY_MM_DD_HH_MM_SS(placeDo.getCreateTime()));

			placeVoList.add(placeVo);
		}

		return placeVoList;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.service.PlaceService#queryPlace(java.lang.String)
	 */
	@Override
	public PlaceVo queryPlace(String id) {

		PlaceDo placeDo = placeDao.queryPlace(id);

		PlaceVo placeVo = new PlaceVo();
		BeanUtils.copyProperties(placeDo, placeVo);

		return placeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.PlaceService#queryPlaceByPlaceNo(java.lang.String)
	 */
	@Override
	public PlaceVo queryPlaceByPlaceNo(String placeNo) {

		PlaceDo placeDo = placeDao.queryPlaceByPlaceNo(placeNo);

		PlaceVo placeVo = new PlaceVo();
		BeanUtils.copyProperties(placeDo, placeVo);

		return placeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.PlaceService#queryPlaceByPlaceName(java.lang.String)
	 */
	@Override
	public PlaceVo queryPlaceByPlaceName(String placeName) {

		PlaceDo placeDo = placeDao.queryPlaceByPlaceName(placeName);

		PlaceVo placeVo = new PlaceVo();
		BeanUtils.copyProperties(placeDo, placeVo);

		return placeVo;
	}

	
	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.service.PlaceService#createPlace(cn.boxiao.bxn.meeting.vo.PlaceVo)
	 */
	@Override
	public PlaceVo createPlace(PlaceVo placeVo) {

		PlaceDo placeDo = new PlaceDo();
		BeanUtils.copyProperties(placeVo, placeDo);

		placeDo = placeDao.createPlace(placeDo);
		placeVo.setId(placeVo.getId());

		return placeVo;
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.service.PlaceService#deletePlace(java.lang.String)
	 */
	@Override
	public int deletePlace(String id) {

		return placeDao.deletePlace(id);
	}

	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.place.service.PlaceService#updatePlace(cn.boxiao.bxn.meeting.vo.PlaceVo)
	 */
	@Override
	public int updatePlace(PlaceVo placeVo) {

		PlaceDo placeDo = new PlaceDo();
		BeanUtils.copyProperties(placeVo, placeDo);

		return placeDao.updatePlace(placeDo);
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.PlaceService#getImportTemplate()
	 */
	@Override
	public HSSFWorkbook getImportTemplate() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("会议地点");

		// 表头样式
		CellStyle cellStyleHead = workbook.createCellStyle();
		cellStyleHead.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		cellStyleHead.setFont(font);

		// 内容样式
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		List<String> headList = CsvUtils.convertCSVToList(TEMPLATE_HEADER);
		Row headFirstRow = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			Cell headFirstCell = headFirstRow.createCell(i);
			headFirstCell.setCellValue(headList.get(i));
			headFirstCell.setCellStyle(cellStyleHead);

			sheet.setColumnWidth(i, 30 * 256);
		}

		return workbook;
	}
	
	/**
	 * 
	 * @see cn.boxiao.bxn.meeting.service.PlaceService#generate4Export()
	 */
	@Override
	public HSSFWorkbook generate4Export() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("会议地点");

		// 表头样式
		CellStyle cellStyleHead = workbook.createCellStyle();
		cellStyleHead.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		cellStyleHead.setFont(font);

		// 内容样式
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);

		List<String> headList = CsvUtils.convertCSVToList(TEMPLATE_HEADER);
		Row headFirstRow = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			Cell headFirstCell = headFirstRow.createCell(i);
			headFirstCell.setCellValue(headList.get(i));
			headFirstCell.setCellStyle(cellStyleHead);

			sheet.setColumnWidth(i, 30 * 256);
		}

		int rowNum = 1;
		List<PlaceDo> places = placeDao.queryPlaces("");
		for (PlaceDo place : places) {
			Row row = sheet.createRow(rowNum);
			{
				Cell cell = row.createCell(0);
				cell.setCellValue(place.getPlaceNo());
				cell.setCellStyle(cellStyle);
			}
			{
				Cell cell = row.createCell(1);
				cell.setCellValue(place.getPlaceName());
				cell.setCellStyle(cellStyle);
			}
			{
				Cell cell = row.createCell(2);
				cell.setCellValue(place.getDescription());
				cell.setCellStyle(cellStyle);
			}
			rowNum++;
		}
		return workbook;
	}

}
