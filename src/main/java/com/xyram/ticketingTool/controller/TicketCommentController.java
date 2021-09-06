package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.service.TicketAssignService;
import com.xyram.ticketingTool.service.TicketCommentService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
//@RequestMapping("/api/ticketComments")
public class TicketCommentController {
	private final Logger logger = LoggerFactory.getLogger(TicketCommentController.class);
	@Autowired
	TicketCommentService ticketCommentService;

	/**
	 * add ticket comments
	 * 
	 * URL:http://<server name>/<context>/api/ticketAssign/assignticket
	 * 
	 * 
	 * 
	 */
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH + "/addCommentForTicket",AuthConstants.DEVELOPER_BASEPATH + "/addCommentForTicket",AuthConstants.INFRA_USER_BASEPATH + "/addCommentForTicket"})
	public TicketComments addCommentForTicket(@RequestBody TicketComments ticketComments) {
		logger.info("Received request to add comments for tickets");
		return ticketCommentService.addCommentForTicket(ticketComments);
	}

	@PutMapping(value= {AuthConstants.ADMIN_BASEPATH +"/editTicketCommentns/{ticketCommentsId}",AuthConstants.DEVELOPER_BASEPATH +"/editTicketCommentns/{ticketCommentsId}",AuthConstants.INFRA_USER_BASEPATH +"/editTicketCommentns/{ticketCommentsId}"})
	public TicketComments editTicketCommentns(@PathVariable String ticketCommentsId,
			@RequestBody TicketComments ticketComments) {
		logger.info("Recive request to edit ticket commenst by id:" + ticketComments.getId());
		return ticketCommentService.editTicketCommentns(ticketCommentsId, ticketComments);
	}
}