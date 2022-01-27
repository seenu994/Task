package com.xyram.ticketingTool.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.CompanyWingService;
import com.xyram.ticketingTool.util.AuthConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@RestController
@CrossOrigin
 public class CompanyWingsController {
	private final Logger logger = LoggerFactory.getLogger(CompanyWingsController.class);

	@Autowired
	CompanyWingService companyWingService;
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllWing",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllWing",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllWing",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllWing",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllWing",
			AuthConstants.HR_BASEPATH + "/getAllWing"})
	public ApiResponse getAllWing(Map<String, Object> filter) {
		logger.info("indide CatagoryController :: getAllWing");
		return companyWingService.getAllWing(filter);
	}
}