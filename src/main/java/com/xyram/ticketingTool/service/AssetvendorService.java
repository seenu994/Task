package com.xyram.ticketingTool.service;

import java.awt.print.Pageable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;

@Service
@Transactional
public interface AssetvendorService 
{

	//AssetVendor getAllAssestVendor(String string);

	//AssetVendor getAllAssestVendor(AssetVendor vendor);

	//ApiResponse getAllAssestVendor(AssetVendor vendor);

	//AssetVendor getAllAssestVendor(String vendorID);
	
    // ApiResponse addAssestVendor(AssetVendor vendor);
	

	ApiResponse getAllAssestVendor(AssetVendor assetVendor);
	

}
