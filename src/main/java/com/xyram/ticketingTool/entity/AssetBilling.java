package com.xyram.ticketingTool.entity;

import java.util.Date;

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
@Table(name="asset_billing")
public class AssetBilling extends AuditModel
{
	
	
	@Id
	@IdPrefix(value = "AB")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
    @Column(name="asset_bill_id")
    public String assetBillId;

	
    @Column(name="billing_type")
    public String billingType;
    
    @Column(name="under_warrenty")
    public Boolean underWarrenty;
    
    @Column(name="asset_amount")
    public Double assetAmount;
    
    @Column(name="gst_amount")
    public Double gstAmount;
    
    @Column(name="transaction_date")
    public Date transactionDate;
    
	@Column(name = "vendor_id")
    public String vendorId;
	
    @Column(name="bill_photo_url" , nullable = true)
    public String billPhotoUrl;
    
    @Column(name="issue_id")
    public String assetIssueId;
    
    @Column(name="return_date")
    public Date returnDate;
    
    @Column(name="amount_paid")
    public boolean amountPaid;
    
    @Column(name = "asset_id")
    private  String assetId;

	public String getAssetBillId() {
		return assetBillId;
	}

	public void setAssetBillId(String assetBillId) {
		this.assetBillId = assetBillId;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public Boolean getUnderWarrenty() {
		return underWarrenty;
	}

	public void setUnderWarrenty(Boolean underWarrenty) {
		this.underWarrenty = underWarrenty;
	}

	public Double getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(Double assetAmount) {
		this.assetAmount = assetAmount;
	}

	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getBillPhotoUrl() {
		return billPhotoUrl;
	}

	public void setBillPhotoUrl(String billPhotoUrl) {
		this.billPhotoUrl = billPhotoUrl;
	}

	public String getAssetIssueId() {
		return assetIssueId;
	}

	public void setAssetIssueId(String assetIssueId) {
		this.assetIssueId = assetIssueId;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	
	public boolean isAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(boolean amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	

	

	



    
	


	


	
	

	
	
	

}
	