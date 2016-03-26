package com.se.working.project.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
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
	@OneToOne(mappedBy="student")
	private ProjectTitle title;
	@OneToMany(mappedBy = "student")
	@OrderBy("id ASC")
	private Set<ProjectFileDetail> projectFileDetail;
	@OneToMany(mappedBy = "student")
	@OrderBy("id DESC")
	private Set<GuideRecord> guideRecords;
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

	public ProjectTitle getTitle() {
		return title;
	}

	public void setTitle(ProjectTitle title) {
		this.title = title;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Set<ProjectFileDetail> getProjectFileDetail() {
		return projectFileDetail;
	}

	public void setProjectFileDetail(Set<ProjectFileDetail> projectFileDetail) {
		this.projectFileDetail = projectFileDetail;
	}

	public Set<GuideRecord> getGuideRecords() {
		return guideRecords;
	}

	public void setGuideRecords(Set<GuideRecord> guideRecords) {
		this.guideRecords = guideRecords;
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
