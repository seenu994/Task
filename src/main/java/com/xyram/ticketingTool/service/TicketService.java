package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketComments;

public interface TicketService {

	 
	ApiResponse createTickets(Ticket ticketRequest);
	
	ApiResponse getAllTicketsByStatus(String statusId);

	//Ticket ticket(Integer employeeId);

	ApiResponse cancelTicket(Ticket ticketRequest); 
	
	ApiResponse resolveTicket(Ticket ticketRequest);

	Ticket onHoldTicket(Ticket ticketRequest);

	ApiResponse editTicket(String ticketId, Ticket ticket);
	
	ApiResponse reopenTicket( String ticketId,Comments commentObj); 
	
	ApiResponse addComment(Comments commentObj); 
	
	ApiResponse editComment(Comments commentObj); 
	
	ApiResponse deleteComment(Comments commentObj);

	ApiResponse getAllTicket(Pageable pageable);
}
		


