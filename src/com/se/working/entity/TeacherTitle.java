package com.se.working.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TeacherTitle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	public interface TeacherTitleType {
		/**
		 * 讲师
		 */
		public static long LECTURER = 1;
		/**
		 * 副教授
		 */
		public static long AP = 2;
		/**
		 * 教授
		 */
		public static int PROF = 3;
		/**
		 * 助教
		 */
		public static long ASSISTANT = 4;
	}

	
	public TeacherTitle(long id) {
		super();
		this.id = id;
	}

	public TeacherTitle() {
		// TODO Auto-generated constructor stub
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

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
}
