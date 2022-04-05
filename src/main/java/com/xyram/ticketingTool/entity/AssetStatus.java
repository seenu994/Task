package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asset_status")

public class AssetStatus {
    
	@Id
	@Column(name = "asset_status_id")
	private String assetStatusId;
	
	@Column(name = "status_name")
	private String statusName;

	public String getAssetStatusId() {
		return assetStatusId;
	}

	public void setAssetStatusId(String assetStatusId) {
		this.assetStatusId = assetStatusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	
	
	
	
	
}
