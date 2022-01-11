package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
public class NotificationController {
	private final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	NotificationService notificationService;


	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/notifications",AuthConstants.HR_ADMIN_BASEPATH + "/notifications",
			AuthConstants.INFRA_USER_BASEPATH + "/notifications", AuthConstants.DEVELOPER_BASEPATH + "/notifications",AuthConstants.INFRA_ADMIN_BASEPATH + "/notifications" })
	public ApiResponse getAllNotifications(Pageable pageable) {
		logger.info("Received request for get all notifications");
		return notificationService.getAllNotifications(pageable);

	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getNotificationCount", AuthConstants.INFRA_ADMIN_BASEPATH + "/getNotificationCount",
			AuthConstants.INFRA_USER_BASEPATH + "/getNotificationCount", AuthConstants.DEVELOPER_BASEPATH + "/getNotificationCount", AuthConstants.HR_ADMIN_BASEPATH + "/getNotificationCount" })
	public ApiResponse getNotificationCount() {
		logger.info("Received request for getNotificationCount");
		return notificationService.getNotificationCount();

	} 

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/clearAllNotifications",AuthConstants.INFRA_ADMIN_BASEPATH + "/clearAllNotifications",
			AuthConstants.INFRA_USER_BASEPATH + "/clearAllNotifications", AuthConstants.DEVELOPER_BASEPATH + "/clearAllNotifications" })
	public ApiResponse clearAllNotifications() {
		logger.info("Received request for clearAllNotifications");
		return notificationService.clearAllNotifications();

	}

}
