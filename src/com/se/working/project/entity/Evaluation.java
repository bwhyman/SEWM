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
public class Evaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private StudentProject student;
	@ManyToOne
	private ProjectFileType fileType;
	private boolean managerEval = false;
	private boolean teacherEval = false;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	public Evaluation(StudentProject student, ProjectFileType fileType) {
		super();
		this.student = student;
		this.fileType = fileType;
	}
	public Evaluation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public ProjectFileType getFileType() {
		return fileType;
	}
	public void setFileType(ProjectFileType fileType) {
		this.fileType = fileType;
	}
	public boolean isManagerEval() {
		return managerEval;
	}
	public void setManagerEval(boolean managerEval) {
		this.managerEval = managerEval;
	}
	public boolean isTeacherEval() {
		return teacherEval;
	}
	public void setTeacherEval(boolean teacherEval) {
		this.teacherEval = teacherEval;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public StudentProject getStudent() {
		return student;
	}
	public void setStudent(StudentProject student) {
		this.student = student;
	}


}
