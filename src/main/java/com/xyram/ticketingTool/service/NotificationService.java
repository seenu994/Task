package com.xyram.ticketingTool.service;

import javax.management.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notifications;

public interface NotificationService {

	//Page<Notification> getActiveNotifications(Pageable pageable);

	ApiResponse getAllNotifications(Pageable pageable);

}
