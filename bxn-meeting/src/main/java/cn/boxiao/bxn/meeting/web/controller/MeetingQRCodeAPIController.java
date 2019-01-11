package cn.boxiao.bxn.meeting.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.boxiao.bxn.base.client.rest.security.LoggedUser;
import cn.boxiao.bxn.base.client.rest.security.SecurityUtil;
import cn.boxiao.bxn.meeting.service.AttendanceService;

/**
 * @author dongdong
 * @version 2017.3.22 二维码登录接口
 */
@Controller
@RequestMapping(value = "/mobile/api/meeting/v1")
public class MeetingQRCodeAPIController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceService attendanceCardService;

	/**
	 * 二维码打卡
	 * 
	 * @param meetingId
	 * @param timestamp
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/attendances", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> postEpcRecord(@RequestParam String meetingId,
			@RequestParam String timestamp, @RequestParam String token) {
		logger.debug("START: API call postEpcRecord, parameter: [ meetingId={},timestamp={},token={}]", meetingId,
				timestamp, token);

		LoggedUser loggedUser = SecurityUtil.getCurrentUser();
		Map<String, String> recordMap = attendanceCardService.postEpcRecordByQRCode(loggedUser.getUsername(), meetingId,
				timestamp, token);

		logger.debug("END: API call postEpcRecord, return: [{}] ", recordMap);
		return new ResponseEntity<Map<String, String>>(recordMap, HttpStatus.OK);
	}
}
