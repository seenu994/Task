package com.xyram.ticketingTool.Repository;

import java.util.List;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.AssetIssues;

@Repository
@Transactional
public interface AssetIssuesRepository<getAssetIssues> extends JpaRepository<AssetIssues, String>
{
	@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate, "
			+ "i.description as description, i.Solution as Solution,a.aId as aId, "
			+ "i.Status as Status, i.vendorId as vendorId, "
			+ "i.resolvedDate as resolvedDate) "
			+  "a.aid as aid, a.assetStatus as assetStatus, v.vendorId as vendorId,v.Status as Status) from AssetIssues i,"
	        + "left JOIN  AssetIssues i ON i.issueId = a.aid "
	        + "INNER JOIN Asset a On a.aId = v.vendorId JOIN  AssetVendor d On a.vendorId=v.vendorId")
	Page<Map> getAssetIssues(java.awt.print.Pageable pageable);
	
	
	@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
			+ "i.description as description, i.Solution as Solution,a.aId as aId,"
			+ "i.Status as Status, i.vendorId as vendorId,"
            + "i.resolvedDate as resolvedDate)from AssetIssues)"
            + "left JOIN  AssetIssues i ON i.issueId = a.aid,"
            + "INNER JOIN Asset a On a.aId = v.vendorId JOIN  AssetVendor d On a.vendorId=v.vendorId")
	List<Map> searchAssetIssues(@Param("searchAssetIssues") String issueId);

   ApiResponse save(ApiResponse addAssetIssues);



   @Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
			+ "i.description as description, i.Solution as Solution,a.aId as aId,"
			+ "i.Status as Status, i.vendorId as vendorId,"
            + "i.resolvedDate as resolvedDate)from AssetIssues)"
            + "left JOIN  AssetIssues i ON i.issueId = a.aid,"
            + "INNER JOIN Asset a On a.aId = v.vendorId JOIN  AssetVendor d On a.vendorId=v.vendorId")
   
        Page<Map> getIssues(Pageable pageable);
   
AssetIssues changeAssetIssuesStatus(Object getissueId);

AssetIssues downloadAssetIssues(Map<String, Object> filter);
	
}
