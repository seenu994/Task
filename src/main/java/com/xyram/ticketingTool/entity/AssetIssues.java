package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="asset_Issues")
public class AssetIssues 
{
	@Id
    @Column(name="Issue_Id")
    public String issueId;
   
    @OneToOne(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "asset_Id")
    private Asset aId;
    
    @Column(name="Complaint_Raised_Date")
    public Date complaintRaisedDate;
    
    @Column(name="Description")
    public String description;
    
    @Column(name="Solution")
    public String solution;
    
    @Column(name="Status")
    public String status;
    
    @OneToOne(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "Vendor_Id")
    private AssetVendor vendorId;
    
    @Column(name="Resolved_Date")
    public Date resolvedDate;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public Asset getaId() {
		return aId;
	}

	public void setaId(Asset aId) {
		this.aId = aId;
	}

	public Date getComplaintRaisedDate() {
		return complaintRaisedDate;
	}

	public void setComplaintRaisedDate(Date complaintRaisedDate) {
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
		this.solution = solution;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AssetVendor getVendorId() {
		return vendorId;
	}

	public void setVendorId(AssetVendor vendorId) {
		this.vendorId = vendorId;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public void setassetIssues(String getaId) {
		// TODO Auto-generated method stub
		
	}

}
