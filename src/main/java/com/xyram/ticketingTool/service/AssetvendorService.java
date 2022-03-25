package com.xyram.ticketingTool.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.id.generator.IdPrefix;

public interface AssetvendorService {
	

	ApiResponse addAssestVendor(AssetVendor vendor);

	/*
	 * ApiResponse getAllAssetVendorList(Pageable pageable);
	 * 
	 * ApiResponse editassetVendorList(AssetVendor AssetVendorRequest);
	 * 
	 * ApiResponse getAllByvendorId(String vendorID);
	 */

	//ApiResponse getAllByvendorId();
   
	//ssApiResponse getAllVendors(Pageable pageable);


	
	

}
