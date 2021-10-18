package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import com.xyram.ticketingTool.baseData.model.AuditModel;


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
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
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
	
	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "wing_id")
    private CompanyWings wings;
	
	@Column(name="status")
	private String jobStatus;
	
	@Column(name="salary")
	private Integer jobSalary;

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

	public CompanyWings getWings() {
		return wings;
	}

	public void setWings(CompanyWings wings) {
		this.wings = wings;
	}

	public String getStatus() {
		return jobStatus;
	}

	public void setStatus(String status) {
		this.jobStatus = status;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

}
