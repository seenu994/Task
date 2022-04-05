package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "asset")

public class Asset extends AuditModel{

	@Id
	@IdPrefix(value = "ASS")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "asset_id")
	private String assetId;
	
	@Column(name = "vendor_id")
	private String vendorId;
	
	@Column(name = "brand")
	private String brand;

	@Column(name = "purchase_date")
	public Date purchaseDate;

	@Column(name = "model_no")
	private String modelNo;
	
	@Column(name = "serial_no", unique = true)
	private String serialNo;

	@Column(name = "warranty_date")
	private Date warrantyDate;

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
	
	@Column(name = "assigned_to")
	private String assignedTo;
	

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
}
