package com.xyram.ticketingTool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "asset")
public class Asset implements Serializable {

	@Id
	@Column(name = "asset_id")
	private String aId;

	@Column(name = "vendor_id")
	private String vId;

	@Column(name = "brand")
	private String brand;

	@Column(name = "purchase_date")
	private String purchaseDate;

	@Column(name = "model")
	private String model;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "waranty_date")
	private String warantyDate;

	@Column(name = "ram")
	private String ram;
	
	@Column(name = "bag_available")
	private boolean bagAvailable;

	@Column(name = "powercord_available")
	private boolean powercordAvailable;

	@Column(name = "mouse_available")
	private boolean mouseAvailable;

	@Column(name = "asset_photo_url")
	private String assetPhotoUrl;
	
	@Column(name = "asset_status")
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




	public String getPurchaseDate() {
		return purchaseDate;
	}




	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}




	public String getModel() {
		return model;
	}




	public void setModel(String model) {
		this.model = model;
	}




	public String getSerialNo() {
		return serialNo;
	}




	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}




	public String getWarantyDate() {
		return warantyDate;
	}




	public void setWarantyDate(String warantyDate) {
		this.warantyDate = warantyDate;
	}




	public String getRam() {
		return ram;
	}




	public void setRam(String ram) {
		this.ram = ram;
	}




	public boolean getBagAvailable() {
		return bagAvailable;
	}




	public void setBagAvailable(boolean bagAvailable) {
		this.bagAvailable = bagAvailable;
	}




	public boolean getPowercordAvailable() {
		return powercordAvailable;
	}




	public void setPowercordAvailable(boolean powercordAvailable) {
		this.powercordAvailable = powercordAvailable;
	}




	public boolean getMouseAvailable() {
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
