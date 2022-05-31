package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.ArticleRepository;
import com.xyram.ticketingTool.Repository.ReminderRepository;
import com.xyram.ticketingTool.Repository.ReminderlogRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobInterviews;
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
			reminder.setReminderDate(reminderObj.getReminderDate());
			reminder.setReminderTime(reminderObj.getReminderTime());
			Reminder reminderNew;
			if(reminderObj.getReferences() != null && reminderObj.getReferences().length > 0) {
				reminder.setIsHost(true);
				reminder.setReferenceId(getAlphaNumericString(10));
				reminderNew = reminderRepository.save(reminder);
				for(String reference: reminderObj.getReferences()) {
					User user = userRepository.getById(reference);

					if (user != null) {
				
						Reminder reminderRef = new Reminder();
						
						reminderRef.setCreatedBy(currentUser.getUserId());
						reminderRef.setUserId(reference);
						reminder.setReferenceId(reminder.getReferenceId());
						reminderRef.setUpdatedBy(currentUser.getUserId());
						reminderRef.setCreatedAt(new Date());
						reminderRef.setLastUpdatedAt(new Date());
						reminderRef.setTitle(reminderObj.getTitle());
						reminderRef.setReminderDate(reminderObj.getReminderDate());
						reminderRef.setReminderTime(reminderObj.getReminderTime());
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
			}else {
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
	
	static String getAlphaNumericString(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }

//	public void reminderJob(Map<Object, Object> request) {
//		pushNotificationCall.restCallToNotification(
//				pushNotificationRequest.PushNotification(request, 20, NotificationType.REMINDER_SENT.toString()));
//
//	}

	@Override
	public ApiResponse editReminder(Reminder ReminderReq, String ReminderId) {

		ApiResponse response = new ApiResponse(false);

//		ReminderRepository ReminderObject = reminderRepository.getById(ReminderId);
		Reminder ReminderObject = reminderRepository.findReminderById(ReminderId);
		System.out.println("ReminderId::" + ReminderObject);
//		if (currentUser.getUserRole().equals("TICKETINGTOOL_ADMIN")) {
//			response.setSuccess(false);
//			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
//			return response;
//		}

		if (ReminderObject != null) {

			try {
				ReminderObject.setTitle(ReminderReq.getTitle());
				ReminderObject.setReminderDate(ReminderReq.getReminderDate());
				ReminderObject.setReminderTime(ReminderReq.getReminderTime());
//				ReminderObject.setNotifyMembers(ReminderReq.getNotifyMembers());
				ReminderObject.setUpdatedBy(currentUser.getUserId());
				ReminderObject.setLastUpdatedAt(new Date());
//				System.out.println("ReminderId inside ::" + ReminderReq.getReminderDateTime());
				reminderRepository.save(ReminderObject);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.Reminder_UPDATED);

			} catch (Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.Reminder_NOT_UPDATED + " " + e.getMessage());
			}
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
		System.out.println("param Dte::" + paramDate);
		ApiResponse response = new ApiResponse(false);
		List<Map> list = null;
		list = reminderRepository.getRemindersByDateValue(paramDate, currentUser.getUserId());
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
