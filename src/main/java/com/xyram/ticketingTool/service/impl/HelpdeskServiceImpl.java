package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.HelpdeskIssueRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Country;
import com.xyram.ticketingTool.entity.HelpDeskIssueTypes;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.service.HelpDeskIssueService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional

public class HelpdeskServiceImpl implements HelpDeskIssueService {

	private final Logger logger = LoggerFactory.getLogger(HelpdeskServiceImpl.class);	
	
	@Autowired
	HelpdeskIssueRepository helpdeskIssueRepository;

	

	@Override
	public ApiResponse addIssueType(HelpDeskIssueTypes helpDeskIssueTypes) {
		
		
		ApiResponse response = new ApiResponse(false);
		
		response = validateIssue(helpDeskIssueTypes);
		if (response.isSuccess()) {
			
		
		if (helpDeskIssueTypes != null) {
			//helpDeskIssue.setCreatedAt(new Date());
			//helpDeskIssue.setCreatedBy(currentUser.getName());
			helpDeskIssueTypes.setHelpdeskStatus(true);
			helpdeskIssueRepository.save(helpDeskIssueTypes);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ADDED_ISSUE);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ISSUE_NOT_ADDED);
		}
	}
	return response;
}
	
	
	private ApiResponse validateIssue(HelpDeskIssueTypes helpDeskIssueTypes) {
		ApiResponse response = new ApiResponse(false);
		HelpDeskIssueTypes  issuesRequest = helpdeskIssueRepository.getByIssue(helpDeskIssueTypes.getHelpdeskIssueType());
		if (helpDeskIssueTypes.getHelpdeskIssueType().equals("") || helpDeskIssueTypes.getHelpdeskIssueType() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issue is manditory");
		}
		if(issuesRequest != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Issue is already exist");
		}
		
		
		response.setSuccess(true);
		return response;
	}


	@Override
	public ApiResponse editIssueType(HelpDeskIssueTypes Request, String issueTypeId) {
		ApiResponse response = new ApiResponse(false);
		response = validateIssue(Request);
		//HelpDeskIssue issueRequest = helpdeskIssueRepository.getByIssueId(issue_Id);
		if (response.isSuccess()) {
			HelpDeskIssueTypes issueRequest = helpdeskIssueRepository.getByIssueId(issueTypeId);
			if (issueRequest != null) {
				issueRequest.setHelpdeskIssueType(Request.getHelpdeskIssueType());
				
				issueRequest.setHelpdeskStatus(Request.isHelpdeskStatus());
				helpdeskIssueRepository.save(issueRequest);
				
				response.setSuccess(true);
				response.setMessage(ResponseMessages.EDIT_ISSUE);
//				Map content = new HashMap();
//				content.put("vendorId", assetVendorAdded.getAssetVendor());
//				response.setContent(content);

//			} else {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.VENDOR_DETAILS_INVALID);
//				// response.setContent(null);
//			}
			}
		}
		return response;

	}


	@Override
	public ApiResponse getAllissues(Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> issues = helpdeskIssueRepository.getAllIssues(pageable);
	
		Map content = new HashMap();
		content.put("issues", issues);
		if(content != null) {
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.ISSUES_LIST_RETRIVED);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}


	@Override
	public ApiResponse searchhelpdeskIssueType(String searchString) {
		
ApiResponse response = new ApiResponse(false);

		List<Map> issueObj = helpdeskIssueRepository.searchhelpdeskIssueType(searchString);

		Map content = new HashMap();
		content.put("issueObj", issueObj);
		if (content != null) {			
			response.setSuccess(true);
			response.setMessage("Country retrived successfully");
			response.setContent(content);
			
		} else {
			
			response.setSuccess(false);
			
			response.setMessage("Not retrived the data");
		}

		return response;
		
	}
	
		
		



	/*@Override
	public ApiResponse getIssues(String Id, Pageable pageable) {
	
			ApiResponse response = new ApiResponse();
			Page<Map> asset = helpdeskIssueRepository.getIssueById(Id, pageable);
			Map content = new HashMap();
			content.put("asset", helpDeskIssueService);
			if(content != null) {
				response.setSuccess(true);
				response.setMessage("Issues Retrieved Successfully");
				response.setContent(content);
			}
			else {
				response.setSuccess(false);
				response.setMessage("Could not retrieve data");
			}
			return response;
		}*/
		
		
	
			
		
	}

