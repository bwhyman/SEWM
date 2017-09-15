package com.se.working.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.controller.ControllerMapping.SuperAdminRequestMapping;
import com.se.working.interceptor.MyAuthorize;
import com.se.working.interceptor.MyAuthorize.Authorize;
import com.se.working.util.PropertyUtils;

@Controller
@MyAuthorize(Authorize.SUPERADMIN)
public class SuperAdminController {
	
	/**
	 * 加载学期基点日期
	 * @param model
	 */
	@RequestMapping(path = SuperAdminRequestMapping.UPDATE_BASEDATE)
	public void updateBaseDate(Model model) {
		model.addAttribute("basedate", PropertyUtils.getBaseDate());
	}
	
	/**
	 * 更新学期基点日期
	 * @param date
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = SuperAdminRequestMapping.UPDATE_BASEDATE, method = RequestMethod.POST)
	public String updateBaseDate(String date, RedirectAttributes vMap) {
		
		PropertyUtils.setBaseDate(date);
		return ControllerMapping.REDIRECT + SuperAdminRequestMapping.UPDATE_BASEDATE;
	}

	/**
	 * 
	 * ================
	 */
	
	@RequestMapping(path = SuperAdminRequestMapping.SYS_MANAGEMENT)
	public void sysManagement() {
		
	}
}
