package com.se.working.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.entity.User;
import com.se.working.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String userName, String password, HttpServletRequest request) {
		
		User user2 = userService.findByPassword(userName, password);
		if (user2 != null) {
			request.getSession().setAttribute("userId", user2.getId());
			request.getSession().setAttribute("level", user2.getUserAuthority().getLevel());
			return "redirect:main";
		}
		return "login";
	}
	@RequestMapping("/main")
	public void Index() {
	}
	
	@RequestMapping("/b/{id}")
	public void build(@PathVariable int id) {
		System.out.println(id);
	}
}
