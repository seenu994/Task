package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;

public interface AssetvendorService {

	ApiResponse addAssestVendor(AssetVendor vendor);

	ApiResponse editassetVendor(AssetVendor AssetVendorRequest, String vendorId);

	ApiResponse getAllVendorList(Pageable pageable);

	//ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum);

	//ApiResponse updateassetVendorStatus(AssetVendor vendorId, AssetVendorEnum assetVendorEnum);



	ApiResponse updateassetVendorStatus(String vendorId, AssetVendorEnum assetVendorEnum);

	// ApiResponse getAllVendorList(Pageable pageable);

// ApiResponse getAllAssetVendor();

}
