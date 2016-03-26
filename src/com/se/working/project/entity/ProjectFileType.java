package com.se.working.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProjectFileType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String templeteFile;
	private String directory;
	private boolean opened = false;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	public ProjectFileType() {
		// TODO Auto-generated constructor stub
	}

	public ProjectFileType(long id) {
		super();
		this.id = id;
	}

	public interface ProjectFileTypes{
		/**
		 * 论证报告
		 */
		public static long DEMONSTRATIONREPORT = 1;
		/**
		 * 开题报告
		 */
		public static long OPENINGREPORT = 2;
		/**
		 * 开题答辩记录
		 */
		public static long OPENDEFENSERECORD = 3;
		/**
		 * 中期答辩记录
		 */
		public static long INTERIMDEFENSERECORD = 4;
		/**
		 * 论文答辩记录
		 */
		public static long PAPERDEFENSERECORD = 5;
		/**
		 * 中期报告
		 */
		public static long INTERIMREPORT = 6;
		/**
		 * 论文
		 */
		public static long PAPER = 7;
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

	public String getTempleteFile() {
		return templeteFile;
	}

	public void setTempleteFile(String templeteFile) {
		this.templeteFile = templeteFile;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
