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

@Entity
public class MessageStatusType {
	public static final int NOTIFIED = 1;
	public static final int REMINDED = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 状态名称
	private String name;
	@OneToMany(mappedBy = "type")
	@OrderBy(value ="id ASC")
	private Set<MessageStatusDetail> details;
	
	@OneToMany(mappedBy = "currentMessageType")
	@OrderBy(value ="id ASC")
	private Set<Invigilation> invigilations;
	
	// 状态创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public MessageStatusType() {
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

	public Set<MessageStatusDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<MessageStatusDetail> details) {
		this.details = details;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public MessageStatusType(long id) {
		super();
		this.id = id;
	}

	public Set<Invigilation> getInvigilations() {
		return invigilations;
	}

	public void setInvigilations(Set<Invigilation> invigilations) {
		this.invigilations = invigilations;
	}

}
