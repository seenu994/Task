package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="assetIssues")
public class AssetIssues 
{
	@Id
    @Column(name="IssueId")
   public String issueId;
   
	@Column(name = "assetId")
    private String assetId;
    
    @Column(name="ComplaintRaisedDate")
    public String complaintRaisedDate;
    
    @Column(name="Description")
    public String description;
    
    @Column(name="Solution")
    public String Solution;
    
    @Column(name="Status")
    public String Status;
    
	@Column(name = "VendorId")
    private String vendorId;
    
    @Column(name="ResolvedDate")
    public String resolvedDate;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getComplaintRaisedDate() {
		return complaintRaisedDate;
	}

	public void setComplaintRaisedDate(String complaintRaisedDate) {
		this.complaintRaisedDate = complaintRaisedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSolution() {
		return Solution;
	}

	public void setSolution(String solution) {
		Solution = solution;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	

	public String getVendorId() {
		return vendorId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	
    
}
