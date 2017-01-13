package com.se.working.invigilation.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MessageStatusDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private MessageStatusType type;
	@ManyToOne
	private Invigilation invigilation;
	// 状态创建时间
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
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MessageStatusDetail)) {
			return false;
		}
		MessageStatusDetail o = (MessageStatusDetail) obj;
		return Objects.equals(id, o.getId());
	}
	public MessageStatusDetail() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MessageStatusType getType() {
		return type;
	}

	public void setType(MessageStatusType type) {
		this.type = type;
	}

	public Invigilation getInvigilation() {
		return invigilation;
	}

	public void setInvigilation(Invigilation invigilation) {
		this.invigilation = invigilation;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
