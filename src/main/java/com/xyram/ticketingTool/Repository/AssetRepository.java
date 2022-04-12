package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.enumType.AssetStatus;


@Repository
@Transactional
public interface AssetRepository extends JpaRepository<Asset, String>{

	@Query("SELECT a from Asset a where a.assetId =:assetId")
	Asset getByAssetId(String assetId);
	
//    @Query("Select distinct new map(a.assetId as assetId,a.vendorId as vendorId,"		
//        + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
//		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
//	    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a")
//    Page<Map> getAllAsset(Pageable pageable);

    @Query("select a.purchaseDate from Asset a where a.assetId = :id")
	Date getPurchaseDateById(String id);

    @Query("Select distinct new map(a.assetId as assetId,v.vendorName as vendorName,"		
            + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
    		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
    	    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a left join AssetVendor v "
    	    + "on a.vendorId = v.vendorId where a.id LIKE %:assetId% AND "
    		+ "a.assetId =:assetId")
	List<Map> searchAsset(String assetId);

    @Query("SELECT a from Asset a where a.assetId =:assetId")
	Asset getAssetById(String assetId);

    
    @Query("Select distinct new map(a.assetId as assetId,v.vendorName as vendorName, "		
            + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
    		+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
    	    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a left join AssetVendor v "
    	    + "on a.vendorId = v.vendorId where "
    	    + "(:assetStatus is null OR lower(a.assetStatus)=:assetStatus) AND "
			+ "(:ram is null OR a.ram=:ram) AND "
    	    + "(:brand is null OR a.brand=:brand) AND "
			+ "(:vendorId is null OR a.vendorId=:vendorId)")
	Page<Map> getAllAssets(String ram, String brand, AssetStatus assetStatus, String vendorId, Pageable pageable);

    
    @Query("Select distinct new map(e.empId as empId,e.issuedDate as issuedDate,"		
            + "e.bagIssued as bagIssued,e.powercordIssued as powercordIssued,e.mouseIssued as mouseIssued,"
    		+ "e.returnDate as returnDate,e.returnType as returnType,"
    	    + "e.assetEmployeeStatus as assetEmployeeStatus) from AssetEmployee e left join Asset a "
    	    + "on a.assetId = e.assetId where e.assetId =:assetId")
	List<Map> getAssetEmployeeById(String assetId, Pageable pageable);

    
    
    @Query("Select distinct new map(b.assetBillId as assetBillId,b.billingType as billingType,"		
            + "b.underWarrenty as underWarrenty,b.assetAmount as assetAmount,b.gstAmount as gstAmount,"
    		+ "b.transactionDate as transactionDate,b.vendorId as vendorId,"
    	    + "b.billPhotoUrl as billPhotoUrl, b.assetIssueId as assetIssueId, b.returnDate as returnDate, "
    	    + "b.amountPaid as amountPaid) from AssetBilling b right join Asset a "
    	    + "on a.assetId  = b.assetId where b.assetId =:assetId")
	List<Map> getAssetBillingById(String assetId, Pageable pageable);

    @Query("SELECT a.assetId from Asset a where a.assetId =:assetId")
	Asset getAssetById1(String assetId);

    //@Query("select a.purchaseDate from Asset a where a.assetId = :assetId")
	//Asset getPurchaseDateById(Date purchaseDate,String assetId);
    
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
