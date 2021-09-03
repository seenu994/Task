package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.entity.TicketComments;

public interface TicketCommentService {

	TicketComments addCommentForTicket(TicketComments ticketComments);

	TicketComments editTicketCommentns(Integer ticketCommentsId, TicketComments ticketComments); 
		
}
