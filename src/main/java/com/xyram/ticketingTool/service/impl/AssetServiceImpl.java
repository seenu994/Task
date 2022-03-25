package com.xyram.ticketingTool.service.impl;
/*
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetServiceImpl implements AssetService{

	
	/*@Autowired
	AssetRepository assetRepository;
	
	@Override
	public ApiResponse addasset(Asset asset) {
		
	    ApiResponse addasset = null;
		return assetRepository.save(addasset);
	}

	@Override
	public ApiResponse getAllAssets(Pageable pageable) {
		
		return (ApiResponse) assetRepository.getAllAssets(pageable);
	}
	
	
	@Override
	public ApiResponse editAsset(Asset asset) {
		ApiResponse response = new ApiResponse(false);
		
		Object findAll = null;
		Asset assetObj = assetRepository.getById(asset.getaId());
		
		
		if(assetObj != null) {		
			try {
				
				assetRepository.save(assetObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_EDITED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_NOT_EDITED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
		}
		
		return response;
	}
*/
     /*
	@Override
	public ApiResponse editAsset(Asset AssetRequest) {
	
		return null;
	}*/
	

/*
	@Override
	public ApiResponse searchAsset(String searchString) {
		
		ApiResponse response = new ApiResponse();
		
		Object assetId = null;
		List<Map> assetList = assetRepository.searchAsset(assetId, searchString);
		response.setContent1(assetList);
		response.setStatus("success");
		return response;
		
	}



}*/
