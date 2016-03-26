package com.se.working.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.service.SuperAdminService;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {
	private String basePath = "/superadmin/";
	private String redirect = "redirect:";
	
	@Autowired
	private SuperAdminService superAdminService;
	
	@RequestMapping(path = "/initteachertitle", method = RequestMethod.POST)
	public String initTeacherTitle() {
		superAdminService.initTeacherTitle();
		return redirect + "initsys";
	}
	@RequestMapping(path = "/inituserauthority", method = RequestMethod.POST)
	public String initUserAuthority() {
		superAdminService.initUserAuthority();
		return redirect + "initsys";
	}
	@RequestMapping(path = "/initinvistatustype", method = RequestMethod.POST)
	public String initInviStatusType() {
		superAdminService.initInviStatusType();
		return redirect + "initsys";
	}
	@RequestMapping(path = "/inituser", method = RequestMethod.POST)
	public String initUser() {
		superAdminService.initUser();
		return redirect + "initsys";
	}
	@RequestMapping(path = "/initspecinvitype", method = RequestMethod.POST)
	public String initSpecInvType() {
		superAdminService.initSpecInviType();
		return redirect+ "initsys";
	}
	@RequestMapping(path = "/initfiletasktype", method = RequestMethod.POST)
	public String initFileTaskType() {
		superAdminService.initFileType();
		return redirect+ "initsys";
	}
	@RequestMapping(path = "/initfilestatus", method = RequestMethod.POST)
	public String initFileTaskStatus() {
		superAdminService.initFileTaskStatus();
		return redirect+ "initsys";
	}
	
	@RequestMapping(path = "/initProjectFileType", method = RequestMethod.POST)
	public String initProjectFileType() {
		superAdminService.initProjectFileType();
		return redirect+ "initsys";
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
		
		return basePath + viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		
		return basePath + root + "/" + viewpath;
	}
	
}
