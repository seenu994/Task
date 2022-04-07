package com.xyram.ticketingTool.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "asset_vendor")
public class AssetVendor  extends AuditModel{

	// private static final String Status = null;

	@Id
	@IdPrefix(value = "PRO_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "vendorId")
	private String vendorId;

	@Column(name = "address")
	private String address;

	@Column(name = "vendor_name")
	private String vendorName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public AssetVendorEnum getAssetVendorStatus() {
		return assetVendorStatus;
	}

	public void setAssetVendorStatus(AssetVendorEnum assetVendorStatus) {
		this.assetVendorStatus = assetVendorStatus;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "assetVendorStatus")
	private AssetVendorEnum assetVendorStatus = AssetVendorEnum.ACTIVE;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	

	

}

/*
 * public void setStatus(AssetVendorEnum status) { this.status = status; }
 */
