package com.xyram.ticketingTool.response;

import java.util.List;
import java.util.Map;

public class ProjectAdminResponse {

	
	private boolean isAdmin;
	private String  Status;
	
	private String message;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}

