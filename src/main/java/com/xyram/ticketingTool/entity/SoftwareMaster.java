//package com.xyram.ticketingTool.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.xyram.ticketingTool.enumType.SoftwareEnum;
//
//@Entity
//@Table(name = "Software_master")
//
//public class SoftwareMaster {
//	@Id
//	@Column(name = "software_Id")
//	private String softwareId;
//
//	public String getSoftwareId() {
//		return softwareId;
//	}
//
//	public void setSoftwareId(String softwareId) {
//		this.softwareId = softwareId;
//	}
//
//	public String getSoftwareName() {
//		return softwareName;
//	}
//
//	public void setSoftwareName(String softwareName) {
//		this.softwareName = softwareName;
//	}
//
//	public SoftwareEnum getAssetVendorStatus() {
//		return assetVendorStatus;
//	}
//
//	public void setAssetVendorStatus(SoftwareEnum assetVendorStatus) {
//		this.assetVendorStatus = assetVendorStatus;
//	}
//
//	@Column(name = "software_name")
//	private String softwareName;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "softwareStatus")
//	private SoftwareEnum assetVendorStatus = SoftwareEnum.ACTIVE;
//
//}