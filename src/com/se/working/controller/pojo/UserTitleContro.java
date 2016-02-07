package com.se.working.controller.pojo;

import java.util.List;

import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;

public class UserTitleContro {

	private User user;
	private List<TeacherTitle> teacherTitles;
	private String userTitleId;
	public UserTitleContro() {
		// TODO Auto-generated constructor stub
	}
	
	public UserTitleContro(List<TeacherTitle> teacherTitles, String userTitleId) {
		super();
		this.teacherTitles = teacherTitles;
		this.userTitleId = userTitleId;
	}

	public UserTitleContro(User user, List<TeacherTitle> teacherTitles, String userTitleId) {
		super();
		this.user = user;
		this.teacherTitles = teacherTitles;
		this.userTitleId = userTitleId;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<TeacherTitle> getTeacherTitles() {
		return teacherTitles;
	}
	public void setTeacherTitles(List<TeacherTitle> teacherTitles) {
		this.teacherTitles = teacherTitles;
	}
	public String getUserTitleId() {
		return userTitleId;
	}
	public void setUserTitleId(String userTitleId) {
		this.userTitleId = userTitleId;
	}
	


}
