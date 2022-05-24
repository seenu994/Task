package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "employee_permission")
public class EmployeePermission extends AuditModel{
	
	@Id
	@IdPrefix(value = "PER_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String Id;
	
	@Column(name = "job_admin", nullable = false)
	private Boolean jobAdmin;
	
	@Column(name = "job_opening_add", nullable = false) //JOB-OPENINGS-ADD
	private Boolean jobOpeningAdd;
	
	@Column(name = "job_openings_view", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobOpeningsView;
	
//	JOB-APP-UPLOAD
//	JOB-APP-VIEW-ALL
//
//	JOB-INT-VIEW-ALL
//
//	JOB-INT-VIEW-ALL
//
//
//	HR-CAL-SCHEDULE-ADD
//	HR-CAL-VIEW-ALL
//
//	ANN-VIEW-ALL
//	ANN-EDIT-ALL
//	ANN-ADD
//
//	ART-VIEW-ALL
//	ART-EDIT-ALL
//	ART-ADD
//
//	TKT-ADMIN
//	TKT-ADD
//	TKT-ASSIGN
//
//	PRJ-VIEW-ALL
//	PRJ-EDIT-ALL
//	PRJ-ADD
//
//	TIMESHEET-ADMIN
//	TIMESHEET-ADD
//
//	ASST-ADMIN
//	ASST-ADD
//	ASST-ISS-ADD
//	ASST-BILL-ADD
//
//	JV-VIEW-ALL
//	AV-VIEW-ALL
//
//	ISSTRACK-ADMIN
//	ISSTRACK-VIEW-ALL
	
	@Column(name = "emp_admin", nullable = false)
	private Boolean empAdmin;
	
	public String getEmpPermissionId() {
		return Id;
	}

	public void setEmpPermissionId(String empPermissionId) {
		this.Id = empPermissionId;
	}

	public Boolean getEmpAdmin() {
		return empAdmin;
	}

	public void setEmpAdmin(Boolean empAdmin) {
		this.empAdmin = empAdmin;
	}

	public Boolean getJobAdmin() {
		return jobAdmin;
	}

	public void setJobAdmin(Boolean jobAdmin) {
		this.jobAdmin = jobAdmin;
	}

	public Boolean getJobOpeningAdd() {
		return jobOpeningAdd;
	}

	public void setJobOpeningAdd(Boolean jobOpeningAdd) {
		this.jobOpeningAdd = jobOpeningAdd;
	}

	public Boolean getJobOpeningsView() {
		return jobOpeningsView;
	}

	public void setJobOpeningsView(Boolean jobOpeningsView) {
		this.jobOpeningsView = jobOpeningsView;
	}

	
	
//	@Column(name = "job-app-upload", nullable = false)//JOB-APP-UPLOAD
//	private Boolean empAdmin;
	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
//	
//	@Column(name = "emp-admin", nullable = false)
//	private Boolean empAdmin;
	
}
