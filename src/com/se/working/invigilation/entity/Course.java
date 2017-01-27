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

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	// 班级
	private String teachingClass;
	// 地点
	private String location;

	@OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value ="id DESC")
	private Set<CourseSection> courseSections;
	@ManyToOne
	private TeacherInvigilation teacher;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;

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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTeachingClass() {
		return teachingClass;
	}


	public void setTeachingClass(String teachingClass) {
		this.teachingClass = teachingClass;
	}


	public Set<CourseSection> getCourseSections() {
		return courseSections;
	}


	public void setCourseSections(Set<CourseSection> courseSections) {
		this.courseSections = courseSections;
	}


	public TeacherInvigilation getTeacher() {
		return teacher;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public void setTeacher(TeacherInvigilation teacher) {
		this.teacher = teacher;
	}


	public Course() {
		// TODO Auto-generated constructor stub
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}

}
