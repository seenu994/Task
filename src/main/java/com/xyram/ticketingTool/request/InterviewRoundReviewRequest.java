package com.xyram.ticketingTool.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InterviewRoundReviewRequest {

	@NotNull(message = "jobInterviewStatus is mandatory")
	@NotEmpty(message = "jobInterviewStatus should not be  empty")
	private String jobInterviewStatus;
	private Integer rating;

	@NotNull(message = "feedback is mandatory")
	@NotEmpty(message = "feedback should not be  empty")
	private String feedback;

	public String getJobInterviewStatus() {
		return jobInterviewStatus;
	}

	public void setJobInterviewStatus(String jobInterviewStatus) {
		this.jobInterviewStatus = jobInterviewStatus;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
