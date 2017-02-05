package com.se.working.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


// @Controller
public class BaseController {
	
	/*@RequestMapping(path = "/{action}", method = RequestMethod.GET)
	public String getView(@PathVariable String action) {
		
		return "/user/" + action;
	}
	
	@RequestMapping(path = "/{auth}/{action}", method = RequestMethod.GET)
	public String getView(@PathVariable String auth, @PathVariable String action) {
		
		return "/" + auth + "/" + action;
	}
	*//**
	 * 不包含resources的通用请求
	 * @param auth
	 * @param function
	 * @param action
	 * @return
	 *//*
	// 当有mvc负责资源时，抽象方法需过滤资源
	// @RequestMapping(path = "/{auth:[^resources]+}/{function}/{action}", method = RequestMethod.GET)
	@RequestMapping(path = "/{auth}/{function}/{action}", method = RequestMethod.GET)
	public String getView(@PathVariable String auth, @PathVariable String function, @PathVariable String action) {
		
		return  "/" +auth + "/"  + function + "/" + action;
	}*/
}
