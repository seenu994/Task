package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.service.SoftwareMasterService;
//import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin

public class SoftwareMasterController {
	private final Logger logger = LoggerFactory.getLogger(SoftwareMasterController.class);

	// @Autowired
	SoftwareMasterService softwareMasterService;

	@PostMapping(value = "/createsoftwareMaster")
	public ApiResponse addSoftwareMaster(@RequestBody SoftwareMaster softwareMaster) {
		logger.info("Received request to add softwareId");
		return softwareMasterService.addSoftwareMaster(softwareMaster);
	}

	@PutMapping(value = "/editSoftwareMaster")
	public ApiResponse editSoftwareMaster(@RequestBody SoftwareMaster SoftwareMasterRequest) {
		logger.info("received request to edit editSoftwareMaster");
		return softwareMasterService.editSoftwareMaster(SoftwareMasterRequest);
	}

}
