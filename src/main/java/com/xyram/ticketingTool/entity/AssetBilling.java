package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.xyram.ticketingTool.admin.model.User;

@Entity
@Table(name="assetBilling")
public class AssetBilling 
{
	@Id
    @Column(name="billing_type")
    public String billingType;
    
    @Column(name="under_warrenty")
    public Boolean underWarrenty;
    
    @Column(name="asset_amount")
    public Integer assetAmount;
    
    @Column(name="GST_Amount")
    public Integer gstAmount;
    
    @Column(name="transaction_date")
    public Date transactionDate;
    
    @ManyToOne(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "VendorID")
    private AssetVendor vendorId;
	
    @Column(name="bill_photo_url")
    public String billPhotoUrl;
    
    @ManyToOne(cascade = {CascadeType.MERGE })
    @JoinColumn(name="Issue_Id")
    public AssetIssues issueId;
    
    @Column(name="return_date")
    public Date returnDate;
    
    @Column(name="amount_paid")
    public Integer amountPaid;

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

	public Integer getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(Integer assetAmount) {
		this.assetAmount = assetAmount;
	}

	public Integer getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Integer gstAmount) {
		this.gstAmount = gstAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public AssetVendor getVendorId() {
		return vendorId;
	}

	public void setVendorId(AssetVendor vendorId) {
		this.vendorId = vendorId;
	}

	public String getBillPhotoUrl() {
		return billPhotoUrl;
	}

	public void setBillPhotoUrl(String billPhotoUrl) {
		this.billPhotoUrl = billPhotoUrl;
	}

	public AssetIssues getIssueId() {
		return issueId;
	}

	public void setIssueId(AssetIssues issueId) {
		this.issueId = issueId;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Integer getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Integer amountPaid) {
		this.amountPaid = amountPaid;
	}

}
	