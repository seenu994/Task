package com.xyram.ticketingTool.service.impl;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetSoftwareRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetSoftware;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.service.AssetSoftwareService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetSoftwareServiceImpl implements AssetSoftwareService {

	@Autowired
	AssetSoftwareRepository assetSoftwareRepository;
	
	@Autowired
	AssetRepository assetRepository;
	
//	@Autowired
//	SoftwareMasterRepository softwareMasterRepository;
	
	

	@Override
	public ApiResponse addassetSoftware(AssetSoftware assetSoftware) {
	ApiResponse response = new ApiResponse(false);
	response = validateAssetSoftware(assetSoftware);
	if (response.isSuccess()) {
		if (assetSoftware != null) {
			assetSoftwareRepository.save(assetSoftware);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_SOFTWARE_ADDED);
		}

		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ASSET_SOFTWARE_NOT_ADDED);
		}
	}

		return response;
	}

	private ApiResponse validateAssetSoftware(AssetSoftware assetSoftware) {
		ApiResponse response = new ApiResponse();
		
		
//		private String assetId1;
//		private String softId;
//		private String installDate;
//		private String uninstallDate;
//		private String status;
//		private String action;
		
		
		// Validate asset id
		if (assetSoftware.getAssetId1() == null || assetSoftware.getAssetId1().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory");
		} else {
			// Validate asset
			Asset asset = assetRepository.getByAssetId(assetSoftware.getAssetId1());
			if (asset == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
			}
		}
		
		//validate software id
		if (assetSoftware.getSoftwareId() == null || assetSoftware.getSoftwareId().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "software id is mandatory");
		} else {
			// Validate software
//			SoftwareMaster software = softwareMasterRepository.getBysoftId(assetSoftware.getSoftId());
//			if (software == null) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "software id is not valid");
//			}
		}
		
		// install date Validating
		if (assetSoftware.getInstallDate() == null || assetSoftware.getInstallDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "install date is mandatory");
		}
		
		
		
		
		response.setSuccess(true);
 		return response;
		
	
	}
}
