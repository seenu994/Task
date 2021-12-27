package com.xyram.ticketingTool.service.impl;


import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.ticket.Service.NotificationDeclareDataService;
@Service
@Transactional
public class NotificationDeclaredDataServiceImpl  implements NotificationDeclareDataService {
	
@Autowired
CurrentUser userDetail;

@Autowired
NotificationService notificationService;

	@Override
	public Notifications notifyData(Notifications notifications) {
		Employee empObj=new Employee();

		notifications.setNotificationDesc("employee created - " + empObj.getFirstName());
		notifications.setNotificationType(NotificationType.JOB_APPLOCATION_CREATED);
		notifications.setSenderId(empObj.getReportingTo());
		notifications.setReceiverId(userDetail.getUserId());
		notifications.setSeenStatus(false);
		notifications.setCreatedBy(userDetail.getUserId());
		notifications.setCreatedAt(new Date());
		notifications.setUpdatedBy(userDetail.getUserId());
		notifications.setLastUpdatedAt(new Date());

	    return 	notificationService.createNotification(notifications);
	}
}