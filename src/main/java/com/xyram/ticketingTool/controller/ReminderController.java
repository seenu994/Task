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
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.service.ReminderService;
import com.xyram.ticketingTool.util.AuthConstants;


@RestController
@CrossOrigin
public class ReminderController {
	private final Logger logger = LoggerFactory.getLogger(ReminderController.class);
	
	@Autowired
	ReminderService reminderService;
	
	@PostMapping(value = AuthConstants.ADMIN_BASEPATH + "/createReminder")
	public ApiResponse createReminder(@RequestBody Reminder Reminder) {
		logger.info("Creating new Reminder");
		return reminderService.createReminder(Reminder);
	}
	

	
	@PostMapping(value = AuthConstants.ADMIN_BASEPATH + "/editReminder/{remId}")
	public ApiResponse editReminder(@PathVariable String remId,@RequestBody Reminder Reminder) {
		logger.info("Editing Reminder");
		return reminderService.editReminder(Reminder,remId);
	} 
	
	@DeleteMapping(value = AuthConstants.ADMIN_BASEPATH + "/deleteReminder/{ReminderId}")
	public ApiResponse deleteReminder(@PathVariable String ReminderId) {
		logger.info("Received deleting Reminder: ");
		return reminderService.deleteReminder(ReminderId);
	}
	
	@GetMapping(value = AuthConstants.ADMIN_BASEPATH + "/getAllReminders")
	public ApiResponse getAllReminders(Pageable pageable) {
		logger.info("Get all Reminders ");
		return reminderService.getAllReminder(pageable);
	} 
	
	@GetMapping(value = AuthConstants.ADMIN_BASEPATH + "/getRemindersByDate")
	public ApiResponse getRemindersByDateValue(@RequestParam Date paramDate) {
		logger.info("Get all Reminders by date");
		return reminderService.getRemindersByDate(paramDate);
	} 
	
}
