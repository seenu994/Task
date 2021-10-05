package com.xyram.ticketingTool.controller;

import java.util.Map;

import javax.management.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
public class NotificationController {
	private final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	NotificationService notificationService;


	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/notifications",
			AuthConstants.INFRA_USER_BASEPATH + "/notifications", AuthConstants.DEVELOPER_BASEPATH + "/notifications" })
	public ApiResponse getAllNotifications(Pageable pageable) {
		logger.info("Received request for get all notifications");
		return notificationService.getAllNotifications(pageable);

	}

	

}
