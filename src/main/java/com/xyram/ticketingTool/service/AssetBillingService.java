package com.xyram.ticketingTool.service;




import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.request.AssetBillingRequest;

@Service

public interface AssetBillingService 
{
   ApiResponse getAllAssestVendor(String vendorID);
	
	ApiResponse addPurchaseAssetBill(AssetBillingRequest assetBilling);

	ApiResponse editPurchaseAssetBill(AssetBillingRequest assetBilling);

	ApiResponse addRepairAssetBill(AssetBillingRequest assetBilling);

	ApiResponse editRepairAssetBill(AssetBillingRequest assetBilling);

	/*ApiResponse editPurchaseAssetBill(AssetBillingRequest assetBilling);

	ApiResponse addRepairAssetBill(AssetBillingRequest assetBilling);

	ApiResponse editRepairAssetBill(AssetBillingRequest assetBilling);

	ApiResponse returnFromRepair(String assetBillId);*/

	//ApiResponse getAllAssetBillingList();

	//ApiResponse getAllAssetBillingList(Pageable pageable);

	//ApiResponse getAllAssetBillingList(org.springframework.data.domain.Pageable pageable);

	//ApiResponse getAllAssetBillingList(Pageable pageable);

	//ApiResponse getAllAssetBillingList(org.springframework.data.domain.Pageable pageable);

	
	
}
