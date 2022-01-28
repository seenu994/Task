package com.xyram.ticketingTool.request;

import java.util.StringJoiner;

import com.xyram.ticketingTool.enumType.JobApplicationStatus;

public class JobApplicationStatusRequest {

	
	
	
	private JobApplicationStatus jobApplicationStatus;
	
	private String comments;

	public JobApplicationStatus getJobApplicationStatus() {
		return jobApplicationStatus;
	}

	public void setJobApplicationStatus(JobApplicationStatus jobApplicationStatus) {
		this.jobApplicationStatus = jobApplicationStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
}
