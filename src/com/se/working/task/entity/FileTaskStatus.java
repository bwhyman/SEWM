package com.se.working.task.entity;

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
public class FileTaskStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	// 状态名称
	private String name;
	@OneToMany(mappedBy = "currentStatus")
	@OrderBy(value ="id ASC")
	private Set<FileTask> fileTasks;
	@Temporal(TemporalType.TIMESTAMP )
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	/**
	 * 开启状态
	 */
	public final static long STARTED = 1;
	/**
	 * 过期状态
	 */
	public final static long EXPIRED = 2;
	/**
	 * 关闭状态
	 */
	public final static long CLOSED = 3;
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


	public Set<FileTask> getFileTasks() {
		return fileTasks;
	}


	public void setFileTasks(Set<FileTask> fileTasks) {
		this.fileTasks = fileTasks;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public FileTaskStatus(long id) {
		super();
		this.id = id;
	}


	public FileTaskStatus() {
		// TODO Auto-generated constructor stub
	}

}
