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
@Table(name = "version_table")
public class Version extends AuditModel{
	
	@Id
	@IdPrefix(value = "VER")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name="versionname")
	private String versionName;
	
	@Column(name="project_id")
	private String projectId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	
}
