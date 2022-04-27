package com.xyram.ticketingTool.apiresponses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.AssetIssues;

public class ApiResponse {
	
	String message;
	
	boolean success;
	
	Map content;
	
	Map fileDetails;
	
	public ApiResponse(boolean requiredContent){
		
		message = "";
		success = false;
		if(requiredContent) {
			content = new HashMap(); 
		}
		
		fileDetails = new HashMap();
		
	}


	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String class1) {
		this.message = class1;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public Map getContent() {
		return content;
	}


	public void setContent(Map content2) {
		this.content = (Map) content2;
	}


	public void setStatus(String string) {
		// TODO Auto-generated method stub
		
	}



	public void setContent1(List<Map> assetList) {
		// TODO Auto-generated method stub
		
	}


	public Object getissueId() {
		// TODO Auto-generated method stub
		return null;
	}


	public Map getFileDetails() {
		return fileDetails;
	}


	public void setFileDetails(Map fileDetails) {
		this.fileDetails = fileDetails;
	}


	


	


	


	

	


	

	


	


	
	
	

}
