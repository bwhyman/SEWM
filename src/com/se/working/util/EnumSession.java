package com.se.working.util;

public enum EnumSession {
	SESSION_1("08:00", "09:35"), 
	SESSION_3("10:05", "11:40"), 
	SESSION_5("13:40", "15:15"), 
	SESSION_7("15:35", "17:10"), 
	SESSION_9("18:00", "19:34"), 
	SESSION_11("19:40", "21:15");

	private String startTime;
	private String endTime;
	private EnumSession(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}

}
