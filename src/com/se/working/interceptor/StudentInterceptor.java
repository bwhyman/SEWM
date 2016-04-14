package com.se.working.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.se.working.entity.Student;
import com.se.working.entity.UserAuthority.UserAuthorityLevel;

public class StudentInterceptor implements HandlerInterceptor {

	public StudentInterceptor() {
		// TODO Auto-generated constructor stub
	}

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
		Object level = request.getSession().getAttribute("level");
		if (level != null ) {
			if ((int)level == UserAuthorityLevel.STUDENT) {
				Student student = (Student) request.getSession().getAttribute("user");
				if (student != null) {
					return true;
				}
			}else if((int)level > UserAuthorityLevel.STUDENT){
				return true;
			}
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		response.sendRedirect(basePath + "login");
		return false;
		
	}

}
