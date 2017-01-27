package com.se.working.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.se.working.invigilation.entity.InvigilationInfo;

@Entity
public class Groups {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	// 监考匹配表达式
	private String inviRegexPrefix;
	@OneToMany(mappedBy = "groups")
	@OrderBy
	private Set<User> users;
	@OneToMany(mappedBy = "groups")
	@OrderBy
	private Set<InvigilationInfo> infos;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	
	public static final long SE = 1;
	public static final long ACCOUNT = 2;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	/*@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(id, insertTime);
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) {
			return true;
		}
		if (!this.getClass().isInstance(obj)) {
			return false;
		}
		return Objects.equals(hashCode(), Objects.hashCode(obj));
	}*/
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getInviRegexPrefix() {
		return inviRegexPrefix;
	}
	public void setInviRegexPrefix(String inviRegexPrefix) {
		this.inviRegexPrefix = inviRegexPrefix;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	
	public Set<InvigilationInfo> getInfos() {
		return infos;
	}
	public void setInfos(Set<InvigilationInfo> infos) {
		this.infos = infos;
	}
	public Groups(long id) {
		super();
		this.id = id;
	}
	public Groups() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
