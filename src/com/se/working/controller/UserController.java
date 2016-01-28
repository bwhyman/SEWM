package com.se.working.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.User;
import com.se.working.service.UserService;
/**
 * 用户操作
 * @author BO
 *
 */
@Controller
@SessionAttributes(value = "user")
public class UserController {
	private String redirect = "redirect:";
	@Autowired
	private UserService userService;
	/**
	 * 使用redirect重定向时参数会暴露在地址栏，使用RedirectAttributes接口隐藏参数
	 * @param userName
	 * @param password
	 * @param model
	 * @param errorMap 重定向回login时传递登录错误信息参数
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String userName, String password, Model model, RedirectAttributes errorMap) {
		
		User user = userService.findByPassword(userName, password);
		if (user != null) {
			user.getUserAuthority().getLevel();
			model.addAttribute("user", user);
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
