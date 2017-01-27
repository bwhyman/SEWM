package com.se.working.task.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
/**
 * 文件类型，仅用于前端上传时input accept属性规范上传类型<br>
 * name: 无格式要求; type:
 * name: Word文档; type: .doc,.docx
 * name: Excel表格; type: .xls,.xlsx
 * @author BO
 *
 */
@Entity
public class FileType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;
	@OneToMany(mappedBy = "fileType")
	@OrderBy("id ASC")
	private Set<FileTask> fileTasks;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public FileType(long id) {
		super();
		this.id = id;
	}
	public FileType() {
		// TODO Auto-generated constructor stub
	}
	public Set<FileTask> getFileTasks() {
		return fileTasks;
	}
	public void setFileTasks(Set<FileTask> fileTasks) {
		this.fileTasks = fileTasks;
	}

}
