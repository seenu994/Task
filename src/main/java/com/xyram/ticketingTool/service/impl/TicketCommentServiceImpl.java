package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.TicketCommentRepository;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.TicketCommentsStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.TicketCommentService;
@Service
@Transactional
public class TicketCommentServiceImpl  implements TicketCommentService{

@Autowired
TicketCommentRepository  ticketCommentRespository;

@Override
public TicketComments addCommentForTicket(TicketComments ticketComments) {
	
	return ticketCommentRespository.save(ticketComments);
}
@Override
public TicketComments editTicketCommentns(Integer ticketCommentsId, TicketComments ticketComments) {
	TicketComments ticketCommentsEdit = ticketCommentRespository.getById(ticketCommentsId);
            
    if(ticketCommentsEdit != null) {
    	ticketCommentsEdit.setUpdatedBy(ticketCommentsEdit.getCreatedBy());
    	ticketCommentsEdit.setLastUpdatedAt(new Date());
    	ticketCommentsEdit.setStatus(ticketCommentsEdit.getStatus());
    	ticketCommentsEdit.setTicket(ticketCommentsEdit.getTicket());
    	ticketCommentsEdit.setTicketCommentDescription(ticketCommentsEdit.getTicketCommentDescription());
    	ticketCommentsEdit.setUpdatedBy(ticketCommentsEdit.getUpdatedBy());
    	return ticketCommentRespository.save(ticketCommentsEdit); 
    }
    else
    {
    	throw new ResourceNotFoundException("ticketComments not fund found with id "+ticketComments.getId());
    }

}
}


		