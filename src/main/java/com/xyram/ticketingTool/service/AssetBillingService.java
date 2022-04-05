package com.xyram.ticketingTool.service;

import java.awt.print.Pageable;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;

@Service

public interface AssetBillingService 
{
   ApiResponse getAllAssestVendor(String vendorID);
	
	ApiResponse addPurchaseAssetBill(AssetBilling assetBilling);

	ApiResponse editPurchaseAssetBill(AssetBilling assetBilling);

	ApiResponse addRepairAssetBill(AssetBilling assetBilling);

	ApiResponse editRepairAssetBill(AssetBilling assetBilling);

	ApiResponse returnFromRepair(AssetBilling assetBilling);

	ApiResponse getAllAssetBillingList();

	
}
