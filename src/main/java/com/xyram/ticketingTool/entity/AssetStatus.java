package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_status")

public class AssetStatus {
    
	@Id
	@Column(name = "Asset_Id")
	private String assetId;
	
	@Column(name = "asset_available")
	private boolean assetAvailable;
	
	@Column(name = "asset_allotted")
	private boolean assetAllotted;
	
	@Column(name = "asset_repair")
	private boolean assetRepair;
	
	@Column(name = "asset_damaged_broken")
	private boolean assetDamagedBroken;
}
