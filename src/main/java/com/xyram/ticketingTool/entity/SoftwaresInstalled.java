package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "softwares_installed")
public class SoftwaresInstalled {
	
	@Id
	@Column(name = "asset_id")
	private String aId;

	@Column(name = "software_id")
	private String sId;

	@Column(name = "brand")
	private String brand;

	@Column(name = "installed_date")
	private String installeddate;

	@Column(name = "uninstalled_date")
	private String uninstalleddate;

	@Column(name = "status")
	private String status;


}
