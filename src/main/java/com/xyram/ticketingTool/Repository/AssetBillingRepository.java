package com.xyram.ticketingTool.Repository;



import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Employee;

@Repository
@Transactional
public interface AssetBillingRepository extends JpaRepository<AssetBilling, String>
{

	ApiResponse save(ApiResponse addAssetBilling);
	
	/*@Query("SELECT b from AssetBilling b where b.aId = :aId")
	AssetBilling getById(Asset getaId);*/
	
	/* @Query("SELECT b from AssetBilling b where b.assetAmount =:assetAmount")
	 AssetBilling getAssetAmount(Double assetAmount);*/

	
	 /*@Query("SELECT b from AssetBilling b where b.underWarrenty =:underWarrenty")
	 AssetBilling getUnderWarrenty(Boolean underWarrenty);*/
	 
	 /*@Query("SELECT b from AssetBilling b where b.gstAmount =:gstAmount")
	 AssetBilling getGstAmount(Integer gstAmount);*/

	@Query("Select distinct new map (b.assetBillId as assetBillId, b.assetAmount as assetAmount, b.billingType as billingType,"
 		+ "b.gstAmount as gstAmount,b.billPhotoUrl as billPhotoUrl,b.returnDate as returnDate,"
 		+ "b.vendorId as vendorId) from AssetBilling b Inner Join Asset a ON b.assetId = a.assetId where b.billingType = 'return' ")
	 AssetBilling getReturnFromRepair(AssetBilling assetBilling);
	

	//AssetBilling getAssetBillingByDate(int i);

	/* @Query("SELECT b from AssetBilling b where b.returnDate =:returnDate") 
	 AssetBilling getReturnDate(Date returnDate);*/

	/*@Query("SELECT b from AssetBilling b where b.amountPaid =:amountPaid") 
	AssetBilling getAmountPaidAmount(Boolean amountPaid);*/
	
	/*@Query("Select distinct new map(b.assetId as assetId, b.billingType as billingType, b.underWarrenty as underWarrenty, "
			+ "b.assetAmount as assetAmount, b.gstAmount as gstAmount, "
			+ "b.transactionDate as transactionDate, b.vendorId as vendorId, "
			+ "b.billPhotoUrl as billPhotoUrl,b.assetIssueId as assetIssueId, b.returnDate as returnDate, b.amountPaid as amountPaid) from AssetBilling b" )
	Page<Map> getAllAssetBillingList();*/

	@Query("Select b from AssetBilling b where b.assetBillId=:assetBillId")
	AssetBilling getAssetBillById(String assetBillId);


	/*@Query("Select distinct new map(b.assetBillId as assetBillId,b.billingType as billingType,"		
            + "b.underWarrenty as underWarrenty,b.assetAmount as assetAmount,b.gstAmount as gstAmount,"
    		+ "b.transactionDate as transactionDate,b.vendorId as vendorId,"
    	    + "b.billPhotoUrl as billPhotoUrl, b.assetIssueId as assetIssueId, b.returnDate as returnDate, "
    	    + "b.amountPaid as amountPaid) from AssetBilling b left join Asset a on b.assetId = a.assetId where "
    	    + "(:assetId is null OR b.assetId=:assetId)")
	
            Page<Map> getAllAssetBilling(String assetId,Pageable pageable);*/


	@Query("select b from AssetBilling b where b.assetId =:assetId")
	AssetBilling getAssetById(String assetId);

	@Query("Select b.transactionDate from AssetBilling b where b.assetBillId=:assetBillId")
	Date getTransationDate(String assetBillId);

	@Query("Select b.assetIssueId from AssetBilling b where b.assetIssueId=:assetIssueId")
	AssetBilling getAssetIssueById(String assetIssueId);

	//@Query("Select b from AssetBilling b where b.assetId =:assetId")
	
	@Query("Select distinct new map (b.assetBillId as assetBillId,v.vendorName as vendorName,b.assetAmount as assetAmount, b.billingType as billingType, b.amountPaid as amountPaid, "
	 		+ "b.gstAmount as gstAmount,b.transactionDate as transactionDate, b.underWarrenty as underWarrenty,b.returnDate as returnDate, "
	 		+ "b.vendorId as vendorId) from AssetBilling b left Join Asset a ON b.assetId = a.assetId "
	 		+ "left join AssetVendor v on b.vendorId = v.vendorId where b.assetId=:assetId")
	Map getAllAssetBillingByAssetId(String assetId);

	@Query("Select b from AssetBilling b where b.assetBillId=:assetBillId and b.vendorId=:vendorId")
	AssetBilling getByVendorId(String assetBillId, String vendorId);

	@Query("Select b from AssetBilling b where b.assetBillId=:assetBillId and b.assetIssueId=:assetIssueId")
	AssetBilling getAssetIssueById(String assetIssueId, String assetBillId);

	/*@Query("Select assetBilling.issueId from AssetBilling b where b.issueId = :issueId")
	String filterByIssueId(AssetIssues assetIssues);
	
	@Query("Select assetBilling.vendorId from AssetBilling b where b.issueId = :vendorId")
	String filterByVendorId(AssetVendor assetVendor);*/

	
	/*@Query("Select distinct new map(b.billingType as billingType, b.underWarrenty as underWarrenty, "
	+ " b.assetAmount as assetAmount, b.gstAmount as gstAmount, "
	+ " b.transactionDate as transactionDate, b.vendorId as vendorId, "
	+ " b.billPhotoUrl as billPhotoUrl,b.issueId as issueId) from AssetBilling b" 
	+ " INNER JOIN AssetBilling b ON b.vendorID = AssetVendor.vendorID "
	+ " JOIN AssetIssues i ON b.issueId = i.issueId")*/
	 //save(ApiResponse addAssetBilling);
	
    
}


















