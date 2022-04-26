package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HelpDeskIssue;
import com.xyram.ticketingTool.entity.SoftwareMaster;

public interface HelpDeskIssueService {

	ApiResponse addIssue(HelpDeskIssue helpDeskIssue);
	
}
