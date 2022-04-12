package com.xyram.ticketingTool.service;



import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;

@Service
public interface AssetService {
    
	ApiResponse addasset(Asset asset);
	
	ApiResponse editAsset(Asset asset, String id);

//	ApiResponse getAllAsset(Pageable pageable);

	ApiResponse searchAsset(String assetId);

	ApiResponse getAssetById(String assetId);

//	ApiResponse getAssetByVendorName(Pageable pageable, String vendorName);

	ApiResponse getAllAssets(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAssetEmployeeById(String assetId, Pageable pageable);

	ApiResponse getAssetBillingById(String assetId, Pageable pageable);
	
}
