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
	private final Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@Autowired
	AssetService assetService;
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAsset"})
	public ApiResponse addasset(@RequestBody Asset asset) {
		logger.info("Received request to add Asset");
		return assetService.addasset(asset);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAsset/{assetId}"})
    public ApiResponse editAsset(@RequestBody Asset asset,@PathVariable String id) {
		logger.info("Received request to edit Asset");
		return assetService.editAsset(asset,id);
	}
	
	
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllAsset"})
    public ApiResponse getAllAssets (Pageable pageable) {
	        logger.info("inside AssetContoller :: getAllAssets");
			return assetService.getAllAssets(pageable);
	}
    
    
    /*
    @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchAsset"})
    public ApiResponse searchAsset(@PathVariable String searchaid) {
		logger.info("inside AsssetContoller :: searchAsset ");
		return assetService.searchAsset(searchaid);
	}
	*/
}
