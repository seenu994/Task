package com.xyram.ticketingTool.service;

import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TicketAssignee;

public interface TicketAssignService {

	 ApiResponse assignTicketToInfraTeam(TicketAssignee assignee);  
	 
	 ApiResponse reassignTicketToInfraTeam(TicketAssignee assignee);
		
}
