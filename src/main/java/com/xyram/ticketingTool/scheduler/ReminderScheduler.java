package com.xyram.ticketingTool.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.ReminderRepository;
import com.xyram.ticketingTool.Repository.ReminderlogRepository;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.ReminderLog;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.service.impl.ReminderServiceImpl;

@Component
public class ReminderScheduler {
	@Autowired
	PushNotificationCall pushNotificationCall;
	@Autowired
	PushNotificationRequest pushNotificationRequest;
	@Autowired
	ReminderServiceImpl serviceImpl;
	@Autowired
	ReminderlogRepository reminderlogRepository;

	@Scheduled(cron = "0 */5 * * * *")
	public void reminderJob() {
		List<ReminderLog> getAllLogs = reminderlogRepository.getAllLogs();
		getAllLogs.forEach((e) -> {
			System.out.println("element ::" + e);
			Map<Object, Object> request = new HashMap<>();
			request.put("id", e.getUserId());
			request.put("uid", e.getuId());
			request.put("description", "REMINDER SENT");
			request.put("body", "REMINDER SENT - " + e.getDescription());
			pushNotificationCall.restCallToNotification(
					pushNotificationRequest.PushNotification(request, 20, NotificationType.REMINDER_SENT.toString()));

			reminderlogRepository.deleteReminderlog(e.getReminderlogId());
		});

	}
}
