package com.se.working.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	@OrderBy(value ="id ASC")
	private Set<User> users;
	
	/**
	 * 用户权限值
	 * @author BO
	 *
	 */
	public interface UserAuthorityLevel {
		public static int STUDENT = 5;
		public static int TEACHER = 10;
		public static int ADAMIN = 15;
		public static int SUPERADMIN = 20;
	}
	/**
	 * 用户权限ID
	 * @author BO
	 *
	 */
	public interface UserAuthorityType{
		
		public static long TEACHER = 1;
		public static long ADAMIN = 2;
		public static long SUPERADMIN = 3;
		public static long STUDENT = 4;
	}
	
	public UserAuthority(long id) {
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public UserAuthority() {
		// TODO Auto-generated constructor stub
	}

}
