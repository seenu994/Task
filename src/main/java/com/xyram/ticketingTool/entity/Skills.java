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
@Table(name = "skills")
public class Skills extends AuditModel {
	
	@Id
	@IdPrefix(value = "SK")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "skill_id")
	private String skillId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@Column(name = "skill_status")
	private boolean skillStatus;

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public boolean isSkillStatus() {
		return skillStatus;
	}

	public void setSkillStatus(boolean skillStatus) {
		this.skillStatus = skillStatus;
	}
	
	
	
	

}
