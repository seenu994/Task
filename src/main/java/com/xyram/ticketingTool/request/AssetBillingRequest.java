package com.xyram.ticketingTool.request;

//import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class AssetBillingRequest {

	private String assetBilling;
	
	private MultipartFile[] documentFile;

	public String getAssetBilling() {
		return assetBilling;
	}

	public void setAssetBilling(String assetBilling) {
		this.assetBilling = assetBilling;
	}

	public MultipartFile[] getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(MultipartFile[] documentFile) {
		this.documentFile = documentFile;
	}

	/*
	private String assetId;
    private Double assetAmount;
    private String billingType;
    private Double gstAmount;
    private Date transactionDate;
    private String vendorId;
    private boolean amountPaid;
    private String assetIssueId;
    private String returnDate;
    private String underWarrenty;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public Double getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(Double assetAmount) {
		this.assetAmount = assetAmount;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
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

	public boolean isAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(boolean amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getAssetIssuesId() {
		return assetIssueId;
	}

	public void setAssetIssuesId(String assetIssuesId) {
		this.assetIssueId = assetIssuesId;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getUnderWarrenty() {
		return underWarrenty;
	}

	public void setUnderWarrenty(String underWarrenty) {
		this.underWarrenty = underWarrenty;
	}

	public void setAssetIssueId(Object object) {
		// TODO Auto-generated method stub
		
	}
	*/
	/*"assetBillId" :"ABhg71llo984",
    "assetAmount" : 60000,
    "billingType" : "purchase",
    "gstAmount" : 5000,
     "transactionDate":"2022-08-07",
     "vendorId":"PRO_rvbNbwm511" ,
     "amountPaid":true*/
}
