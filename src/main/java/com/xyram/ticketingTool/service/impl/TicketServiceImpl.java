
package com.xyram.ticketingTool.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.CommentRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.NotificationRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketAssignRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.Repository.TicketStatusHistRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.entity.TicketStatusHistory;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.JobOpeningSearchRequest;

import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.PdfUtil;
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
	NotificationRepository notificationsRepository;

	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TicketAssignRepository ticketAssigneeRepository;

	/*
	 * @Autowired TicketCommentServiceImpl commentService;
	 */

	@Autowired
	CurrentUser userDetail;

	@Autowired
	TicketStatusHistRepository tktStatusHistory;

	@Override
	public ApiResponse getAllTicketsByStatus(Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets ;
		if (userDetail.getUserRole().equals("INFRA")) {
			allTickets = ticketrepository.getAllTicketsForInfraUser(pageable, userDetail.getUserId());
		}else {
			allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
					userDetail.getUserRole());
		}
		
		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST+" ROLE :: "+userDetail.getUserRole());
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
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
//			if (userDetail.getUserRole().equals(UserRole.INFRA)) {
//
//			}
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
				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 12,
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
			
			String assignEmployeeId = ticketrepository.getElgibleAssignee();
//			Ticket ticketObj = ticketRepository.getById(assignee.getTicketId());
			if (assignEmployeeId != null) {
				Employee employeeObj = employeeRepository.getById(assignEmployeeId);
				if (employeeObj != null) {
					response.setMessage(ResponseMessages.TICKET_ADDED+" And assigned.");
					TicketAssignee assignee = new TicketAssignee();
					assignee.setEmployeeId(assignEmployeeId);
					assignee.setTicketId(tickets.getId());
					assignee.setCreatedAt(new Date());
					assignee.setCreatedBy(userDetail.getUserId());
					assignee.setStatus(TicketAssigneeStatus.ACTIVE);
					
					tickets.setStatus(TicketStatus.ASSIGNED);
					ticketrepository.save(tickets);
					ticketAssigneeRepository.save(assignee);
//						
					Map request = new HashMap<>();
					request.put("uid", employeeObj.getUserCredientials().getUid());
					request.put("title", "TICKET_ASSIGNED");
					request.put("body","Ticket Assigned - " + tickets.getTicketDescription() );
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 13, NotificationType.TICKET_ASSIGNED.toString()));
//					
					//Inserting Notifications Details
					notifications.setNotificationDesc("Ticket Assigned - " + tickets.getTicketDescription());
					notifications.setNotificationType(NotificationType.TICKET_ASSIGNED);
					notifications.setSenderId(userDetail.getUserId());
					notifications.setReceiverId(userDetail.getUserId());
					notifications.setSeenStatus(false);
					notifications.setCreatedBy(userDetail.getUserId());
					notifications.setCreatedAt(new Date());
					notifications.setUpdatedBy(userDetail.getUserId());
					notifications.setLastUpdatedAt(new Date());
					notificationsRepository.save(notifications);
					
//					response.setSuccess(true);
//					response.setMessage(ResponseMessages.TICKET_ASSIGNED);
//					response.setContent(null);
//					
				}else {
//					response.setSuccess(false);
//					response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
//					response.setContent(null);
				}
			
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
				response.setContent(null);
			}
			
			return response;
		}
	}

	@Override
	public ApiResponse cancelTicket(String ticketId) {
		ApiResponse response = new ApiResponse(false);
		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		Employee employee = employeeRepository.getbyUserByUserId(userDetail.getUserId());
		// System.out.println("Status :: "+ticketNewRequest.getStatus());
		if (ticketNewRequest != null) {
			if (ticketNewRequest.getStatus().equals(TicketStatus.CANCELLED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_CANCELLED);
				response.setContent(null);
			} else if (!ticketNewRequest.getStatus().equals(TicketStatus.COMPLETED)) {
				
				if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					if (ticketNewRequest.getStatus().equals(TicketStatus.ASSIGNED)
							|| ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeIdForDeveloper(ticketNewRequest.getId(),employee.geteId()),
								"Ticket Cancelled By User -", ticketNewRequest, "TICKET_CANCELLED", 16);
					}
				} else {
					if (ticketNewRequest.getStatus().equals(TicketStatus.ASSIGNED)
							|| ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketNewRequest.getId()),
								"Ticket Cancelled By Admin - ", ticketNewRequest, "TICKET_CANCELLED", 17);

					}
					sendPushNotification(ticketNewRequest.getCreatedBy(), "Ticket Cancelled By Admin - ",
							ticketNewRequest, "TICKET_CANCELLED", 17);
				}
				
				ticketNewRequest.setStatus(TicketStatus.CANCELLED);
				ticketNewRequest.setUpdatedBy(userDetail.getUserId());
				ticketNewRequest.setLastUpdatedAt(new Date());
				ticketNewRequest.setCancelledAt(new Date());
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

	public void sendPushNotification(String userId, String message, Ticket ticketNewRequest, String title,
			int notiType) {

		Employee employeeObj = employeeRepository.getbyUserId(userId);
		if (employeeObj != null) {
			Map request = new HashMap<>();
			request.put("uid", userId);
			request.put("title", title);
			request.put("body", message + ticketNewRequest.getTicketDescription());
			Notifications notifications2 = new Notifications();

			if (title.equals("TICKET_CANCELLED")) {
				if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_CANCELLED_BY_USER.toString()));
					notifications2.setNotificationType(NotificationType.TICKET_CANCELLED_BY_USER);

				} else {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_CANCELLED_BY_ADMIN.toString()));
					notifications2.setNotificationType(NotificationType.TICKET_CANCELLED_BY_ADMIN);
				}
			} else if (title.equals("TICKET_REOPENED")) {
				if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_REOPENED_BY_DEV.toString()));
					notifications2.setNotificationType(NotificationType.TICKET_REOPENED_BY_DEV);

				} else {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_REOPENED_BY_ADMIN.toString()));
					notifications2.setNotificationType(NotificationType.TICKET_REOPENED_BY_ADMIN);
				}
			} else if (title.equals("TICKET_COMMENTED")) {
				if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_REOPENED_BY_DEV.toString()));
					notifications2.setNotificationType(NotificationType.COMMENTS_ADDED_BY_DEV);

				} else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_REOPENED_BY_DEV.toString()));
					notifications2.setNotificationType(NotificationType.COMMENTS_ADDED_BY_ADMIN);

				} else {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
							notiType, NotificationType.TICKET_REOPENED_BY_ADMIN.toString()));
					notifications2.setNotificationType(NotificationType.COMMENTS_ADDED_BY_INFRA_USER);
				}
			} else if (title.equals("TICKET_EDITED")) {
				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, notiType,
						NotificationType.TICKET_EDITED_BY_DEV.toString()));
				notifications2.setNotificationType(NotificationType.TICKET_EDITED_BY_DEV);
			}

			notifications2.setNotificationDesc(message + ticketNewRequest.getTicketDescription());
			notifications2.setSenderId(userDetail.getUserId());
			notifications2.setReceiverId(userId);
			notifications2.setSeenStatus(false);
			notifications2.setCreatedBy(userDetail.getUserId());
			notifications2.setCreatedAt(new Date());
			notifications2.setUpdatedBy(userDetail.getUserId());
			notifications2.setLastUpdatedAt(new Date());
			notificationsRepository.save(notifications2);
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
					ticketNewRequest.setResolvedAt(new Date());
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

					Employee employeeObj = employeeRepository.getbyUserId(ticketNewRequest.getCreatedBy());
					if (employeeObj != null) {
						Map request = new HashMap<>();
//						request.put("id", user.get("projectId"));
						request.put("uid", employeeObj.getUserCredientials().getUid());
						request.put("title", "TICKET_RESOLVED");
						request.put("body", "Your Ticket is in resolved - " + ticketNewRequest.getTicketDescription());
						pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
								15, NotificationType.TICKET_RESOLVED.toString()));

						// Inserting Notifications Details
						Notifications notifications = new Notifications();
						notifications
								.setNotificationDesc("Ticket Cancelled - " + ticketNewRequest.getTicketDescription());
						notifications.setNotificationType(NotificationType.TICKET_RESOLVED);
						notifications.setSenderId(userDetail.getUserId());
						notifications.setReceiverId(ticketNewRequest.getCreatedBy());
						notifications.setSeenStatus(false);
						notifications.setCreatedBy(userDetail.getUserId());
						notifications.setCreatedAt(new Date());
						notifications.setUpdatedBy(userDetail.getUserId());
						notifications.setLastUpdatedAt(new Date());
						notificationsRepository.save(notifications);
					}

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
	public ApiResponse editTicket(MultipartFile[] files, String ticketId, String ticketRequest) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketrepository.getById(ticketId);
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
		// String s = ticketrepository.getTicketById(ticketId);
		// System.out.println("s Value " + s);
		if (ticketObj != null) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {

				ticketObj.setTicketDescription(ticketreq.getTicketDescription());
				ticketObj.setPriorityId(ticketreq.getPriorityId());
				ticketObj.setProjectId(ticketreq.getProjectId());
				ticketObj.setStatus(ticketreq.getStatus());
				ticketObj.setUpdatedBy(userDetail.getUserId());
				
				ticketObj.setLastUpdatedAt(new Date());
				ticketrepository.save(ticketObj);

				// Inserting Ticket history details
				TicketStatusHistory tktStatusHist = new TicketStatusHistory();
				// tktStatusHist.setTicketId(ticketId);
				tktStatusHist.setTicketStatus(TicketStatus.EDITED);
				tktStatusHist.setCreatedBy(userDetail.getUserId());
				tktStatusHist.setCreatedAt(new Date());
				tktStatusHist.setUpdatedBy(userDetail.getUserId());
				tktStatusHist.setLastUpdatedAt(new Date());
				tktStatusHistory.save(tktStatusHist);
				if (files != null)
					attachmentService.storeImage(files, ticketId);
				// Change userID to assignee
				sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketObj.getId()),
						"Ticket Edited By User - " + ticketObj.getTicketDescription(), ticketObj, "TICKET_EDITED", 26);

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
			} else if (ticketObj.getStatus().equals(TicketStatus.COMPLETED) || ticketObj.getStatus().equals(TicketStatus.CANCELLED)) {

				if (commentObj.getTicketCommentDescription() == null
						|| commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				} else {
					ticketObj.setStatus(TicketStatus.REOPEN);
					commentObj.setCreatedBy(userDetail.getUserId());
					commentObj.setCreatedAt(new Date());
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

					if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(userDetail.getUserId(), "Ticket Re-opened By User -", ticketObj,
								"TICKET_REOPENED", 19);
						List<Map> userList = employeeServiceImpl.getListOfInfraAdmins();

						for (Map user : userList) {

							Map request = new HashMap<>();
							request.put("id", user.get("employeeId"));
							request.put("uid", user.get("uid"));
							request.put("title", "TICKET CREATED");
							request.put("body", "New Ticket Created - " + ticketObj.getTicketDescription());
							pushNotificationCall.restCallToNotification(pushNotificationRequest
									.PushNotification(request, 19, NotificationType.TICKET_REOPENED_BY_DEV.toString()));

						}
					} else {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketObj.getId()),
								"Ticket Re-opened By Admin - ", ticketObj, "TICKET_REOPENED", 18);
						sendPushNotification(ticketObj.getCreatedBy(), "Ticket Re-opened By Admin - ", ticketObj,
								"TICKET_REOPENED", 18);
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
		Ticket ticketObj = ticketrepository.getById(commentObj.getTicketId());
//		String ticketStatus = ticketrepository.getTicketById(commentObj.getTicketId());
		if (ticketObj.getStatus() != null || ticketObj.getStatus().equals("")) {
			// if (ticketObj != null) {
			// if (!ticketObj.equalsIgnoreCase(TicketStatus.COMPLETED)) {
			if (!ticketObj.getStatus().equals(TicketStatus.COMPLETED.toString())) {
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

					if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
						if (ticketObj.equals(TicketStatus.ASSIGNED.toString())
								|| ticketObj.equals(TicketStatus.INPROGRESS.toString())) {
							// Change userDetail.getUserId() to Ticket Assignee
							sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketObj.getId()),
									"Commented on Ticket - ", ticketObj, "TICKET_COMMENTED", 24);
						}
					} else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")) {
						if (ticketObj.equals(TicketStatus.ASSIGNED.toString())
								|| ticketObj.equals(TicketStatus.INPROGRESS.toString())) {
							// Change userDetail.getUserId() to Ticket Assignee
							sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketObj.getId()),
									"Commented on Ticket By Admin - ", ticketObj, "TICKET_COMMENTED", 23);
						}
						sendPushNotification(ticketObj.getCreatedBy(), "Commented on Ticket By Admin - ", ticketObj,
								"TICKET_COMMENTED", 23);
					} else {
						sendPushNotification(ticketObj.getCreatedBy(), "Commented on Ticket By Infra User - ",
								ticketObj, "TICKET_COMMENTED", 25);
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
								commentObj.setCreatedBy(comment.getCreatedBy());
								commentObj.setUpdatedBy(userDetail.getUserId());
								commentObj.setLastUpdatedAt(new Date());
								
								// commentObj.setCreatedBy(comment.getCreatedBy());
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
					if (!comment.getCreatedBy().equals(userDetail.getUserId())) {
						response.setSuccess(false);
						response.setMessage(ResponseMessages.NOT_AUTHORISED);
						response.setContent(null);
					} else {
						commentRepository.delete(commentObj);
						response.setSuccess(true);
						response.setMessage(ResponseMessages.TICKET_COMMENTS_DELETED);
						response.setContent(null);
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
	public ApiResponse searchTicket(String searchString) {
		List<Map> serachList = ticketrepository.searchTicket(searchString);
		Map content = new HashMap();
		content.put("serachList", serachList);
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

				if (ticketNewRequest.getStatus() == TicketStatus.ASSIGNED || ticketNewRequest.getStatus() == TicketStatus.REOPEN  ) {

					ticketNewRequest.setStatus(TicketStatus.INPROGRESS);
					ticketNewRequest.setUpdatedBy(userDetail.getUserId());
					ticketNewRequest.setLastUpdatedAt(new Date());
					ticketrepository.save(ticketNewRequest);

					Employee employeeObj = employeeRepository.getbyUserId(ticketNewRequest.getCreatedBy());

					if (employeeObj != null) {
						Map request = new HashMap<>();
//						request.put("id", user.get("projectId"));
						request.put("uid", employeeObj.getUserCredientials().getUid());
						request.put("title", "TICKET_INPROGRESS");
						request.put("body", "Your Ticket is in review - " + ticketNewRequest.getTicketDescription());
						pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
								14, NotificationType.TICKET_INREVIEW.toString()));
					}

					response.setSuccess(true);
					response.setMessage(ResponseMessages.TICKET_INPROGRESS);
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

	@Override
	public Optional<Ticket> findById(String Id) {
		return ticketrepository.findById(Id);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketrepository.findAll();
	}
	
	@Override
	public ApiResponse getAllTicketsByDuration(Pageable pageable,String date1,String date2) {
		// TODO Auto-generated method stub
	System.out.println("inside service method");
		ApiResponse response = new ApiResponse(false);
		
		
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(  "yyyy-MM-dd");
			 Date startTime,endTime;
			startTime=sdf.parse(date1);
			endTime = sdf.parse(date2);
			Page<Map> allTks =  ticketrepository.getAllTicketsByDuration(pageable, startTime, endTime);
			//  System.out.println( "values"+allTks.getContent());
			/*
			 * for(Map map: allTks) { map.entrySet(); // map.forEach((k, v) ->
			 * System.out.println("Key : " + k + ", Value : " + v.toString())); }
			 */
			if (allTks != null) {
				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EXIST+" ROLE :: "+userDetail.getUserRole());
				Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
				
				content.put("tickets", (Page<Map>) allTks);
				
				response.setContent(content);
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
				response.setContent(null);
			}
	     
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;

		
	}
	
	@Override
	public ApiResponse getTicketStatusCountWithProject(Pageable pageable) {
		// TODO Auto-generated method stub
	System.out.println("inside service method");
		ApiResponse response = new ApiResponse(false);
		
	
		Page<Map> allTks =  ticketrepository.getTicketStatusCountWithProject(pageable);
	
	

		System.out.println("ticket service impl");
		
		
			if (allTks != null) {
				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EXIST+" ROLE :: "+userDetail.getUserRole());
				Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
				
				content.put("tickets", allTks);
				
				response.setContent(content);
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
				response.setContent(null);
			}
	     
		return response;
	}


	@Override
	public ApiResponse getAllTicketsDetails(Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets = ticketrepository.getAllTicketsDetails(pageable);
		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("tickets", allTickets);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}

		return response;
		
	}
	
	  public static String findDifference(Date date, Date date2)
		 {
			  long difference_In_Days = 0;
			  String duration=" ";
		      try {
		        	 SimpleDateFormat sdf = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
		       Date d1 = date;
		       Date d2 = date2;
		  
		          
		            long difference_In_Time
		                = d2.getTime() - d1.getTime();
		  
		            difference_In_Days
		                = (difference_In_Time
		                   / (1000 * 60 * 60 * 24))
		                  % 365;
		            long difference_In_Hours
	                = (difference_In_Time
	                   / (1000 * 60 * 60))
	                  % 24;
		            System.out.println(
		                   
		                    + difference_In_Days
		                    + " days, "
		                    +difference_In_Hours+"hrs"
		                    );
		       duration= String.valueOf(difference_In_Days)+"Days"+String.valueOf(difference_In_Hours)+"hr";
		     
		  
		            return duration;
		        }
		  
		        // Catch the Exception
		        catch (Exception e) {
		            e.printStackTrace();
		        }
				return duration;
				
		    }
	//report 	  ticketrepo.getTicketDataByStatus
	  
	

	@Override
	public ApiResponse getTicketDtlsByProjectNameAndStatus(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);
		String projectName = filter.containsKey("projectName") ? ((String) filter.get("projectName")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;
		String searchQuery = filter.containsKey("searchQuery") ? ((String) filter.get("searchQuery")).toLowerCase()
				: null;
		Date parsedFromDate = null;
		Date parsedToDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			parsedFromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
			parsedToDate = toDate != null ? dateFormat.parse(toDate) : null;

		} catch (ParseException e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format date should be yyyy-MM-dd");

		}
		TicketStatus status = null;
		try {
			status = filter.containsKey("status") ? TicketStatus.toEnum((String) filter.get("status")) : null;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					filter.get("status").toString() + " is not a valid status");
		}
		Page<Map> allTks =  ticketrepository.getTicketDataByStatusProjectName(projectName,status,parsedFromDate,parsedToDate,searchQuery,pageable);
		if (allTks != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST+" ROLE :: "+userDetail.getUserRole());
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			
			content.put("tickets", (Page<Map>) allTks);
			
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		
		
		return response;

	}
	
	
	
	

	
	}
