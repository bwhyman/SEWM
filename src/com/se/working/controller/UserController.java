package com.se.working.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.TeacherTitle;
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
	private String userBasePath = "/user/";
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
	public String login(String userName, String password, HttpSession session, RedirectAttributes errorMap) {
		
		User user = userService.findByPassword(userName, password);
		if (user != null) {
			user.getUserAuthority().getLevel();
			session.setAttribute("user", user);
			return redirect + "main";
		}
		errorMap.addFlashAttribute("loginerror", "nouser");
		return redirect + "login";
	}
	/**
	 * 加载用于基本信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateusersetting")
	public String updateUserSetting(Map<String, Object> vMap, HttpSession session) {
		User user = userService.findById(5L);
		// User user = userService.findById(((User)session.getAttribute("user")).getId());
		vMap.put("user", user);
		vMap.put("titles", userService.findTeacherTitles());
		return userBasePath + "updateusersetting";
	}
	
	@RequestMapping(path = "/updatepassword", method = RequestMethod.POST)
	public String updatePassword(String pwd, HttpSession session) {
		System.out.println(pwd);
		// userService.updatePassword(((User)session.getAttribute("user")).getId(), pwd);
		return redirect + "updateusersetting";
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = "/updateusersetting", method = RequestMethod.POST)
	public String updateUserSetting(User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.update(user);
		return redirect + "updateusersetting";
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
		
		return userBasePath + viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		
		return userBasePath + root + "/" + viewpath;
	}
}
