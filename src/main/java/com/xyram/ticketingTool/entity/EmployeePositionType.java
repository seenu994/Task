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
@Table(name="employee_position_type")
public class EmployeePositionType {

	
	@Id
	@IdPrefix(value = "EPT")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="id")
	private String id;
	
	@Column(name="position_type")
	private String positionType;
	
	@Column(name="position_status")
	private String positionStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	
	
	
	
}
