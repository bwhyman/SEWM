package com.se.working.tld;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.se.working.controller.ControllerMap;
import com.se.working.entity.User;

public class MyAuthorize extends SimpleTagSupport{

	private Set<Object> access;
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		if (access != null && access.size() > 0) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			User user= (User) attr.getRequest().getSession().getAttribute(ControllerMap.USER);
			boolean show = false;
			for (Object o : access) {
				if (user.getUserAuthority().getId() == (long) o) {
					show = true;
					break;
				}
			}
			if (show) {
				getJspBody().invoke(null);
			}
		}
	}
	public Set<Object> getAccess() {
		return access;
	}
	public void setAccess(Set<Object> access) {
		this.access = access;
	}
	
	
}
