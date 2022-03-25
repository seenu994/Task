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
   

    @OneToOne(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "asset_Id")
    private Asset aId;
    
    @Column(name="ComplaintRaisedDate")
    public String complaintRaisedDate;
    
    @Column(name="Description")
    public String description;
    
    @Column(name="solution")
    public String solution;
    
    @Column(name="status")
    public String status;
    
    @OneToOne(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "VendorId")
    private AssetVendor vendorId;
    
    @Column(name="ResolvedDate")
    public String resolvedDate;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public Asset getAssetId() {
		return aId;
	}

	public void setVendorId(AssetVendor vendorId) {
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
		return solution;
	}

	public void setSolution(String solution) {
		solution = solution;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		status = status;
	}

	

	public AssetVendor getVendorId() {
		return vendorId;
	}

	public void setAssetId(String assetId) {
		this.aId = aId;
	}

	public String getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public static void setissueId(String issueId2) {
		// TODO Auto-generated method stub
		
	}

	
    
}
