package com.se.working.invigilation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 监考安排详细信息
 * @author BO
 *
 */
@Entity
public class Invigilation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private InvigilationInfo invInfo;
	@ManyToOne
	private TeacherInvigilation teacher;
	// 监考安排时间
	@Temporal(TemporalType.TIMESTAMP )
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date assignTime;
	public Invigilation() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public InvigilationInfo getInvInfo() {
		return invInfo;
	}
	public void setInvInfo(InvigilationInfo invInfo) {
		this.invInfo = invInfo;
	}
	public TeacherInvigilation getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherInvigilation teacher) {
		this.teacher = teacher;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	
}
