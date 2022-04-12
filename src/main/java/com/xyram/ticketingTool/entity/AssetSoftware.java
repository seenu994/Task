package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.AssetSoftwareStatus;

@Entity
@Table(name = "asset_software")
public class AssetSoftware extends AuditModel {
	
	@Id
	@Column(name = "asset_id1")
	private String assetId1;

	@Column(name = "software_id")
	private String softwareId;

	@Column(name = "install_date")
	private Date installDate;

	@Column(name = "uninstall_date")
	private Date uninstallDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "asset_software_status")
	private AssetSoftwareStatus assetSoftwareStatus = AssetSoftwareStatus.ACTIVE;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "action")
	private AssetSoftwareStatus action = AssetSoftwareStatus.UNINSTALL;

	public String getAssetId1() {
		return assetId1;
	}

	public void setAssetId1(String assetId1) {
		this.assetId1 = assetId1;
	}

	public String getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public Date getUninstallDate() {
		return uninstallDate;
	}

	public void setUninstallDate(Date uninstallDate) {
		this.uninstallDate = uninstallDate;
	}

	public AssetSoftwareStatus getAssetSoftwareStatus() {
		return assetSoftwareStatus;
	}

	public void setAssetSoftwareStatus(AssetSoftwareStatus assetSoftwareStatus) {
		this.assetSoftwareStatus = assetSoftwareStatus;
	}

	public AssetSoftwareStatus getAction() {
		return action;
	}

	public void setAction(AssetSoftwareStatus action) {
		this.action = action;
	}

	
}
