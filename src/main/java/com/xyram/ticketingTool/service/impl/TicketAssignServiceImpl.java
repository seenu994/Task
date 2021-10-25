package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.NotificationRepository;
import com.xyram.ticketingTool.Repository.TicketAssignRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.service.TicketAssignService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */

@Service
@Transactional
public class TicketAssignServiceImpl implements TicketAssignService {

	@Autowired
	TicketAssignRepository ticketAssignRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CurrentUser userDetail;
	
	@Autowired
	  NotificationService notificationService;
	
	@Autowired
	NotificationRepository notificationsRepository;
	
	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;
	
	@Autowired
	PushNotificationCall pushNotificationCall;
	
	@Autowired
	PushNotificationRequest pushNotificationRequest;


	
	@Override
	public ApiResponse reassignTicketToInfraTeam(TicketAssignee assignee) {
		
		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketRepository.getById(assignee.getTicketId());
		if (ticketObj != null) {
			Employee employeeObj = employeeRepository.getById(assignee.getEmployeeId());
			if (employeeObj != null) {
				assignee.setCreatedAt(new Date());
				assignee.setCreatedBy(userDetail.getUserId());
				assignee.setStatus(TicketAssigneeStatus.ACTIVE);
				
				ticketObj.setStatus(TicketStatus.ASSIGNED);
				ticketRepository.save(ticketObj);
				List<TicketAssignee> allAssignees = ticketAssignRepository.findByTicketId(assignee.getTicketId());
				for(int i=0;i<allAssignees.size();i++) {
					TicketAssignee prevAssignee = allAssignees.get(i);
					prevAssignee.setStatus(TicketAssigneeStatus.INACTIVE);
					
					ticketAssignRepository.save(prevAssignee);
				}
				ticketAssignRepository.save(assignee);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_REASSIGNED);
				response.setContent(null);
				
				
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);
			}
		
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse assignTicketToInfraTeam(TicketAssignee assignee) {
		
		ApiResponse response = new ApiResponse(false);
		Ticket ticketObj = ticketRepository.getById(assignee.getTicketId());
		if (ticketObj != null) {
			Employee employeeObj = employeeRepository.getById(assignee.getEmployeeId());
			if (employeeObj != null) {
				assignee.setCreatedAt(new Date());
				assignee.setCreatedBy(userDetail.getUserId());
				assignee.setStatus(TicketAssigneeStatus.ACTIVE);
				
				ticketObj.setStatus(TicketStatus.ASSIGNED);
				ticketRepository.save(ticketObj);
				
				List<TicketAssignee> allAssignees = ticketAssignRepository.findByTicketId(assignee.getTicketId());
				for(int i=0;i<allAssignees.size();i++) {
					TicketAssignee prevAssignee = allAssignees.get(i);
					prevAssignee.setStatus(TicketAssigneeStatus.INACTIVE);
					ticketAssignRepository.save(prevAssignee);
				}
				ticketAssignRepository.save(assignee);
					
				Map request=	new HashMap<>();
				request.put("uid", employeeObj.getUserCredientials().getUid());
				request.put("title", "TICKET_ASSIGNED");
				request.put("body","Ticket Assigned - " + ticketObj.getTicketDescription() );
				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 13, NotificationType.TICKET_ASSIGNED.toString()));
				
				//Inserting Notifications Details
				Notifications notifications = new Notifications();
				notifications.setNotificationDesc("Ticket Assigned - " + ticketObj.getTicketDescription());
				notifications.setNotificationType(NotificationType.TICKET_ASSIGNED);
				notifications.setSenderId(userDetail.getUserId());
				notifications.setReceiverId(userDetail.getUserId());
				notifications.setSeenStatus(false);
				notifications.setCreatedBy(userDetail.getUserId());
				notifications.setCreatedAt(new Date());
				notifications.setUpdatedBy(userDetail.getUserId());
				notifications.setLastUpdatedAt(new Date());
			//	notificationsRepository.save(notifications);
				notificationService.createNotification(notifications);
				
				response.setSuccess(true);
				response.setMessage(ResponseMessages.TICKET_ASSIGNED);
				response.setContent(null);
				
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);
			}
		
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.TICKET_NOT_EXIST);
			response.setContent(null);
		}
		return response;

	}

}
