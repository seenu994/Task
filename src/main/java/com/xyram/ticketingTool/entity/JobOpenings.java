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
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

@Entity
@Table(name = "job_openings")
public class JobOpenings extends AuditModel{
	
	/*
	 * `job_code` varchar(255) NOT NULL DEFAULT '',
  `job_title` varchar(255) NOT NULL DEFAULT '',
  `job_description` varchar(5000) DEFAULT NULL,
  `job_skills` varchar(255) NOT NULL DEFAULT '',
  `total_openings` smallint(255) NOT NULL,
  `filled_positions` smallint(255) DEFAULT NULL,
  `min_exp` smallint(255) NOT NULL,
  `max_exp` smallint(255) NOT NULL,
  `wing_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `salary` int(255) DEFAULT NULL,
	 */
	
	@Id
	@IdPrefix(value = "JOBOP_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="job_id")
	private String Id;
	
	@Column(name="job_code")
	private String jobCode;
	
	@Column(name="job_title")
	private String jobTitle;
	
	@Column(name="job_description")
	private String jobDescription;

	@Column(name="job_skills")
	private String jobSkills;
	
	@Column(name="total_openings")
	private Integer totalOpenings;
	
	@Column(name="filled_positions")
	private Integer filledPositions;
	
	@Column(name="min_exp")
	private Integer minExp;
	
	@Column(name="max_exp")
	private Integer maxExp;
	
	@OneToOne(cascade = {  CascadeType.ALL})
	@JoinColumn(name = "wing_id")
    private CompanyWings wings;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private JobOpeningStatus jobStatus = JobOpeningStatus.VACANT;
	
	@Column(name="salary")
	private Integer jobSalary;
	
	@Column(name="vendor_view")
	private Integer vendor_view = 0;
	
	@Column(name="notify_vendor")
	private boolean notifyVendor;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public Integer getSalary() {
		return jobSalary;
	}

	public void setSalary(Integer salary) {
		this.jobSalary = salary;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobSkills() {
		return jobSkills;
	}

	public void setJobSkills(String jobSkills) {
		this.jobSkills = jobSkills;
	}

	public Integer getTotalOpenings() {
		return totalOpenings;
	}

	public void setTotalOpenings(Integer totalOpenings) {
		this.totalOpenings = totalOpenings;
	}

	public Integer getFilledPositions() {
		return filledPositions;
	}

	public void setFilledPositions(Integer filledPositions) {
		this.filledPositions = filledPositions;
	}

	public Integer getMinExp() {
		return minExp;
	}

	public void setMinExp(Integer minExp) {
		this.minExp = minExp;
	}

	public Integer getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(Integer maxExp) {
		this.maxExp = maxExp;
	}

	public JobOpeningStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobOpeningStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public CompanyWings getWings() {
		return wings;
	}

	public void setWings(CompanyWings wings) {
		this.wings = wings;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(Integer jobSalary) {
		this.jobSalary = jobSalary;
	}

	public Integer getVendor_view() {
		return vendor_view;
	}

	public void setVendor_view(Integer vendor_view) {
		this.vendor_view = vendor_view;
	}

	public boolean isNotifyVendor() {
		return notifyVendor;
	}

	public void setNotifyVendor(boolean notifyVendor) {
		this.notifyVendor = notifyVendor;
	}

	
	
}
