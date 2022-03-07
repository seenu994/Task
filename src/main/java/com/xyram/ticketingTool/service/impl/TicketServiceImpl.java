
package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.CommentRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.NotificationRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketAssignRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.Repository.TicketStatusHistRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.entity.TicketStatusHistory;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.ResponseMessages;
import com.xyram.ticketingTool.vo.TicketVo;

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
	NotificationService notificationService;

	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TicketAssignRepository ticketAssigneeRepository;

	@Autowired
	ProjectMemberRepository memberRepo;

	/*
	 * @Autowired TicketCommentServiceImpl commentService;
	 */

	@Autowired
	CurrentUser userDetail;

	@Autowired
	TicketStatusHistRepository tktStatusHistory;

	@Override
	public ApiResponse getAllTicketsByStatus(TicketStatus status, Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets;
		if (userDetail.getUserRole().equals("TICKETINGTOOL_ADMIN")
				|| userDetail.getUserRole().equalsIgnoreCase("INFRA_ADMIN")) {
			allTickets = ticketrepository.getAllTicketsForAdmin(pageable, userDetail.getUserRole(), status,null);
		}

		else {
			allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
					userDetail.getUserRole(), status, true,null);
		}

		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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
	public ApiResponse getAllCompletedTickets(Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets = ticketrepository.getAllCompletedTickets(pageable, userDetail.getUserId(),
				userDetail.getUserRole());

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
	public ApiResponse createTickets(MultipartFile[] files, String ticketRequest, String assigneeId) {
		ApiResponse response = new ApiResponse(false);
		ObjectMapper objectMapper = new ObjectMapper();
		Ticket ticketreq = null;
		try {
			ticketreq = objectMapper.readValue(ticketRequest, Ticket.class);
		} catch (JsonMappingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		Projects project = projectRepository.getById(ticketreq.getProjectId());
		if (project == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_NOTEXIST);
			response.setContent(null);
			return response;
			} else {
				if (ticketreq.getCreatedBy() != null) {
					User empObj = employeeRepository.getUserByUSerId(ticketreq.getCreatedBy());
					if (empObj == null ) {
						response.setSuccess(false);
						response.setMessage("Not a valid Raised By Employee ");
						response.setContent(null);
						return response;
					}
					ticketreq.setCreatedBy(ticketreq.getCreatedBy());
				}
	
				else {
					ticketreq.setCreatedBy(userDetail.getUserId());
	
				}
				ticketreq.setCreatedAt(new Date());
				ticketreq.setUpdatedBy(userDetail.getUserId());
				ticketreq.setLastUpdatedAt(new Date());
				
				Ticket tickets = ticketrepository.save(ticketreq);
	
				if (files != null) {
					attachmentService.storeImage(files, tickets.getId());
				}
	
				TicketAssignee assignee=null;
				if (assigneeId != null) {
					Employee employeeObj = employeeRepository.getByEmpId(assigneeId);
					if (employeeObj != null  && employeeObj.getStatus() != UserStatus.OFFLINE) {
						 assignee = new TicketAssignee();
						assignee.setEmployeeId(assigneeId);
						assignee.setTicketId(tickets.getId());
						assignee.setCreatedAt(new Date());
						assignee.setCreatedBy(userDetail.getUserId());
						assignee.setStatus(TicketAssigneeStatus.ACTIVE);
	
						tickets.setStatus(TicketStatus.ASSIGNED);
						ticketAssigneeRepository.save(assignee);
					}else {
						tickets.setStatus(TicketStatus.INITIATED);
					}
				}
	
				else {
	
					tickets.setStatus(TicketStatus.INITIATED);
	
				}
	
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_CREATED);
			response.setContent(null);
		}
		return response;

	}

	@Override
	public ApiResponse cancelTicket(String ticketId) {
		ApiResponse response = new ApiResponse(false);
		Ticket ticketNewRequest = ticketrepository.getById(ticketId);
		System.out.println(userDetail.getUserId());
		Employee employee = employeeRepository.getbyUserByUserId(userDetail.getUserId());

		// System.out.println("Status :: "+ticketNewRequest.getStatus());
		if (ticketNewRequest != null) {
			if (ticketNewRequest.getStatus().equals(TicketStatus.CANCELLED)) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.TICKET_ALREADY_CANCELLED);
				response.setContent(null);
			} else if (!ticketNewRequest.getStatus().equals(TicketStatus.COMPLETED)) {

				if (userDetail.getUserRole() != null && userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					if (ticketNewRequest.getStatus().equals(TicketStatus.ASSIGNED)
							|| ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(
								ticketAssigneeRepository.getAssigneeIdForDeveloper(ticketNewRequest.getId(),
										employee.geteId()),
								"Ticket Cancelled By User -", ticketNewRequest, "TICKET_CANCELLED", 16);
					}

				} else {
					TicketAssignee ticketAssigneObj = new TicketAssignee();
					TicketAssignee ticket = ticketAssigneeRepository
							.getDuplicateTickate(ticketAssigneObj.getTicketId());
					if (ticketAssigneObj.getStatus().equals(TicketAssigneeStatus.INACTIVE)
							|| ticketNewRequest.getStatus().equals(TicketStatus.ASSIGNED)
							|| ticketNewRequest.getStatus().equals(TicketStatus.INPROGRESS)) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticket),
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
			request.put("id", userDetail.getUserId());
			request.put("uid", employeeObj.getUserCredientials().getUid());
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
						// notificationsRepository.save(notifications);
						notificationService.createNotification(notifications);
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
	public ApiResponse editTicket( String ticketId,TicketVo ticketVo) {

		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = null;
		ticketObj = ticketrepository.getById(ticketId);
		Employee employee = employeeRepository.getbyUserByUserId(userDetail.getUserId());
		ObjectMapper objectMapper = new ObjectMapper();
		Ticket ticketreq = null;
		try {
			ticketreq = objectMapper.readValue(ticketVo.getTicketRequest(), Ticket.class);
		} catch (JsonMappingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		if (ticketObj != null) {
			if (ticketObj.getStatus().equals(TicketStatus.COMPLETED)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "U CAN't Edit Already Completed Ticket ");
				
			}

				ticketObj.setTicketDescription(ticketreq.getTicketDescription());
				ticketObj.setPriorityId(ticketreq.getPriorityId());
				ticketObj.setProjectId(ticketreq.getProjectId());
				ticketObj.setUpdatedBy(userDetail.getUserId());
				ticketObj.setLastUpdatedAt(new Date());
			
				createTicketHistory(ticketObj,TicketStatus.EDITED);
				
			   ticketrepository.save(ticketObj);
				
				
				if (ticketVo.getFiles() != null &&ticketVo.getFiles().length>0)
					attachmentService.storeImage(ticketVo.getFiles(), ticketId);
				// Change userID to assignee
//				sendPushNotification(
//						ticketAssigneeRepository.getAssigneeIdForDeveloper(ticketObj.getId(), employee.geteId()),
//						"Ticket Edited By User - " + ticketObj.getTicketDescription(), ticketObj, "TICKET_EDITED", 26);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EDITED);
				response.setContent(null);

			
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		return response;
	}
	
	
	

public TicketStatusHistory createTicketHistory(Ticket ticket,TicketStatus status)
{
	
	TicketStatusHistory tktStatusHist = new TicketStatusHistory();
	 tktStatusHist.setTicketId(ticket.getId());
	tktStatusHist.setTicketStatus(status);
	tktStatusHist.setCreatedBy(userDetail.getUserId());
	tktStatusHist.setCreatedAt(new Date());
	tktStatusHist.setUpdatedBy(userDetail.getUserId());
	tktStatusHist.setLastUpdatedAt(new Date());
return 	tktStatusHistory.save(tktStatusHist);
	
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
			} else if (ticketObj.getStatus().equals(TicketStatus.COMPLETED)
					|| ticketObj.getStatus().equals(TicketStatus.CANCELLED)) {

				if (commentObj.getTicketCommentDescription() == null
						|| commentObj.getTicketCommentDescription().length() == 0) {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.TICKET_COMMENTS_NOT_EXIST);
					response.setContent(null);
				} else {
					if(ticketAssigneeRepository.getActiveAssigneeId(ticketObj.getId())!=null)
						ticketObj.setStatus(TicketStatus.REOPEN);
					else
						ticketObj.setStatus(TicketStatus.INITIATED);
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
// Hemu Commented : Getting 500 Error
//					if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
//						// Change userDetail.getUserId() to Ticket Assignee
//						sendPushNotification(userDetail.getUserId(), "Ticket Re-opened By User -", ticketObj,
//								"TICKET_REOPENED", 19);
//						List<Map> userList = employeeServiceImpl.getListOfInfraAdmins();
//
//						for (Map user : userList) {
//
//							Map request = new HashMap<>();
//							request.put("id", user.get("employeeId"));
//							request.put("uid", user.get("uid"));
//							request.put("title", "TICKET CREATED");
//							request.put("body", "New Ticket Created - " + ticketObj.getTicketDescription());
//							pushNotificationCall.restCallToNotification(pushNotificationRequest
//									.PushNotification(request, 19, NotificationType.TICKET_REOPENED_BY_DEV.toString()));
//
//						}
//					} else {
//						// Change userDetail.getUserId() to Ticket Assignee
//						sendPushNotification(ticketAssigneeRepository.getAssigneeId(ticketObj.getId()),
//								"Ticket Re-opened By Admin - ", ticketObj, "TICKET_REOPENED", 18);
//						sendPushNotification(ticketObj.getCreatedBy(), "Ticket Re-opened By Admin - ", ticketObj,
//								"TICKET_REOPENED", 18);
//					}

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
	public ApiResponse searchTicket(Map<String, Object> filter, Pageable pageable) {
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		String priority = filter.containsKey("priority") ? ((String) filter.get("priority")) : null;

		Page<List<Map>> serachList = null;
//		if (userDetail.getUserRole().equals("TICKETINGTOOL_ADMIN")) {
//			serachList = ticketrepository.searchAllTicket(searchString, priority);
//		}
//		else if  (userDetail.getUserRole().equals("INFRA_ADMIN")) {
//			serachList = ticketrepository.searchAllTicket(searchString, priority);
//
//		} else if (userDetail.getUserRole().equals("INFRA_USER")) {
//			serachList = ticketrepository.searchSelfAssignedTicket(searchString, priority, userDetail.getUserId());
//		} else {
//			serachList = ticketrepository.searchSelfTicket(searchString, priority, userDetail.getUserId());
//		}
		serachList = ticketrepository.searchAllTicket(searchString, priority,pageable);
		ApiResponse response = new ApiResponse(true);

		Map content = new HashMap();
		content.put("serachList", serachList);
		// ApiResponse response = new ApiResponse(true);
		response.setMessage(ResponseMessages.TICKET_LIST);
		response.setSuccess(true);
		response.setContent(content);

		return response;
	} 
	
	@Override
	public ApiResponse getAllTicketReports(Map<String, Object> filter, Pageable pageable) {
		
		ApiResponse response = new ApiResponse(true);

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setSuccess(false);
		}
		
		String projectId = filter.containsKey("projectId") ? ((String) filter.get("projectId"))
				: null;
		String statusStr = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase()
				: null;
		String assigneeId = filter.containsKey("assigneeId") ? ((String) filter.get("assigneeId"))
				: null;
		String createrId = filter.containsKey("createrId") ? ((String) filter.get("createrId"))
				: null;
		String priority = filter.containsKey("priority") ? ((String) filter.get("priority")) : null;
		
		if(statusStr!=null) {
			TicketStatus status = null;
			try {
				status = filter.containsKey("status") ? TicketStatus.toEnum((String) filter.get("status")) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}
		

		List<Map> serachList = null;
		if(userDetail.getUserRole().equals("TICKETINGTOOL_ADMIN") ||
				userDetail.getUserRole().equals("INFRA_ADMIN")) {
			
		}else if(userDetail.getUserRole().equals("INFRA_USER")){
			assigneeId = userDetail.getUserId();
		}else {
			createrId = userDetail.getUserId();
		}

		serachList = ticketrepository.searchAllTickets(projectId,fromDateStr,toDateStr,statusStr,createrId,assigneeId, priority,pageable);
		Integer listCount =  ticketrepository.searchAllTicketsCount(projectId,fromDateStr,toDateStr,statusStr,createrId,assigneeId, priority);
		Map content = new HashMap();
		content.put("serachList", serachList);
		content.put("totalCount", listCount);
		// ApiResponse response = new ApiResponse(true);
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

				if (ticketNewRequest.getStatus() == TicketStatus.ASSIGNED
						|| ticketNewRequest.getStatus() == TicketStatus.REOPEN) {

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
	public ApiResponse getAllTicketsByDuration(Pageable pageable, String date1, String date2) {
		// TODO Auto-generated method stub
		System.out.println("inside service method");
		ApiResponse response = new ApiResponse(false);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime, endTime;
			startTime = sdf.parse(date1);
			endTime = sdf.parse(date2);
			Page<Map> allTks = ticketrepository.getAllTicketsByDuration(pageable, startTime, endTime);
			// System.out.println( "values"+allTks.getContent());
			/*
			 * for(Map map: allTks) { map.entrySet(); // map.forEach((k, v) ->
			 * System.out.println("Key : " + k + ", Value : " + v.toString())); }
			 */
			if (allTks != null) {
				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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

		Page<Map> allTks = ticketrepository.getTicketStatusCountWithProject(pageable);

		System.out.println("ticket service impl");

		if (allTks != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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

	public static String findDifference(Date date, Date date2) {
		long difference_In_Days = 0;
		String duration = " ";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date d1 = date;
			Date d2 = date2;

			long difference_In_Time = d2.getTime() - d1.getTime();

			difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
			System.out.println(

					+difference_In_Days + " days, " + difference_In_Hours + "hrs");
			duration = String.valueOf(difference_In_Days) + "Days" + String.valueOf(difference_In_Hours) + "hr";

			return duration;
		}

		// Catch the Exception
		catch (Exception e) {
			e.printStackTrace();
		}
		return duration;

	}
	// report ticketrepo.getTicketDataByStatus

	@Override
	public ApiResponse getTicketDtlsByProjectNameAndStatus(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		String projectName = filter.containsKey("projectName") ? ((String) filter.get("projectName")).toLowerCase()
				: null;

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

		Page<Map> allTks = ticketrepository.getTicketDataByStatusProjectName(projectName, status, parsedFromDate,
				parsedToDate, searchQuery, pageable);

		if (allTks != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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

	@Override
	public ApiResponse getAllTicketsByStatusMobile(Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets;
		if (userDetail.getUserRole() != null && userDetail.getUserRole().equals("INFRA_USER")) {
			allTickets = ticketrepository.getAllTicketsForInfraUser(pageable, userDetail.getUserId());
		} else {
			allTickets = ticketrepository.getAllTicketsByStatusMobile(pageable, userDetail.getUserId(),
					userDetail.getUserRole());
		}

		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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
	public ApiResponse getTicketCount(Map<String, Object> filter) {
		ApiResponse response = new ApiResponse(false);

		List<Map> allTickets;
		String status = filter.containsKey("status") ? ((String) filter.get("status")) : "ACTIVE";

		TicketAssigneeStatus ticketStatus = null;
		try {
			ticketStatus = status != null ? TicketAssigneeStatus.toEnum(status) : null;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Invalid Status Passed ");
		}
		
		if (userDetail.getUserRole() != null && userDetail.getUserRole().equals("INFRA_USER")) {
			allTickets = ticketrepository.getTicketCountForInfra(userDetail.getUserRole(), userDetail.getUserId());
		}
		else
			allTickets = ticketrepository.getTicketCount(userDetail.getUserRole(), userDetail.getUserId());
		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage("ticket status count retrieved successfully!!");
			Map content = new HashMap();
			content.put("tickets", allTickets);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage("Count Not found");
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse getAllSupporytTickets(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets = null;

		String status = filter.containsKey("ticketStatus") ? ((String) filter.get("ticketStatus")) : null;
		String priority = filter.containsKey("priority") ? ((String) filter.get("priority")) : null;
		
		
		boolean isUser = filter.containsKey("isUser") ? ((Boolean) filter.get("isUser")) : true;

		TicketStatus ticketStatus = null;
		try {
			ticketStatus = status != null ? TicketStatus.toEnum(status) : null;
			if (ticketStatus != null) {
				isUser = false;
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Invalid Status Passed ");
		}

		if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")
				|| userDetail.getUserRole().equalsIgnoreCase("INFRA_ADMIN") 
				|| userDetail.getUserRole().equalsIgnoreCase("INFRA_USER")) {
			
			if(userDetail.getUserRole().equalsIgnoreCase("INFRA_USER") && status.equalsIgnoreCase("INITIATED")) {
				allTickets = ticketrepository.getAllTicketsForAdmin(pageable, userDetail.getUserRole(), ticketStatus,priority);
			}else {
				if(status != null) {
					if(status.equalsIgnoreCase("COMPLETED")) {
						allTickets = ticketrepository.getAllTicketsByCompleted(pageable, userDetail.getUserId(),
								userDetail.getUserRole(), ticketStatus,priority);
					}else
						allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
							userDetail.getUserRole(), ticketStatus, isUser,priority);
				}else {
					allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
							userDetail.getUserRole(), ticketStatus, isUser,priority);
				}
			}
		}

		else {
			if(status != null) {
				if(status.equalsIgnoreCase("COMPLETED"))
					allTickets = ticketrepository.getAllTicketsByCompleted(pageable, userDetail.getUserId(),
						userDetail.getUserRole(), ticketStatus,priority);
				else
					allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
							userDetail.getUserRole(), ticketStatus, isUser,priority);
			}else
				allTickets = ticketrepository.getAllTicketsByStatus(pageable, userDetail.getUserId(),
					userDetail.getUserRole(), ticketStatus, isUser,priority);
		}

		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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
	public ApiResponse getAllActiveTickets(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		Page<Map> allTickets = null;

		String priority = filter.containsKey("priority") ? ((String) filter.get("priority")) : null;

		allTickets = ticketrepository.getAllActiveTickets(pageable, userDetail.getUserId(),priority);

		if (allTickets != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_EXIST + " ROLE :: " + userDetail.getUserRole());
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
	public ApiResponse updateTicketResolution(Map<String, Object> filter) {
		ApiResponse response = new ApiResponse(false);
		String ticketId = filter.containsKey("ticketId") ? ((String) filter.get("ticketId")) : null;
		String resolution = filter.containsKey("resolution") ? ((String) filter.get("resolution")) : null;
		Ticket ticketObj = ticketrepository.getTicketDetailById(ticketId);
		
		if(ticketId != null && resolution != null && resolution.length() > 0 && ticketObj != null) {
			ticketObj.setResolution(resolution);
			ticketrepository.save(ticketObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.TICKET_RESOLUTION_UPDATED);
			response.setContent(null);
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_RESOLUTION_NOT_UPDATED);
			response.setContent(null);
		}
		
		
		return response;
	}

	@Override
	public ApiResponse editTicket(MultipartFile[] files, String ticketId, String ticketRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	
}