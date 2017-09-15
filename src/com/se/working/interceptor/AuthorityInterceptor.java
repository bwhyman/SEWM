package com.se.working.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.se.working.controller.ControllerMapping;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.interceptor.MyAuthorize.Authorize;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LogManager.getLogger(getClass());
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute(ControllerMapping.USER);
		// 登录
		if (user == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			response.sendRedirect(basePath + "login");
			return false;
		}
		// 权限
		if (handler instanceof HandlerMethod) {
			long start = System.nanoTime();
			
			long level = user.getUserAuthority().getId();
			Authorize authorize = null;
			
			if (level == UserAuthority.TEACHER) {
				authorize = Authorize.TEACHER;
			}
			if (level == UserAuthority.ADMIN) {
				authorize = Authorize.ADMIN;
			}
			if (level >= UserAuthority.SUPERADMIN) {
				authorize = Authorize.SUPERADMIN;
			}
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			MyAuthorize controllerA = method.getDeclaringClass().getAnnotation(MyAuthorize.class);
			Authorize[] authorizes = null;
			if (controllerA != null) {
				authorizes = controllerA.value();
			}
			// 当方法上声明时，以方法为准
			MyAuthorize methodA = method.getDeclaringClass().getAnnotation(MyAuthorize.class);
			if (methodA != null) {
				authorizes = methodA.value();
			}
			for (Authorize a : authorizes) {
				if (a == authorize) {
					long end = System.nanoTime();
					// logger.info(end - start);
					return true;
				}
			}
		}
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return false;
	}

}
