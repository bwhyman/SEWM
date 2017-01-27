package com.se.working.message.pojo;


/**
 * 监考提醒:时间:${time};地点:${location};人员:${names}
 * @author BO
 *
 */
public class AlidayuInviRemind {

	private String time = "";
	private String location = "";
	private String names = "";
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public AlidayuInviRemind() {
		// TODO Auto-generated constructor stub
	}

}
