package com.se.working.invigilation.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.se.working.entity.User;

/**
 * 教师监考属性
 * @author BO
 *
 */
@Entity
public class TeacherInvigilation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@MapsId
	@OneToOne
	private User user;
	// 监考次数
	private int invQuantity;
	// 是否安排监考
	private boolean enabled = true;
	// 特殊监考次数
	private int sqecQuantity;
	// 课程
	@OneToMany(mappedBy = "teacher")
	@OrderBy(value ="id DESC")
	private Set<Course> courses;
	// 授课时间
	@OneToMany(mappedBy = "teacher")
	private Set<CourseSection> courseSections;
	// 监考安排
	@OneToMany(mappedBy = "teacher")
	@OrderBy(value ="id DESC")
	private Set<Invigilation> invigilations;
	// 特殊监考安排
	@OneToMany(mappedBy = "teacher")
	@OrderBy(value ="id DESC")
	private Set<SpecialInvigilation> specialInvigilations;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getInvQuantity() {
		return invQuantity;
	}

	public Set<CourseSection> getCourseSections() {
		return courseSections;
	}

	public void setCourseSections(Set<CourseSection> courseSections) {
		this.courseSections = courseSections;
	}

	public void setInvQuantity(int invQuantity) {
		this.invQuantity = invQuantity;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getSqecQuantity() {
		return sqecQuantity;
	}

	public void setSqecQuantity(int sqecQuantity) {
		this.sqecQuantity = sqecQuantity;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<Invigilation> getInvigilations() {
		return invigilations;
	}

	public void setInvigilations(Set<Invigilation> invigilations) {
		this.invigilations = invigilations;
	}

	public Set<SpecialInvigilation> getSpecialInvigilations() {
		return specialInvigilations;
	}

	public void setSpecialInvigilations(Set<SpecialInvigilation> specialInvigilations) {
		this.specialInvigilations = specialInvigilations;
	}

	public TeacherInvigilation() {
		// TODO Auto-generated constructor stub
	}
}
