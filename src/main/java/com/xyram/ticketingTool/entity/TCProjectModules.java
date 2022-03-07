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
@Table(name = "testcase_prjmodules")
public class TCProjectModules extends AuditModel {
	@Id
	@IdPrefix(value = "TCPM_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="tcpm_id")
	private String tcpmId;
	
	@Column(name = "module_desc")
	private String moduleDesc;

	@Column(name = "project_id")
	private String projectId;


	public String getTcpmId() {
		return tcpmId;
	}


	public void setTcpmId(String tcpmId) {
		this.tcpmId = tcpmId;
	}
	
	
	public String getModuleDesc() {
		return moduleDesc;
	}


	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
