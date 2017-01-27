package com.se.working.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.se.working.entity.User;

public class MySessionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(object.getClass().getName());
		if (object instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) object;
			System.out.println(handlerMethod.getMethod().getName());
		}
		
		/*User user = (User) request.getSession().getAttribute("user");
		Method[] methods = object.getClass().getDeclaredMethods();
		
		for (Method method : methods) {
			method.setAccessible(true);
			System.out.println(method.getName());
			for (Parameter p : method.getParameters()) {
				System.out.println(p.getType());
				if (p.isAnnotationPresent(MySessionAttribute.class)) {
					System.out.println("Parameter");
					method.invoke(object, request.getSession().getAttribute("user"));
				}
			}
		}*/
		return true;
	}
}
