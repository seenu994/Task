package com.xyram.ticketingTool.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	 @Query("SELECT b from AssetBilling b where b.assetAmount =:assetAmount")
	 AssetBilling getAssetAmount(Integer assetAmount);

	
	 @Query("SELECT b from AssetBilling b where b.underWarrenty =:underWarrenty")
	 AssetBilling getUnderWarrenty(Boolean underWarrenty);
	 
	 @Query("SELECT b from AssetBilling b where b.gstAmount =:gstAmount")
	 AssetBilling getGstAmount(Integer gstAmount);

	@Query("Select distinct new map (b.assetBillId as assetBillId, b.assetAmount as assetAmount, b.billingType as billingType,"
 		+ "b.gstAmount as gstAmount,b.billPhotoUrl as billPhotoUrl,b.returnDate as returnDate,"
 		+ "b.assetVendor as assetVendor) from AssetBilling b Inner Join Asset a ON b.asset = a.assetId where b.billingType = 'return' ")
	 AssetBilling getReturnFromRepair(AssetBilling assetBilling);
	

	//AssetBilling getAssetBillingByDate(int i);

	 @Query("SELECT b from AssetBilling b where b.returnDate =:returnDate") 
	 AssetBilling getReturnDate(Date returnDate);

	@Query("SELECT b from AssetBilling b where b.amountPaid =:amountPaid") 
	AssetBilling getAmountPaidAmount(Integer amountPaid);
	
	@Query("Select distinct new map(b.asset as asset, b.billingType as billingType, b.underWarrenty as underWarrenty, "
			+ "b.assetAmount as assetAmount, b.gstAmount as gstAmount, "
			+ "b.transactionDate as transactionDate, b.assetVendor as assetVendor, "
			+ "b.billPhotoUrl as billPhotoUrl,b.assetIssues as assetIssues, b.returnDate as returnDate, b.amountPaid as amountPaid) from AssetBilling b" )
	List<Map> getAllAssetBilling();

	
	
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


















