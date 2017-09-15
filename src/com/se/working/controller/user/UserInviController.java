package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.se.working.controller.ControllerMapping;
import com.se.working.controller.ControllerMapping.UserInviViewMapping;
import com.se.working.controller.ControllerMapping.UserInviRequestMapping;
import com.se.working.entity.User;
import com.se.working.interceptor.MyAuthorize;
import com.se.working.interceptor.MyAuthorize.Authorize;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.service.InviService;
import com.se.working.util.DateUtils;

@Controller
@SessionAttributes(value = ControllerMapping.USER)
@MyAuthorize({Authorize.SUPERADMIN, Authorize.TEACHER, Authorize.ADMIN})
public class UserInviController {
	@Autowired
	private InviService inviService;

	/**
	 * 基于选择列出登录教师当前监考信息
	 * 
	 * @param invitype
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(path = UserInviRequestMapping.LIST_MY_INVIINFO, method = RequestMethod.GET)
	public String listMyInviInfos(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable String invitype, Model model) {
		List<InvigilationInfo> infos = new ArrayList<>();
		switch (invitype) {
		case "undone":
			infos = inviService.getInvis(user.getId(), InvigilationStatusType.ASSIGNED);
			break;
		case "done":
			infos = inviService.getInvis(user.getId(), InvigilationStatusType.DONE);
			break;
		case "all":
			infos = inviService.getInviInfos(user.getId());
			break;
		}
		model.addAttribute("infos", infos);
		model.addAttribute("type", invitype);
		return UserInviViewMapping.LIST_MY_INVIINFO;
	}

	/**
	 * 基于选择列出所有监考信息
	 * 
	 * @param model
	 */
	@RequestMapping(path = { UserInviRequestMapping.LIST_INVIINFO_INVITYPE,
			UserInviRequestMapping.LIST_INVIINFO_INVITYPE_MAX }, method = RequestMethod.GET)
	public String listInviInfos(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable String invitype,
			@PathVariable Optional<Integer> max, Model model) {
		List<InvigilationInfo> infos = new ArrayList<>();
		// 总页数
		double countpages = 1;
		// 当前页
		int currentpage = 1;
		// 如果有当前页参数参数传入
		if (max.isPresent()) {
			currentpage = max.get();
		}
		// 最大显示数据条目
		int maxResults = 20;
		// 起始条目，0为第一条记录
		int firstResult = (currentpage - 1) * maxResults;
		// 当前监考状态总条目
		int typeSize = 0;
		// 当前监考状态类型
		long inviTypeId = 0;

		switch (invitype) {
		case "unassinvi":
			inviTypeId = InvigilationStatusType.UNASSIGNED;
			break;
		case "assinvi":
			inviTypeId = InvigilationStatusType.ASSIGNED;
			break;
		case "done":
			inviTypeId = InvigilationStatusType.DONE;
			break;
		case "all":
			inviTypeId = 0;
			break;
		default:
			return ControllerMapping.ERROR;
		}
		if (inviTypeId == 0) {
			infos = inviService.getInviInfosByGroupId(user.getGroups().getId(), firstResult, maxResults);
			typeSize = inviService.getInviInfosByGroupId(user.getGroups().getId()).size();
			// 指定监考状态的总页数
			countpages = Math.ceil((double) typeSize / (double) maxResults);
		} else {

			infos = inviService.getInviInfos(user.getGroups().getId(), inviTypeId, firstResult, maxResults);
			typeSize = inviService.getInviInfosByTypeId(inviTypeId).size();
			// 指定监考状态的总页数
			countpages = Math.ceil((double) typeSize / (double) maxResults);
		}

		List<Integer> weeks = new ArrayList<>(infos.size());
		for (InvigilationInfo i : infos) {
			weeks.add(DateUtils.getWeekRelativeBaseDate(i.getStartTime()));
		}
		model.addAttribute("weeks", weeks);
		model.addAttribute("firstresult", firstResult);
		model.addAttribute("typesize", typeSize);
		model.addAttribute("countpages", countpages);
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("infos", infos);
		model.addAttribute("type", invitype);
		return UserInviViewMapping.LIST_INVIINFO;
	}

	/**
	 * 监考分配详细信息
	 * @param user
	 * @param inviinfoid
	 * @param model
	 * @return
	 */
	@RequestMapping(path = UserInviRequestMapping.INVIINFO_DETAIL_ID, method = RequestMethod.GET)
	public String listInviInfoDetail(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable long inviinfoid, Model model) {
		InvigilationInfo info = inviService.getInviInfo(inviinfoid, user.getGroups().getId());
		if (info == null) {
			return ControllerMapping.ERROR;
		}
		int week = DateUtils.getWeekRelativeBaseDate(info.getStartTime());
		model.addAttribute("info", info);
		model.addAttribute("week", week);
		return UserInviViewMapping.LIST_INVIINFO_DETAIL;
	}

	/**
	 * 下载监考分配详细表格
	 * 
	 * @return
	 */
	@RequestMapping(path = UserInviRequestMapping.DOWNLOAD_INVIINFO_EXCEL)
	public ResponseEntity<byte[]> downloadInviInfoExcel(@ModelAttribute(ControllerMapping.USER) User user) {
		return inviService.downloadInviInfoExcel(user.getGroups().getId());
	}
}
