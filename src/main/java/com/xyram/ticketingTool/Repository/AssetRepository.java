package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.xyram.ticketingTool.entity.Role;


@Repository
public interface AssetRepository extends JpaRepository<Asset, String>{
	
	
	

	@Query("SELECT a from Asset a where a.assetId =:assetId")
	Asset getByassetId(String assetId);

//	@Query("SELECT a from Asset a where a.assignedTo =:assignedTo")
//	Asset getByAssignedTo(String assignedTo);

	
        @Query("Select distinct new map(a.assetId as assetId,a.vendorId as vendorId,"		
            + "a.brand as brand,a.purchaseDate as purchaseDate,a.modelNo as modelNo,"
			+ "a.serialNo as serialNo,a.warrantyDate as warrantyDate,a.ram as ram,"
		    + "a.assetStatus as assetStatus, a.assignedTo as assignedTo) from Asset a")
        Page<Map> getAllAsset(Pageable pageable);

        
		

		

//        @Query("select a from Asset a where a.purchaseDate = :purchaseDate")
//		Date getBypurchaseDate(Date purchaseDate);
//
//        @Query("select a from Asset a where a.warrantyDate = :warrantyDate")
//		Asset getByaId(Date warrantyDate);

//        @Query("select a from Asset a where a.purchaseDate = :purchaseDate")
//		Date getbyPurchaseDate(Date aId);

		
	        
	//Asset findAll(String getaId);

	//@Query("SELECT a.purchasDate from Asset a where a.purchaseDate =:purchaseDate")
	//Asset getByDate(Date purchaseDate);
   
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

	ApiResponse save(ApiResponse addasset);

	*/


}
