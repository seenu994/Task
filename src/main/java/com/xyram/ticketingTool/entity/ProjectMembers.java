package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;

@Entity
@Table(name = "project_members")

public class ProjectMembers extends AuditModel {

	@Id
	@Column(name = "project_member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "projectmemberstatus")
	private ProjectMembersStatus status = ProjectMembersStatus.INACTIVE;
//@JsonIgnore
//	@OneToOne(cascade = { CascadeType.MERGE })
//	@JoinColumn(name = "employee_id")
//	private  Employee employee;
//@JsonIgnore
//	@OneToOne(cascade = { CascadeType.MERGE })
//	@JoinColumn(name = "project_id")
//	private  Projects project;

	@JoinColumn(name = "employee_id")
	private  Integer employeeId;
	
	@JoinColumn(name = "project_id")
	private  Integer projectId;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public ProjectMembersStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectMembersStatus status) {
		this.status = status;
	}

//	public Employee getEmployee() {
//		return employee;
//	}
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}
//
//	public Projects getProject() {
//		return project;
//	}
//
//	public void setProject(Projects project) {
//		this.project = project;
//	}


}
	
	
	
	
