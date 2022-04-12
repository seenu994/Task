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
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.AssetSoftware;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.service.AssetSoftwareService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class AssetSoftwareController {
	
private final Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@Autowired
	AssetSoftwareService assetSoftwareService;
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAssetSoftware"})
	public ApiResponse addassetSoftware(@RequestBody AssetSoftware assetSoftware) {
		logger.info("Received request to add Asset Software");
		return assetSoftwareService.addassetSoftware(assetSoftware);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateAssetSoftware/{assetId}"})
	ApiResponse updateAssetSoftware(@RequestBody AssetSoftware assetSoftware,@PathVariable String assetId) {
		logger.info("Received request to update Asset Software");
		return assetSoftwareService.updateAssetSoftware(assetSoftware, assetId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetSoftwareById/{assetId}"})
    public ApiResponse getAssetSoftwareId(@PathVariable String assetId, Pageable pageable) {
	        logger.info("Received request to get Asset Software by Id");
			return assetSoftwareService.getAssetSoftwareById(assetId, pageable);
	}

}
