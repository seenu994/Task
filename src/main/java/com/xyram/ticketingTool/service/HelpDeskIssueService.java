package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HelpDeskIssueTypes;

public interface HelpDeskIssueService {

	ApiResponse addIssueType(HelpDeskIssueTypes helpDeskIssue);
	ApiResponse editIssueType(HelpDeskIssueTypes Request, String issueTypeId);
	//ApiResponse getIssues(String issue_Id, Pageable pageable);
	ApiResponse getAllissues(Pageable pageable);
	ApiResponse searchhelpdeskIssueType(String searchString);
}
