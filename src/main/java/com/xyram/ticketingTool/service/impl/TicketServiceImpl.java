
package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.CommentRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.NotificationsRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.Repository.TicketStatusHistRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketStatusHistory;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.UserRole;
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
	PushNotificationCall pushNotificationCall;

	@Autowired
	PushNotificationRequest pushNotificationRequest;

	@Autowired
	TicketAttachmentService attachmentService;

	@Autowired
	NotificationsRepository notificationsRepository;

	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;
	
	@Autowired
	EmployeeRepository employeeRepository;

	/*
	 * @Autowired TicketCommentServiceImpl commentService;
	 */

	@Autowired
	CurrentUser userDetail;

	@Autowired
	TicketStatusHistRepository tktStatusHistory;

	@Override
	public ApiResponse getAllTicketsByStatus() {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		List<Map> allTickets = ticketrepository.getAllTicketsByStatus(userDetail.getUserId(), userDetail.getUserRole());
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
	public ApiResponse getAllCompletedTickets() {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		List<Map> allTickets = ticketrepository.getAllCompletedTickets(userDetail.getUserId(),
				userDetail.getUserRole());
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
	public ApiResponse getTktDetailsById(String ticketId) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		boolean noErrors = true;
		// List<Map> allTickets =
		// ticketrepository.getAllCompletedTickets(userDetail.getUserId(),
		// userDetail.getUserRole());4
		if (!userDetail.getUserRole().equals(UserRole.TICKETINGTOOL_ADMIN)) {
			if (userDetail.getUserRole().equals(UserRole.DEVELOPER)) {
				if (!ticketNewRequest.getCreatedBy().equals(userDetail.getUserId())) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.NOT_AUTHORISED);
					response.setContent(null);
					noErrors = false;
				}

			}
			if (userDetail.getUserRole().equals(UserRole.INFRA)) {

			}
		}
		if (noErrors) {
			if (ticketNewRequest != null) {
				Map<String, List<Map>> content = new HashMap<String, List<Map>>();
				// String tktStatus = ticketrepository.getTicketById(ticketId);

				// Map<String, String> CurTktStatus = new HashMap<String, String>();
				// CurTktStatus.put("CurrentStatus", tktStatus);
				// response.setContent(CurTktStatus);

				List<Map> ticketComments = ticketrepository.getTktcommntsById(ticketId);
				content.put("Comments", ticketComments);

				List<Map> ticketAttachments = ticketrepository.getTktAttachmentsById(ticketId);
				content.put("AttachmentDetails", ticketAttachments);

				// Map<String, String> CurTktStatus = new HashMap<>();
				// ticketComments = new ArrayList<>();
				// CurTktStatus.put("CurrentStatus", tktStatus);
				// ticketComments.add(CurTktStatus);

				List<Map> ticketDetails = ticketrepository.getTicketSearchById(ticketId);
				content.put("CurrentStatus", ticketDetails);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EXIST);
				response.setContent(content);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
				response.setContent(null);
			}
		}
		return response;
	}

	@Override
	public ApiResponse createTickets(MultipartFile[] files, String ticketRequest) {
		ApiResponse response = new ApiResponse(false);
		ObjectMapper objectMapper = new ObjectMapper();
		Ticket ticketreq = null;
		try {
			ticketreq = objectMapper.readValue(ticketRequest, Ticket.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * JSONObject json = new JSONObject(ticketRequest); Ticket ticketreq=new
		 * Ticket();
		 */
		Projects project = projectRepository.getById(ticketreq.getProjectId());

		if (project == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_NOTEXIST);
			response.setContent(null);
			return response;
		} else {
			ticketreq.setCreatedBy(userDetail.getUserId());
			ticketreq.setCreatedAt(new Date());
			ticketreq.setUpdatedBy(userDetail.getUserId());
			ticketreq.setLastUpdatedAt(new Date());
			// ticketreq.setStatus(TicketStatus.INITIATED);
			Ticket tickets = ticketrepository.save(ticketreq);

			// Calling file upload method
			if (files != null)
				attachmentService.storeImage(files, tickets.getId());

			// Inserting Ticket history details
			TicketStatusHistory tktStatusHist = new TicketStatusHistory();
			tktStatusHist.setTicketId(tickets.getId());
			tktStatusHist.setTicketStatus(TicketStatus.INITIATED);
			tktStatusHist.setCreatedBy(userDetail.getUserId());
			tktStatusHist.setCreatedAt(new Date());
			tktStatusHist.setUpdatedBy(userDetail.getUserId());
			tktStatusHist.setLastUpdatedAt(new Date());
			tktStatusHistory.save(tktStatusHist);

			List<Map> userList = employeeServiceImpl.getListOfInfraAdmins();

			for (Map user : userList) {

				Map request = new HashMap<>();
				request.put("id", user.get("employeeId"));
				request.put("uid", user.get("uid"));
				request.put("title", "TICKET CREATED");
				request.put("body", "New Ticket Created - " + ticketreq.getTicketDescription());
				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 17,
						NotificationType.TICKET_CREATED.toString()));

			}

			// Inserting Notifications Details
			Notifications notifications = new Notifications();
			notifications.setNotificationDesc("New Ticket Created - " + ticketreq.getTicketDescription());
			notifications.setNotificationType(NotificationType.TICKET_CREATED);
			notifications.setSenderId(userDetail.getUserId());
			notifications.setReceiverId(userDetail.getUserId());
			notifications.setSeenStatus(false);
			notifications.setCreatedBy(userDetail.getUserId());
			notifications.setCreatedAt(new Date());
			notifications.setUpdatedBy(userDetail.getUserId());
			notifications.setLastUpdatedAt(new Date());
			notifications.setTicketId(tickets.getId());
			notificationsRepository.save(notifications);

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
		// System.out.println("Status :: "+ticketNewRequest.getStatus());
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

				// Inserting Ticket history details
				TicketStatusHistory tktStatusHist = new TicketStatusHistory();
				tktStatusHist.setTicketId(ticketId);
				tktStatusHist.setTicketStatus(TicketStatus.CANCELLED);
				tktStatusHist.setCreatedBy(userDetail.getUserId());
				tktStatusHist.setCreatedAt(new Date());
				tktStatusHist.setUpdatedBy(userDetail.getUserId());
				tktStatusHist.setLastUpdatedAt(new Date());
				tktStatusHistory.save(tktStatusHist);

				// Inserting Notifications Details
				if (ticketNewRequest.getStatus().equals(TicketStatus.ASSIGNED)
						|| ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
					Notifications notifications = new Notifications();
					notifications.setNotificationDesc("Ticket Cancelled - " + ticketNewRequest.getTicketDescription());
					if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER"))
						notifications.setNotificationType(NotificationType.TICKET_CANCELLED_BY_USER);
					else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
						notifications.setNotificationType(NotificationType.TICKET_CANCELLED_BY_ADMIN);
					else
						notifications.setNotificationType(NotificationType.TICKET_CANCELLED_BY_USER);
					notifications.setSenderId(userDetail.getUserId());
					notifications.setReceiverId(userDetail.getUserId());
					notifications.setSeenStatus(false);
					notifications.setCreatedBy(userDetail.getUserId());
					notifications.setCreatedAt(new Date());
					notifications.setUpdatedBy(userDetail.getUserId());
					notifications.setLastUpdatedAt(new Date());
					notificationsRepository.save(notifications);
				}

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_CANCELLED);
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
	public ApiResponse resolveTicket(String ticketId) {
		{
			ApiResponse response = new ApiResponse(false);
			Ticket ticketNewRequest = ticketrepository.getById(ticketId);
			// System.out.println("Status :: "+ticketNewRequest.getStatus());
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

					// Inserting Ticket history details
					TicketStatusHistory tktStatusHist = new TicketStatusHistory();
					tktStatusHist.setTicketId(ticketId);
					tktStatusHist.setTicketStatus(TicketStatus.COMPLETED);
					tktStatusHist.setCreatedBy(userDetail.getUserId());
					tktStatusHist.setCreatedAt(new Date());
					tktStatusHist.setUpdatedBy(userDetail.getUserId());
					tktStatusHist.setLastUpdatedAt(new Date());
					tktStatusHistory.save(tktStatusHist);

					// Inserting Notifications Details
					Notifications notifications = new Notifications();
					notifications.setNotificationDesc("Ticket Cancelled - " + ticketNewRequest.getTicketDescription());
					notifications.setNotificationType(NotificationType.TICKET_RESOLVED);
					notifications.setSenderId(userDetail.getUserId());
					notifications.setReceiverId(userDetail.getUserId());
					notifications.setSeenStatus(false);
					notifications.setCreatedBy(userDetail.getUserId());
					notifications.setCreatedAt(new Date());
					notifications.setUpdatedBy(userDetail.getUserId());
					notifications.setLastUpdatedAt(new Date());
					notificationsRepository.save(notifications);

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
	}

	@Override
	public ApiResponse onHoldTicket(String ticketId) {
		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		if (ticketObj != null) {

			ticketObj.setStatus(TicketStatus.ONHOLD);
			ticketObj.setUpdatedBy(ticketObj.getUpdatedBy());
			ticketObj.setLastUpdatedAt(new Date());
			ticketrepository.save(ticketObj);

			// Inserting Ticket history details
			TicketStatusHistory tktStatusHist = new TicketStatusHistory();
			tktStatusHist.setTicketId(ticketId);
			tktStatusHist.setTicketStatus(TicketStatus.ONHOLD);
			tktStatusHist.setCreatedBy(userDetail.getUserId());
			tktStatusHist.setCreatedAt(new Date());
			tktStatusHist.setUpdatedBy(userDetail.getUserId());
			tktStatusHist.setLastUpdatedAt(new Date());
			tktStatusHistory.save(tktStatusHist);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.ONHOLD_STATUS);
			response.setContent(null);

		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse editTicket(String ticketId, Ticket ticketRequest) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
		// String s = ticketrepository.getTicketById(ticketId);
		// System.out.println("s Value " + s);
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				ticketObj.setTicketDescription(ticketRequest.getTicketDescription());
				ticketObj.setPriorityId(ticketRequest.getPriorityId());
				ticketObj.setProjectId(ticketRequest.getProjectId());
				ticketObj.setStatus(ticketRequest.getStatus());
				ticketObj.setUpdatedBy(userDetail.getUserId());
				ticketObj.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketObj);

				// Inserting Ticket history details
				TicketStatusHistory tktStatusHist = new TicketStatusHistory();
				tktStatusHist.setTicketId(ticketId);
				tktStatusHist.setTicketStatus(TicketStatus.EDITED);
				tktStatusHist.setCreatedBy(userDetail.getUserId());
				tktStatusHist.setCreatedAt(new Date());
				tktStatusHist.setUpdatedBy(userDetail.getUserId());
				tktStatusHist.setLastUpdatedAt(new Date());
				tktStatusHistory.save(tktStatusHist);

				// Inserting Notifications Details
				Notifications notifications = new Notifications();
				notifications.setNotificationDesc("Ticket Cancelled - " + ticketObj.getTicketDescription());
				notifications.setNotificationType(NotificationType.TICKET_EDITED_BY_DEV);
				notifications.setSenderId(userDetail.getUserId());
				notifications.setReceiverId(userDetail.getUserId());
				notifications.setSeenStatus(false);
				notifications.setCreatedBy(userDetail.getUserId());
				notifications.setCreatedAt(new Date());
				notifications.setUpdatedBy(userDetail.getUserId());
				notifications.setLastUpdatedAt(new Date());
				notificationsRepository.save(notifications);

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
					commentObj.setUpdatedBy(userDetail.getUserId());
					commentObj.setLastUpdatedAt(new Date());
					commentRepository.save(commentObj);

					ticketObj.setUpdatedBy(userDetail.getUserId());
					ticketObj.setLastUpdatedAt(new Date());
					ticketrepository.save(ticketObj);

					// Inserting Ticket history details
					TicketStatusHistory tktStatusHist = new TicketStatusHistory();
					tktStatusHist.setTicketId(ticketId);
					tktStatusHist.setTicketStatus(TicketStatus.REOPEN);
					tktStatusHist.setCreatedBy(userDetail.getUserId());
					tktStatusHist.setCreatedAt(new Date());
					tktStatusHist.setUpdatedBy(userDetail.getUserId());
					tktStatusHist.setLastUpdatedAt(new Date());
					tktStatusHistory.save(tktStatusHist);

					// Inserting Notifications Details
					if (ticketObj.getStatus().equals(TicketStatus.ASSIGNED)
							|| ticketObj.getStatus().equals(TicketStatus.INPROGRESS)) {
						Notifications notifications = new Notifications();
						notifications.setNotificationDesc("Ticket Cancelled - " + ticketObj.getTicketDescription());
						if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER"))
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_DEV);
						else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_ADMIN);
						else
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_DEV);
						notifications.setSenderId(userDetail.getUserId());
						notifications.setReceiverId(userDetail.getUserId());
						notifications.setSeenStatus(false);
						notifications.setCreatedBy(userDetail.getUserId());
						notifications.setCreatedAt(new Date());
						notifications.setUpdatedBy(userDetail.getUserId());
						notifications.setLastUpdatedAt(new Date());
						notificationsRepository.save(notifications);
					}

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
		// Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
		String ticketObj = ticketrepository.getTicketById(commentObj.getTicketId());
		if (ticketObj != null && ticketObj != "") {
			// if (ticketObj != null) {
			// if (!ticketObj.equalsIgnoreCase(TicketStatus.COMPLETED)) {
			if (!ticketObj.equalsIgnoreCase(TicketStatus.COMPLETED.toString())) {
				if (commentObj.getTicketCommentDescription() == null
						|| commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				} else {
					commentObj.setCreatedBy(userDetail.getUserId());
					commentObj.setCreatedAt(new Date());
					commentObj.setUpdatedBy(userDetail.getUserId());
					commentObj.setLastUpdatedAt(new Date());
					commentRepository.save(commentObj);

					// Inserting Notifications Details
					if (ticketObj.equalsIgnoreCase(TicketStatus.ASSIGNED.toString())
							|| ticketObj.equalsIgnoreCase(TicketStatus.INPROGRESS.toString())) {
						Notifications notifications = new Notifications();
						notifications.setNotificationDesc(
								"New comments added - " + commentObj.getTicketCommentDescription());
						if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER"))
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_DEV);
						else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_ADMIN);
						else
							notifications.setNotificationType(NotificationType.TICKET_REOPENED_BY_DEV);
						notifications.setSenderId(userDetail.getUserId());
						notifications.setReceiverId(userDetail.getUserId());
						notifications.setSeenStatus(false);
						notifications.setCreatedBy(userDetail.getUserId());
						notifications.setCreatedAt(new Date());
						notifications.setUpdatedBy(userDetail.getUserId());
						notifications.setLastUpdatedAt(new Date());
						notificationsRepository.save(notifications);
					}

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
				if (comment != null) {
					if (commentObj.getTicketCommentDescription() == null
							|| commentObj.getTicketCommentDescription().length() == 0) {
						response.setSuccess(false);
						response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
						response.setContent(null);
					} else {
						if (comment.getCreatedBy() != null) {
							if (comment.getCreatedBy().equals(userDetail.getUserId())) {
								commentObj.setUpdatedBy(userDetail.getUserId());
								commentObj.setLastUpdatedAt(new Date());
								commentObj.setCreatedBy(comment.getCreatedBy());
								commentRepository.save(commentObj);
								response.setSuccess(true);
								response.setMessage(ResponseMessages.TICKET_COMMENTS_EDITED);
								response.setContent(null);
							}
						} else {
							response.setSuccess(false);
							response.setMessage(ResponseMessages.UN_AUTHORISED);
							response.setContent(null);
						}
					}
				} else {
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
				if (comment != null) {

					commentRepository.delete(commentObj);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_DELETED);
					response.setContent(null);

				} else {
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
		Page<Map> TicketList = ticketrepository.getAllTicketList(pageable);
		Map content = new HashMap();
		content.put("TicketList", TicketList);
		ApiResponse response = new ApiResponse(true);
		response.setMessage(ResponseMessages.TICKET_LIST);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse inprogressTicket(String ticketId) {
		ApiResponse response = new ApiResponse(false);

		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		// System.out.println("Status :: "+ticketNewRequest.getStatus());
		if (ticketNewRequest != null) {
			if (ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_INPROGRESS);
				response.setContent(null);
			} else {
				if (ticketNewRequest.getStatus() == TicketStatus.ASSIGNED) {

					ticketNewRequest.setStatus(TicketStatus.INPROGRESS);
					ticketNewRequest.setUpdatedBy(userDetail.getUserId());
					ticketNewRequest.setLastUpdatedAt(new Date());
					ticketrepository.save(ticketNewRequest);
					
					Employee employeeObj = employeeRepository.getById(ticketNewRequest.getCreatedBy());
					Map request=	new HashMap<>();
//					request.put("id", user.get("projectId"));
					request.put("uid", employeeObj.getUserCredientials().getUid());
					request.put("title", "TICKET_ASSIGNED");
					request.put("body","Your Ticket is in review - " + ticketNewRequest.getTicketDescription() );
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 13, NotificationType.TICKET_INREVIEW.toString()));


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
			// return response;
		}
		return response;
	}

	@Override
	public ApiResponse getTicketSearchById(String ticketId) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		List<Map> allTickets = ticketrepository.getTicketSearchById(ticketId);
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
}