package com.xyram.ticketingTool.service.impl;

import java.awt.print.Pageable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetBillingRepository;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetBilling;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.service.AssetBillingService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetBillingServiceImpl implements AssetBillingService
{
	@Autowired
	AssetBillingRepository  assetBillingRepository;


	@Override
	public ApiResponse addAssetBilling(AssetBilling assetBilling) 
	{
       ApiResponse response = new ApiResponse(false);
		
		if(assetBilling != null) 
		{
			 assetBillingRepository.save(assetBilling);
	         response.setSuccess(true);
	         response.setMessage(ResponseMessages.ASSET_BILL_ADDED);
		}
		else 
		{
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_BILL_NOT_ADDED);
		}
		
		return response;
	}

	/*@Override
	public ApiResponse editAssetBilling(AssetBilling assetBilling) 
	{
        ApiResponse response = new ApiResponse(false);
		
		AssetBilling assetBillingObj = assetBillingRepository.getById(assetBilling.getById());
		
		
		if(assetBillingObj != null) {		
			try 
			{
				assetBillingRepository.save(assetBillingObj);
                response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_BILL_EDITED);
				
			}
			catch(Exception e) 
			{
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_BILL_NOT_EDITED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_BILL_NOT_EDITED);
		}
		
		return response;
	}

	@Override
	public ApiResponse getAllAssetBilling(Pageable pageable) {
		
		return (ApiResponse) assetBillingRepository.getAllAssetBilling(pageable);
	}*/

}
