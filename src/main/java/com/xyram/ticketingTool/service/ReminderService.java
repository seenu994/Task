package com.xyram.ticketingTool.service;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.request.ReminderRequest;


public interface ReminderService {
	ApiResponse createReminder(ReminderRequest Reminder);

	ApiResponse editReminder(Reminder ReminderReq, String ReminderId);

	ApiResponse deleteReminder(String ReminderId);

	ApiResponse getAllReminder(Pageable pageable);

	ApiResponse getRemindersByDate(Date paramDate);
}
