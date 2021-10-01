package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.entity.TicketFile;



public interface FileService  {
	
	Map saveFileToDB(MultipartFile file);
	
	TicketFile getFileByFileName(String fileName);
}