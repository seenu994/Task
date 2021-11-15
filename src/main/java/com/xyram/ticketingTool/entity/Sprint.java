package com.xyram.ticketingTool.entity;

import java.util.Date;

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
@Table(name = "sprint")
public class Sprint extends AuditModel {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size(max = 8)
	@Column(name = "sprint_id")
	private String Id;

	@Column(name = "sprint_start_date")
	private Date sprintStartDate;
	
	@Column(name = "sprint_goals")
	private Date sprintGoals;

	@Column(name = "sprint_name")
	private Date sprintName;

	@Column(name = "sprint_end_date")
	private Date sprintEndDate;

	@Column(name = "sprint_status")
	private String sprintStatus;
	
	
	@Column(name = "sprint_extended_count")
	private Integer sprintExtendedCount;
	
	
	@Column(name = "sprint_extended_by")
	private String sprintExtendedBy;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Projects project;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Date getSprintStartDate() {
		return sprintStartDate;
	}

	public void setSprintStartDate(Date sprintStartDate) {
		this.sprintStartDate = sprintStartDate;
	}

	public Date getSprintEndDate() {
		return sprintEndDate;
	}

	public void setSprintEndDate(Date sprintEndDate) {
		this.sprintEndDate = sprintEndDate;
	}

	public String getSprintStatus() {
		return sprintStatus;
	}

	public void setSprintStatus(String sprintStatus) {
		this.sprintStatus = sprintStatus;
	}

	public Integer getSprintExtendedCount() {
		return sprintExtendedCount;
	}

	public void setSprintExtendedCount(Integer sprintExtendedCount) {
		this.sprintExtendedCount = sprintExtendedCount;
	}

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public Date getSprintName() {
		return sprintName;
	}
	
	public void setSprintName(Date sprintName) {
		this.sprintName = sprintName;
	}
	
	public Date getSprintGoals() {
		return sprintGoals;
	}

	public void setSprintGoals(Date sprintGoals) {
		this.sprintGoals = sprintGoals;
	}
}
