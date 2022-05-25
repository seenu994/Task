package com.xyram.ticketingTool.request;

import com.xyram.ticketingTool.entity.EmployeePermission;

public class CurrentUser {
	
	private String name;

	private String userId;

	private String userRole;
	
	private String scopeId;
	
	private String firstName;
	
	private EmployeePermission permission;

	public EmployeePermission getPermission() {
		return permission;
	}

	public void setPermission(EmployeePermission permission) {
		this.permission = permission;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	
	
}
