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
import com.xyram.ticketingTool.service.AssetEmployeeService;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.AuthConstants;


@RestController
public class AssetEmployeeController {
	
      private final Logger logger = LoggerFactory.getLogger(AssetEmployeeController.class);
	
	@Autowired
	AssetEmployeeService assetEmployeeService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addAssetEmployee"})
	public ApiResponse addAssetEmployee(@RequestBody AssetEmployee assetEmployee) {
		logger.info("Received request to add Asset Employee");
		return assetEmployeeService.addAssetEmployee(assetEmployee);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editAssetEmployee/{assetId}"})
    public ApiResponse editAssetEmployee(@RequestBody AssetEmployee assetEmployee, @PathVariable String assetId) {
		logger.info("Received request to edit asset employee");
		return assetEmployeeService.editAssetEmployee(assetEmployee, assetId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateAssetEmployee/{assetId}"})
	ApiResponse updateAssetEmployee(@RequestBody AssetEmployee assetEmployee,@PathVariable String assetId) {
		logger.info("Received request to update Asset Employee");
		return assetEmployeeService.updateAssetEmployee(assetEmployee, assetId);
	}
		
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAssetEmployeeById/{assetId}"})
    public ApiResponse getAssetEmployeeById(@PathVariable String assetId, Pageable pageable) {
	        logger.info("Received request to get Asset Employee by Id");
			return assetEmployeeService.getAssetEmployeeById(assetId, pageable);
	}

}
