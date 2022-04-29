package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.CompanyWings;
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

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createWing",
			AuthConstants.HR_ADMIN_BASEPATH + "/createWing", AuthConstants.INFRA_USER_BASEPATH + "/createWing",
			AuthConstants.DEVELOPER_BASEPATH + "/createWing", AuthConstants.HR_BASEPATH + "/createWing",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createWing" })
	public ApiResponse createWing(@RequestBody CompanyWings wings) {
		logger.info("Request for creating wing");
		return companyWingService.createWing(wings);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllWing", AuthConstants.HR_ADMIN_BASEPATH + "/getAllWing",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllWing", AuthConstants.DEVELOPER_BASEPATH + "/getAllWing",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllWing", AuthConstants.HR_BASEPATH + "/getAllWing" })
	public ApiResponse getAllWing(Map<String, Object> filter) {
		logger.info("indide CatagoryController :: getAllWing");
		return companyWingService.getAllWing(filter);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateWing/{id}",
			AuthConstants.HR_ADMIN_BASEPATH + "/updateWing/{id}",
			AuthConstants.INFRA_USER_BASEPATH + "/updateWing/{id}",
			AuthConstants.DEVELOPER_BASEPATH + "/updateWing/{id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/updateWing/{id}", AuthConstants.HR_BASEPATH + "/updateWing/{id}" })
	public ApiResponse updateWing(@PathVariable String id, @RequestBody CompanyWings wings) {
		logger.info("Requesting for update wing");
		return companyWingService.updateWing(id, wings);
	}

}