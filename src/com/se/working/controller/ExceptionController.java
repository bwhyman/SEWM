package com.se.working.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.se.working.exception.SEWMException;

/**
 * 用于Controller全局控制
 * @author BO
 *
 */
@ControllerAdvice
public class ExceptionController {
	private static Logger logger = LogManager.getLogger(ExceptionController.class);
	/**
	 * 异常页面重定向，携带参数，可以刷新
	 * 全局SEWMException异常显示，任何可能抛出该异常的页面仅需定义exception即可接收异常信息
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(SEWMException.class)  
	public String  handleSEWMException(Exception e, HttpServletRequest request) {
		
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("exception", e.getMessage());
		e.printStackTrace();
		logger.warn(e);
	  return "redirect:" + request.getHeader("Referer");
	}  
	
	
	@ExceptionHandler(Throwable.class)  
	public void handlerException(Exception e, HttpServletResponse response) throws IOException {
		response.sendError(500);
	} 
	
	/*@ExceptionHandler(Throwable.class)  
	public String  handlerException(Exception e, HttpServletRequest request) {
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("exception", e.getMessage());
		e.printStackTrace();
		logger.warn( request.getHeader("Referer"));
		logger.warn(e);
		return "redirect:" + request.getHeader("Referer");
	} */
}
