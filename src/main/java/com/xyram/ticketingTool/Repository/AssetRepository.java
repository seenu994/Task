package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.enumType.AssetStatus;


@Repository
@Transactional
public interface AssetRepository extends JpaRepository<Asset, String>{

//	@Query("SELECT a from Asset a where a.assetId =:assetId")
//	Asset getByassetId(String assetId);

	@Query("SELECT a from Asset a where a.assetId =:assetId")
	Asset getByAssetId(String assetId);
	
//    @Query("Select distinct new map(a.assetId as assetId,a.vendorId as vendorId,"		
//        + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
//		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
//	    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a")
//    Page<Map> getAllAsset(Pageable pageable);

    @Query("select a.purchaseDate from Asset a where a.assetId = :id")
	Date getPurchaseDateById(String id);

//    @Query("Select distinct new map(a.assetId as assetId,v.vendorName as vendorName,"		
//            + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
//    		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
//    	    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a join AssetVendor v "
//    	    + "on a.vendorId = v.vendorId where a.id LIKE %:assetId% AND "
//    		+ "a.assetId =:assetId")
//	List<Map> searchAsset(String assetId);

    @Query("SELECT a from Asset a where a.assetId =:assetId")
	Asset getAssetById(String assetId);

    @Query("Select distinct new map(a.assetId as assetId,v.vendorName as vendorName,"
    		+ "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
    		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
    		+ "a.bagAvailable as bagAvailable, a.powercordAvailable as powercordAvailable,"
    		+ "a.mouseAvailable as mouseAvailable, a.assetPhotoUrl as assetPhotoUrl,"
    		+ "a.assetStatus as assetStatus, CONCAT(e.firstName ,' ', e.lastName) as assignedTo) from Asset a "
    		+ "left join AssetVendor v on v.vendorId = a.vendorId left join AssetEmployee b on b.assetId = a.assetId "
    		+ "left join Employee e on e.eId = b.empId where a.assetId =:assetId")
	Map getAllAssetById(String assetId);
    
      @Query("Select distinct new map(a.assetId as assetId,v.vendorName as vendorName, "		
            + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
    		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
     	    + "a.assetStatus as assetStatus, CONCAT(e.firstName ,' ', e.lastName) as assignedTo) from Asset a left join AssetVendor v "
    	    + "on v.vendorId = a.vendorId left join AssetEmployee b on b.assetId = a.assetId "
    	    + " left join Employee e on e.eId = b.empId where "
    	    + "(:assetStatus is null OR a.assetStatus=:assetStatus) AND "
			+ "(:ram is null OR a.ram =:ram) AND "
    	    + "(:brand is null OR a.brand=:brand) AND "
			+ "(:vendorId is null OR a.vendorId=:vendorId) AND "
    	    + "(:searchString  is null "
    	    + " OR a.modelNo LIKE %:searchString%  OR a.serialNo LIKE %:searchString% "
    	    + " OR a.assetId LIKE %:searchString% OR e.firstName LIKE %:searchString% OR e.lastName LIKE %:searchString%) AND "
    	    + "(:fromDateStr is null OR Date(a.purchaseDate) >= STR_TO_DATE(:fromDateStr, '%Y-%m-%d')) AND "
    	    + "(:toDateStr is null OR Date(a.purchaseDate) <= STR_TO_DATE(:toDateStr, '%Y-%m-%d')) ")
	Page<Map> getAllAssets(String ram, String brand, AssetStatus assetStatus, String vendorId, String searchString, String fromDateStr, String toDateStr, Pageable pageable);

    
//    @Query("Select distinct new map(e.empId as empId,e.issuedDate as issuedDate,"		
//            + "e.bagIssued as bagIssued,e.powercordIssued as powercordIssued,e.mouseIssued as mouseIssued,"
//    		+ "e.returnDate as returnDate,e.returnType as returnType,"
//    	    + "e.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee e join Asset a "
//    	    + "on a.assetId = e.assetId where e.assetId =:assetId")
//	List<Map> getAssetEmployeeById(String assetId, Pageable pageable);

    
    
//    @Query("Select distinct new map(b.assetBillId as assetBillId,b.billingType as billingType,"		
//            + "b.underWarrenty as underWarrenty,b.assetAmount as assetAmount,b.gstAmount as gstAmount,"
//    		+ "b.transactionDate as transactionDate,b.vendorId as vendorId,"
//    	    + "b.billPhotoUrl as billPhotoUrl, b.assetIssueId as assetIssueId, b.returnDate as returnDate, "
//    	    + "b.amountPaid as amountPaid) from AssetBilling b join Asset a "
//    	    + "on a.assetId  = b.assetId where b.assetId =:assetId")
//	List<Map> getAssetBillingById(String assetId, Pageable pageable);


    @Query("SELECT a.assetId from Asset a where a.assetId =:assetId")
	Asset getAssetById1(String assetId);

    //@Query("select a.purchaseDate from Asset a where a.assetId = :assetId")
	//Asset getPurchaseDateById(Date purchaseDate,String assetId);
    
    
//    @Query("Select distinct new map(m.softwareName as softwareName,s.installDate as installDate,"		
//            + "s.uninstallDate as uninstallDate,s.assetSoftwareStatus as assetSoftwareStatus) "
//    	    + "from AssetSoftware s join Asset a on s.assetId1  = a.assetId "
//    	    + "join SoftwareMaster m on s.softwareId = m.softwareId where s.assetId1 =:assetId")
//	List<Map> getAssetSoftwareById(String assetId, Pageable pageable);

    
//    @Query("Select distinct new map(i.assetIssueId as assetIssueId,i.complaintRaisedDate as complaintRaisedDate,"		
//            + "i.description as description,i.solution as solution,i.vendorId as vendorId,"
//    		+ "i.resolvedDate as resolvedDate, "
//    	    + "i.assetIssueStatus as assetIssueStatus) from AssetIssues i join Asset a "
//    	    + "on a.assetId = i.assetId where i.assetId =:assetId")
//	List<Map> getAssetIssuesById(String assetId, Pageable pageable);

    @Query("Select distinct a from Asset a join AssetVendor v "
    	    + "on a.vendorId = v.vendorId where "
    	    + "(:status is null OR lower(a.assetStatus)=:status) AND "
			+ "(:ram is null OR a.ram=:ram) AND "
    	    + "(:brand is null OR a.brand=:brand) AND "
			+ "(:vendorId is null OR a.vendorId=:vendorId)")
	List<Asset> getAllAssetsForDownload(String ram, String brand, AssetStatus status, String vendorId);

    @Query("select a.warrantyDate from Asset a where a.assetId = :id")
	Date getWarrantyDateById(String id);
    
	/*
	@Query("Select distinct new map(a.aId as aId,a.vId as vId,"
			+ "a.brand as brand,a.purchasedate as purchasedate,a.model as model,"
			+ "a.serialno as serialno,a.warantydate as warantydate,a.ram as ram,"
			+ "a.bagavailable as bagavailable,a.powercordavailable as powercordavailable,"
			+ "a.mouseavailable as mouseavailable,a.assetphotourl as assetphotourl,"
			+ "a.assetstatus as assetstatus) from Asset a "
			+ "where a.title like %:searchString% or a.description like %:searchString% "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> searchAllAssets(Pageable pageable, String searchString);
	*/
	
	//List<Map> searchAsset(Object assetId, String searchString);
	
	//@Query("SELECT a from Asset a where a.assignedTo =:assignedTo")
	//Asset findAssetByaId(String assignedTo);
	/*
	@Query("SELECT a from Asset a where a.vId=:id")
	Asset findAssetByvId(String id);

	ApiResponse save(ApiResponse addasset);*/

	
}
