package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import com.xyram.ticketingTool.baseData.model.AuditModel;


@Entity
@Table(name = "job_application")
public class JobApplication extends AuditModel{
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="application_id")
	private String Id;
	
	@Column(name="candidate_name")
	private String candidateName;
	
	@Column(name="candidate_mobile")
	private String candidateMobile;

	@Column(name="candidate_email")
	private String candidateEmail;
	
	@Column(name="total_exp")
	private Integer totalExp;
	
	@Column(name="relevant_exp")
	private Integer relevantExp;
	
	@Column(name="referred_employee")
	private String referredEmployee;
	
	@Column(name="referred_vendor")
	private String referredVendor;
	
	@Column(name="expected_salary")
	private Integer expectedSalary;
	
	@Column(name="resume_path")
	private String resumePath;
	
	@Column(name="job_code")
	private String jobCode;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "job_id")
    private JobOpenings jobOpenings;
	
	@Column(name="status")
	private String status;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
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

	public Integer getRelevantExp() {
		return relevantExp;
	}

	public void setRelevantExp(Integer relevantExp) {
		this.relevantExp = relevantExp;
	}

	public String getReferredEmployee() {
		return referredEmployee;
	}

	public void setReferredEmployee(String referredEmployee) {
		this.referredEmployee = referredEmployee;
	}

	public String getReferredVendor() {
		return referredVendor;
	}

	public void setReferredVendor(String referredVendor) {
		this.referredVendor = referredVendor;
	}

	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getResumePath() {
		return resumePath;
	}

	public void setResumePath(String resumePath) {
		this.resumePath = resumePath;
	}

	public JobOpenings getJobOpenings() {
		return jobOpenings;
	}

	public void setJobOpenings(JobOpenings jobOpenings) {
		this.jobOpenings = jobOpenings;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
