package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.AssetEmployeeStatus;
import com.xyram.ticketingTool.enumType.ModuleStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "asset_employee")
public class AssetEmployee extends AuditModel {
	
	@Id
	@IdPrefix(value = "AE")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "asset_emp_id")
	private String assetEmpId;
	
	@Column(name = "asset_id")
	private String assetId;
	
	@Column(name = "employee_id")
	private String empId;
	
	@Column(name = "issued_date")
	private Date issuedDate;
	
	@Column(name = "bag_issued")
	private  boolean bagIssued;
	
	@Column(name = "powercord_issued")
	private boolean powercordIssued;
	
	@Column(name = "mouse_issued")
	private boolean mouseIssued;
	
	@Column(name = "return_date")
	private Date returnDate;
	
	@Column(name = "return_reason", length = 500)
	private String returnReason;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "return_type")
	private AssetEmployeeStatus returnType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "asset_employee_status")
	private ModuleStatus assetEmployeeStatus = ModuleStatus.ACTIVE;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public boolean isBagIssued() {
		return bagIssued;
	}

	public void setBagIssued(boolean bagIssued) {
		this.bagIssued = bagIssued;
	}

	public boolean isPowercordIssued() {
		return powercordIssued;
	}

	public void setPowercordIssued(boolean powercordIssued) {
		this.powercordIssued = powercordIssued;
	}

	public boolean isMouseIssued() {
		return mouseIssued;
	}

	public void setMouseIssued(boolean mouseIssued) {
		this.mouseIssued = mouseIssued;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public AssetEmployeeStatus getReturnType() {
		return returnType;
	}

	public void setReturnType(AssetEmployeeStatus returnType) {
		this.returnType = returnType;
	}

	public ModuleStatus getAssetEmployeeStatus() {
		return assetEmployeeStatus;
	}

	public void setAssetEmployeeStatus(ModuleStatus assetEmployeeStatus) {
		this.assetEmployeeStatus = assetEmployeeStatus;
	}

	

}
