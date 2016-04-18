package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.se.working.util.EnumConstant;

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
	@RequestMapping(path = "listmyinviinfo/{invitype}/{page}", method = RequestMethod.GET)
	public String listMyInviInfos(@PathVariable String invitype, @PathVariable int page, Map<String, Object> vMap, HttpSession session){
		List<InvigilationInfo> infos = new ArrayList<>();
		User user = (User) session.getAttribute(USER);
		long count = 0;
		switch (invitype) {
		case "undone":
			infos = inviService.findInvisByUserIdAndTypeId(user.getId(), InviStatusType.ASSIGNED, page);
			count = inviService.getCountByUserIdAndTypeId(user.getId(), InviStatusType.ASSIGNED);
			break;
		case "done":
			infos = inviService.findInvisByUserIdAndTypeId(user.getId(), InviStatusType.DONE, page);
			count = inviService.getCountByUserIdAndTypeId(user.getId(), InviStatusType.DONE);
			break;
		case "all":
			infos = inviService.findInviInfosByUserId(user.getId(), page);
			count = inviService.getCountInviInfosByUserId(user.getId());
			break;
		}

		// 反序
		Collections.reverse(infos);
		vMap.put("infos", infos);
		vMap.put("type", invitype);
		vMap.put("count", count);
		vMap.put("currentPage", page);
		vMap.put("countPage", count%EnumConstant.values()[0].getPageCount()==0
				?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
		return basePath + "listmyinviinfo";
	}
	
	/**
	 * 基于选择列出所有监考信息
	 * 
	 * @param vMap
	 */
	@RequestMapping(path = "listinviinfo/{invitype}/{page}", method = RequestMethod.GET)
	public String listInviInfos(@PathVariable String invitype, @PathVariable int page, Map<String, Object> vMap) {
		List<InvigilationInfo> infos = new ArrayList<>();
		long count = 0;
		switch (invitype) {
		case "unassinvi":
			infos = inviService.findInviInfosByTypeId(InviStatusType.UNASSIGNED, page);
			count = inviService.getCountInviInfosByTypeId(InviStatusType.UNASSIGNED);
			break;
		case "assinvi":
			infos = inviService.findInviInfosByTypeId(InviStatusType.ASSIGNED, page);
			count = inviService.getCountInviInfosByTypeId(InviStatusType.ASSIGNED);
			break;
		case "done":
			infos = inviService.findInviInfosByTypeId(InviStatusType.DONE, page);
			count = inviService.getCountInviInfosByTypeId(InviStatusType.DONE);
			break;
		case "all":
			infos = inviService.findAllInviInfosByPage(page);
			count = inviService.findAllInviInfosCount();
			break;
			default:
				return basePath + "error";
		}
		// 反序
		Collections.reverse(infos);
		vMap.put("infos", infos);
		vMap.put("type", invitype);
		vMap.put("count", count);
		vMap.put("currentPage", page);
		vMap.put("countPage", count%(EnumConstant.values()[0].getPageCount())==0
				?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
		return basePath + "listinviinfo";
	}
	
	public UserInviController() {
		// TODO Auto-generated constructor stub
	}

}
