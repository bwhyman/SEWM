package com.se.working.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.User;
import com.se.working.service.UserService;
/**
 * 用户操作
 * @author BO
 *
 */
@Controller
public class UserController {
	private String redirect = "redirect:";
	@Autowired
	private UserService userService;
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param errorMap 重定向回login时传递登录错误信息参数
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String userName, String password, HttpServletRequest request,RedirectAttributes errorMap) {
		
		User user2 = userService.findByPassword(userName, password);
		if (user2 != null) {
			request.getSession().setAttribute("userId", user2.getId());
			request.getSession().setAttribute("level", user2.getUserAuthority().getLevel());
			return redirect + "main";
		}
		errorMap.addFlashAttribute("loginerror", "nouser");
		return redirect + "login";
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
		return viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		System.out.println(root + "/" + viewpath);
		return root + "/" + viewpath;
	}
}
