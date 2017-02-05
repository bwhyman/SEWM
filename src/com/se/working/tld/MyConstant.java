package com.se.working.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyConstant extends SimpleTagSupport {
	private String clazz;
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		getJspContext().getELContext().getImportHandler().importClass(clazz);
	}
	
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
