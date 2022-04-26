package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyram.ticketingTool.Repository.HelpdeskIssueRepository;
import com.xyram.ticketingTool.Repository.SoftwareMasterRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HelpDeskIssue;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.service.HelpDeskIssueService;
import com.xyram.ticketingTool.service.SoftwareMasterService;
import com.xyram.ticketingTool.util.ResponseMessages;

public class HelpdeskServiceImpl implements HelpDeskIssueService {

	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);	
	
	@Autowired
	HelpdeskIssueRepository helpdeskIssueRepository;

	@Autowired
	HelpDeskIssueService helpDeskIssueService;

	@Override
	public ApiResponse addIssue(HelpDeskIssue helpDeskIssue) {
		
		
		ApiResponse response = new ApiResponse(false);
		if (response.isSuccess()) {
		//response = validateSoftwareMaster(helpDeskIssue);
		if (helpDeskIssue.getIssueName() != null) {
			//helpDeskIssue.setCreatedAt(new Date());
			//helpDeskIssue.setCreatedBy(currentUser.getName());
			
			//HelpDeskIssue helpdeskissue= helpdeskIssueRepository.save(helpDeskIssue);
			response.setMessage(ResponseMessages.ADDED_SOFTWAREMASTER);
			response.setSuccess(true);

			Map content = new HashMap();
			content.put("IssueId", helpDeskIssue.getIssue_Id());
			content.put("IssueName",helpDeskIssue.getIssueName());
			
			response.setContent(content);
		}
		}
		return response;
	
	}
	
}
