package com.xyram.ticketingTool.ticket.Model;


import java.io.Serializable;

import com.xyram.ticketingTool.admin.model.User;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String sessionId;
	private final User user;
	private final String baseResourcePath;

	public JwtResponse(String jwttoken,String sessionId,String baseResourcePath,User user) {
		this.jwttoken = jwttoken;
		this.sessionId = sessionId;
		this.user= user;
		this.baseResourcePath = baseResourcePath;
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
}