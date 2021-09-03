package com.xyram.ticketingTool.service;

import java.util.Map;


import com.xyram.ticketingTool.entity.TicketAssignee;

public interface TicketAssignService {

	 Map<String, String> assignTicketToInfraTeam(Map<String, Object> requestMap); 
		
}
