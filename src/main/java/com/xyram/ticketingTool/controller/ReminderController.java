package com.xyram.ticketingTool.controller;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.request.ReminderRequest;
import com.xyram.ticketingTool.service.ReminderService;

@RestController
@CrossOrigin
public class ReminderController {
	private final Logger logger = LoggerFactory.getLogger(ReminderController.class);

	@Autowired
	ReminderService reminderService;

	@PostMapping("/createReminder")
	public ApiResponse createReminder(@RequestBody ReminderRequest ReminderRequest) {
//		logger.info("Creating new Reminder");
		return reminderService.createReminder(ReminderRequest);
	}

	@PostMapping("/editReminder/{remId}")
	public ApiResponse editReminder(@PathVariable String remId, @RequestBody ReminderRequest Reminder) {
		logger.info("Editing Reminder");
		return reminderService.editReminder(Reminder, remId);
	}

	@DeleteMapping("/deleteReminder/{ReminderId}")
	public ApiResponse deleteReminder(@PathVariable String ReminderId) {
		logger.info("Received deleting Reminder: ");
		return reminderService.deleteReminder(ReminderId);
	}

	@GetMapping("/getAllReminders")
	public ApiResponse getAllReminders(Pageable pageable) {
		logger.info("Get all Reminders ");
		return reminderService.getAllReminder(pageable);
	}

	@GetMapping("/getRemindersByDate")
	public ApiResponse getRemindersByDateValue(@RequestParam Date paramDate) {
		logger.info("Get all Reminders by date");
		return reminderService.getRemindersByDate(paramDate);
	}

}
