package cn.boxiao.bxn.meeting.web.controller;

import org.apache.commons.lang.StringUtils;
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

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.meeting.ErrCode;
import cn.boxiao.bxn.meeting.service.MeetingTypeService;
import cn.boxiao.bxn.meeting.vo.MeetingTypeVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class MeetingTypeWebAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MeetingTypeService meetingTypeService;

	/**
	 * 获取指定会议类型信息
	 * 
	 * @param typeId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/types/{typeId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<MeetingTypeVo> getMeetingType(@PathVariable String typeId) {

		logger.debug("START: API call getMeetingType, parameter: [typeId={}]", typeId);
		MeetingTypeVo MeetingTypeVo = meetingTypeService.queryMeetingType(typeId);

		logger.debug("END: API call getMeetingType, return: [HttpStatus.OK] ");

		return new ResponseEntity<MeetingTypeVo>(MeetingTypeVo, HttpStatus.OK);
	}

	/**
	 * 创建会议类型
	 * 
	 * @param meetingTypeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/types", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createMeetingType(MeetingTypeVo meetingTypeVo) {

		logger.debug("START: API call createMeetingType, parameter: [meetingTypeVo={}]", meetingTypeVo);

		// 校验入参
		if (StringUtils.isEmpty(meetingTypeVo.getTypeName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		LoggedUser userVo = SecurityUtil.getCurrentUser();
		meetingTypeVo.setCreatorId(String.valueOf(userVo.getId()));
		meetingTypeService.createMeetingType(meetingTypeVo);

		AuditLog.audit("创建会议类型,信息内容:" + meetingTypeVo.toString());
		logger.debug("END: API call createMeetingType, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 更新会议类型
	 * 
	 * @param typeId
	 * @param meetingTypeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/types/{typeId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateMeetingType(@PathVariable String typeId, MeetingTypeVo meetingTypeVo) {

		logger.debug("START: API call updateMeetingType, parameter: [meetingTypeVo={}]", meetingTypeVo);

		// 校验入参
		if (StringUtils.isEmpty(meetingTypeVo.getTypeName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		meetingTypeVo.setId(typeId);
		meetingTypeService.updateMeetingType(meetingTypeVo);

		AuditLog.audit("更新会议类型,信息内容:" + meetingTypeVo.toString());
		logger.debug("END: API call updateMeetingType, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 删除会议类型
	 * 
	 * @param typeId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/types/{typeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteMeetingType(@PathVariable String typeId) {

		logger.debug("START: API call deleteMeetingType, parameter: [typeId={}]", typeId);

		meetingTypeService.deleteMeetingType(typeId);

		AuditLog.audit("删除会议类型,会议类型ID:" + typeId);
		logger.debug("END: API call deleteMeetingType, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
