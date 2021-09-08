
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.CommentRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.Repository.ticketAttachmentRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
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
	
	@Autowired
	CurrentUser userDetail;
	
	@Override
	public ApiResponse getAllTicketsByStatus() {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		//List<Ticket> allTickets = ticketrepository.findAllByNameAndCreatedAndTicketStatus(statusId, userDetail.getUserId());
		List<String> allTickets = ticketrepository.getAllTicketsByStatus(userDetail.getUserId(), userDetail.getUserRole());
		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST);
			Map<String, List<String>> content = new HashMap<String, List<String>>();
			content.put("tickets", allTickets);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			
			response.setContent(null);
		}
		
		return response;
	}
	
	@Override
	public ApiResponse getAllCompletedTickets() {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		//List<Ticket> allTickets = ticketrepository.findAllByNameAndCreatedAndTicketStatus(statusId, userDetail.getUserId());
		List<Map> allTickets = ticketrepository.getAllCompletedTickets(userDetail.getUserId(), userDetail.getUserRole());
		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST);
			Map<String, List<Map>> content = new HashMap<String, List<Map>>();
			content.put("tickets", allTickets);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			
			response.setContent(null);
		}
		
		return response;
	}

	@Override
	public ApiResponse createTickets(Ticket ticketRequest) {
		ApiResponse response = new ApiResponse(false);

		Projects project = projectRepository.getById(ticketRequest.getProjectId());

		if (project == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_NOTEXIST);
			response.setContent(null);
			return response;
		} else {
			ticketRequest.setCreatedAt(new Date());
			ticketRequest.setLastUpdatedAt(new Date());
			ticketRequest.setCreatedBy(userDetail.getUserId());
			ticketRequest.setStatus(TicketStatus.INITIATED);
			Ticket tickets = ticketrepository.save(ticketRequest);
			// attachmentService.storeImage(tickets);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_ADDED);
			Map<String, String> content = new HashMap<String, String>();
			content.put("ticketId", tickets.getId());
			response.setContent(content);
			return response;
		}

	}

	@Override
	public ApiResponse cancelTicket(String ticketId) {
		ApiResponse response = new ApiResponse(false);
		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		//System.out.println("Status :: "+ticketNewRequest.getStatus());
		if (ticketNewRequest != null) {
			if (ticketNewRequest.getStatus().equals(TicketStatus.CANCELLED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_CANCELLED);
				response.setContent(null);
			} else if (!ticketNewRequest.getStatus().equals(TicketStatus.COMPLETED)) {
				ticketNewRequest.setStatus(TicketStatus.CANCELLED);
				ticketNewRequest.setUpdatedBy(userDetail.getUserId());
				ticketNewRequest.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketNewRequest);

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
	public ApiResponse resolveTicket(String ticketId) { {
		ApiResponse response = new ApiResponse(false);
		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		System.out.println("Status :: "+ticketNewRequest.getStatus());
		if (ticketNewRequest != null) {
			if (ticketNewRequest.getStatus().equals(TicketStatus.COMPLETED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_RESOLVED);
				response.setContent(null);
			} else if (ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {

				ticketNewRequest.setStatus(TicketStatus.COMPLETED);
				ticketNewRequest.setUpdatedBy(userDetail.getUserId());
				ticketNewRequest.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketNewRequest);

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
	}}

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
	public ApiResponse editTicket(String ticketId, Ticket ticketRequest) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		//String s = ticketrepository.getTicketById(ticketId);
		//System.out.println("s Value " + s);
		if (ticketObj != null ) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				ticketObj.setTicketDescription(ticketRequest.getTicketDescription());
				ticketObj.setLastUpdatedAt(new Date());
				ticketObj.setPriorityId(ticketRequest.getPriorityId());
				ticketObj.setProjectId(ticketRequest.getProjectId());
				ticketObj.setStatus(ticketRequest.getStatus());
				ticketObj.setUpdatedBy(userDetail.getUserId());
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
	public ApiResponse reopenTicket(String ticketId, Comments commentObj) {

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

					ticketObj.setUpdatedBy(userDetail.getUserId());
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
		//Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
		String ticketObj = ticketrepository.getTicketById(commentObj.getTicketId());
		if (ticketObj != null && ticketObj != "") {
		//if (ticketObj != null) {
			//if (!ticketObj.equalsIgnoreCase(TicketStatus.COMPLETED)) {
			if (!ticketObj.equalsIgnoreCase(TicketStatus.COMPLETED.toString())) {
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

	
	@Override
	public ApiResponse getAllTicket(Pageable pageable) {
       Page<Map> TicketList =   ticketrepository.getAllTicketList(pageable);
       Map content = new HashMap();
       content.put("TicketList", TicketList);
       ApiResponse response = new ApiResponse(true);
       response.setMessage(ResponseMessages.TICKET_LIST);
       response.setSuccess(true);
       response.setContent(content);
       return  response;
	}

	@Override
	public ApiResponse inprogressTicket(String ticketId) {
			ApiResponse response = new ApiResponse(false);
			
			Ticket ticketNewRequest = ticketrepository.getById(ticketId);
			//System.out.println("Status :: "+ticketNewRequest.getStatus());
			if (ticketNewRequest != null) {
				if (ticketNewRequest.getStatus() .equals( TicketStatus.INPROGRESS)) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_ALREADY_INPROGRESS);
					response.setContent(null);
				}else {
					if (ticketNewRequest.getStatus() == TicketStatus.ASSIGNED) {

						ticketNewRequest.setStatus(TicketStatus.INPROGRESS);
						ticketNewRequest.setUpdatedBy(userDetail.getUserId());
						ticketNewRequest.setLastUpdatedAt(new Date());
						ticketrepository.save(ticketNewRequest);

						response.setSuccess(true);
						response.setMessage(ResponseMessages.TICKET_ASSIGNED);
						response.setContent(null);

					} else {
						response.setSuccess(false);
						response.setMessage(ResponseMessages.TICKET_NOT_IN_ASSIGNE_STATE);
						response.setContent(null);
					}
				}
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
				response.setContent(null);
				//return response;
			}
			return response;
		}
	}