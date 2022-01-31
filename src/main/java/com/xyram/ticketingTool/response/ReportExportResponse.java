package com.xyram.ticketingTool.response;

import java.util.Map;

public class ReportExportResponse {
	
	private Map fileDetails;
	

	private String  Status;
	
	
	private String message;


	public Map getFileDetails() {
		return fileDetails;
	}


	public void setFileDetails(Map fileDetails) {
		this.fileDetails = fileDetails;
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
