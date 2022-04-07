package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    public double assetAmount;
    
    @Column(name="gst_amount")
    public Integer gstAmount;
    
    @Column(name="transaction_date")
    public Date transactionDate;
    
	@Column(name = "vendor_id")
    public AssetVendor assetVendor;
	
    @Column(name="bill_photo_url")
    public String billPhotoUrl;
    
    @Column(name="issue_id")
    public AssetIssues assetIssueId;
    
    @Column(name="return_date")
    public Date returnDate;
    
    @Column(name="amount_paid")
    public boolean amountPaid;
    
    
	@Column(name = "asset_id")
    private  Asset assetId;


	//public AssetBilling getBillingType;


	//public AssetBilling getBillPhotoUrl;

    
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


	public AssetVendor getAssetVendor() {
		return assetVendor;
	}


	public void setAssetVendor(AssetVendor assetVendor) {
		this.assetVendor = assetVendor;
	}


	public String getBillPhotoUrl() {
		return billPhotoUrl;
	}


	public void setBillPhotoUrl(String billPhotoUrl) {
		this.billPhotoUrl = billPhotoUrl;
	}


	public AssetIssues getAssetIssues() {
		return assetIssues;
	}


	public void setAssetIssues(AssetIssues assetIssues) {
		this.assetIssues = assetIssues;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}


	public boolean getAmountPaid() {
		return amountPaid;
	}


	public void setAmountPaid(boolean amountPaid) {
		this.amountPaid = amountPaid;
	}


	


	public Object getVendorId() {
		AssetVendor assetVendor = new AssetVendor();
		return assetVendor;
	}


	public Object getPurchaseDate() {
		// TODO Auto-generated method stub
		return null;
	}


	


	public String getAssetBillId() {
		return assetBillId;
	}


	public void setAssetBillId(String assetBillId) {
		this.assetBillId = assetBillId;
	}


	public Asset getAsset() {
		return asset;
	}


	public void setAsset(Asset asset) {
		this.asset = asset;
	}


	public AssetBilling setAmountPaid(AssetBilling assetBilling) {
		// TODO Auto-generated method stub
		return assetBilling;
	}



	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	

}
	