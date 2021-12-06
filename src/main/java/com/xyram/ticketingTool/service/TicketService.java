package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Ticket;
//import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.TicketStatus;


public interface TicketService {

	 
	ApiResponse createTickets(MultipartFile[] files, String ticketRequest);
	
	ApiResponse getAllTicketsByStatus(TicketStatus status, Pageable pageable);
	
	ApiResponse getAllCompletedTickets(Pageable pageable);

	//Ticket ticket(Integer employeeId);

	ApiResponse cancelTicket(String ticketId); 
	
	ApiResponse resolveTicket(String ticketId);

	//Ticket onHoldTicket(Ticket ticketRequest);

	ApiResponse editTicket(MultipartFile[] files, String ticketId, String ticketRequest);
	
	ApiResponse reopenTicket( String ticketId,Comments commentObj); 
	
	ApiResponse addComment(Comments commentObj); 
	
	ApiResponse editComment(Comments commentObj); 
	


	ApiResponse getAllTicket(Pageable pageable);

	ApiResponse inprogressTicket(String ticketId);
	
	ApiResponse getTktDetailsById(String ticketId);
	
	ApiResponse getTicketSearchById(String ticketId);

	ApiResponse onHoldTicket(String ticketId);
	

	Optional <Ticket> findById(String Id);
	List<Ticket> findAll();

	
	ApiResponse getAllTicketsByDuration(Pageable pageable, String date1, String date2);
ApiResponse getTicketStatusCountWithProject(Pageable pageable);
	ApiResponse searchTicket(String searchString);

	

	ApiResponse deleteComment(Comments commentObj);
 ApiResponse getAllTicketsDetails(Pageable pageable);

ApiResponse getTicketDtlsByProjectNameAndStatus(Map<String, Object> filter, Pageable pageable);

ApiResponse getAllTicketsByStatusMobile(Pageable pageable);

ApiResponse getTicketCount();


}
		


