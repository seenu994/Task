package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetSoftware;
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
		logger.info("Received request to add Asset");
		return assetSoftwareService.addassetSoftware(assetSoftware);
	}

}
