package com.xyram.ticketingTool.service.impl;

import java.awt.print.Pageable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.service.AssetBillingService;

@Service
@Transactional
public class AssetBillingServiceImpl implements AssetBillingService
{

	@Override
	public ApiResponse addAssetBilling(AssetBilling assetBilling) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse editAssetBilling(AssetBilling assetBilling) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse getAllAssetBilling(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
