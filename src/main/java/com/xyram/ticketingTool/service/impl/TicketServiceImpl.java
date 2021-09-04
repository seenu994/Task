
package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	ProjectRepository projectRepository;

	@Autowired
	TicketService ticketService;

	@Autowired
	TicketAttachmentService attachmentService;

	@Autowired
	TicketCommentServiceImpl commentService;

	@Override
	public ApiResponse createTickets(Ticket ticketRequest) {
		ApiResponse response = new ApiResponse(false);

		Projects project = projectRepository.getById(ticketRequest.getProjects());

		if (project == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_NOTEXIST);
			response.setContent(null);
			return response;
		} else {
			ticketRequest.setCreatedAt(new Date());
			ticketRequest.setLastUpdatedAt(new Date());
			ticketRequest.setStatus(TicketStatus.INITIATED);
			ticketRequest.setCreatedBy(ticketRequest.getCreatedBy());
			Ticket tickets = ticketrepository.save(ticketRequest);
			// attachmentService.storeImage(tickets);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_ADDED);
			Map<String, Integer> content = new HashMap<String, Integer>();
			content.put("ticketId", tickets.getId());
			response.setContent(content);
			return response;
		}

	}

	@Override
	public ApiResponse cancelTicket(Ticket ticketObj) {
		ApiResponse response = new ApiResponse(false);

		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.CANCELLED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_CANCELLED);
				response.setContent(null);
			} else if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				ticketObj.setStatus(TicketStatus.CANCELLED);
				ticketObj.setUpdatedBy(null);
				ticketObj.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
	}

	@Override
	public ApiResponse resolveTicket(Ticket ticketObj) {
		ApiResponse response = new ApiResponse(false);

		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			} else if (ticketObj.getStatus().equals(TicketStatus.ONREVIEW)) {

				ticketObj.setStatus(TicketStatus.COMPLETED);
				ticketObj.setUpdatedBy(null);
				ticketObj.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_RESOLVED);
				response.setContent(null);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_IN_REVIEW);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
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
	public ApiResponse editTicket(Integer ticketId, Ticket ticketRequest) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				ticketObj.setTicketDescription(ticketRequest.getTicketDescription());
				ticketObj.setLastUpdatedAt(new Date());
				ticketObj.setPriority(ticketRequest.getPriority());
				ticketObj.setProjects(ticketRequest.getProjects());
				ticketObj.setStatus(ticketRequest.getStatus());
				ticketObj.setUpdatedBy(ticketRequest.getUpdatedBy());
				ticketrepository.save(ticketObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EDITED);
				response.setContent(null);	

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			}

		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse reopenTicket(Integer ticketId, Comments commentObj) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		if (ticketObj != null) {
			if (ticketObj.getStatus().equals(TicketStatus.REOPEN)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_REOPEND);
				response.setContent(null);
			} else if (ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				if (commentObj.getTicketCommentDescription() == null
						|| commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				} else {
					ticketObj.setStatus(TicketStatus.REOPEN);
					commentRepository.save(commentObj);

					ticketObj.setUpdatedBy(null);
					ticketObj.setLastUpdatedAt(new Date());
					ticketrepository.save(ticketObj);

					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_REOPENED);
					response.setContent(null);
				}

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_RESOLVED);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
	}
	
	@Override
	public ApiResponse addComment(Comments commentObj) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				if (commentObj.getTicketCommentDescription() == null
						|| commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				} else {
					commentRepository.save(commentObj);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_ADDED);
					response.setContent(null);
				}

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
	}
	
	@Override
	public ApiResponse editComment(Comments commentObj) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {
				
				Comments comment = commentRepository.getById(commentObj.getId());
				if(comment != null) {
					if (commentObj.getTicketCommentDescription() == null
							|| commentObj.getTicketCommentDescription().length() == 0) {
						response.setSuccess(false);
						response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
						response.setContent(null);
					} else {
						commentRepository.save(commentObj);
						response.setSuccess(true);
						response.setMessage(ResponseMessages.TICKET_COMMENTS_EDITED);
						response.setContent(null);
					}
				}else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_RECORD_NOTEXIST);
					response.setContent(null);
				}
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
	}
	
	@Override
	public ApiResponse deleteComment(Comments commentObj) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {
				
				Comments comment = commentRepository.getById(commentObj.getId());
				if(comment != null) {
					
					commentRepository.delete(commentObj);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_DELETED);
					response.setContent(null);
					
				}else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_RECORD_NOTEXIST);
					response.setContent(null);
				}
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			}

			return response;
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
			return response;
		}
	}

}