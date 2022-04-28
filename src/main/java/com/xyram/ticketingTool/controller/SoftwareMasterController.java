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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.SoftwareEnum;
import com.xyram.ticketingTool.service.SoftwareMasterService;
import com.xyram.ticketingTool.util.AuthConstants;
@CrossOrigin
@RestController


public class SoftwareMasterController {
	private final Logger logger = LoggerFactory.getLogger(SoftwareMasterController.class);

	@Autowired
	SoftwareMasterService softwareMasterService;
	
	

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createsoftwareMaster",
			AuthConstants.INFRA_USER_BASEPATH + "/createsoftwareMaster",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createsoftwareMaster" })
	public ApiResponse addSoftwareMaster(@RequestBody SoftwareMaster softwareMaster) {
		logger.info("Received request to add softwareId");
		return softwareMasterService.addSoftwareMaster(softwareMaster);
	}
	
	

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editSoftwareMaster/{softwareId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editSoftwareMaster/{softwareId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editSoftwareMaster/{softwareId}" })
	public ApiResponse editSoftwareMaster(@RequestBody SoftwareMaster SoftwareMasterRequest,
			@PathVariable String softwareId) {
		logger.info("received request to editSoftwareMaster");
		return softwareMasterService.editSoftwareMaster(SoftwareMasterRequest, softwareId);
	}
	

//	@GetMapping(value = "/getAllsoftwareMasterList")
//	public ApiResponse getAllsoftwareMasterList(Pageable pageable) {
//		logger.info("Received request to getAllsoftwareMasterList");
//		return softwareMasterService.getAllsoftwareMasterList(pageable);
//}
	

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllsoftwareMaster",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllsoftwareMaster",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllsoftwareMaster" })
	public ApiResponse getAllsoftwareMaster(@RequestParam Map<String, Object> filter, Pageable pageable) {
		logger.info("Received request to getAllsoftwareMasterLust");
		return softwareMasterService.getAllsoftwareMaster(filter, pageable);
	}
	
	

	@PutMapping(value = {AuthConstants.ADMIN_BASEPATH + "/changeSoftwareMasterStatus/{softwareId}/{softwareMasterStatus}",
			AuthConstants.INFRA_USER_BASEPATH + "/changeSoftwareMasterStatus/{softwareId}/{softwareMasterStatus}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/changeSoftwareMasterStatus/{softwareId}/{softwareMasterStatus}" })

	public ApiResponse editSoftwareMasterStatus(@PathVariable String softwareId,
			@PathVariable SoftwareEnum softwareMasterStatus) {
		logger.info("Received request to change softwareMaster status to: " + softwareMasterStatus + "for softwareId: "
				+ softwareId);
		return softwareMasterService.updatesoftwareMasterStatus(softwareId, softwareMasterStatus);
	}
	
	

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchsoftwareMaster/{softwareId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchVendor/{vendorName}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchVendor/{vendorName}" })
	public ApiResponse searchsoftwareId(@PathVariable String softwareId) {
		logger.info("Received request to search softwareId");
		return softwareMasterService.searchsoftwareId(softwareId);
	}
}