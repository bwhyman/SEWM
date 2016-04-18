package com.se.working.invigilation.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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



/**
 * 监考信息
 * @author BO
 *
 */
@Entity
public class InvigilationInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	// 监考人数
	private int requiredNumber;
	// 监考安排
	@OneToMany(mappedBy = "invInfo", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Invigilation> invigilations;
	// 监考安排状态历史信息
	@OneToMany(mappedBy = "invInfo",  cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value ="id ASC")
	private Set<InvigilationStatusDetail> invStatusDetail;
	// 当前监考安排状态
	@ManyToOne
	private InvigilationStatusType currentStatusType;
	// 监考信息载入时间
	@Temporal(TemporalType.TIMESTAMP )
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	// 监考开始时间
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar startTime;
	// 监考结束时间 
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endTime;
	// 监考地点
	private String location;
	// 监考课程
	private String course;
	// 备注
	private String comment;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRequiredNumber() {
		return requiredNumber;
	}

	public void setRequiredNumber(int requiredNumber) {
		this.requiredNumber = requiredNumber;
	}

	public Set<Invigilation> getInvigilations() {
		return invigilations;
	}

	public void setInvigilations(Set<Invigilation> invigilations) {
		this.invigilations = invigilations;
	}

	public Set<InvigilationStatusDetail> getInvStatusDetail() {
		return invStatusDetail;
	}

	public void setInvStatusDetail(Set<InvigilationStatusDetail> invStatusDetail) {
		this.invStatusDetail = invStatusDetail;
	}

	public InvigilationStatusType getCurrentStatusType() {
		return currentStatusType;
	}

	public void setCurrentStatusType(InvigilationStatusType currentStatusType) {
		this.currentStatusType = currentStatusType;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public InvigilationInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
}
