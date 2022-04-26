package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HelpDeskIssue;
import com.xyram.ticketingTool.service.HelpDeskIssueService;
import com.xyram.ticketingTool.util.AuthConstants;



public class HelpdeskIssueController {

	
	private final Logger logger = LoggerFactory.getLogger(HelpdeskIssueController.class);

	@Autowired
	HelpDeskIssueService helpDeskIssueService;
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addIssue",
			AuthConstants.INFRA_USER_BASEPATH + "/addIssue",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/addIssue" })
	public ApiResponse addIssue(@RequestBody HelpDeskIssue helpDeskIssue) {
		logger.info("Received request to add issue");
		return helpDeskIssueService.addIssue(helpDeskIssue);
	}
	
}
