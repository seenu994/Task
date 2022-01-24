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
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "sprint")
public class Sprint extends AuditModel {
	@Id
	@IdPrefix(value = "SPR_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "sprint_id")
	private String id;

	@Column(name = "sprint_start_date")
	private Date sprintStartDate;
	
	@Column(name = "sprint_goals")
	private Date sprintGoals;

	@Column(name = "sprint_name")
	private String sprintName;

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
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	
	public Date getSprintGoals() {
		return sprintGoals;
	}

	public void setSprintGoals(Date sprintGoals) {
		this.sprintGoals = sprintGoals;
	}
	

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public String getSprintExtendedBy() {
		return sprintExtendedBy;
	}

	public void setSprintExtendedBy(String sprintExtendedBy) {
		this.sprintExtendedBy = sprintExtendedBy;
	}
}
