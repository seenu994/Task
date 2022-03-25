package com.xyram.ticketingTool.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Announcement;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetServiceImpl implements AssetService{

	
	@Autowired
	AssetRepository assetRepository;
	
	    @Override
	 	public ApiResponse addasset(Asset asset)
		{
		ApiResponse response = new ApiResponse(false);
		response = validateAsset(asset);
		
		if(asset != null)
		{
		assetRepository.save(asset);
		response.setSuccess(true);
		response.setMessage(ResponseMessages.ASSET_ADDED);
		}
		else
		{
		response.setSuccess(false);
		response.setMessage(ResponseMessages.ASSET_NOT_ADDED);
		}
		
		
		
		return response;
		}
	    private ApiResponse validateAsset(Asset asset) {
			ApiResponse response = new ApiResponse(false);
			if (asset.getvId() != null) {
				
				
				
				response.setMessage(ResponseMessages.VENDOR_ID_INVALID);

				response.setSuccess(false);
			}


			else {
				response.setMessage(ResponseMessages.VENDOR_ID_ADDED);

				response.setSuccess(true);
			}

			return response;
		}
	 
   /*
	@Override
	public ApiResponse getAllAssets(Pageable pageable) {
		
		return (ApiResponse) assetRepository.getAllAssets(pageable);
	}
	
	
	@Override
	public ApiResponse editAsset(Asset asset) {
		ApiResponse response = new ApiResponse(false);
		
	
		Asset assetObj = assetRepository.getById(asset.getaId());
		
		
		if(assetObj != null) {		
			try {
				
				assetRepository.save(assetObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_EDITED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
		}
		
		return response;
	}

     
	

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
	*/
    /*
	@Override
	public ApiResponse searchAsset(String aid) {
	ApiResponse response = new ApiResponse(false);
	Asset assetRequest = new Asset();
	assetRequest.setaId(aid);
	List<Map> assetList = assetRepository.searchAsset(aid);
	Map content = new HashMap();
	
	content.put("AssetList", assetList);
	response.setSuccess(true);
	response.setContent(content);
	return response;
	}


		*/

}
