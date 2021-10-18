package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import com.xyram.ticketingTool.baseData.model.AuditModel;


@Entity
@Table(name = "job_interviews")

public class JobInterviews extends AuditModel{
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="interview_id")
	private String Id;
	
	/*
	 * `interview_id` varchar(255) NOT NULL,
  `application_id` varchar(255) NOT NULL,
  `interviewer` varchar(255) DEFAULT NULL,
  `round_no` varchar(255) DEFAULT NULL,
  `round_details` varchar(255) DEFAULT NULL,
  `interview_date` datetime DEFAULT NULL,
  `interview_type` varchar(255) DEFAULT NULL,
  `interview_link` varchar(255) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `recommendation` varchar(255) DEFAULT NULL,
	 */
	
	@Column(name="round_no")
	private Integer roundNo;
	
	@Column(name="interviewer")
	private String interviewer;

	@Column(name="round_details")
	private String roundDetails;
	
	@Column(name="interview_date")
	private Date interviewDate;
	
	@Column(name="interview_type")
	private String interviewType;
	
	@Column(name="interview_link")
	private String interviewLink;
	
	@Column(name="feedback")
	private String feedback;
	
	@Column(name="recommendation")
	private Integer recommendation;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "application_id")
    private JobApplication jobApplication;
	
	@Column(name="status")
	private String status;
	
	@Column(name="rating")
	private Integer rateGiven;

	public Integer getRating() {
		return rateGiven;
	}

	public void setRating(Integer rating) {
		this.rateGiven = rating;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Integer getRoundNo() {
		return roundNo;
	}

	public void setRoundNo(Integer roundNo) {
		this.roundNo = roundNo;
	}

	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	public String getRoundDetails() {
		return roundDetails;
	}

	public void setRoundDetails(String roundDetails) {
		this.roundDetails = roundDetails;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getInterviewLink() {
		return interviewLink;
	}

	public void setInterviewLink(String interviewLink) {
		this.interviewLink = interviewLink;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Integer getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	public JobApplication getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplication jobApplication) {
		this.jobApplication = jobApplication;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
