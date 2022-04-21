package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.SoftwareEnum;

@Entity
@Table(name = "Software_master")

public class SoftwareMaster extends AuditModel{ 
	@Id
	@Column(name = "software_Id" ,unique =true)
	private String softwareId;

	@Column(name = "software_name")
	private String softwareName;

	@Enumerated(EnumType.STRING)
	@Column(name = "softwareStatus")
	private SoftwareEnum softwareMasterStatus = SoftwareEnum.ACTIVE;

	public String getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public SoftwareEnum getSoftwareMasterStatus() {
		return softwareMasterStatus;
	}

	public void setSoftwareMasterStatus(SoftwareEnum softwareMasterStatus) {
		this.softwareMasterStatus = softwareMasterStatus;
	}

}