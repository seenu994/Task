package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.ReminderRepository;
import com.xyram.ticketingTool.Repository.ReminderlogRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Notes;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Reminder;
import com.xyram.ticketingTool.entity.ReminderLog;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.ReminderRequest;
import com.xyram.ticketingTool.scheduler.ReminderScheduler;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.service.ReminderService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {
	private static final Logger logger = LoggerFactory.getLogger(EmpoloyeeServiceImpl.class);
	@Autowired
	CurrentUser currentUser;
	@Autowired
	NotificationService notificationService;
	@Autowired
	ReminderRepository reminderRepository;
	@Autowired
	PushNotificationCall pushNotificationCall;
	@Autowired
	PushNotificationRequest pushNotificationRequest;
	@Autowired
	ReminderlogRepository reminderlogRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReminderScheduler reminderScheduler;

	@Override
	public ApiResponse createReminder(ReminderRequest reminderObj) {
		ApiResponse response = new ApiResponse(false);
		if (reminderObj != null) {

			// Validate the Object
			Reminder reminder = new Reminder();

			reminder.setCreatedBy(currentUser.getUserId());
			reminder.setUserId(currentUser.getUserId());
			reminder.setUpdatedBy(currentUser.getUserId());
			reminder.setCreatedAt(new Date());
			reminder.setLastUpdatedAt(new Date());
			reminder.setTitle(reminderObj.getTitle());
			reminder.setReminderDateTime(reminderObj.getReminderDateTime());
			Reminder reminderNew;
			if (reminderObj.getReferences() != null && reminderObj.getReferences().length > 0) {
				reminder.setIsHost(true);
				String[] references = reminderObj.getReferences();
//				for (String iterable_element : references) {
//					System.out.println("getReferences:: " + iterable_element);
//					reminder.setReferences(iterable_element.join());
//				}
				reminder.setReferences(String.join(",", references));
//				reminder.setReferences(reminderObj.getReferences());
				reminder.setReferenceId(getAlphaNumericString(10));
				reminderNew = reminderRepository.save(reminder);
				for (String reference : reminderObj.getReferences()) {
					User user = userRepository.getById(reference);

					if (user != null) {

						Reminder reminderRef = new Reminder();

						reminderRef.setCreatedBy(currentUser.getUserId());
						reminderRef.setUserId(reference);
						reminderRef.setReferenceId(reminder.getReferenceId());
						reminderRef.setReferences(reminder.getReferences());
						reminderRef.setUpdatedBy(currentUser.getUserId());
						reminderRef.setCreatedAt(new Date());
						reminderRef.setLastUpdatedAt(new Date());
						reminderRef.setTitle(reminderObj.getTitle());
						reminderRef.setReminderDateTime(reminderObj.getReminderDateTime());
						reminderRef.setIsHost(false);
						reminderRef = reminderRepository.save(reminder);

						ReminderLog reminderLog = new ReminderLog();
						reminderLog.setDescription("REMINDER SENT");
						reminderLog.setuId(user.getUid());
						reminderLog.setUserId(user.getId());
						reminderlogRepository.save(reminderLog);

						// inserting notification details
						Notifications notifications = new Notifications();
						notifications.setNotificationDesc("REMINDER SENT - " + reminderNew.getTitle());
						notifications.setNotificationType(NotificationType.REMINDER_SENT);
						notifications.setSenderId(reference);
						notifications.setReceiverId(currentUser.getUserId());
						notifications.setSeenStatus(false);
						notifications.setCreatedBy(currentUser.getUserId());
						notifications.setCreatedAt(new Date());
						notifications.setUpdatedBy(currentUser.getUserId());
						notifications.setLastUpdatedAt(new Date());

						notificationService.createNotification(notifications);
					}

				}
			} else {
				reminderNew = reminderRepository.save(reminder);
			}

			System.out.println("Reminder notifyMemberArr::" + reminderObj.getReferences());

			response.setSuccess(true);
			response.setMessage(ResponseMessages.Reminder_ADDED);

		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Reminder_NOT_ADDED);
		}
		return response;
	}

	static String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}



	@Override
	public ApiResponse editReminder(ReminderRequest reminderObj, String ReminderId) {

		ApiResponse response = new ApiResponse(false);

		Reminder reminderObject = reminderRepository.findReminderById(ReminderId);
		System.out.println("ReminderId::" + reminderObject);
		if (reminderObject != null) {

//			try {
//				ReminderObject.setTitle(ReminderReq.getTitle());
//				ReminderObject.setReminderDateTime(ReminderReq.getReminderDateTime());
//				ReminderObject.setUpdatedBy(currentUser.getUserId());
//				ReminderObject.setLastUpdatedAt(new Date());
//				reminderRepository.save(ReminderObject);
//				response.setSuccess(true);
//				response.setMessage(ResponseMessages.Reminder_UPDATED);
//
//			} catch (Exception e) {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.Reminder_NOT_UPDATED + " " + e.getMessage());
//			}
			
			
			reminderObject.setCreatedBy(currentUser.getUserId());
			reminderObject.setUserId(currentUser.getUserId());
			reminderObject.setUpdatedBy(currentUser.getUserId());
			reminderObject.setCreatedAt(new Date());
			reminderObject.setLastUpdatedAt(new Date());
			reminderObject.setTitle(reminderObj.getTitle());
			reminderObject.setReminderDateTime(reminderObj.getReminderDateTime());
			Reminder reminderNew;
			if (reminderObj.getReferences() != null && reminderObj.getReferences().length > 0) {
				reminderObject.setIsHost(true);
				String[] references = reminderObj.getReferences();
//				for (String iterable_element : references) {
//					System.out.println("getReferences:: " + iterable_element);
//					reminder.setReferences(iterable_element.join());
//				}
				reminderObject.setReferences(String.join(",", references));
//				reminder.setReferences(reminderObj.getReferences());
				reminderObject.setReferenceId(getAlphaNumericString(10));
				reminderNew = reminderRepository.save(reminderObject);
				for (String reference : reminderObj.getReferences()) {
					User user = userRepository.getById(reference);

					if (user != null) {

						Reminder reminderRef = new Reminder();

						reminderRef.setCreatedBy(currentUser.getUserId());
						reminderRef.setUserId(reference);
						reminderRef.setReferenceId(reminderObject.getReferenceId());
						reminderRef.setReferences(reminderObject.getReferences());
						reminderRef.setUpdatedBy(currentUser.getUserId());
						reminderRef.setCreatedAt(new Date());
						reminderRef.setLastUpdatedAt(new Date());
						reminderRef.setTitle(reminderObj.getTitle());
						reminderRef.setReminderDateTime(reminderObj.getReminderDateTime());
						reminderRef.setIsHost(false);
						reminderRef = reminderRepository.save(reminderObject);

						ReminderLog reminderLog = new ReminderLog();
						reminderLog.setDescription("REMINDER SENT");
						reminderLog.setuId(user.getUid());
						reminderLog.setUserId(user.getId());
						reminderlogRepository.save(reminderLog);

						// inserting notification details
						Notifications notifications = new Notifications();
						notifications.setNotificationDesc("REMINDER SENT - " + reminderNew.getTitle());
						notifications.setNotificationType(NotificationType.REMINDER_SENT);
						notifications.setSenderId(reference);
						notifications.setReceiverId(currentUser.getUserId());
						notifications.setSeenStatus(false);
						notifications.setCreatedBy(currentUser.getUserId());
						notifications.setCreatedAt(new Date());
						notifications.setUpdatedBy(currentUser.getUserId());
						notifications.setLastUpdatedAt(new Date());

						notificationService.createNotification(notifications);
					}

				}
			} else {
				reminderNew = reminderRepository.save(reminderObject);
			}
			response.setSuccess(true);
			response.setMessage(ResponseMessages.Reminder_UPDATED);
			
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Reminder_NOT_UPDATED);
		}

		return response;
	}

	@Override
	public ApiResponse deleteReminder(String reminderId) {
		ApiResponse response = new ApiResponse(false);

//		ReminderRepository ReminderObject = reminderRepository.getById(ReminderId);
		Reminder ReminderObject = reminderRepository.findReminderById(reminderId);

		System.out.println("ReminderId::" + ReminderObject + reminderId);
		if (ReminderObject != null) {
			try {

				reminderRepository.deleteReminder(reminderId);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.Reminder_DELETED);

			} catch (Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.Reminder_NOT_DELETED + " " + e.getMessage());
			}
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Reminder_NOT_DELETED);
		}

		return response;
	}

	@Override
	public ApiResponse getAllReminder(Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		Page<Map> list = null;

		list = reminderRepository.getAllReminders(pageable);

		if (list.getSize() > 0) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.Reminder_LIST_RETRIEVED);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Reminders", list);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Reminder_LIST_NOT_RETRIEVED);
		}

		return response;
	}

	@Override
	public ApiResponse getRemindersByDate(Date paramDate) {

		ApiResponse response = new ApiResponse(false);
		List<Map> list = null;
		list = reminderRepository.getRemindersByDateValue(paramDate, currentUser.getUserId());
		
		System.out.println("reminderObj::" + list + "userId"+ currentUser.getUserId());

		Map content = new HashMap();
		content.put("Reminders", list);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.Reminder_LIST_RETRIEVED);
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Reminder_LIST_NOT_RETRIEVED);
		}

		return response;
	}

}
