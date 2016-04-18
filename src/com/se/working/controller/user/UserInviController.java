package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.entity.User;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationStatusType.InviStatusType;
import com.se.working.invigilation.service.InviService;

@Controller
@RequestMapping("/invi")
public class UserInviController {
	private String USER = "user";
	private String redirect = "redirect:";
	private String basePath = "/user/invi/";
	@Autowired
	private InviService inviService;
	
	/**
	 * 基于选择列出登录教师当前监考信息
	 * @param invitype
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "listmyinviinfo/{invitype}", method = RequestMethod.GET)
	public String listMyInviInfos(@PathVariable String invitype, Map<String, Object> vMap, HttpSession session){
		List<InvigilationInfo> infos = new ArrayList<>();
		User user = (User) session.getAttribute(USER);
		switch (invitype) {
		case "undone":
			infos = inviService.findInvisByUserIdAndTypeId(user.getId(), InviStatusType.ASSIGNED);
			break;
		case "done":
			infos = inviService.findInvisByUserIdAndTypeId(user.getId(), InviStatusType.DONE);
			break;
		case "all":
			infos = inviService.findInviInfosByUserId(user.getId());
			break;
		}

		// 反序
		Collections.reverse(infos);
		vMap.put("infos", infos);
		vMap.put("type", invitype);
		return basePath + "listmyinviinfo";
	}
	
	/**
	 * 基于选择列出所有监考信息
	 * 
	 * @param vMap
	 */
	@RequestMapping(path = { "listinviinfo/{invitype}/{max}", "listinviinfo/{invitype}" }, method = RequestMethod.GET)
	public String listInviInfos(@PathVariable String invitype, @PathVariable Optional<Integer> max,
			Map<String, Object> vMap) {
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
			inviTypeId = InviStatusType.UNASSIGNED;
			break;
		case "assinvi":
			inviTypeId = InviStatusType.ASSIGNED;
			break;
		case "done":
			inviTypeId = InviStatusType.DONE;
			break;
		case "all":
			inviTypeId = 0;
			break;
		default:
			return basePath + "error";
		}
		if (inviTypeId == 0) {
			infos =  inviService.findAllInviInfos(firstResult, maxResults);
			typeSize = inviService.findAllInviInfos().size();
			// 指定监考状态的总页数
			countpages =Math.ceil( (double) typeSize /  (double) maxResults);
		} else {
			
			infos =  inviService.findInviInfosByTypeId(inviTypeId, firstResult, maxResults);
			typeSize = inviService.findInviInfosByTypeId(inviTypeId).size();
			// 指定监考状态的总页数
			countpages =Math.ceil( (double)typeSize /  (double) maxResults);
		}
		vMap.put("firstresult", firstResult);
		
		vMap.put("typesize", typeSize);
		vMap.put("countpages", countpages);
		vMap.put("currentpage", currentpage);
		vMap.put("infos", infos);
		vMap.put("type", invitype);
		return basePath + "listinviinfo";
	}
	
	public UserInviController() {
		// TODO Auto-generated constructor stub
	}

}
