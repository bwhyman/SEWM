package com.se.working.invigilation.entity;

import java.util.Calendar;
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
public class SpecialInvigilationInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 监考人数
	private int requiredNumber;
	// 监考类型
	@ManyToOne
	private SpecialInvigilationType specType;
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
	@OneToMany(mappedBy = "specInv")
	@OrderBy(value ="id DESC")
	private Set<SpecialInvigilation> specialInvigilations;
	// 监考信息载入时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	public SpecialInvigilationInfo() {
		// TODO Auto-generated constructor stub
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
	public SpecialInvigilationType getSpecType() {
		return specType;
	}
	public void setSpecType(SpecialInvigilationType specType) {
		this.specType = specType;
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
	public Set<SpecialInvigilation> getSpecialInvigilations() {
		return specialInvigilations;
	}
	public void setSpecialInvigilations(Set<SpecialInvigilation> specialInvigilations) {
		this.specialInvigilations = specialInvigilations;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
