package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "asset")
public class Asset {

	@Id
	@Column(name = "asset_id")
	private String aId;

	@Column(name = "vendor_id")
	private String vId;

	@Column(name = "brand")
	private String brand;

	@Column(name = "purchase_date")
	private String purchasedate;

	@Column(name = "model")
	private String model;

	@Column(name = "serial_no")
	private String serialno;

	@Column(name = "waranty_date")
	private String warantydate;

	@Column(name = "RAM")
	private String ram;
	
	@Column(name = "bag_available")
	private String bagavailable;

	@Column(name = "powercord_available")
	private String powercordavailable;

	@Column(name = "mouse_available")
	private String mouseavailable;

	@Column(name = "asset_photo_URL")
	private String assetphotourl;
	
	@Column(name = "asset_status")
	private String assetstatus;

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

	public String getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getWarantydate() {
		return warantydate;
	}

	public void setWarantydate(String warantydate) {
		this.warantydate = warantydate;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getBagavailable() {
		return bagavailable;
	}

	public void setBagavailable(String bagavailable) {
		this.bagavailable = bagavailable;
	}

	public String getPowercordavailable() {
		return powercordavailable;
	}

	public void setPowercordavailable(String powercordavailable) {
		this.powercordavailable = powercordavailable;
	}

	public String getMouseavailable() {
		return mouseavailable;
	}

	public void setMouseavailable(String mouseavailable) {
		this.mouseavailable = mouseavailable;
	}

	public String getAssetphotourl() {
		return assetphotourl;
	}

	public void setAssetphotourl(String assetphotourl) {
		this.assetphotourl = assetphotourl;
	}

	public String getAssetstatus() {
		return assetstatus;
	}

	public void setAssetstatus(String assetstatus) {
		this.assetstatus = assetstatus;
	}

	
}
