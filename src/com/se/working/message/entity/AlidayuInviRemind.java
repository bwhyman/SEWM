package com.se.working.message.entity;


/**
 * [监考提醒:时间:${t};地点:${l};人员:${ns};备注:${c};
 * @author BO
 *
 */
public class AlidayuInviRemind {

	private String t = "";
	private String l = "";
	private String ns = "";
	private String c = "";
	
	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public AlidayuInviRemind() {
		// TODO Auto-generated constructor stub
	}

}
