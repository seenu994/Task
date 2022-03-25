package com.xyram.ticketingTool.service;

import java.awt.print.Pageable;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;

@Service
public interface AssetBillingService 
{
	ApiResponse addAssetBilling(AssetBilling assetBilling);

	//ApiResponse editAssetBilling(AssetBilling assetBilling);
	
	//ApiResponse getAllAssetBilling(Pageable pageable);
}
