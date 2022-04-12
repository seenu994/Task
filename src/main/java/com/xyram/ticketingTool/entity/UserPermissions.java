package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name="user_permisssions")
public class UserPermissions {

	@Id
	@IdPrefix(value = "USERPER_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="permission_id")
	private String Id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="emp_mod_permission")
	private int empModPermission;
	
	@Column(name="project_mod_permission")
	private int projectModPermission;
	
	@Column(name="job_opening_mod_permission")
	private int jobOpeningModPermission;
	
	@Column(name="ticket_mod_permission")
	private int ticketModPermission;
	
	@Column(name="job_app_mod_permission")
	private int jobAppModPermission;
	
	@Column(name="job_interviews_mod_permission")
	private int jobInterviewsModPermission;
	
	@Column(name="job_vendors_mod_permission")
	private int jobVendorsModPermission;
	
	@Column(name="job_offer_mod_permission")
	private int jobOfferModPermission;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEmpModPermission() {
		return empModPermission;
	}

	public void setEmpModPermission(int empModPermission) {
		this.empModPermission = empModPermission;
	}

	public int getProjectModPermission() {
		return projectModPermission;
	}

	public void setProjectModPermission(int projectModPermission) {
		this.projectModPermission = projectModPermission;
	}

	public int getJobOpeningModPermission() {
		return jobOpeningModPermission;
	}

	public void setJobOpeningModPermission(int jobOpeningModPermission) {
		this.jobOpeningModPermission = jobOpeningModPermission;
	}

	public int getTicketModPermission() {
		return ticketModPermission;
	}

	public void setTicketModPermission(int ticketModPermission) {
		this.ticketModPermission = ticketModPermission;
	}

	public int getJobAppModPermission() {
		return jobAppModPermission;
	}

	public void setJobAppModPermission(int jobAppModPermission) {
		this.jobAppModPermission = jobAppModPermission;
	}

	public int getJobInterviewsModPermission() {
		return jobInterviewsModPermission;
	}

	public void setJobInterviewsModPermission(int jobInterviewsModPermission) {
		this.jobInterviewsModPermission = jobInterviewsModPermission;
	}

	public int getJobVendorsModPermission() {
		return jobVendorsModPermission;
	}

	public void setJobVendorsModPermission(int jobVendorsModPermission) {
		this.jobVendorsModPermission = jobVendorsModPermission;
	}

	public int getJobOfferModPermission() {
		return jobOfferModPermission;
	}

	public void setJobOfferModPermission(int jobOfferModPermission) {
		this.jobOfferModPermission = jobOfferModPermission;
	}

	
	
	
	
}
