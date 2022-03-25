package com.xyram.ticketingTool.Repository;
/*
import java.awt.print.Pageable;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.Employee;

@Repository
@Transactional
public interface AssetBillingRepository extends JpaRepository<AssetBilling, String>
{
	
	@Query("Select distinct new map(a.billingType as billingType, a.underWarrenty as underWarrenty, "
			+ "a.assetAmount as assetAmount, a.gstAmount as gstAmount, "
			+ "a.transactionDate as transactionDate, a.vendorId as vendorId, "
			+ "a.billPhotoUrl as billPhotoUrl,a.issueId as issueId) from AssetBilling a")
	Page<Map> getAllAssetBilling(Pageable pageable);

	ApiResponse save(ApiResponse addAssetBilling);

	
	
    
}*/
