package com.se.working.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.service.SuperAdminService;

@Controller
@RequestMapping("superadmin")
public class SuperAdminController {
	private String superAdminBasePath = "/superadmin/";
	private String redirect = "redirect:";
	
	@Autowired
	private SuperAdminService superAdminService;
	
	@RequestMapping("/initteachertitle")
	public String initTeacherTitle() {
		superAdminService.initTeacherTitle();
		return redirect + superAdminBasePath + "/initsys";
	}
	@RequestMapping("/inituserauthority")
	public String initUserAuthority() {
		superAdminService.initUserAuthority();
		return redirect + superAdminBasePath + "/initsys";
	}
	@RequestMapping("/initinvistatustype")
	public String initInviStatusType() {
		superAdminService.initInviStatusType();
		return redirect + superAdminBasePath + "/initsys";
	}
	@RequestMapping("/inituser")
	public String initUser() {
		superAdminService.initUser();
		return redirect + superAdminBasePath + "/initsys";
	}
	
	
	/**
	 * 直接加载页面时的通配方法
	 * 不会覆盖显式声明的请求
	 * 仅对一级目录有效
	 * @param viewpath
	 * @return 视图路径
	 */
	@RequestMapping(path = "/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String viewpath) {
		
		return superAdminBasePath + viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		
		return superAdminBasePath + root + "/" + viewpath;
	}
	
}
