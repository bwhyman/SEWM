package com.se.working.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.se.working.exception.SEWMException;

@ControllerAdvice
public class BaseController {

	@ExceptionHandler(SEWMException.class)  
	public String  handlerException(Exception e, HttpServletRequest request) {
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("exception", e.getMessage());
		e.printStackTrace();
	  return "redirect:" + request.getServletPath();
	}  
	public BaseController() {
		// TODO Auto-generated constructor stub
	}

}
