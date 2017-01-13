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
public class SpecialInvigilation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private TeacherInvigilation teacher;
	@ManyToOne
	private SpecialInvigilationInfo specInv;
	// 监考安排时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date assignTime;
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
		if (!(obj instanceof SpecialInvigilation)) {
			return false;
		}
		SpecialInvigilation o = (SpecialInvigilation) obj;
		return Objects.equals(id, o.getId());
	}
	public SpecialInvigilation() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TeacherInvigilation getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherInvigilation teacher) {
		this.teacher = teacher;
	}

	public SpecialInvigilationInfo getSpecInv() {
		return specInv;
	}

	public void setSpecInv(SpecialInvigilationInfo specInv) {
		this.specInv = specInv;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
}
