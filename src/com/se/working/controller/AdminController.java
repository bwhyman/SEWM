package com.se.working.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.se.working.entity.User;

/**
 * 管理员操作
 * @author BO
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	/**
	 *  管理员根目录
	 *  所有管理员权限操作以此为根
	 *  单态，无需设为常量
	 */
	private String adminBasePath = "/admin/";
	private String redirect = "redirect:";
	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
	public String addUser(User user, int titleId) {
		System.out.println(titleId);
		System.out.println(user.getUserName());
		return redirect + adminBasePath + "adduser";
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
		System.out.println("viewpath: " + viewpath);
		return adminBasePath + viewpath;
	}
}
