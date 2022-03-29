package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.xyram.ticketingTool.admin.model.User;


@Entity
@Table(name = "asset")

public class Asset {

	@Id
	@Column(name = "Asset_Id")
	private String aId;
	

	@Column(name = "Vendor_Id")
	private String vId;
	
//	@OneToOne(cascade = { CascadeType.MERGE })
//	@JoinColumn(name = "VendorId")
//	private AssetVendor vendorId;

	@Column(name = "Brand")
	private String brand;

	@Column(name = "Purchase_Date")
	public Date purchaseDate;

	@Column(name = "Model_No")
	private String modelNo;
	
    
	@Column(name = "Serial_No", unique = true)
	private String serialNo;

	@Column(name = "Warranty_Date")
	private Date warrantyDate;

	@Column(name = "Ram")
	private String ram;
	
	@Column(name = "Bag_Available")
	private boolean bagAvailable;

	@Column(name = "Powercord_Available")
	private boolean powercordAvailable;

	@Column(name = "Mouse_Available")
	private boolean mouseAvailable;

	@Column(name = "Asset_Photo_Url")
	private String assetPhotoUrl;
	
	@Column(name = "Asset_Status")
	private String assetStatus;
	
	

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}
	

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public boolean isBagAvailable() {
		return bagAvailable;
	}

	public void setBagAvailable(boolean bagAvailable) {
		this.bagAvailable = bagAvailable;
	}

	public boolean isPowercordAvailable() {
		return powercordAvailable;
	}

	public void setPowercordAvailable(boolean powercordAvailable) {
		this.powercordAvailable = powercordAvailable;
	}

	public boolean isMouseAvailable() {
		return mouseAvailable;
	}

	public void setMouseAvailable(boolean mouseAvailable) {
		this.mouseAvailable = mouseAvailable;
	}

	public String getAssetPhotoUrl() {
		return assetPhotoUrl;
	}

	public void setAssetPhotoUrl(String assetPhotoUrl) {
		this.assetPhotoUrl = assetPhotoUrl;
	}

	public String getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	

	
}
