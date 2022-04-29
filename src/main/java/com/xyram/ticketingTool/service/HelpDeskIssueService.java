package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HelpDeskIssue;

public interface HelpDeskIssueService {

	ApiResponse addIssue(HelpDeskIssue helpDeskIssue);
	ApiResponse editIssue(HelpDeskIssue Request, String issue_Id);
	//ApiResponse getIssues(String issue_Id, Pageable pageable);
	ApiResponse getAllissues(Pageable pageable);
}
