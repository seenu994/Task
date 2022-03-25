package com.xyram.ticketingTool.controller;

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

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.AuthConstants;
@RestController
@CrossOrigin
public class AssestVendorController {
	


private final Logger logger = LoggerFactory.getLogger(AssestVendorController.class);
	
	@Autowired
	AssetvendorService assetvendorService;

	@PostMapping(value = "/createVendor" )
	public ApiResponse addAssestVendor(@RequestBody AssetVendor vendor) { 
		logger.info("Received request to add Asset vendor");
		return assetvendorService.addAssestVendor(vendor);
	}
	
	/*@GetMapping(value = "/getAllAssetVendorList")
    public ApiResponse getAllAssetVendorList (java.awt.print.Pageable pageable) {
	        logger.info("inside AssestVendorController :: getAssetvendorService");
	        return assetvendorService.getAllAssetVendorList(pageable);
	        }*/

	
	/*public ApiResponse getAllByvendorId(@PathVariable String vendorID) {
		logger.info("inside AssestVendorController :: getAllByvendorId ");
		return assetvendorService.getAllByvendorId(vendorID);
	}*/

	
	
	/*
	 * @PutMapping(value = "/editassetVendorList") public ApiResponse
	 * editassetVendorList(@RequestBody AssetVendor AssetVendorRequest) {
	 * logger.info("inside AssestVendorController :: editassetVendorList"); return
	 * assetvendorService.editassetVendorList(AssetVendorRequest); }
	 */
}