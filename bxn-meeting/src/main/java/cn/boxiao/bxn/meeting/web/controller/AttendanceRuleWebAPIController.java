package cn.boxiao.bxn.meeting.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;

import cn.boxiao.bxn.common.util.AuditLog;
import cn.boxiao.bxn.common.util.JsonHelper;
import cn.boxiao.bxn.meeting.service.AttendanceRuleService;
import cn.boxiao.bxn.meeting.vo.AttendanceRuleVo;

@Controller
@RequestMapping(value = "/webapi/meeting/v1")
public class AttendanceRuleWebAPIController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceRuleService attendanceRuleService;

	/**
	 * 更新考勤规则
	 * 
	 * @param placeVo
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/rules", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateAttendanceRule(@RequestParam String ruleJson) {

		logger.debug("START: API call updateAttendanceRule, parameter: [ruleJson={}]", ruleJson);

		List<AttendanceRuleVo> ruleList = JsonHelper.deserialize(ruleJson,
				new TypeReference<ArrayList<AttendanceRuleVo>>() {
				});
		// 校验入参
		attendanceRuleService.updateAttendanceRule(ruleList);

		AuditLog.audit("更新考勤规则,考勤规则信息:" + ruleList);
		logger.debug("END: API call updateAttendanceRule, return: [HttpStatus.OK] ");
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
