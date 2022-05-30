package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;

public interface AssetvendorService {

	ApiResponse addAssestVendor(AssetVendor vendor);

	ApiResponse editassetVendor(AssetVendor AssetVendorRequest, String vendorId);
	
	ApiResponse getVendorById(String vendorId);
	
	ApiResponse getAllVendor(Map<String, Object> filter,Pageable peageble);
	
	ApiResponse searchAssetVendor(String searchString);
	
    ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum);

	//ApiResponse searchVendorName(String vendorName);

	

	

	// ApiResponse getAllVendorList(Pageable pageable);

// ApiResponse getAllAssetVendor();

}
