package com.se.working.invigilation.entity;

import java.util.Set;

import javax.persistence.CascadeType;
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
	// 推荐监考
	private boolean enabledRecommend = true;
	// 特殊监考次数
	private int sqecQuantity;
	// 课程
	@OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy(value ="id ASC")
	private Set<Course> courses;
	// 监考安排
	@OneToMany(mappedBy = "teacher")
	@OrderBy(value ="id ASC")
	private Set<Invigilation> invigilations;
	// 特殊监考安排
	@OneToMany(mappedBy = "teacher")
	@OrderBy(value ="id ASC")
	private Set<SpecialInvigilation> specialInvigilations;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEnabledRecommend() {
		return enabledRecommend;
	}

	public void setEnabledRecommend(boolean enabledRecommend) {
		this.enabledRecommend = enabledRecommend;
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
