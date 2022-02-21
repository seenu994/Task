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
import com.xyram.ticketingTool.enumType.TimesheetStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "timesheet")
public class TimeSheet extends AuditModel{
	
	@Id
	@IdPrefix(value = "TMS_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "timesheet_id")
	private String timeSheetId;
	
	@Column(name = "timesheet_date")
	private Date timeSheetDate;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "project_id")
	private String projectId;
	
	@Column(name = "task_name")
	private String taskName;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "task_desc",length = 1000)
	private String taskDescription;
	
	@Column(name = "approver_id")
	private String approverId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private TimesheetStatus status = TimesheetStatus.PENDING;

	@Column(name = "hours_spent")
	private Float hoursSpent;
	
	@Column(name = "task_comments",length = 1000)
	private String taskComments;
	
	@Column(name = "reason",length = 1000)
	private String reason;
	
	public String getTimeSheetId() {
		return timeSheetId;
	}

	public void setTimeSheetId(String timeSheetId) {
		this.timeSheetId = timeSheetId;
	}

	public Date getTimeSheetDate() {
		return timeSheetDate;
	}

	public void setTimeSheetDate(Date timeSheetDate) {
		this.timeSheetDate = timeSheetDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Float getHoursSpent() {
		return hoursSpent;
	}

	public void setHoursSpent(Float hoursSpent) {
		this.hoursSpent = hoursSpent;
	}

	public String getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public TimesheetStatus getStatus() {
		return status;
	}

	public void setStatus(TimesheetStatus status) {
		this.status = status;
	}
	
	
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	

}
