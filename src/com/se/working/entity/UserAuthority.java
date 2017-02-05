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
	 */
	public final static int STUDENT_LEVEL = 5;
	public final static int TEACHER_LEVEL = 10;
	public final static int ADAMIN_LEVEL = 15;
	public final static int SUPERADMIN_LEVEL = 20;
	/**
	 * 用户权限ID
	 * @author BO
	 *
	 */
	public static long TEACHER = 1;
	public static long ADMIN = 2;
	public static long SUPERADMIN = 3;
	public static long STUDENT = 4;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
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
