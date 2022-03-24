package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name="Asset_Vendor")

public class AssetVendor  {
	
	
@Id
@IdPrefix(value = "VE")
@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
       @Column(name="VendorID")
       private String vendorID;
	

		@Column(name="Address")
		private String address;
		
		@Column(name="Vendorname")
		private String vendorName;	
	
		
		@Column(name="Mobileno")
		private String mobileNo;
		
		@Column(name="Email")
		private String email;
		
		
		@Column(name="City")
		private String City;
		
		@Column(name="Country")
		private String Country;
		
		@Column(name="Status")
		private String Status;

		public String getVendorID() {
			return vendorID;
		}

		public void setVendorID(String vendorID) {
			this.vendorID = vendorID;
		}

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
			return City;
		}

		public void setCity(String city) {
			City = city;
		}

		public String getCountry() {
			return Country;
		}

		public void setCountry(String country) {
			Country = country;
		}

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
		}
		
		
}