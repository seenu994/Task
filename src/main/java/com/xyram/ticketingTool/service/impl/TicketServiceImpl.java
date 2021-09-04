
package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.CommentRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketCommentRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketrepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userrepository;

	@Autowired
	TicketService ticketService;

	@Autowired
	TicketAttachmentService attachmentService;
	
	@Autowired
	TicketCommentServiceImpl commentService;
	


	@Override
	public Ticket createTickets(Ticket ticketRequest) {
		ticketRequest.setCreatedAt(new Date());
		ticketRequest.setLastUpdatedAt(new Date());
		ticketRequest.setStatus(TicketStatus.INITIATED);
		ticketRequest.setCreatedBy(ticketRequest.getCreatedBy());
		Ticket tickets = ticketrepository.save(ticketRequest);
		// attachmentService.storeImage(tickets);
		return tickets;
	}

	@Override
	public Ticket cancelTicket(Ticket ticketRequest) {
		return ticketrepository.findById(ticketRequest.getId()).map(ticket -> {
			ticket.setStatus(TicketStatus.CANCELLED);
			ticket.setUpdatedBy(ticketRequest.getUpdatedBy());
			ticket.setLastUpdatedAt(new Date());
			// return ticketrepository.save(ticket);
			return ticketrepository.save(ticket);
		}).orElseThrow(() -> new ResourceNotFoundException("ticket not found with id: " + ticketRequest.getId()));
	}

	@Override
	public Ticket onHoldTicket(Ticket ticketRequest) {
		return ticketrepository.findById(ticketRequest.getId()).map(ticket -> {
			ticket.setStatus(TicketStatus.ONHOLD);
			ticket.setUpdatedBy(ticketRequest.getUpdatedBy());
			ticket.setLastUpdatedAt(new Date());
			return ticketrepository.save(ticket);
		}).orElseThrow(() -> new ResourceNotFoundException("ticket not found with id:" + ticketRequest.getId()));

	}

	@Override
	public Ticket editTicket(Integer ticketId, Ticket ticketRequest) {
		return ticketrepository.findById(ticketId).map(ticket -> {

			ticket.setTicketDescription(ticketRequest.getTicketDescription());
			ticket.setCreatedBy(ticketRequest.getCreatedBy());
			ticket.setLastUpdatedAt(new Date());
			ticket.setPriority(ticketRequest.getPriority());
			ticket.setProjects(ticketRequest.getProjects());
			ticket.setStatus(ticketRequest.getStatus());
			ticket.setUpdatedBy(ticketRequest.getUpdatedBy());
			return ticketrepository.save(ticket);

		}).orElseThrow(() -> new ResourceNotFoundException("ticket   not found with id: "));
	}
	@Override
	public ApiResponse reopenTicket(Integer ticketId, Comments commentObj) {
		
		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		if(ticketObj != null) {
			if(ticketObj.getStatus().equals(TicketStatus.REOPEN)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_REOPEND);
				response.setContent(null);
			}
			else if(ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {
				
				if( commentObj.getTicketCommentDescription() == null || commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				}else {
					ticketObj.setStatus(TicketStatus.REOPEN);
//					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//					String username = auth.getPrincipal().toString();
					
//					User user = userrepository.get
					commentRepository.save(commentObj);
					
					ticketObj.setUpdatedBy(null);
					ticketObj.setLastUpdatedAt(new Date());
					ticketrepository.save(ticketObj);
					
				    					
					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_REOPENED);
					response.setContent(null);
				}
				
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_RESOLVED);
				response.setContent(null);
			}
			
			return response;
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}		
	}

}