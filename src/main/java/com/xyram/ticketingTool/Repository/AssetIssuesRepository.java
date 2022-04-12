package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
//import com.xyram.ticketingTool.entity.AssetIssuesStatus;

@Repository
@Transactional
public interface AssetIssuesRepository extends JpaRepository<AssetIssues, String>
{
//	@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate, "
//			+ "i.description as description, i.solution as solution,a.aId as aId, "
//			+ "i.status as status, i.vendorId as vendorId, "
//			+ "i.resolvedDate as resolvedDate ,"
//			+  "a.aId as aId, a.assetstatus as assetstatus, v.vendorId as vendorId,v.Status as status) from AssetIssues i "
//	        + "left JOIN i.aId  a  Inner JOIN  AssetVendor v On a.vId=v.vendorId")
//	List<Map> getAssetIssues(Pageable pageable);
	
	
	ApiResponse save(ApiResponse addAssetIssues);
	
	/*@Query("Select distinct new map(i.assetIssue as assetIssue, i.complaintRaisedDate as complaintRaisedDate, "
	+ "i.description as description, i.solution as solution,i.aId as aId, "
	+ "i.assetIssueStatus as assetIssueStatus, i.assetVendor as assetVendor, "
	+ "i.resolvedDate as resolvedDate )from AssetIssues i ")*/
 
//AssetIssues getAssetIssues(AssetIssues assetIssues);
	
	@Query("Select distinct new map(i.assetIssueId as assetIssueId, i.complaintRaisedDate as complaintRaisedDate, "
	+ "i.description as description, i.solution as solution,i.assetId as assetId, "
	+ "i.assetIssueStatus as assetIssueStatus, i.vendorId as vendorId, "
	+ "i.resolvedDate as resolvedDate ) from AssetIssues i ")

	AssetIssues getAssetIssuesList(AssetIssues assetIssues);
	/*@Query("SELECT i from AssetIssues i where i.assetIssue =:assetIssue")
	 AssetIssues getAssetIssue(AssetIssues assetIssue);*/
	/*static AssetIssues getById(AssetIssues issueId) {
		
		return null;
	}*

	static AssetIssues getIssueId(String issueId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
			+ "i.description as description, i.Solution as Solution,a.aId as aId,"
			+ "i.Status as Status, i.vendorId as vendorId,"
            + "i.resolvedDate as resolvedDate)from AssetIssues)"
            + "left JOIN  AssetIssues i ON i.issueId = a.aid,"
            + "INNER JOIN Asset a On a.aId = v.vendorId JOIN  AssetVendor d On a.vendorId=v.vendorId")
	List<Map> searchAssetIssues(@Param("searchAssetIssues") String issueId);

   



   @Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
			+ "i.description as description, i.Solution as Solution,a.aId as aId,"
			+ "i.Status as Status, i.vendorId as vendorId,"
            + "i.resolvedDate as resolvedDate)from AssetIssues)"
            + "left JOIN  AssetIssues i ON i.issueId = a.aid,"
            + "INNER JOIN Asset a On a.aId = v.vendorId JOIN  AssetVendor d On a.vendorId=v.vendorId")
   
        Page<Map> getIssues(Pageable pageable);
   
AssetIssues changeAssetIssuesStatus(Object getissueId);

AssetIssues downloadAssetIssues(Map<String, Object> filter);
	
	//ApiResponse save(ApiResponse addAssetIssues);*/

	@Query("Select i from AssetIssues i where i.assetIssueId=:assetIssueId")
	AssetIssues getAssetIssueById(String assetIssueId);

	@Query("Select i from AssetIssues i where i.assetIssueStatus=:assetIssueStatus")
	AssetIssues getAssetIssueStatus();


	//@Query("Select s AssetIssues s where s.assetIssuesStatus:assetIssuesStatus")
	//AssetIssuesStatus getAssetIssuesStatus(String assetIssuesStatus);

	
	
}
