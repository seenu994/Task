package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAttachment;

public interface TicketAttachmentService {

	public Map storeImage(MultipartFile file, Ticket ticketId);

	//public Map deleteImage(MultipartFile file, Ticket ticketId);

	public String deleteImage(Integer ticketId);
}
		