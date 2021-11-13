package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.enumType.ModuleStatus;

@Entity
@Table(name = "module")
public class Module {
    
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size(max = 5)
	@Column(name = "id")
	private String id;

	@Column(name = "module_name")
	private String moduleName;

	
	@Column(name = "module_id",unique = true)
	private Integer moduleId;
	
	

	@Enumerated(EnumType.STRING)
	@Column(name = "module_status")
	private ModuleStatus moduleStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public ModuleStatus getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(ModuleStatus moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
	
	

}
