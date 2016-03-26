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
public class ProjectFileDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private StudentProject student;
	@ManyToOne
	private ProjectTitle title;
	@ManyToOne
	private ProjectFileType projectFileType;
	private String fileName;
	private String directory;
	@OneToMany(mappedBy = "projectFileDetail")
	@OrderBy("id ASC")
	private Set<GuideRecord> guideRecords;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public StudentProject getStudent() {
		return student;
	}

	public void setStudent(StudentProject student) {
		this.student = student;
	}

	public ProjectTitle getTitle() {
		return title;
	}

	public void setTitle(ProjectTitle title) {
		this.title = title;
	}

	public ProjectFileType getProjectFileType() {
		return projectFileType;
	}

	public void setProjectFileType(ProjectFileType projectFileType) {
		this.projectFileType = projectFileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public ProjectFileDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public Set<GuideRecord> getGuideRecords() {
		return guideRecords;
	}

	public void setGuideRecords(Set<GuideRecord> guideRecords) {
		this.guideRecords = guideRecords;
	}

}
