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
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.service.DesiggnaionService;
import com.xyram.ticketingTool.util.AuthConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@CrossOrigin
@RestController

public class DesignationController {
	private final Logger logger = LoggerFactory.getLogger(DesignationController.class);
	@Autowired
	DesiggnaionService desiggnaionService;
	
	
	/**
	 * getAllRole
	 * 
	 * URL:http://<server name>/<context>/api/catogory/getAllCatagory
	 * 
	 * @requestType get
	 * @return List<Category>
	 */

	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllDesignation",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllDesignation", AuthConstants.DEVELOPER_BASEPATH + "/getAllDesignation", AuthConstants.HR_ADMIN_BASEPATH + "/getAllDesignation",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllDesignation" })
	public ApiResponse getAllDesignation(Pageable pageable) {
		logger.info("indide Designation Controller :: getAllDesignation");
		return desiggnaionService.getAllDesignation(pageable);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addDesignation",
			AuthConstants.INFRA_USER_BASEPATH + "/addDesignation", AuthConstants.DEVELOPER_BASEPATH + "/addDesignation", AuthConstants.HR_ADMIN_BASEPATH + "/addDesignation",AuthConstants.INFRA_ADMIN_BASEPATH + "/addDesignation" })
	public ApiResponse addDesignation(@RequestBody Designation designation) {
		logger.info("indide Designation Controller :: getAllDesignation");
		return desiggnaionService.addDesignation(designation);
	}
	
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editDesignation/{Id}",
			AuthConstants.INFRA_USER_BASEPATH + "/editDesignation/{Id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editDesignation/{Id}" })
	public ApiResponse editDesignation(@RequestBody Designation Request, @PathVariable String Id) {
		logger.info("Received request to edit designation");

		return desiggnaionService.editDesignation(Request,Id);
	}
}
	