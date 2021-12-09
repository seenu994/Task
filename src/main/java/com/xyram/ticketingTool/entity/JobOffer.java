package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name="job_offers")
public class JobOffer extends AuditModel {
	
	@Id
	@IdPrefix(value = "JOBOFF_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="job_offer_id")
	private String Id;
	
	@Column(name="candidate_name")
	private String candidateName;
	
	@Column(name="candidate_mobile")
	private String candidateMobile;

	@Column(name="candidate_email")
	private String candidateEmail;
	
	@Column(name="total_exp")
	private Integer totalExp;
	
	@Column(name="job_code")
	private String jobCode;
	
	@Column(name="job_title")
	private String jobTitle;
	
	@Column(name="application_id")
	private String applicationId;
	
	@Column(name="salary")
	private double salary;
	
	@Column(name="date_of_joining")
	private Date doj;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private JobOfferStatus status = JobOfferStatus.OFFERED;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateMobile() {
		return candidateMobile;
	}

	public void setCandidateMobile(String candidateMobile) {
		this.candidateMobile = candidateMobile;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public Integer getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(Integer totalExp) {
		this.totalExp = totalExp;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public JobOfferStatus getStatus() {
		return status;
	}

	public void setStatus(JobOfferStatus status) {
		this.status = status;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	

}
