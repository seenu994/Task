package com.xyram.ticketingTool.service;

import java.awt.print.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;

public interface AssetvendorService {
	

	ApiResponse addAssestVendor(AssetVendor vendor);
	 
	
     ApiResponse editassetVendor(AssetVendor AssetVendorRequest, String vendorId);
    // ApiResponse getAllVendorList();

//	ApiResponse getAllVendorList(Pageable pageable);


	//ApiResponse getAllVendorList(AssetVendor pageable);


	//ApiResponse getAllVendorList(Pageable pageable);

// ApiResponse getAllAssetVendor();
	


	

}
