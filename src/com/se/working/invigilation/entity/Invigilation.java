package com.se.working.invigilation.entity;

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
 * 监考分配详细信息
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
	// 监考消息细节
	@OneToMany(mappedBy = "invigilation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value ="id ASC")
	private Set<MessageStatusDetail> messageDetails;
	// 当前消息状态
	@ManyToOne
	private MessageStatusType currentMessageType;
	
	// 监考分配时间
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
	public Set<MessageStatusDetail> getMessageDetails() {
		return messageDetails;
	}
	public void setMessageDetails(Set<MessageStatusDetail> messageDetails) {
		this.messageDetails = messageDetails;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public MessageStatusType getCurrentMessageType() {
		return currentMessageType;
	}
	public void setCurrentMessageType(MessageStatusType currentMessageType) {
		this.currentMessageType = currentMessageType;
	}
	
}
