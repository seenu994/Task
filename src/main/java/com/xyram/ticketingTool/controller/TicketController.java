package com.xyram.ticketingTool.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketComments;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@RestController
@CrossOrigin
//@RequestMapping("/api/ticket")
class TicketController {
	private final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	TicketService ticketService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createTickets",
			AuthConstants.DEVELOPER_BASEPATH + "/createTickets", AuthConstants.INFRA_USER_BASEPATH + "/createTickets" })
	public ApiResponse createTickets(@RequestBody Ticket ticketRequest) {
		logger.info("Received request to add tickets");
		return ticketService.createTickets(ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/cancelTicket",
			AuthConstants.DEVELOPER_BASEPATH + "/cancelTicket", AuthConstants.INFRA_USER_BASEPATH + "/cancelTicket" })
	public ApiResponse cancelTicket(@RequestBody Ticket ticketRequest) {
		logger.info("Received request to update ticket for ticketId: " + ticketRequest.getId());
		return ticketService.cancelTicket(ticketRequest);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/resolveTicket",
			AuthConstants.DEVELOPER_BASEPATH + "/cancelTicket", AuthConstants.INFRA_USER_BASEPATH + "/cancelTicket" })
	public ApiResponse resolveTicket(@RequestBody Ticket ticketRequest) {
		logger.info("Received request to update ticket for ticketId: " + ticketRequest.getId());
		return ticketService.resolveTicket(ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/onHoldTicket",
			AuthConstants.INFRA_USER_BASEPATH + "/onHoldTicket" })
	public Ticket onHoldTicket(@RequestBody Ticket ticketRequest) {
		logger.info("Received request to update ticket for ticketId: " + ticketRequest.getId());
		return ticketService.onHoldTicket(ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/editTicket/{ticketId}" })
	public ApiResponse editTicket(@PathVariable Integer ticketId, @RequestBody Ticket ticketRequest) {
		logger.info("Recive request to edit ticket by id:" + ticketRequest.getId());
		return ticketService.editTicket(ticketId, ticketRequest);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/reopenTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/reopenTicket/{ticketId}" })
	public ApiResponse reopenTicket(@PathVariable Integer ticketId, @RequestBody Comments commentObj) {
//		logger.info("Recive request to reopened ticket by id:" + ticketRequest.getId());
		commentObj.setCreatedAt(new Date());
		return ticketService.reopenTicket(ticketId, commentObj); 
	}

	/*
	 * @GetMapping(value = { AuthConstants.ADMIN_BASEPATH +"/getAllTicket") public
	 * Page<Ticket> getAllTicket(Pageable pageable) {
	 * logger.info("indide TicketController :: getAllTicket"); return
	 * ticketService.getAllTicket(pageable); }
	 */

}
