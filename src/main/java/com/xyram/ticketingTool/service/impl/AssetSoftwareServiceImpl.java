package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.AssetSoftwareRepository;
import com.xyram.ticketingTool.Repository.SoftwareMasterRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.AssetSoftware;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.AssetSoftwareStatus;
import com.xyram.ticketingTool.enumType.AssetStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AssetSoftwareService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetSoftwareServiceImpl implements AssetSoftwareService {

	@Autowired
	AssetSoftwareRepository assetSoftwareRepository;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	SoftwareMasterRepository softwareMasterRepository;
	
	@Autowired 
	CurrentUser currentUser;
	
	@Override
	public ApiResponse addassetSoftware(AssetSoftware assetSoftware) {
	ApiResponse response = new ApiResponse(false);
	response = validateAssetSoftware(assetSoftware);
	
	if (response.isSuccess()) {
		if (assetSoftware != null) {
			assetSoftware.setCreatedAt(new Date());
			assetSoftware.setCreatedBy(currentUser.getName());
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
			Asset asset = assetRepository.getAssetById(assetSoftware.getAssetId1());
			if (asset == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
			}
		}
		
		//validate software id
		if (assetSoftware.getSoftwareId() == null || assetSoftware.getSoftwareId().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "software id is mandatory");
		} else {
			// Validate software
			SoftwareMaster software = softwareMasterRepository.getBysoftId(assetSoftware.getSoftwareId());
			if (software == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "software id is not valid");
			}
			String assetSoft = assetSoftwareRepository.getSoftByAssetId(assetSoftware.getSoftwareId());
			if(assetSoft != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "software is already installed to that Asset");
			}
		}
		
		// install date Validating
		if (assetSoftware.getInstallDate() == null || assetSoftware.getInstallDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "install date is mandatory");
		}
		else {
			Date d1 = assetSoftware.getInstallDate();
			Date d2 = new Date();
			if(d1.after(d2)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "install date should be less than current date");
			}
		}
		response.setSuccess(true);
 		return response;
	}

	@Override
	public ApiResponse getAssetSoftwareById(String assetId, Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> asset = assetSoftwareRepository.getAssetSoftwareById(assetId, pageable);
//		System.out.println(asset);
		Map content = new HashMap();
		content.put("asset", asset);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Software Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}

	@Override
	public ApiResponse updateAssetSoftware(AssetSoftware assetSoftware, String assetId) {
		ApiResponse response = new ApiResponse();
		AssetSoftware softObj = assetSoftwareRepository.getByAssetId(assetId);
		if(softObj != null) {
//			System.out.println(softObj.getAction());
			if(softObj.getAction().equals(AssetSoftwareStatus.UNINSTALL)) {
//				System.out.println("Hello");
				softObj.setAction(AssetSoftwareStatus.INSTALL);
				softObj.setUninstallDate(new Date());
				softObj.setAssetSoftwareStatus(AssetSoftwareStatus.INACTIVE);
			}
			else {
//				System.out.println("HAI");
				softObj.setAction(AssetSoftwareStatus.UNINSTALL);
				softObj.setInstallDate(new Date());
				softObj.setUninstallDate(null);
				softObj.setAssetSoftwareStatus(AssetSoftwareStatus.ACTIVE);
			}
			//System.out.println(softObj.getAction());
			softObj.setLastUpdatedAt(new Date());
			softObj.setUpdatedBy(currentUser.getName());
			assetSoftwareRepository.save(softObj);
			response.setSuccess(true);
			response.setMessage("Asset Sofware updated Successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("Invalid Asset Id");
		}
		return response;
	}

	}
