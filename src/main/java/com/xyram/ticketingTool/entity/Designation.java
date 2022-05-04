package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.enumType.DesignationEnum;
import com.xyram.ticketingTool.enumType.SoftwareEnum;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "designation")

public class Designation  {

	@Id
	@IdPrefix(value = "DES_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="designation_id")
	private String Id;

	@Column(name = "designation_name")
	private String designationName;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "designationStatus")
	private DesignationEnum designationStatus = DesignationEnum.ACTIVE;


	
	public DesignationEnum getDesignationStatus() {
		return designationStatus;
	}

	public void setDesignationStatus(DesignationEnum designationStatus) {
		this.designationStatus = designationStatus;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	
	
}