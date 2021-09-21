package com.xyram.ticketingTool.Communication;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyram.ticketingTool.request.CurrentUser;


@Component
public class PushNotificationRequest {



	@Autowired
	CurrentUser currentUser;



	public static String PushNotification(Map request, int notificationTypeId, String type) {
		System.out.println("hi");
		try {

			String dt = null;
			dt = "\"data\": {\r\n"
//				+ "\"roomName\": \""+requestData.getRoomName()+"\",\r\n"
//				+ "\"twilioAccessToken\": \""+requestData.getTwilioAccessToken()+"\",\r\n"
					+ "\"typeId\":\"" + notificationTypeId + "\",\r\n" + "\"type\":\"" + type + "\" ,\r\n";

//	dt =prepareNotificationBody(dt , notificationType);
			dt = dt + "\"employeeId\": \"" + request.get("id") + "\"\r\n },";
			// call push notification
			String requestBody = "{\r\n" + "\"notification\": {\r\n" + "\"title\":\"" + request.get("title") + "\",\r\n"
					+ "\"body\": \"" + request.get("body") + "\" \r\n" + "}," + dt + "\"priority\": \"high\",\r\n"
					+ "\"content_available\": true,\r\n" + "\"to\": \"" + request.get("uid") + "\"\r\n" + "}";
			return requestBody;

		} catch (Exception e) {

		}
		return null;

	}

	
}