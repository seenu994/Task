package com.xyram.ticketingTool.ticket.Model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	private String uid;
	private String osType;
	private String deviceId;

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

	public JwtRequest(String username, String password, String uid,String osType,String deviceId) {
		this.setUsername(username);
		this.setPassword(password);
		this.setUid(uid);
        this.setOsType(osType);
        this.setDeviceId(deviceId);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	
}