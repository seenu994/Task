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
@Table(name = "new_joiners")

public class NewJoiner extends AuditModel{
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="joiner_id")
	private String Id;
	
	
	/*
	 * EATE TABLE `new_joiners` (
  `joiner_id` varchar(255) NOT NULL,
  `application_id` varchar(255) NOT NULL,
  `joining_date` datetime DEFAULT NULL,
  `salary_offered` varchar(255) DEFAULT NULL,
	 */
	
	@Column(name="round_no")
	private Integer roundNo;
	
	@Column(name="joining_date")
	private Date joiningDate;
	
	@Column(name="salary_offered")
	private Integer salaryOffered;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "application_id")
    private JobApplication jobApplication;
	
	@Column(name="status")
	private String status;

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

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Integer getSalaryOffered() {
		return salaryOffered;
	}

	public void setSalaryOffered(Integer salaryOffered) {
		this.salaryOffered = salaryOffered;
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
