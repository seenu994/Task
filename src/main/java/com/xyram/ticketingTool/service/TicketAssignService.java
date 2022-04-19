package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TicketAssignee;

public interface TicketAssignService {

	 ApiResponse assignTicketToInfraTeam(TicketAssignee assignee);  
	 
	 ApiResponse reassignTicketToInfraTeam(TicketAssignee assignee);
		
}
