package com.se.working.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 用户基本属性信息
 * @author BO
 *
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Column(unique = true)
	private String employeeNumber;
	private String password;
	@ManyToOne
	private Groups groups;
	// 职称
	@ManyToOne
	private TeacherTitle title;
	@Column(length=1000)
	private String introduction;
	/**
	 * 是否接收短信通知，教师出国可以关闭
	 */
	private boolean enabledMessage = true;
	// 权限
	@ManyToOne
	private UserAuthority userAuthority;
	@Column(length = 15)
	private String phoneNumber;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(long id) {
		super();
		this.id = id;
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
	
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TeacherTitle getTitle() {
		return title;
	}
	public void setTitle(TeacherTitle title) {
		this.title = title;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}
	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}
	public boolean isEnabledMessage() {
		return enabledMessage;
	}
	public void setEnabledMessage(boolean enabledMessage) {
		this.enabledMessage = enabledMessage;
	}
	public Groups getGroups() {
		return groups;
	}
	public void setGroups(Groups groups) {
		this.groups = groups;
	}
	
}
