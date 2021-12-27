package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notifications;

public interface NotificationService {

	//Page<Notification> getActiveNotifications(Pageable pageable);

	ApiResponse getAllNotifications(Pageable pageable);
	
	ApiResponse getNotificationCount(); 
	
	ApiResponse clearAllNotifications();

	Notifications createNotification(Notifications notification);

}
