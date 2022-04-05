//package com.xyram.ticketingTool.controller;
//
//import javax.transaction.Transactional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.xyram.ticketingTool.apiresponses.ApiResponse;
//import com.xyram.ticketingTool.entity.AssetVendor;
//import com.xyram.ticketingTool.entity.SoftwareMaster;
//import com.xyram.ticketingTool.service.AssetvendorService;
//import com.xyram.ticketingTool.service.SoftwareMasterService;
//
//@Repository  
//@Transactional
//public class SoftwareMasterController {
//	private final Logger logger = LoggerFactory.getLogger(SoftwareMasterController.class);
//
//
//	@Autowired
//	SoftwareMasterService softwareMasterService;
//
//	@PostMapping(value = "/createsoftwareMaster")
//	public ApiResponse addSoftwareMaster(@RequestBody SoftwareMaster softwareMaster) {
//		logger.info("Received request to add softwareId");
//		return softwareMasterService.addSoftwareMaster(softwareMaster);
//	}
//
//	
//}
