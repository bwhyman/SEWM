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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.controller.ControllerMapping;
import com.se.working.controller.ControllerMapping.UserRequestMapping;
import com.se.working.controller.ControllerMapping.UserViewMapping;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.interceptor.MyAuthorize;
import com.se.working.interceptor.MyAuthorize.Authorize;
import com.se.working.service.UserService;
import com.se.working.util.PropertyUtils;

/**
 * 用户操作
 * 
 * @author BO
 *
 */
@Controller
@SessionAttributes(value = ControllerMapping.USER)
@MyAuthorize({Authorize.SUPERADMIN, Authorize.TEACHER, Authorize.ADMIN})
public class UserController {
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
	@RequestMapping(UserRequestMapping.LOGIN)
	public String login(HttpServletRequest request, Model model) {
		request.getSession().invalidate();
		User user = getUserCookie(request);
		if (user != null) {
			model.addAttribute("remember", "true");
			model.addAttribute("name", user.getName());
		}
		return UserViewMapping.LOGIN;
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
	@RequestMapping(path = UserRequestMapping.LOGIN, method = RequestMethod.POST)
	public String login(String employeeNumber, String password, String checked, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes errorMap,Model model) {
		User user = login(employeeNumber, password, request.getSession());
		if (user != null) {
			model.addAttribute(ControllerMapping.USER, user);
			if (checked != null) {
				createCookie(response, employeeNumber, password, user.getName());
			}
			return ControllerMapping.REDIRECT + UserRequestMapping.MAIN;
		}
		// 清空cookie
		removeCookie(request, response);
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return ControllerMapping.REDIRECT + UserRequestMapping.LOGIN;
	}

	/**
	 * 基于Cookie登录
	 * 
	 * @param request
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(path = UserRequestMapping.ILOGIN, method = RequestMethod.POST)
	public String iLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes errorMap, Model model) {
		User userCookie = getUserCookie(request);
		if (userCookie != null) {
			User user = login(userCookie.getEmployeeNumber(), userCookie.getPassword(), request.getSession());
			if ( user != null) {
				model.addAttribute(ControllerMapping.USER, user);
				return ControllerMapping.REDIRECT + UserRequestMapping.MAIN;
			}
		}
		// 登录错误，清空Cookie
		removeCookie(request, response);
		errorMap.addFlashAttribute("exception", "员工号或密码错误！");
		return ControllerMapping.REDIRECT + UserRequestMapping.LOGIN;
	}

	/**
	 * 加载用于基本信息
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(path = UserRequestMapping.UPDATE_USERSETTING)
	public String updateUserSetting(@ModelAttribute(ControllerMapping.USER) User user, Model model) {
		model.addAttribute("user", userService.getUser(user.getId()));
		model.addAttribute("titles", userService.getTeacherTitles());
		return UserViewMapping.UPDATE_USERSETTING;
	}

	/**
	 * 更新密码
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = UserRequestMapping.UPDATE_PASSWORD, method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute(ControllerMapping.USER) User user, String pwd) {
		userService.updatePassword(user.getId(), pwd);
		return ControllerMapping.REDIRECT + UserRequestMapping.UPDATE_USERSETTING;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = UserRequestMapping.UPDATE_USERSETTING, method = RequestMethod.POST)
	public String updateUserSetting(@ModelAttribute(ControllerMapping.USER) User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.updateUser(user);
		return ControllerMapping.REDIRECT + UserRequestMapping.UPDATE_USERSETTING;
	}

	@RequestMapping(path = UserRequestMapping.LOGOUT)
	public String logout(HttpSession session, SessionStatus status) {
		session.removeAttribute(ControllerMapping.USER);
		session.invalidate();
		status.setComplete();
		return ControllerMapping.REDIRECT + UserRequestMapping.LOGIN;
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
		User user = userService.getUser(employeeNumber, password);
		if (user != null) {
			// 延迟加载
			user.getUserAuthority().getLevel();
			user.getGroups().getId();
			session.setAttribute(ControllerMapping.USER, user);
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
	
	/**
	 * 静态页面
	 * =============================
	 */
	@RequestMapping(value = {UserRequestMapping.MAIN, "/"})
	public String main() {
		return UserViewMapping.MAIN;
	}
	
}
