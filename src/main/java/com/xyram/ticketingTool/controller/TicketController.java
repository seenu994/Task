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

//	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllTicketsByStatus/{statusId}",
//			AuthConstants.DEVELOPER_BASEPATH + "/getAllTicketsByStatus/{statusId}", AuthConstants.INFRA_USER_BASEPATH + "/getAllTicketsByStatus/{statusId}" })
//	public ApiResponse getAllTicketsByStatus(@PathVariable String statusId) {
//		logger.info("Received request to add tickets");
//		return ticketService.getAllTicketsByStatus(statusId);
//	}

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createTickets",
			AuthConstants.DEVELOPER_BASEPATH + "/createTickets", AuthConstants.INFRA_USER_BASEPATH + "/createTickets" })
	public ApiResponse createTickets(@RequestBody Ticket ticketRequest) {
		logger.info("Received request to add tickets");
		return ticketService.createTickets(ticketRequest);
	}
	

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/cancelTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/cancelTicket/{ticketId}", AuthConstants.INFRA_USER_BASEPATH + "/cancelTicket/{ticketId}" })
	public ApiResponse cancelTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.cancelTicket(ticketId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/resolveTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/resolveTicket", AuthConstants.INFRA_USER_BASEPATH + "/resolveTicket" })
	public ApiResponse resolveTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.resolveTicket(ticketId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/onHoldTicket",
			AuthConstants.INFRA_USER_BASEPATH + "/onHoldTicket" })
	public Ticket onHoldTicket(  @RequestBody Ticket ticketRequest) {
		logger.info("Received request to update ticket for ticketId: " +   ticketRequest.getId());
		return ticketService.onHoldTicket(ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/editTicket/{ticketId}" })
	public ApiResponse editTicket(@PathVariable String ticketId, @RequestBody Ticket ticketRequest) {
		logger.info("Recive request to edit ticket by id:" + ticketRequest.getId());
		return ticketService.editTicket(ticketId, ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/reopenTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/reopenTicket/{ticketId}" })
	public ApiResponse reopenTicket(@PathVariable String ticketId, @RequestBody Comments commentObj) {
//		logger.info("Recive request to reopened ticket by id:" + ticketRequest.getId());
		commentObj.setCreatedAt(new Date());
		return ticketService.reopenTicket(ticketId, commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addComment}",
			AuthConstants.DEVELOPER_BASEPATH + "/addComment" })
	public ApiResponse addComment(@RequestBody Comments commentObj) {
		commentObj.setCreatedAt(new Date());
		return ticketService.addComment(commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editComment",
			AuthConstants.DEVELOPER_BASEPATH + "/editComment" })
	public ApiResponse editComment(@RequestBody Comments commentObj) {
		commentObj.setCreatedAt(new Date());
		return ticketService.editComment(commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteComment",
			AuthConstants.DEVELOPER_BASEPATH + "/deleteComment" })
	public ApiResponse deleteComment(@RequestBody Comments commentObj) {
		commentObj.setCreatedAt(new Date());
		return ticketService.deleteComment(commentObj);
	}

	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllTicket",AuthConstants.INFRA_USER_BASEPATH +"/getAllTicket"})
	public ApiResponse getAllTicket(Pageable pageable) {
		logger.info("indide Ticket controller :: getAllTicket");
		return ticketService.getAllTicket(pageable);
	}
	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllTktByStatus", 
			AuthConstants.INFRA_USER_BASEPATH +"/getAllTktByStatus", 
			AuthConstants.DEVELOPER_BASEPATH +"/getAllTktByStatus"})
	public ApiResponse getAllTicketsByStatus() {
		logger.info("indide Ticket controller :: getAllTicket");
		return ticketService.getAllTicketsByStatus();
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/inprogressTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/inprogressTicket/{ticketId}", AuthConstants.INFRA_USER_BASEPATH + "/inprogressTicket/{ticketId}" })
	public ApiResponse inprogressTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.inprogressTicket(ticketId);
}
}