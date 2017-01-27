package com.se.working.invigilation.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 监考安排状态
 * 未分配，已分配，已完成
 * @author BO
 *
 */
@Entity
public class InvigilationStatusType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	// 状态名称
	private String name;
	@OneToMany(mappedBy = "currentStatusType")
	@OrderBy(value ="id ASC")
	private Set<InvigilationInfo> invInfo;
	// 监考状态详细信息
	@OneToMany(mappedBy = "invStatus")
	@OrderBy(value ="id ASC")
	private Set<InvigilationInfoStatusDetail> details;
	
	// 状态创建时间
	@Temporal(TemporalType.TIMESTAMP )
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	/**
	 * 
	 */
	public final static long UNASSIGNED = 1;
	public final static long ASSIGNED = 2;
	public final static long DONE = 3;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public InvigilationStatusType(long id) {
		super();
		this.id = id;
	}
	public InvigilationStatusType() {
		// TODO Auto-generated constructor stub
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
	public Set<InvigilationInfo> getInvInfo() {
		return invInfo;
	}
	public void setInvInfo(Set<InvigilationInfo> invInfo) {
		this.invInfo = invInfo;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Set<InvigilationInfoStatusDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<InvigilationInfoStatusDetail> details) {
		this.details = details;
	}
}
