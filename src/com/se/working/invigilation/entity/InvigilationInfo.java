package com.se.working.invigilation.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
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

import com.se.working.entity.Groups;



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
	@ManyToOne
	private Groups groups;
	// 监考人数
	private int requiredNumber;
	// 监考安排
	@OneToMany(mappedBy = "invInfo", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("id ASC")
	private Set<Invigilation> invigilations;
	// 监考安排状态历史信息
	@OneToMany(mappedBy = "invInfo",  cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value ="id ASC")
	private Set<InvigilationInfoStatusDetail> invStatusDetail;
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
	// 备注
	private String comment;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(id);
	}
	
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

	public Set<InvigilationInfoStatusDetail> getInvStatusDetail() {
		return invStatusDetail;
	}

	public void setInvStatusDetail(Set<InvigilationInfoStatusDetail> invStatusDetail) {
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
	public Groups getGroups() {
		return groups;
	}
	public void setGroups(Groups groups) {
		this.groups = groups;
	}
	
}
