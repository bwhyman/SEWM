package com.se.working.controller.user;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.Student;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority.UserAuthorityLevel;
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
	 * 加载login页面
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.invalidate();
		return "login";
	}

	/**
	 * 使用redirect重定向时参数会暴露在地址栏，使用RedirectAttributes接口隐藏参数
	 * 
	 * @param userName
	 * @param password
	 * @param model
	 * @param errorMap
	 *            重定向回login时传递登录错误信息参数
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String employeeNumber, String password, HttpSession session, RedirectAttributes errorMap) {

		User user = userService.findByPassword(employeeNumber, password);
		if (user != null) {
			session.setAttribute("level", user.getUserAuthority().getLevel());
			session.setAttribute(USER, user);
			return redirect + "main";
		}
		
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
		User user = (User) session.getAttribute(USER);
		vMap.put(USER, user);
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
		int level = (int) session.getAttribute("level");
		if (level == UserAuthorityLevel.STUDENT) {
			userService.updateStudentPassword(((Student)session.getAttribute(USER)).getId(), pwd);
		}else {
			userService.updatePassword(((User)session.getAttribute("user")).getId(),pwd);
		}
		
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
		return FileTaskUtils.downloadFile(file);
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
