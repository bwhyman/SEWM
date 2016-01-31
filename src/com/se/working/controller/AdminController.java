package com.se.working.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.se.working.entity.User;
import com.se.working.service.UserService;

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
	@Autowired
	private UserService userService;
	
	/**
	 * 用户管理初始化
	 * @param users
	 * @return
	 */
	@RequestMapping("/usermanager")
	public String listUser(Map<String, Object> users) {
		users.put("users", userService.findAll());
		return adminBasePath + "usermanager";
	}
	
	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
	public String addUser(User user, long titleId) {
		
		return redirect + adminBasePath + "adduser";
	}
	
	@RequestMapping(path = "/selectuser")
	@ResponseBody
	public  User getUserById(long userId) {
		User user = userService.findById(userId);	
		/*
		 * 重新封装需要数据
		 * 直接使用gson转化，gson会通过getter方法加载全部属性数据
		 * 在双向关系中会形成死循环
		 */
		User user2 = new User();
		user2.setId(user.getId());
		user2.setName(user.getName());
		user2.setPhoneNumber(user.getPhoneNumber());
		user2.setEmployeeNumber(user.getEmployeeNumber());
		return user2;
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
