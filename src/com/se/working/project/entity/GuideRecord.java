package com.se.working.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GuideRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private ProjectTitle title;
	@ManyToOne
	private StudentProject student;
	@ManyToOne
	private ProjectFileType projectFileType;
	@ManyToOne
	private ProjectFileDetail projectFileDetail;
	@Column(length = 500)
	private String comment;
	private String fileName;
	private String directory;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	
	public GuideRecord() {
		// TODO Auto-generated constructor stub
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public ProjectTitle getTitle() {
		return title;
	}


	public void setTitle(ProjectTitle title) {
		this.title = title;
	}


	public StudentProject getStudent() {
		return student;
	}


	public void setStudent(StudentProject student) {
		this.student = student;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
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


	public String getDirectory() {
		return directory;
	}


	public void setDirectory(String directory) {
		this.directory = directory;
	}


	public ProjectFileType getProjectFileType() {
		return projectFileType;
	}


	public void setProjectFileType(ProjectFileType projectFileType) {
		this.projectFileType = projectFileType;
	}


	public ProjectFileDetail getProjectFileDetail() {
		return projectFileDetail;
	}


	public void setProjectFileDetail(ProjectFileDetail projectFileDetail) {
		this.projectFileDetail = projectFileDetail;
	}


}
