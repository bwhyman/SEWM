package com.se.working.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * 用户权限
 * @author BO
 *
 */
@Entity
public class UserAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private int level;
	@OneToMany(mappedBy = "userAuthority")
	private Set<User> users;
	public interface AuthorityType {
		public static int STUDENT = 1;
		public static int TEACHER = 5;
		public static int ADAMIN = 15;
		public static int SUPERADMIN = 20;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public UserAuthority() {
		// TODO Auto-generated constructor stub
	}

}
