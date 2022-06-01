package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xyram.ticketingTool.baseData.model.AuditModel;

@Entity
@Table(name = "emp_default_permissions")
public class EmpDefaultPermissions extends AuditModel {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
