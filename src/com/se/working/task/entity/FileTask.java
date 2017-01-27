package com.se.working.task.entity;

import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 上传文件子任务
 * 
 * @author BO
 *
 */
@Entity
public class FileTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	// 任务名称，可以重名，结合ID创建文件夹
	private String name;
	private String comment;
	// 任务创建者
	@ManyToOne
	private TeacherTask createUser;
	@ManyToOne
	private FileTaskStatus currentStatus;
	// 任务相当目录，01-课表\\
	private String directory;
	// 任务模板文件，没有路径，课表_模板.xls，单一文件用于版本控制
	private String templeteFile;
	
	private boolean singleFile = false;
	// 分值
	private int point;
	// 任务详细信息
	@OneToMany(mappedBy = "fileTask", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("id ASC")
	private Set<FileTaskDetail> fileTaskDetails;
	// 文件类型
	@ManyToOne
	private FileType fileType;
	// 结束时间
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endTime;
	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public FileTask() {
		// TODO Auto-generated constructor stub
	}

	public FileTaskStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(FileTaskStatus currentStatus) {
		this.currentStatus = currentStatus;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<FileTaskDetail> getFileTaskDetails() {
		return fileTaskDetails;
	}

	public void setFileTaskDetails(Set<FileTaskDetail> fileTaskDetails) {
		this.fileTaskDetails = fileTaskDetails;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public TeacherTask getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeacherTask createUser) {
		this.createUser = createUser;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getTempleteFile() {
		return templeteFile;
	}

	public void setTempleteFile(String templeteFile) {
		this.templeteFile = templeteFile;
	}

	public boolean isSingleFile() {
		return singleFile;
	}

	public void setSingleFile(boolean singleFile) {
		this.singleFile = singleFile;
	}
}
