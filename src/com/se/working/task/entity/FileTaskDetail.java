package com.se.working.task.entity;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 文件任务详细信息
 * @author BO
 *
 */
@Entity
public class FileTaskDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private FileTask fileTask;
	@ManyToOne
	private TeacherTask teacher;
	// 是否完成
	private boolean done = false;
	// 任务文件名称，课表_王波.xls
	private String file;
	// 完成时间
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar completeTime;
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
		if (!(obj instanceof FileTaskDetail)) {
			return false;
		}
		FileTaskDetail o = (FileTaskDetail) obj;
		return Objects.equals(id, o.getId());
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public FileTask getFileTask() {
		return fileTask;
	}
	public void setFileTask(FileTask fileTask) {
		this.fileTask = fileTask;
	}
	public TeacherTask getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTask teacher) {
		this.teacher = teacher;
	}
	public Calendar getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Calendar completeTime) {
		this.completeTime = completeTime;
	}
	public FileTaskDetail() {
		// TODO Auto-generated constructor stub
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
