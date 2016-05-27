package com.se.working.controller.student;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.Student;
import com.se.working.service.StudentService;

@Controller
@RequestMapping("/student/")
public class StudentController {

	private String USER = "user";
	private String basePath = "/student/";
	private String redirect = "redirect:";
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * 加载用于基本信息
	 * 
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateusersetting")
	public String updateUserSetting(Map<String, Object> vMap, HttpSession session) {
		Student student = (Student) session.getAttribute(USER);
		vMap.put(USER, student);
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
		studentService.updateStudentPassword(((Student)session.getAttribute(USER)).getId(), pwd);
		return redirect + "updateusersetting";
	}
	
	
	@RequestMapping(path = "/addPhoneNumber", method = RequestMethod.POST)
	public String addPhoneNumber(String phoneNumber, HttpSession session){
		Student student = (Student) session.getAttribute(USER);
		student.setPhoneNumber(phoneNumber);
		studentService.update(student);
		return redirect + "/student/main";
	}
	
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
	@RequestMapping(path = "/studentlogin", method = RequestMethod.POST)
	public String login(String studentId, String password, HttpSession session, RedirectAttributes errorMap){
		Student student = studentService.findStudentByPassword(studentId, password);
		if (student != null) {
			session.setAttribute("level", student.getUserAuthority().getLevel());
			session.setAttribute(USER, student);
			return redirect + "/student/main";
		}
		errorMap.addFlashAttribute("studentexception", "学号或密码错误！");
		return redirect + "/login";
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