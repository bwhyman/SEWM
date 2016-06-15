package com.se.working.project.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.se.working.entity.User;

@Entity
public class TeacherProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@MapsId
	@OneToOne
	private User user;
	private int leadNum = 0;
	@OneToMany(mappedBy = "teacher")
	@OrderBy("id ASC")
	private Set<ProjectTitle> titles;
	public TeacherProject() {
		// TODO Auto-generated constructor stub
	}
	
	public TeacherProject(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<ProjectTitle> getTitles() {
		return titles;
	}
	public void setTitles(Set<ProjectTitle> titles) {
		this.titles = titles;
	}

	public int getLeadNum() {
		return leadNum;
	}

	public void setLeadNum(int leadNum) {
		this.leadNum = leadNum;
	}

}
