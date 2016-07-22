package com.se.working.controller.user;

import java.io.File;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.service.UserService;
import com.se.working.util.FileTaskUtils;

/**
 * 用户操作
 * 
 * @author BO
 *
 */
@Controller
public class UserController {
	private String USER = "user";
	private String redirect = "redirect:";
	private String basePath = "/user/";
	@Autowired
	private UserService userService;

	/**
	 * 加载login页面，清空session，加载cookie
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpSession session, HttpServletRequest request, Map<String, Object> vMap) {
		session.invalidate();
		String remember = null;
		String name = null;
		Cookie cookie = getCookie(request);
		if (cookie != null) {
			remember = "true";
			String result = new String(Base64.getDecoder().decode(cookie.getValue()));
			String result2 = new String(Base64.getDecoder().decode(result));
			String regex = "(.*)/(.*)/(.*)/(.*)/";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(result2);
			while (matcher.find()) {
				name = matcher.group(4);
			}
			
		}
		vMap.put("remember", remember);
		vMap.put("name", name);
		return "login";
	}

	/**
	 * 使用redirect重定向时参数会暴露在地址栏，使用RedirectAttributes接口隐藏参数
	 * 重定向回login时传递登录错误信息参数
	 * @param employeeNumber
	 * @param password
	 * @param checked
	 * @param session
	 * @param response
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String employeeNumber, String password, String checked, HttpSession session, HttpServletResponse response, RedirectAttributes errorMap) {
		User user = userService.findByPassword(employeeNumber, password);
		if (user != null) {
			user.getUserAuthority().getLevel();
			session.setAttribute(USER, user);
			if (checked != null) {
				createCookie(response, employeeNumber, password, user.getName());
			}
			return redirect + "main";
		}
		
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return redirect + "login";
	}
	
	/**
	 * 基于Cookie登录
	 * @param session
	 * @param request
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(value = "/ilogin", method = RequestMethod.POST)
	public String iLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes errorMap) {

		Cookie cookie = getCookie(request);
		if (cookie != null) {
			String result = new String(Base64.getDecoder().decode(cookie.getValue()));
			String result2 = new String(Base64.getDecoder().decode(result));
			String regex = "(.*)/(.*)/(.*)/(.*)/";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(result2);
			String employeeNumber = null;
			String password = null;
			while (matcher.find()) {
				employeeNumber = matcher.group(1);
				password = matcher.group(3);
			}
			if (employeeNumber != null && password != null) {
				User user = userService.findByPassword(employeeNumber, password);
				if (user != null) {
					user.getUserAuthority().getLevel();
					session.setAttribute(USER, user);
					return redirect + "main";
				}
			}
		}
		// 登录错误，清空Cookie
		cleanCookie(request, response);
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return redirect + "login";
	}

	/**
	 * 加载用于基本信息
	 * 
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateusersetting")
	public String updateUserSetting(Map<String, Object> vMap, HttpSession session) {
		vMap.put(USER, userService.findById(((User) session.getAttribute(USER)).getId()));
		vMap.put("titles", userService.findTeacherTitles());
		return basePath + "updateusersetting";
	}

	/**
	 * 更新密码
	 * 
	 * @param pwd
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/updatepassword", method = RequestMethod.POST)
	public String updatePassword(String pwd, HttpSession session) {
		userService.updatePassword(((User)session.getAttribute(USER)).getId(), pwd);
		return redirect + "updateusersetting";
	}

	/**
	 * 更新用户信息
	 * 
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

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(USER);
		session.invalidate();

		return redirect + "login";
	}

	/**
	 * 下载文件
	 * 
	 * @param taskid
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = "/download/{directory}/{filename}/", method = RequestMethod.GET)
	public ResponseEntity<byte[]> dowload(@PathVariable String directory, @PathVariable String filename) {
		
		// 获取文件
		File file = FileTaskUtils.getOrCreateFileTaskFile(directory, filename);

		if (!file.exists()) {
			throw new SEWMException("文件不存在");
		}
		// 文件转换
		return FileTaskUtils.toResponseEntity(file);
	}
	
	/**
	 * 创建Cookie，时效1年，算法： 
	 * @param response
	 * @param employeeNumber
	 * @param password
	 */
	private void createCookie(HttpServletResponse response, String employeeNumber, String password, String name) {
		String base42 =  employeeNumber + "/" + "R28H22ZVTAL" + "/" + password + "/" + name + "/";
		String result = Base64.getEncoder().encodeToString(base42.getBytes());
		Cookie cookie = new Cookie("sewm", Base64.getEncoder().encodeToString(result.getBytes()));
		int expiry = 60 * 60 * 24 * 365;
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
	/**
	 * 获取Cookie
	 * @param request
	 * @return
	 */
	private Cookie getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("sewm")) {
					cookie = c;
				}
			}
		}
		return cookie;
	}
	
	/**
	 * 清空Cookie
	 * @param request
	 * @param response
	 */
	private void cleanCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(request);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/*
	 * ==================================================
	 */
	/**
	 * 直接加载页面时的通配方法 不会覆盖显式声明的请求 仅对一级目录有效
	 * 
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
