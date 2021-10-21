package com.xyram.ticketingTool.request;

import com.xyram.ticketingTool.enumType.JobOpeningStatus;

public class JobOpeningSearchRequest {
	
	private JobOpeningStatus status;
	
	private String wing;
	
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	

	public JobOpeningStatus getStatus() {
		return status;
	}

	public void setStatus(JobOpeningStatus status) {
		this.status = status;
	}

	public String getWing() {
		return wing;
	}

	public void setWing(String wing) {
		this.wing = wing;
	}
	
	

}
