package cn.boxiao.bxn.meeting.web.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.common.util.FileDownloadUtils;
import cn.boxiao.bxn.meeting.ErrCode;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class PlaceWebAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlaceService placeService;

	/**
	 * 获取指定考勤会议地点信息
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/places/{placeId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PlaceVo> getPlace(@PathVariable String placeId) {

		logger.debug("START: API call getPlace, parameter: [placeId={}]", placeId);
		PlaceVo placeVo = placeService.queryPlace(placeId);

		logger.debug("END: API call getPlace, return: [HttpStatus.OK] ");

		return new ResponseEntity<PlaceVo>(placeVo, HttpStatus.OK);
	}

	/**
	 * 创建考勤会议地点
	 * 
	 * @param placeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/places", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createPlace(PlaceVo placeVo) {

		logger.debug("START: API call createPlace, parameter: [placeVo={}]", placeVo);

		// 校验入参
		if (StringUtils.isEmpty(placeVo.getPlaceNo()) || StringUtils.isEmpty(placeVo.getPlaceName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		placeService.createPlace(placeVo);

		AuditLog.audit("创建考勤会议地点,信息内容:" + placeVo.toString());
		logger.debug("END: API call createPlace, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 更新考勤会议地点
	 * 
	 * @param placeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/places/{placeId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updatePlace(@PathVariable String placeId, PlaceVo placeVo) {

		logger.debug("START: API call updatePlace, parameter: [placeVo={}]", placeVo);

		// 校验入参
		if (StringUtils.isEmpty(placeVo.getPlaceNo()) || StringUtils.isEmpty(placeVo.getPlaceName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		placeVo.setId(placeId);
		placeService.updatePlace(placeVo);

		AuditLog.audit("更新考勤会议地点,信息内容:" + placeVo.toString());
		logger.debug("END: API call updatePlace, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 删除考勤会议地点
	 * 
	 * @param placeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/places/{placeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deletePlace(@PathVariable String placeId) {

		logger.debug("START: API call deletePlace, parameter: [placeId={}]", placeId);

		placeService.deletePlace(placeId);

		AuditLog.audit("删除考勤会议地点,会议地点ID:" + placeId);
		logger.debug("END: API call deletePlace, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 下载空间信息导入模版
	 * 
	 * @param response
	 * @param request
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/place-templates", method = RequestMethod.GET)
	public void downloadPlaceTemplate(HttpServletResponse response, HttpServletRequest request) {
		logger.debug("START: API call downloadPlaceTemplate, parameter: [request={}]", request);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition",
				"attachment; filename=" + FileDownloadUtils.fileNameToUtf8String(request, "会议地点导入模版.xls"));
		response.resetBuffer();
		HSSFWorkbook workbook = placeService.getImportTemplate();
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("会议地点导入模板下载失败，错误信息", e);
		}
		logger.debug("会议地点导入模板下载成功。");
		logger.debug("END: API call downloadPlaceTemplate. ");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "places-export", method = RequestMethod.GET)
	public void exportPlace(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("START: API call exportPlace, parameter: [request={}]", request);

		response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置导出文件格式
		response.setHeader("Content-Disposition",
				"attachment; filename=" + FileDownloadUtils.fileNameToUtf8String(request, "全部会议地点.xls"));
		response.resetBuffer();

		HSSFWorkbook workbook = placeService.generate4Export();

		OutputStream out;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			response.flushBuffer();
			out.close();
		} catch (Exception e) {
			logger.error("会议地点导出失败，错误信息", e);
		}

		logger.debug("会议地点导出成功。");
		logger.debug("END: API call exportPlace. ");
	}
}
