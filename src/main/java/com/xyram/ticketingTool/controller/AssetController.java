package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.log.SysoCounter;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.service.AssetEmployeeService;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController

public class AssetController {
	private final Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@Autowired
	AssetService assetService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAsset"})
	public ApiResponse addasset(@RequestBody Asset asset) {
		logger.info("Received request to add Asset");
		return assetService.addasset(asset);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAsset/{assetId}"})
    public ApiResponse editAsset(@RequestBody Asset asset,@PathVariable String assetId) {
		logger.info("Received request to edit Asset");
		return assetService.editAsset(asset,assetId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAssets"})
	ApiResponse getAllAssets(@RequestBody Map<String, Object>filter, Pageable pageable) {
		System.out.println(filter);
		logger.info("Received request to Get all asset");
		return assetService.getAllAssets(filter, pageable);
	}
	
	
//    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAsset"})
//    public ApiResponse getAllAsset(Pageable pageable) {
//	        logger.info("Received request to get all Asset");
//			return assetService.getAllAsset(pageable);
//	 }
    
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetById/{assetId}"})
    public ApiResponse getAssetById(@PathVariable String assetId) {
	        logger.info("Received request to get Asset by Id");
			return assetService.getAssetById(assetId);
	}
    
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetEmployeeById/{assetId}"})
    public ApiResponse getAssetEmployeeById(@PathVariable String assetId, Pageable pageable) {
	        logger.info("Received request to get Asset Employee by Id");
			return assetService.getAssetEmployeeById(assetId, pageable);
	}
    
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetBillingById/{assetId}"})
    public ApiResponse getAssetBillingId(@PathVariable String assetId, Pageable pageable) {
	        logger.info("Received request to get Asset Billing by Id");
			return assetService.getAssetBillingById(assetId, pageable);
	}
    
    
    
//    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetByVendorName/{vendorName}"})
//    public ApiResponse getAssetByVendorName(Pageable pageable, @PathVariable String vendorName ) {
//	        logger.info("Received request to get Asset by Vendor Name");
//			return assetService.getAssetByVendorName(pageable, vendorName);
//	}
    
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAsset/{assetId}"})
    public ApiResponse searchAsset(@PathVariable String assetId) {
		logger.info("Received request to search Asset");
		return assetService.searchAsset(assetId);
	}
    
    
	
}
