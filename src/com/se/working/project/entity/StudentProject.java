package com.se.working.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.se.working.entity.User;

@Entity
public class StudentProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@MapsId
	@OneToOne
	private User user;
	private boolean opened = false;
	@OneToOne(mappedBy = "student")
	private SelectedTitleDetail selectedTitleDetail;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	public StudentProject(long id) {
		super();
		this.id = id;
	}

	public StudentProject() {
		// TODO Auto-generated constructor stub
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

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public SelectedTitleDetail getSelectedTitleDetail() {
		return selectedTitleDetail;
	}

	public void setSelectedTitleDetail(SelectedTitleDetail selectedTitleDetail) {
		this.selectedTitleDetail = selectedTitleDetail;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

}
