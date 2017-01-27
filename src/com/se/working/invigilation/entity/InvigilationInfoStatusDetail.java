package com.se.working.invigilation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class InvigilationInfoStatusDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	// 监考信息
	@ManyToOne
	private InvigilationInfo invInfo;
	// 本次状态
	@ManyToOne
	private InvigilationStatusType invStatus;
	//  本次状态改变时间
	@Temporal(TemporalType.TIMESTAMP)
	// 仅更新有效，无法仅更新时间
	// @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date assignTime;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	
	public InvigilationInfoStatusDetail() {
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
	public InvigilationStatusType getInvStatus() {
		return invStatus;
	}
	public void setInvStatus(InvigilationStatusType invStatus) {
		this.invStatus = invStatus;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	
}
