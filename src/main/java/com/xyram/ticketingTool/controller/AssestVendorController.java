package com.xyram.ticketingTool.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.service.AssetvendorService;

@RestController
@CrossOrigin
public class AssestVendorController {

	private final Logger logger = LoggerFactory.getLogger(AssestVendorController.class);

	@Autowired
	AssetvendorService assetvendorService;

	@PostMapping(value = "/createVendor")
	public ApiResponse addAssestVendor(@RequestBody AssetVendor vendor) {
		logger.info("Received request to add Asset vendor");
		return assetvendorService.addAssestVendor(vendor);
	}

	@PutMapping(value = "/editVendorById/{vendorId}")
	public ApiResponse editassetVendor(@RequestBody AssetVendor AssetVendorRequest,@PathVariable String vendorId) {
		logger.info("Received request to edit Asset vendor");

		return assetvendorService.editassetVendor(AssetVendorRequest,vendorId);
	}
	/*
	 * @GetMapping(value = "/getAllAssetVendor") public ApiResponse
	 * getAllAssetVendor(Pageable pageable) {
	 * logger.info("indide AssestVendorController ::getAllAssetVendor");
	 * 
	 * return assetvendorService.getAllAssetVendor(pageable); }
	 */
//	@GetMapping(value = "/getAllVendorList")
//	public ApiResponse getAllVendorList(Pageable pageable ) {
//		logger.info("indide AssestVendorController :: getAllVendorList");
//		return assetvendorService.getAllVendorList();
//	}
	
	
}
