package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Ticket;

public interface TicketService {

	 
	Ticket createTickets(Ticket ticketRequest);

	//Ticket ticket(Integer employeeId);

	Ticket cancelTicket(Ticket ticketRequest);

	Ticket onHoldTicket(Ticket ticketRequest);

	Ticket editTicket(Integer ticketId, Ticket ticket); 
}
		


