package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.EmployeeLocation;
import com.xyram.ticketingTool.service.EmployeeLocationService;
import com.xyram.ticketingTool.util.AuthConstants;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class EmployeeLocationController {
	
private final Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@Autowired
	EmployeeLocationService employeeLocationService;
	
	@PostMapping(AuthConstants.ADMIN_BASEPATH +"/createLocation")
	public ApiResponse createLocation(@RequestBody EmployeeLocation locationReq) {
		logger.info("Request for creating Location");
		return employeeLocationService.createLocation(locationReq);
	}

}
