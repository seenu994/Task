package com.xyram.ticketingTool.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class TicketVo {

	
	private MultipartFile[] files;
	

	private String ticketRequest;
	
	
	
	private boolean self;
	
	
	
	
	private String assigneeId;


	public MultipartFile[] getFiles() {
		return files;
	}


	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}


	public String getTicketRequest() {
		return ticketRequest;
	}


	public void setTicketRequest(String ticketRequest) {
		this.ticketRequest = ticketRequest;
	}


	public String getAssigneeId() {
		return assigneeId;
	}


	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}


	public boolean isSelf() {
		return self;
	}


	public void setSelf(boolean self) {
		this.self = self;
	}
	
	
	
	
 
}
