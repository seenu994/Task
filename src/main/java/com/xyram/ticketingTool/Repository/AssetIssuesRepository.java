package com.xyram.ticketingTool.Repository;



import java.util.Date;
import java.util.List;





import java.util.Map;



import javax.transaction.Transactional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
//import com.xyram.ticketingTool.entity.AssetIssuesStatus;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.enumType.AssetStatus;



@Repository
@Transactional
public interface AssetIssuesRepository extends JpaRepository<AssetIssues, String>
{
// @Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate, "
// + "i.description as description, i.solution as solution,a.aId as aId, "
// + "i.status as status, i.vendorId as vendorId, "
// + "i.resolvedDate as resolvedDate ,"
// + "a.aId as aId, a.assetstatus as assetstatus, v.vendorId as vendorId,v.Status as status) from AssetIssues i "
// + "left JOIN i.aId a Inner JOIN AssetVendor v On a.vId=v.vendorId")
// List<Map> getAssetIssues(Pageable pageable);


ApiResponse save(ApiResponse addAssetIssues);

/*@Query("Select distinct new map(i.assetIssue as assetIssue, i.complaintRaisedDate as complaintRaisedDate, "
+ "i.description as description, i.solution as solution,i.aId as aId, "
+ "i.assetIssueStatus as assetIssueStatus, i.assetVendor as assetVendor, "
+ "i.resolvedDate as resolvedDate )from AssetIssues i ")*/

//AssetIssues getAssetIssues(AssetIssues assetIssues);

/*@Query("Select distinct new map(i.assetIssueId as assetIssueId, i.complaintRaisedDate as complaintRaisedDate, "
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
+ "left JOIN AssetIssues i ON i.issueId = a.aid,"
+ "INNER JOIN Asset a On a.aId = v.vendorId JOIN AssetVendor d On a.vendorId=v.vendorId")
List<Map> searchAssetIssues(@Param("searchAssetIssues") String issueId);







@Query("Select distinct new map(i.issueId as issueId, i.complaintRaisedDate as complaintRaisedDate,"
+ "i.description as description, i.Solution as Solution,a.aId as aId,"
+ "i.Status as Status, i.vendorId as vendorId,"
+ "i.resolvedDate as resolvedDate)from AssetIssues)"
+ "left JOIN AssetIssues i ON i.issueId = a.aid,"
+ "INNER JOIN Asset a On a.aId = v.vendorId JOIN AssetVendor d On a.vendorId=v.vendorId")

Page<Map> getIssues(Pageable pageable);

AssetIssues changeAssetIssuesStatus(Object getissueId);



AssetIssues downloadAssetIssues(Map<String, Object> filter);

//ApiResponse save(ApiResponse addAssetIssues);*/



@Query("Select i from AssetIssues i where i.assetIssueId=:assetIssueId")
AssetIssues getAssetIssueById(String assetIssueId);



@Query("Select i from AssetIssues i where i.assetIssueStatus=:assetIssueStatus")
AssetIssues getAssetIssueStatus();



@Query("Select distinct new map(i.assetIssueId as assetIssueId, i.complaintRaisedDate as complaintRaisedDate, "
+ "i.description as description, i.solution as solution,i.assetId as assetId, "
+ "i.assetIssueStatus as assetIssueStatus, i.vendorId as vendorId, "
+ "i.resolvedDate as resolvedDate ) from AssetIssues i left join Asset a on i.assetId = a.assetId "
+ "join AssetVendor v on i.vendorId = v.vendorId where "
+ "(:assetIssueStatus is null OR lower(i.assetIssueStatus)=:assetIssueStatus) AND "
+ "(:assetId is null OR i.assetId=:assetId) AND "
+ "(:vendorId is null OR i.vendorId=:vendorId)")

Page<Map> getAllAssetsIssues(String assetId, String vendorId, AssetIssueStatus assetIssueStatus, Pageable pageable);



@Query("Select distinct new map(i.assetIssueId as assetIssueId, i.complaintRaisedDate as complaintRaisedDate, "
+ "i.description as description, i.solution as solution,i.assetId as assetId, "
+ "i.assetIssueStatus as assetIssueStatus, i.vendorId as vendorId, "
+ "i.resolvedDate as resolvedDate ) from AssetIssues i join Asset a on i.assetId = a.assetId "
+ "join AssetVendor v on i.vendorId = v.vendorId where i.assetIssueId LIKE %:assetIssueId ")

List<Map> searchAssetIssue(String assetIssueId);




@Query("Select i.complaintRaisedDate from AssetIssues i where i.assetIssueId=:assetIssueId")
Date getCompaintRaisedDate(String assetIssueId);








}