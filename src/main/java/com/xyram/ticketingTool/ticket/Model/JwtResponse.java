package com.xyram.ticketingTool.ticket.Model;


import java.io.Serializable;
import java.util.List;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.UserPermissions;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String sessionId;
	private final User user;
	private final String baseResourcePath;
	private final List<UserPermissions> permissions;
	public JwtResponse(String jwttoken,String sessionId,String baseResourcePath,User user, List<UserPermissions> permissions) {
		this.jwttoken = jwttoken;
		this.sessionId = sessionId;
		this.user= user;
		this.baseResourcePath = baseResourcePath;
		this.permissions = permissions;
	}

	public String getToken() {
		return this.jwttoken;
	}
	public String getSessionId() {
		return sessionId;
	}
	public User getUser() {
		return this.user;
}
	public String getBaseResourcePath() {
		return baseResourcePath;
	}

	public List<UserPermissions> getPermissions() {
		return permissions;
	}
	
	
}