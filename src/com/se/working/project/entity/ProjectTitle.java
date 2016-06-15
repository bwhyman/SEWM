package com.se.working.project.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProjectTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String major;
	private String property;
	@Column(length = 2000)
	private String objective;
	@ManyToOne
	private TeacherProject teacher;
	@OneToMany(mappedBy = "title")
	@OrderBy("id ASC")
	private Set<SelectedTitleDetail> selectedTitleDetails;
	@OneToMany(mappedBy = "title")
	private Set<ProjectFileDetail> projectFileDetails;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	
	public ProjectTitle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProjectTitle(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public TeacherProject getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherProject teacher) {
		this.teacher = teacher;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Set<SelectedTitleDetail> getSelectedTitleDetails() {
		return selectedTitleDetails;
	}
	public void setSelectedTitleDetails(Set<SelectedTitleDetail> selectedTitleDetails) {
		this.selectedTitleDetails = selectedTitleDetails;
	}

	public Set<ProjectFileDetail> getProjectFileDetails() {
		return projectFileDetails;
	}

	public void setProjectFileDetails(Set<ProjectFileDetail> projectFileDetails) {
		this.projectFileDetails = projectFileDetails;
	}

	
}
