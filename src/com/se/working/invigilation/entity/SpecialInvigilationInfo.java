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

@Entity
public class SpecialInvigilationInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 监考类型
	@ManyToOne
	private SpecialInvigilationType specType;
	// 监考时间
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateTime;
	// 备注
	private String comment;
	// 监考地点
	private String location = "科学会堂";
	@OneToMany(mappedBy = "specInv", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@OrderBy(value ="id ASC")
	private Set<SpecialInvigilation> specialInvigilations;
	// 监考信息载入时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
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
	
	public SpecialInvigilationInfo() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SpecialInvigilationType getSpecType() {
		return specType;
	}
	public void setSpecType(SpecialInvigilationType specType) {
		this.specType = specType;
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
	public Calendar getDateTime() {
		return dateTime;
	}
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
