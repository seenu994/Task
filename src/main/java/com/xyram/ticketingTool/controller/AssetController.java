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

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController

public class AssetController {
	/*private final Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@Autowired
	AssetService assetService;
	
	
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAsset"})
	public ApiResponse addasset(@RequestBody Asset asset) {
		logger.info("Received request to add Asset");
		return assetService.addasset(asset);
	}
	
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAsset"})
    public ApiResponse getAllAssets (java.awt.print.Pageable pageable) {
	        logger.info("inside AssetContoller :: getAllAssets");
			return assetService.getAllAssets(pageable);
	}
    
    @PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAsset"})
    public ApiResponse editAsset(@RequestBody Asset AssetRequest) {
		logger.info("inside AssetContoller :: editAsset");
		return assetService.editAsset(AssetRequest);
	}
    
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAsset"})
    public ApiResponse searchAsset(@PathVariable String searchString) {
		logger.info("inside AsssetContoller :: searchAsset ");
		return assetService.searchAsset(searchString);
	}*/
	
}
