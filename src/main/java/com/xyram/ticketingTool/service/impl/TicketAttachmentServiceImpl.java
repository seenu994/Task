package com.xyram.ticketingTool.service.impl;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.NotificationRepository;
import com.xyram.ticketingTool.Repository.TicketAssignRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
//import com.xyram.ticketingTool.Repository.TicketCommentRepository;
import com.xyram.ticketingTool.Repository.ticketAttachmentRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAttachment;
import com.xyram.ticketingTool.enumType.NotificationType;
//import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.TicketCommentsStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.ResponseMessages;
//import com.xyram.ticketingTool.service.TicketCommentService;
@Service
@Transactional
public class TicketAttachmentServiceImpl  implements TicketAttachmentService{

@Autowired
ticketAttachmentRepository  ticketattachmentRepository;

@Autowired
TicketRepository  ticketRepository;

@Autowired
TicketAssignRepository ticketAssigneeRepository;

@Autowired
NotificationRepository notificationsRepository;

@Autowired
EmployeeRepository employeeRepository;

@Autowired
PushNotificationCall pushNotificationCall;

@Autowired
PushNotificationRequest pushNotificationRequest;

@Autowired
CurrentUser userDetail;


static ChannelSftp channelSftp = null;
static Session session = null;
static Channel channel = null;
static String PATHSEPARATOR = "/";

String SFTPKEY = "/home/ubuntu/tomcat-be/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat-be/webapps/image/ticket-attachment";


@Override
public Map storeImage(MultipartFile[] files,String ticketId) {
	Map fileMap = new HashMap();
	System.out.println(files);
	  byte[] filearray;
	  for (MultipartFile constentFile : files) {
	try {
	       filearray=constentFile.getBytes();
//	       System.out.println(file.);
	       String fileextension = constentFile.getOriginalFilename().substring(constentFile.getOriginalFilename().lastIndexOf("."));
	      String filename = getRandomFileName()+fileextension;//constentFile.getOriginalFilename();
	      
//	       addFileAdmin(constentFile);
//	       addFileCustomer(file);
//	      String[] fileextension = constentFile.getOriginalFilename().split("-");
	       if(addFileAdmin(constentFile,filename)!= null) {
	    	  TicketAttachment ticketAttachment = new  TicketAttachment();
	    	  Ticket tickets = ticketRepository.getById(ticketId);
	    	  ticketAttachment.setCreatedBy(userDetail.getUserId());
	    	  ticketAttachment.setCreatedAt(new Date());
	    	  ticketAttachment.setUpdatedBy(userDetail.getUserId());
	    	  
	    	  ticketAttachment.setLastUpdatedAt(new Date());
	    	  ticketAttachment.setTicket(tickets);
	    	  ticketAttachment.setImagePath(filename);
	    	  ticketattachmentRepository.save(ticketAttachment);
	    	  
	    	  if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					if (tickets.getStatus().equals(TicketStatus.ASSIGNED.toString())
							|| tickets.getStatus().equals(TicketStatus.INPROGRESS.toString())) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeId(tickets.getId()), "New Attachment added by User - ", tickets, "TICKET_COMMENTED", 24);
					}						
				}else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")) {
					if (tickets.getStatus().equals(TicketStatus.ASSIGNED.toString())
							|| tickets.getStatus().equals(TicketStatus.INPROGRESS.toString())) {
						// Change userDetail.getUserId() to Ticket Assignee
						sendPushNotification(ticketAssigneeRepository.getAssigneeId(tickets.getId()), "New Attachment added by Admin - ", tickets, "TICKET_COMMENTED", 23);
					}
					sendPushNotification(tickets.getCreatedBy(),"New Attachment added by Admin - ",tickets,"TICKET_COMMENTED",23);
				}else {
					sendPushNotification(tickets.getCreatedBy(),"New Attachment added by Infra User - ",tickets,"TICKET_COMMENTED",25);
				}
	       }
	       fileMap.put("fileName", filename);
	} catch (IOException e) {
		e.printStackTrace();
	}
	  }
	return fileMap ;
}

public String getRandomFileName() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();

    return generatedString;
}

public void sendPushNotification(String userId, String message, Ticket ticketNewRequest, String title, int notiType) {
	
	Employee employeeObj = employeeRepository.getbyUserByUserId(userId);
	if(employeeObj != null) {
		
		if(employeeObj.getUserCredientials() != null) {
			if(employeeObj.getUserCredientials().getUid() != null) {
				Map request=	new HashMap<>();
				request.put("id",userDetail.getUserId());
				request.put("uid", employeeObj.getUserCredientials().getUid());
				request.put("title", title);
				request.put("body",message + ticketNewRequest.getTicketDescription() );
				Notifications notifications2 = new Notifications();
		
				if (userDetail.getUserRole().equalsIgnoreCase("DEVELOPER")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, notiType, NotificationType.ATTACHMENT_ADDED_BY_DEV.toString()));
					notifications2.setNotificationType(NotificationType.ATTACHMENT_ADDED_BY_DEV);
		
				}else if (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")) {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, notiType, NotificationType.ATTACHMENT_ADDED_BY_ADMIN.toString()));
					notifications2.setNotificationType(NotificationType.ATTACHMENT_ADDED_BY_ADMIN);
		
				}else {
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, notiType, NotificationType.ATTACHMENT_ADDED_BY_INFRA_USER.toString()));
					notifications2.setNotificationType(NotificationType.ATTACHMENT_ADDED_BY_INFRA_USER);
				}
				
				notifications2.setNotificationDesc(message + ticketNewRequest.getTicketDescription());
				notifications2.setSenderId(userDetail.getUserId());
				notifications2.setReceiverId(userId);
				notifications2.setSeenStatus(false);
				notifications2.setCreatedBy(userDetail.getUserId());
				notifications2.setCreatedAt(new Date());
				notifications2.setUpdatedBy(userDetail.getUserId());
				notifications2.setLastUpdatedAt(new Date());
				notificationsRepository.save(notifications2);
			}
			
		}
	}
	
}


public String addFileAdmin(MultipartFile file, String fileName){
	System.out.println("bjsjsjn");
    String SFTPHOST = "13.229.182.200"; // SFTP Host Name or SFTP Host IP Address
    int SFTPPORT = 22; // SFTP Port Number
    String SFTPUSER = "ubuntu"; // User Name
    String SFTPPASS = ""; // Password
    String SFTPKEY = "/home/ubuntu/tomcat-be/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
//    String SFTPWORKINGDIR = "/home/ubuntu/tomcat-customer/webapps/images/daydrop-images"; 
    String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat-be/webapps/image/ticket-attachment";// Source Directory on SFTP server
    String fileNameOriginal = fileName;
//    String LOCALDIRECTORY = "C:\\daydrop-images"; // Local Target Directory
    try {
          JSch jsch = new JSch();
        if (SFTPKEY != null && !SFTPKEY.isEmpty()) {
			jsch.addIdentity(SFTPKEY);
		}
        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//        session.setPassword(SFTPPASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(); // Create SFTP Session
        channel = session.openChannel("sftp"); // Open SFTP Channel
        channel.connect();
        channelSftp = (ChannelSftp) channel;
//        channelSftp.cd(SFTPWORKINGDIR);
//        channelSftp.put(file.getInputStream(),file.getOriginalFilename());
//        channelSftp.disconnect();
//        channel = session.openChannel("sftp"); // Open SFTP Channel
//        channel.connect();
//        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTPWORKINGDIRAADMIN);// Change Directory on SFTP Server
//      File f = new File(fileName);
      channelSftp.put(file.getInputStream(),fileName);
        System.out.println("added");
//        recursiveFolderUpload(LOCALDIRECTORY, SFTPWORKINGDIR);
    } catch (Exception ex) {
        ex.printStackTrace();
    } 
        finally {
        if (channelSftp != null)
            channelSftp.disconnect();
        if (channel != null)
            channel.disconnect();
        if (session != null)
            session.disconnect();
    }
	return fileNameOriginal;
	}


@Override
public ApiResponse deleteImage(String ticketId) {
	ApiResponse response = new ApiResponse(false);
	 TicketAttachment ticketObj=ticketattachmentRepository.getById(ticketId);
	 deleteFile(ticketObj.getImagePath());
	 ticketattachmentRepository.deletByTicket(ticketId);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.DELETE_ATTACHMENT);
				response.setContent(null);
			

		return response;
}


private String deleteFile(String imagePath) {
	System.out.println("bjsjsjn");
    String SFTPHOST = "13.229.55.43"; // SFTP Host Name or SFTP Host IP Address
    int SFTPPORT = 22; // SFTP Port Number
    String SFTPUSER = "ubuntu"; // User Name
    String SFTPPASS = ""; // Password
    
//    String fileNameOriginal = file.getOriginalFilename();
//    String LOCALDIRECTORY = "C:\\daydrop-images"; // Local Target Directory
    try {
          JSch jsch = new JSch();
        if (SFTPKEY != null && !SFTPKEY.isEmpty()) {
			jsch.addIdentity(SFTPKEY);
		}
        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//        session.setPassword(SFTPPASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(); // Create SFTP Session
        channel = session.openChannel("sftp"); // Open SFTP Channel
        channel.connect();
        channelSftp = (ChannelSftp) channel;
//        channelSftp.cd(SFTPWORKINGDIR);
//        channelSftp.put(file.getInputStream(),file.getOriginalFilename());
//        channelSftp.disconnect();
//        channel = session.openChannel("sftp"); // Open SFTP Channel
//        channel.connect();
//        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTPWORKINGDIRAADMIN);// Change Directory on SFTP Server
//      File f = new File(fileName);
      channelSftp.rm(imagePath);
        System.out.println("added");
//        recursiveFolderUpload(LOCALDIRECTORY, SFTPWORKINGDIR);
    } catch (Exception ex) {
        ex.printStackTrace();
    } 
        finally {
        if (channelSftp != null)
            channelSftp.disconnect();
        if (channel != null)
            channel.disconnect();
        if (session != null)
            session.disconnect();
    }
    String message = "deleted";
	return message;// TODO Auto-generated method stub
	
}
}

//public String addFileCustomer(MultipartFile file){
//	System.out.println("bjsjsjn");
//    String SFTPHOST = "13.233.17.5"; // SFTP Host Name or SFTP Host IP Address
//    int SFTPPORT = 22; // SFTP Port Number
//    String SFTPUSER = "ubuntu"; // User Name
//    String SFTPPASS = ""; // Password
//    String SFTPKEY = "/home/ubuntu/tomcat-admin/webapps/daydrop/WEB-INF/classes/daydropdev.ppk";
//    String SFTPWORKINGDIR = "/home/ubuntu/tomcat-customer/webapps/images/daydrop-images"; 
////    String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat-admin/webapps/images-admin/daydrop-images";// Source Directory on SFTP server
//    String fileNameOriginal = file.getOriginalFilename();
////    String LOCALDIRECTORY = "C:\\daydrop-images"; // Local Target Directory
//    try {
//          JSch jsch = new JSch();
//        if (SFTPKEY != null && !SFTPKEY.isEmpty()) {
//			jsch.addIdentity(SFTPKEY);
//		}
//        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
////        session.setPassword(SFTPPASS);
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//        session.connect(); // Create SFTP Session
//        channel = session.openChannel("sftp"); // Open SFTP Channel
//        channel.connect();
//        channelSftp = (ChannelSftp) channel;
////        channelSftp.cd(SFTPWORKINGDIR);
////        channelSftp.put(file.getInputStream(),file.getOriginalFilename());
////        channelSftp.disconnect();
////        channel = session.openChannel("sftp"); // Open SFTP Channel
////        channel.connect();
////        channelSftp = (ChannelSftp) channel;
//        channelSftp.cd(SFTPWORKINGDIR);// Change Directory on SFTP Server
////      File f = new File(fileName);
//      channelSftp.put(file.getInputStream(),file.getOriginalFilename());
//        System.out.println("added");
////        recursiveFolderUpload(LOCALDIRECTORY, SFTPWORKINGDIR);
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    } 
//        finally {
//        if (channelSftp != null)
//            channelSftp.disconnect();
//        if (channel != null)
//            channel.disconnect();
//        if (session != null)
//            session.disconnect();
//    }
//	return fileNameOriginal;
//	}

