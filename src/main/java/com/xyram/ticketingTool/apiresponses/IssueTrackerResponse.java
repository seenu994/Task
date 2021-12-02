package com.xyram.ticketingTool.apiresponses;

import java.util.List;
import java.util.Map;

public class IssueTrackerResponse {
	
	
	private List<Map> content;
	private String  Status;
	
	private String message;

	
	
	
	
	

	
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Map> getContent() {
		return content;
	}
	public void setContent(List<Map> content) {
		this.content = content;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	
	
	
	

}
