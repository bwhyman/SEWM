package com.se.working.controller.user;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.controller.ControllerMap;
import com.se.working.controller.ControllerMap.UserRequestMap;
import com.se.working.controller.ControllerMap.UserResponseMap;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.service.UserService;
import com.se.working.util.PropertyUtils;

/**
 * 用户操作
 * 
 * @author BO
 *
 */
@Controller
@SessionAttributes(value = ControllerMap.USER)
public class UserController {

	private String basePath = "/user/";

	@Autowired
	private UserService userService;

	@RequestMapping("/ok")
	public String ok() {
		System.out.println("ok");
		return "ok";
	}
	
	/**
	 * 加载login页面，清空session，加载cookie
	 * 
	 * @return
	 */
	@RequestMapping(UserRequestMap.LOGIN)
	public String login(HttpServletRequest request, Model model) {
		request.getSession().invalidate();
		User user = getUserCookie(request);
		if (getUserCookie(request) != null) {
			model.addAttribute("remember", "true");
			model.addAttribute("name", user.getName());
		}
		return UserResponseMap.LOGIN;
	}

	/**
	 * 使用redirect重定向时参数会暴露在地址栏，使用RedirectAttributes接口隐藏参数 重定向回login时传递登录错误信息参数
	 * 
	 * @param employeeNumber
	 * @param password
	 * @param checked
	 * @param request
	 * @param response
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.LOGIN, method = RequestMethod.POST)
	public String login(String employeeNumber, String password, String checked, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes errorMap,Model model) {
		User user = login(employeeNumber, password, request.getSession());
		if (user != null) {
			model.addAttribute(ControllerMap.USER, user);
			if (checked != null) {
				createCookie(response, employeeNumber, password, user.getName());
			}
			return ControllerMap.REDIRECT + UserRequestMap.MAIN;
		}
		// 清空cookie
		removeCookie(request, response);
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return ControllerMap.REDIRECT + UserRequestMap.LOGIN;
	}

	/**
	 * 基于Cookie登录
	 * 
	 * @param request
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.ILOGIN, method = RequestMethod.POST)
	public String iLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes errorMap, Model model) {
		User userCookie = getUserCookie(request);
		if (userCookie != null) {
			User user = login(userCookie.getEmployeeNumber(), userCookie.getPassword(), request.getSession());
			if ( user != null) {
				model.addAttribute(ControllerMap.USER, user);
				return ControllerMap.REDIRECT + UserRequestMap.MAIN;
			}
		}
		// 登录错误，清空Cookie
		removeCookie(request, response);
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return ControllerMap.REDIRECT + UserRequestMap.LOGIN;
	}

	/**
	 * 加载用于基本信息
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.UPDATE_USERSETTING)
	public String updateUserSetting(@ModelAttribute(ControllerMap.USER) User user, Model model) {
		model.addAttribute("user", userService.findById(user.getId()));
		model.addAttribute("titles", userService.findTeacherTitles());
		return UserResponseMap.UPDATE_USERSETTING;
	}

	/**
	 * 更新密码
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.UPDATE_PASSWORD, method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute(ControllerMap.USER) User user, String pwd) {
		userService.updatePassword(user.getId(), pwd);
		return ControllerMap.REDIRECT + UserRequestMap.UPDATE_USERSETTING;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.UPDATE_USERSETTING, method = RequestMethod.POST)
	public String updateUserSetting(@ModelAttribute(ControllerMap.USER) User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.update(user);
		return ControllerMap.REDIRECT + UserRequestMap.UPDATE_USERSETTING;
	}

	@RequestMapping(path = UserRequestMap.LOGOUT)
	public String logout(HttpSession session, SessionStatus status) {
		session.removeAttribute(ControllerMap.USER);
		session.invalidate();
		status.setComplete();
		return ControllerMap.REDIRECT + UserRequestMap.LOGIN;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 * @param filename
	 * @return
	 */
	/*
	 * @RequestMapping(path = "/download/{directory}/{filename}/", method =
	 * RequestMethod.GET) public ResponseEntity<byte[]> dowload(@PathVariable
	 * String directory, @PathVariable String filename) {
	 * 
	 * // 获取文件 File file = FileTaskUtils.getOrCreateFileTaskFile(directory,
	 * filename);
	 * 
	 * if (!file.exists()) { throw new SEWMException("文件不存在"); } // 文件转换 return
	 * FileTaskUtils.toResponseEntity(file); }
	 */

	/**
	 * 
	 * @param employeeNumber
	 * @param password
	 * @param session
	 * @return
	 */
	private User login(String employeeNumber, String password, HttpSession session) {
		User user = userService.findByPassword(employeeNumber, password);
		if (user != null) {
			// 延迟加载
			user.getUserAuthority().getLevel();
			user.getGroups().getId();
			session.setAttribute(ControllerMap.USER, user);
		}
		return user;
	}

	/**
	 * 创建Cookie，时效1年，算法：
	 * 
	 * @param response
	 * @param employeeNumber
	 * @param password
	 */
	private void createCookie(HttpServletResponse response, String employeeNumber, String password, String name) {
		String base42 = employeeNumber + "/" + "R28H22ZVTAL" + "/" + password + "/" + name + "/";
		String result = Base64.getEncoder().encodeToString(base42.getBytes());
		Cookie cookie = new Cookie(PropertyUtils.getCookieName(),
				Base64.getEncoder().encodeToString(result.getBytes()));
		int expiry = 60 * 60 * 24 * 365;
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	/**
	 * 获取Cookie
	 * 
	 * @param request
	 * @return
	 */
	private Cookie getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(PropertyUtils.getCookieName())) {
					cookie = c;
				}
			}
		}
		return cookie;
	}

	/**
	 * 清空Cookie
	 * 
	 * @param request
	 * @param response
	 */
	private void removeCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(request);
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private User getUserCookie(HttpServletRequest request) {
		User user = null;
		Cookie cookie = getCookie(request);
		Matcher matcher = null;
		if (cookie != null) {
			user = new User();
			String result = new String(Base64.getDecoder().decode(cookie.getValue()));
			String result2 = new String(Base64.getDecoder().decode(result));
			String regex = "(.*)/(.*)/(.*)/(.*)/";
			Pattern pattern = Pattern.compile(regex);
			matcher = pattern.matcher(result2);
			while (matcher.find()) {
				user.setEmployeeNumber(matcher.group(1));
				user.setPassword(matcher.group(3));
				user.setName(matcher.group(4));
			}
		}
		return user;
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
