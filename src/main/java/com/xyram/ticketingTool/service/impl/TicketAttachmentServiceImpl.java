package com.xyram.ticketingTool.service.impl;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xyram.ticketingTool.Repository.TicketCommentRepository;
import com.xyram.ticketingTool.Repository.ticketAttachmentRepository;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAttachment;
import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.TicketCommentsStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.service.TicketCommentService;
@Service
@Transactional
public class TicketAttachmentServiceImpl  implements TicketAttachmentService{

@Autowired
ticketAttachmentRepository  ticketattachmentRepository;

static ChannelSftp channelSftp = null;
static Session session = null;
static Channel channel = null;
static String PATHSEPARATOR = "/";

@Override
public Map storeImage(MultipartFile[] files,Ticket ticketId) {
	Map fileMap = new HashMap();
	System.out.println(files);
	  byte[] filearray;
	  for (MultipartFile constentFile : files) {
	try {
	       filearray=constentFile.getBytes();
//	       System.out.println(file.);
	      String filename=constentFile.getOriginalFilename();
	       addFileAdmin(constentFile);
//	       addFileCustomer(file);
	       if(addFileAdmin(constentFile)!= null) {
	    	  TicketAttachment ticketAttachment = new  TicketAttachment();
	    	  ticketAttachment.setTicket(ticketId);
	    	  ticketAttachment.setImagePath(filename);
	    	  ticketattachmentRepository.save(ticketAttachment);
	       }
	       fileMap.put("fileName", filename);
	} catch (IOException e) {
		e.printStackTrace();
	}
	  }
	return fileMap ;
}


public String addFileAdmin(MultipartFile file){
	System.out.println("bjsjsjn");
    String SFTPHOST = "13.229.55.43"; // SFTP Host Name or SFTP Host IP Address
    int SFTPPORT = 22; // SFTP Port Number
    String SFTPUSER = "ubuntu"; // User Name
    String SFTPPASS = ""; // Password
    String SFTPKEY = "/home/ubuntu/tomcat/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
//    String SFTPWORKINGDIR = "/home/ubuntu/tomcat-customer/webapps/images/daydrop-images"; 
    String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat/webapps/image/ticket-attachment";// Source Directory on SFTP server
    String fileNameOriginal = file.getOriginalFilename();
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
      channelSftp.put(file.getInputStream(),file.getOriginalFilename());
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
public String deleteImage(Integer ticketId) {
	String message = "";
	 TicketAttachment ticketObj=ticketattachmentRepository.getById(ticketId);
	 deleteFile(ticketObj.getImagePath());
	 ticketattachmentRepository.deletByTicket(ticketId);
	 
	 message = "Deleted ticket id successfully";
	 
	 return message;
}


private String deleteFile(String imagePath) {
	System.out.println("bjsjsjn");
    String SFTPHOST = "13.229.55.43"; // SFTP Host Name or SFTP Host IP Address
    int SFTPPORT = 22; // SFTP Port Number
    String SFTPUSER = "ubuntu"; // User Name
    String SFTPPASS = ""; // Password
    String SFTPKEY = "/home/ubuntu/tomcat/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
//    String SFTPWORKINGDIR = "/home/ubuntu/tomcat-customer/webapps/images/daydrop-images"; 
    String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat/webapps/image/ticket-attachment";// Source Directory on SFTP server
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

