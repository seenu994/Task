package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;

public interface TicketAttachmentService {

	public Map storeImage(MultipartFile[] files, String ticketId);

	//public Map deleteImage(MultipartFile file, Ticket ticketId);

	public ApiResponse deleteImage(String ticketId);
}
		