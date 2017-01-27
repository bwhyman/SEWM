package com.se.working.task.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 通知内容
	private String comment;
	// 地点
	private String location;
	// 分数
	private int point;
	// 发布者
	@ManyToOne
	private TeacherTask createUser;
	// 结束时间
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endTime;
	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	/**
	 * referencedColumnName属性为数据库字段，基于共同主键，teacherX实体类在数据表中没有自己的主键id字段，
	 * 映射时生成的user对应主键字段名称为user_id
	 */
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "notif_id", referencedColumnName = "ID"), 
						inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName="user_id"))
	@OrderBy("id ASC")
	private Set<TeacherTask> teachers;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Set<TeacherTask> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<TeacherTask> teachers) {
		this.teachers = teachers;
	}

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public TeacherTask getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeacherTask createUser) {
		this.createUser = createUser;
	}

}
