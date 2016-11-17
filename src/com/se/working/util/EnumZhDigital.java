package com.se.working.util;

@Deprecated
public enum EnumZhDigital {
	YI("一","1"), ER("二"," 2"), SAN("三", "3"), SI("四", "4"), WU("五", "5");
	
	private String zh;
	private String digital;
	private EnumZhDigital(String zh, String digital) {
		this.zh = zh;
		this.digital = digital;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public String getDigital() {
		return digital;
	}
	public void setDigital(String digital) {
		this.digital = digital;
	}
	

}
