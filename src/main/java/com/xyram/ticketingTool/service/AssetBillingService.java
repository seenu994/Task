package com.xyram.ticketingTool.service;

import org.springframework.stereotype.Service;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;

@Service
public interface AssetBillingService 
{
    ApiResponse addPurchaseAssetBill(AssetBilling assetBilling);
	
	ApiResponse editPurchaseAssetBill(AssetBilling assetBilling, String assetBillId);

    ApiResponse addRepairAssetBill(AssetBilling assetBilling);

	ApiResponse editRepairAssetBill(AssetBilling assetBilling, String assetBillId);
 
	ApiResponse getAllAssetBillingByAssetId(String assetId);

	ApiResponse returnFromRepair(AssetBilling assetBilling, String assetBillId);

}
