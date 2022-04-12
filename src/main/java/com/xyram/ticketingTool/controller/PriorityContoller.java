package com.xyram.ticketingTool.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Priority;
import com.xyram.ticketingTool.service.PriorityService;

/**
 * 
 * @author sahana.neelappa
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/priority")
public class PriorityContoller {
	private final Logger logger = LoggerFactory.getLogger(PriorityContoller.class);
	@Autowired
	PriorityService priorityService;
	
	
	/**
	 * getAllRole
	 * 
	 * URL:http://<server name>/<context>/api/catogory/getAllCatagory
	 * 
	 * @requestType get
	 * @return List<Category>
	 */

	/* @CrossOrigin("*") */
	@GetMapping("/getAllPriority")
	public Page<Priority> getAllPriority(Pageable pageable) {
		logger.info("indide PriorityContoller :: getAllPriority");
		return priorityService.getAllPriority(pageable);
	}
}
	