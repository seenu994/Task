package com.xyram.ticketingTool.Communication;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;


@Component
public class PushNotificationCall {

	private String path = "https://fcm.googleapis.com/fcm/send";
	private static String deviceId = "63b0f325b0475f48";
	
	 private static String requestBody = "{\r\n" + "\"notification\": {\r\n" +
	  "\"title\": \"RPM telelink notification\",\r\n" +
	  "\"body\": \"Hello World!\",\r\n" + "\"icon\": \"img\", \r\n" +
	  "\"sound\": \"default\"\r\n" + "},\r\n" + "\"priority\": \"high\",\r\n" +
	//  "\"content_available\": true,\r\n" + "\"to\": \"fSD3n5wWRZGwU-2jPUrSCw:APA91bEja7DdwL0ZUP_erwXTt_02x_zvA1KaIscdIY4TVs4WtWU52TI5Vm39KO01nvxbXRYLFpdtmS3NThbTWi7L-ZKU4jg8yortCdlEQwMOAks3_ZW_N0xd1AceqE-4usDNGOB3gcCv\"" + "}";
	  "\"content_available\": true,\r\n" + "\"to\": \"dVoAWhn5R1Wb-flyBb8epA:APA91bFaGY-7pD6HuOPi8iuxs8tpbyPVnapEFKH4F02XtLW9ECQA38hUkcvXKXUZidTquBl8dkQsr-jF45fIER9NW6o6Q-gfW3M-HIp1fJDM6vM7lFIg7uArD3UXmb492PY8ck78qHiw\"" + "}";


	public Object restCallToNotification(String requestContent) {
		try {
			System.out.println("PushNotification.restCallToNotification() :: \n "+requestContent);
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(path);  

			HttpHeaders headers = new HttpHeaders();
			//headers.set("Authorization", "key = AAAAYJ-rlwE:APA91bGoWg7KElR5D_w8qsz4qlOO4vZfVK1yvV1qt54rJuvefyMHA9YOg1EHWKn4PqAnximBZYDxSDFT5idPf2MUat5DOwr8L77fGcE0Dhq4V41q967d_aogReTuMJBzDAzJkN1hYO2s");
			//headers.set("Authorization", "key = AAAAYJ-rlwE:APA91bE7SQL7LkkBUiUfR99U-JyX1EOZaPW-Oghh1g7G1dQ-3DB0n8VDk9UyU2qz1Vxu9qoCT5d5d_zOtIa6KyOkQL6MIjrDF1OIutPwvZ_RbOKNGAX-PQLI0acyqqaawGJalrts80FH");
			headers.set("Authorization", "key = AAAALt2cXNc:APA91bFL0ppvetydLc5KNbJ3hAfrng9jcTpNOJU6iKkcoyH7C1sbqyoh8_weNCLoZCGDMqlJH09gADBcaPUpNFet_FrQtUzqBDucAGIZ4nvmCQu2FXHSJ7SbGTM7j2bfkbY_mFwLYXqt");
			headers.set("Content-Type", "application/json");

			Gson json = new Gson();  
			Object requestJson = json.fromJson(requestContent.toString(), Object.class);			
			HttpEntity<Object> request = new HttpEntity<>(requestJson, headers);
			ResponseEntity<Object> result = restTemplate.postForEntity(uri, request, Object.class);

			System.out.println("ResponseBody :: "+result.getBody());
			System.out.println("ResponseCode :: "+result.getStatusCode());
			return result.getBody();
		} catch (Exception e) {
			//e.printStackTrace();
			return "ERROR";
		}
	}
	public static void main(String args[]) {
		PushNotificationCall pushNotify = new PushNotificationCall();
		try {
			pushNotify.restCallToNotification(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}