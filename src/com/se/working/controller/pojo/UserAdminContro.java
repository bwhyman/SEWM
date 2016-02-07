package com.se.working.controller.pojo;

import java.util.List;

import com.se.working.entity.User;

/**
 * 封装修改用户权限对象
 * @author BO
 *
 */
public class UserAdminContro {
	private List<User> users;
	private String[] newAdminIds;
	private String[] olderAdminIds;
	
	
	public UserAdminContro(List<User> users, String[] newAdminIds, String[] olderAdminIds) {
		super();
		this.users = users;
		this.newAdminIds = newAdminIds;
		this.olderAdminIds = olderAdminIds;
	}

	public UserAdminContro() {
		// TODO Auto-generated constructor stub
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String[] getNewAdminIds() {
		return newAdminIds;
	}

	public void setNewAdminIds(String[] newAdminIds) {
		this.newAdminIds = newAdminIds;
	}

	public String[] getOlderAdminIds() {
		return olderAdminIds;
	}

	public void setOlderAdminIds(String[] olderAdminIds) {
		this.olderAdminIds = olderAdminIds;
	}
	

}
