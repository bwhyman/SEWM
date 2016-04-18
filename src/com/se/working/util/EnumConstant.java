package com.se.working.util;

public enum EnumConstant {

	PAGECOUNT(15);
	private int pageCount;

	private EnumConstant(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}
	
}
