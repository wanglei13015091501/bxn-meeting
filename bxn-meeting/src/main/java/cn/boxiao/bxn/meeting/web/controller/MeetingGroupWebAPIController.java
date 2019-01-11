package cn.boxiao.bxn.meeting.web.controller;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;
import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.common.util.BxnFlashScopeHelper;
import cn.boxiao.bxn.meeting.ErrCode;
import cn.boxiao.bxn.meeting.service.MeetingGroupService;
import cn.boxiao.bxn.meeting.vo.MeetingGroupVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class MeetingGroupWebAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MeetingGroupService meetingGroupService;

	/**
	 * 创建部门
	 * 
	 * @param meetingGroupVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groups", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createMeetingGroup(MeetingGroupVo meetingGroupVo) {

		logger.debug("START: API call createMeetingGroup, parameter: [meetingGroupVo={}]", meetingGroupVo);

		// 校验入参
		if (StringUtils.isEmpty(meetingGroupVo.getGroupName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		LoggedUser userVo = SecurityUtil.getCurrentUser();
		meetingGroupVo.setCreatorId(String.valueOf(userVo.getId()));
		meetingGroupService.createMeetingGroup(meetingGroupVo);

		AuditLog.audit("创建会议部门,信息内容:" + meetingGroupVo.toString());
		logger.debug("END: API call createMeetingGroup, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 更新部门
	 * 
	 * @param groupId
	 * @param meetingGroupVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groups/{groupId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateMeetingGroup(@PathVariable String groupId, MeetingGroupVo meetingGroupVo) {

		logger.debug("START: API call updateMeetingGroup, parameter: [meetingGroupVo={}]", meetingGroupVo);

		// 校验入参
		if (StringUtils.isEmpty(meetingGroupVo.getGroupName())) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		meetingGroupVo.setId(groupId);
		meetingGroupService.updateMeetingGroup(meetingGroupVo);

		AuditLog.audit("更新会议类型,信息内容:" + meetingGroupVo.toString());
		logger.debug("END: API call updateMeetingGroup, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 删除部门
	 * 
	 * @param groupId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groups/{groupId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteMeetingGroup(@PathVariable String groupId) {

		logger.debug("START: API call deleteMeetingGroup, parameter: [groupId={}]", groupId);

		meetingGroupService.deleteMeetingGroup(groupId);

		AuditLog.audit("删除会议部门,会议部门ID:" + groupId);
		logger.debug("END: API call deleteMeetingGroup, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 添加部门用户
	 * 
	 * @param meetingGroupVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groups/users", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createMeetingGroupUser(@RequestParam String groupId, @RequestParam String userIds,
			HttpSession session) {

		logger.debug("START: API call createMeetingGroupUser, parameter: [groupId={},userIds={}]", groupId, userIds);

		// 校验入参
		if (StringUtils.isBlank(groupId) || StringUtils.isBlank(userIds)) {
			throw new BXQBusinessRuntimeException(ErrCode.NOT_HAVE_CORRECT_DATA_FORMAT, "内部错误");
		}

		LoggedUser userVo = SecurityUtil.getCurrentUser();

		String duplicateName = meetingGroupService.createMeetingGroupUser(groupId, userIds,
				String.valueOf(userVo.getId()));
		if (StringUtils.isNotBlank(duplicateName)) {
			BxnFlashScopeHelper.addAttr(session, Constants.SUCCESS_MSG_BXN_FLASH_SCOPE_KEY,
					"无法添加" + duplicateName + "，因为相同部门分类下不能重复添加用户");
		}

		AuditLog.audit("添加部门用户,部门ID:" + groupId + ",用户ID：" + userIds + ",重复用户未添加成功：" + duplicateName);
		logger.debug("END: API call createMeetingGroupUser, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 删除部门用户
	 * 
	 * @param groupId
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN" })
	@RequestMapping(value = "/groups/{groupId}/users/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteMeetingGroupUser(@PathVariable String groupId, @PathVariable String userId) {

		logger.debug("START: API call deleteMeetingGroupUser, parameter: [groupId={}, userId={}]", groupId, userId);

		meetingGroupService.deleteMeetingGroupUser(groupId, userId);

		AuditLog.audit("删除部门用户,部门ID:" + groupId + ",用户ID:" + userId);
		logger.debug("END: API call deleteMeetingGroupUser, return: [HttpStatus.OK] ");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
