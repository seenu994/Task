package com.xyram.ticketingTool.entity;

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
    @Column(name="Billing_Type")
    public String billingType;
    
    @Column(name="UnderWarrenty")
    public String underWarrent;
    
    @Column(name="AssetAmount")
    public String assetAmount;
    
    @Column(name="GSTAmount")
    public String gstAmount;
    
    @Column(name="TransactionDate")
    public String transactionDate;
    
    
	@Column(name = "vendorId")
    private String vendorId;
    
    @Column(name="BillPhotoUrl")
    public String billPhotoUrl;
    
    @OneToOne(cascade = {CascadeType.MERGE })
    @JoinColumn(name="IssueId")
    public AssetIssues issueId;
    
    @Column(name="ReturnDate")
    public String returnDate;
    
    @Column(name="AmountPaid")
    public String amountPaid;

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getUnderWarrent() {
		return underWarrent;
	}

	public void setUnderWarrent(String underWarrent) {
		this.underWarrent = underWarrent;
	}

	public String getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(String assetAmount) {
		this.assetAmount = assetAmount;
	}

	public String getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(String gstAmount) {
		this.gstAmount = gstAmount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
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

	public AssetIssues getIssueId() {
		return issueId;
	}

	public void setIssueId(AssetIssues issueId) {
		this.issueId = issueId;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

    
	
   
    
}
