package com.se.working.task.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.se.working.entity.User;
@Entity
public class TeacherTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 分数
	private int point;
	@MapsId
	@OneToOne
	private User user;
	// 任务完成细节
	@OneToMany(mappedBy = "teacher")
	@OrderBy("id ASC")
	private Set<FileTaskDetail> fDetails;
	@OneToMany(mappedBy = "createUser")
	private Set<FileTask> createFileTasks;
	@OneToMany(mappedBy = "createUser")
	private Set<Notification> createNotifications;
	
	@ManyToMany(mappedBy = "teachers")
	@OrderBy("id ASC")
	private Set<Notification> notifications;
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
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<FileTaskDetail> getfDetails() {
		return fDetails;
	}
	public void setfDetails(Set<FileTaskDetail> fDetails) {
		this.fDetails = fDetails;
	}

	public TeacherTask(long id) {
		super();
		this.id = id;
	}
	public TeacherTask() {
		// TODO Auto-generated constructor stub
	}
	public Set<FileTask> getCreateFileTasks() {
		return createFileTasks;
	}
	public void setCreateFileTasks(Set<FileTask> createFileTasks) {
		this.createFileTasks = createFileTasks;
	}
	public Set<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}
	public Set<Notification> getCreateNotifications() {
		return createNotifications;
	}
	public void setCreateNotifications(Set<Notification> createNotifications) {
		this.createNotifications = createNotifications;
	}
	
}
