package cn.boxiao.bxn.meeting.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.service.PlaceService;
import cn.boxiao.bxn.meeting.vo.PlaceVo;

@Controller
@RequestMapping(value = "/meeting/place")
public class PlaceWebController implements MeetingConstants {

	@Autowired
	private PlaceService placeService;

	/**
	 * 进入考勤会议地点设置页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public ModelAndView toIndexPage(
			@RequestParam(value = "page.page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(required = false) String queryContent) {

		Model model = new ExtendedModelMap();
		List<PlaceVo> placePage = placeService.queryPlaces(queryContent);
		model.addAttribute("places", placePage);

		return new ModelAndView("meeting/place/index", model.asMap());
	}

	/**
	 * 进入考勤会议地点创建页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/createPage", method = RequestMethod.GET)
	public ModelAndView toCreatePage() {

		Model model = new ExtendedModelMap();

		return new ModelAndView("meeting/place/_create", model.asMap());
	}

	/**
	 * 进入考勤会议地点更新页
	 * 
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView toEditPage(@RequestParam String placeId) {

		Model model = new ExtendedModelMap();
		PlaceVo placeVo = placeService.queryPlace(placeId);
		model.addAttribute("place", placeVo);

		return new ModelAndView("meeting/place/_edit", model.asMap());
	}
	
	/**
	 * 考勤会议地点导入页
	 * @return
	 */
	@Secured({ "PERM_MEETING_ADMIN", "PERM_MEETING_OWNER" })
	 @RequestMapping(value = "/importPage",method= RequestMethod.GET)
	    public String toImport(){
	        return "meeting/place/import";
	    }
	 
}
