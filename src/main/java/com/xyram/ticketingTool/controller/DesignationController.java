package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CorsFilter;

import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.DesiggnaionService;
import com.xyram.ticketingTool.service.RoleService;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/designation")
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

	/* @CrossOrigin("*") */
	@GetMapping("/getAllDesignation")
	public Page<Designation> getAllDesignation(Pageable pageable) {
		logger.info("indide RoleController :: getAllRole");
		return desiggnaionService.getAllgetAllDesignationRole(pageable);
	}
}
	