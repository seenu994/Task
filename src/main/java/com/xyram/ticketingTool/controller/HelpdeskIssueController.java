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
import com.xyram.ticketingTool.entity.HelpDeskIssueTypes;
import com.xyram.ticketingTool.service.HelpDeskIssueService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController

public class HelpdeskIssueController {

	
	private final Logger logger = LoggerFactory.getLogger(HelpdeskIssueController.class);

	@Autowired
	HelpDeskIssueService helpDeskIssueService;
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addHelpDeskIssue",
			AuthConstants.INFRA_USER_BASEPATH + "/addIssue",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/addHelpDeskIssue" })
	public ApiResponse addIssue(@RequestBody HelpDeskIssueTypes helpDeskIssue) {
		logger.info("Received request to add issue");
		return helpDeskIssueService.addIssueType(helpDeskIssue);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editIssues/{issueTypeId}",
			AuthConstants.INFRA_USER_BASEPATH + "/editIssues/{issueTypeId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editIssues/{issueTypeId}" })
	public ApiResponse editIssue(@RequestBody HelpDeskIssueTypes Request,
			@PathVariable String issueTypeId) {
		logger.info("received request to editIssue");
		return helpDeskIssueService.editIssueType(Request, issueTypeId);
	}
	
	/*@GetMapping(value = { AuthConstants.INFRA_ADMIN_BASEPATH + "/getIssues/{issue_Id}",
			AuthConstants.INFRA_USER_BASEPATH + "/getIssues/{issue_Id}",
			AuthConstants.ADMIN_BASEPATH + "/getIssues/{issue_Id}"})
    public ApiResponse getIssues(@PathVariable String issue_Id, Pageable pageable) {
	        logger.info("Received request to get issue by Id");
			return helpDeskIssueService.getIssues(issue_Id, pageable);
	}*/
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getAllIssues",
	   		  AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllIssues",
	             AuthConstants.INFRA_USER_BASEPATH + "/getAllIssues"})
	     public ApiResponse getAllIssues(Pageable pageable) {
	 	        logger.info("Received request to get all issues");
	 			return helpDeskIssueService.getAllissues(pageable);
	 	 }
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/searchhelpdeskIssueType/{searchString}",
			 AuthConstants.INFRA_ADMIN_BASEPATH + "/searchhelpdeskIssueType/{searchString}",
             AuthConstants.INFRA_USER_BASEPATH + "/searchhelpdeskIssueType/{searchString}"})
	 public ApiResponse searchhelpdeskIssueType(@PathVariable String searchString) {
			logger.info("Received request to search IssueType");
			return helpDeskIssueService.searchhelpdeskIssueType(searchString);
	 }

	
	
}
