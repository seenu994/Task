package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
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
import com.xyram.ticketingTool.entity.HelpDeskIssue;
import com.xyram.ticketingTool.service.HelpDeskIssueService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional

public class HelpdeskServiceImpl implements HelpDeskIssueService {

	private final Logger logger = LoggerFactory.getLogger(HelpdeskServiceImpl.class);	
	
	@Autowired
	HelpdeskIssueRepository helpdeskIssueRepository;

	@Autowired
	HelpDeskIssueService helpDeskIssueService;

	@Override
	public ApiResponse addIssue(HelpDeskIssue helpDeskIssue) {
		
		
		ApiResponse response = new ApiResponse(false);
		
		response = validateIssue(helpDeskIssue);
		if (response.isSuccess()) {
			System.out.println("Hello");
		
		if (helpDeskIssue != null) {
			//helpDeskIssue.setCreatedAt(new Date());
			//helpDeskIssue.setCreatedBy(currentUser.getName());
			
			helpdeskIssueRepository.save(helpDeskIssue);
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
	
	
	private ApiResponse validateIssue(HelpDeskIssue helpDeskIssue) {
		ApiResponse response = new ApiResponse(false);

		if (helpDeskIssue.getIssueName().equals("") || helpDeskIssue.getIssueName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issue is manditory");
		}
		
		
		response.setSuccess(true);
		return response;
	}


	@Override
	public ApiResponse editIssue(HelpDeskIssue Request, String issue_Id) {
		ApiResponse response = new ApiResponse(false);
		response = validateIssue(Request);
		//HelpDeskIssue issueRequest = helpdeskIssueRepository.getByIssueId(issue_Id);
		if (response.isSuccess()) {
			HelpDeskIssue issueRequest = helpdeskIssueRepository.getByIssueId(issue_Id);
			if (issueRequest != null) {
				issueRequest.setIssueName(Request.getIssueName());
				
				
				helpdeskIssueRepository.save(issueRequest);
				// AssetVendor assetVendorAdded = new AssetVendor();
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

