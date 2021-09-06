package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "designation")

public class Designation  {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="designatin_id")
	private String Id;

	@Column(name = "designation_name")
	private String designationName;

	
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