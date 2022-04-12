package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "tech_skils")
public class TechSkills extends AuditModel{
 /*
  * `skill_id` varchar(255) NOT NULL,
  `skill_name` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  */
	
	@Id
	@IdPrefix(value = "SKI_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="skill_id")
	private String Id;
	
	@Column(name="skill_name")
	private String skillName;
	
	@Column(name="status")
	private String status;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
