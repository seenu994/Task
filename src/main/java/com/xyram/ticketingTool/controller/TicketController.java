package com.xyram.ticketingTool.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.AuthConstants;

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

	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createTickets",
			AuthConstants.INFRA_USER_BASEPATH + "/createTickets" })
	public ApiResponse createTickets(@RequestPart(name = "files", required = false) MultipartFile[] files,
			@RequestPart String ticketRequest) {
		logger.info("Received request to add tickets");
		return ticketService.createTickets(files, ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/cancelTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/cancelTicket/{ticketId}",
			AuthConstants.INFRA_USER_BASEPATH + "/cancelTicket/{ticketId}" })
	public ApiResponse cancelTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.cancelTicket(ticketId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/resolveTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/resolveTicket/{ticketId}",
			AuthConstants.INFRA_USER_BASEPATH + "/resolveTicket/{ticketId}" })
	public ApiResponse resolveTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.resolveTicket(ticketId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/onHoldTicket/{ticketId}",
			AuthConstants.INFRA_USER_BASEPATH + "/onHoldTicket/{ticketId} " })
	public ApiResponse onHoldTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket status for ticketId: " + ticketId);
		return ticketService.onHoldTicket(ticketId);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/editTicket/{ticketId}" })
	public ApiResponse editTicket(@RequestPart(name = "files", required = false) MultipartFile[] files,@PathVariable String ticketId, @RequestPart Ticket ticketRequest) {
		logger.info("Recive request to edit ticket by id:" + ticketRequest.getId());
		return ticketService.editTicket(files,ticketId, ticketRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/reopenTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/reopenTicket/{ticketId}" })
	public ApiResponse reopenTicket(@PathVariable String ticketId, @RequestBody Comments commentObj) {
//		logger.info("Recive request to reopened ticket by id:" + ticketRequest.getId());
		commentObj.setCreatedAt(new Date());
		return ticketService.reopenTicket(ticketId, commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/addComment",
			AuthConstants.DEVELOPER_BASEPATH + "/addComment", 
			AuthConstants.INFRA_USER_BASEPATH + "/addComment" })
	public ApiResponse addComment(@RequestBody Comments commentObj) {
		commentObj.setCreatedAt(new Date());
		return ticketService.addComment(commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editComment",
			AuthConstants.DEVELOPER_BASEPATH + "/editComment", 
			AuthConstants.INFRA_USER_BASEPATH + "/editComment" })
	public ApiResponse editComment(@RequestBody Comments commentObj) {
		commentObj.setCreatedAt(new Date());
		return ticketService.editComment(commentObj);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteComment/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/deleteComment/{ticketId}", 
			AuthConstants.INFRA_USER_BASEPATH + "/deleteComment/{ticketId}" })
	public ApiResponse deleteComment(@PathVariable("ticketId") String ticketId) {
		//commentObj.setCreatedAt(new Date());
		return ticketService.deleteComment(ticketId);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllTicket",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllTicket" ,AuthConstants.DEVELOPER_BASEPATH + "/getAllTicket"})
	public ApiResponse getAllTicket(Pageable pageable) {
		logger.info("inside Ticket controller :: getAllTicket");
		return ticketService.getAllTicket(pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchTicket/{searchString}",
			AuthConstants.INFRA_USER_BASEPATH + "/searchTicket/{searchString}" ,
			AuthConstants.DEVELOPER_BASEPATH + "/searchTicket/{searchString}"})
	public ApiResponse searchTicket(@PathVariable String searchString) {
		logger.info("inside Ticket controller :: getAllTicket");
		return ticketService.searchTicket(searchString);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllTktByStatus",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllTktByStatus",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllTktByStatus" })
	public ApiResponse getAllTicketsByStatus(Pageable pageable) {
		logger.info("inside Ticket controller :: getAllTicket");
		return ticketService.getAllTicketsByStatus(pageable);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllCompletedTickets",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllCompletedTickets",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllCompletedTickets" })
	public ApiResponse getAllCompletedTickets() {
		logger.info("inside Ticket controller :: getAllTicket");
		return ticketService.getAllCompletedTickets();
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/inprogressTicket/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/inprogressTicket/{ticketId}",
			AuthConstants.INFRA_USER_BASEPATH + "/inprogressTicket/{ticketId}" })
	public ApiResponse inprogressTicket(@PathVariable String ticketId) {
		logger.info("Received request to update ticket for ticketId: " + ticketId);
		return ticketService.inprogressTicket(ticketId);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTktDetailsById/{ticketId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getTktDetailsById/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getTktDetailsById/{ticketId}" })
	public ApiResponse getTktDetailsById(@PathVariable String ticketId) {
			logger.info("inside Ticket controller :: getTktDetailsById");
			return ticketService.getTktDetailsById(ticketId);
		}
		
		@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTicketSearchById/{ticketId}",
				AuthConstants.INFRA_USER_BASEPATH + "/getTicketSearchById/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getTicketSearchById/{ticketId}" })
	public ApiResponse getTicketSearchById(@PathVariable String ticketId) {
		logger.info("inside Ticket controller :: getTicketSearchById");
		return ticketService.getTicketSearchById(ticketId);
	}
		
		
		@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTickets/{ticketId}",
				AuthConstants.INFRA_USER_BASEPATH + "/getTickets/{ticketId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getTickets/{ticketId}" })
		public Optional<Ticket> getTicketDetailsById(@PathVariable String ticketId) {
			logger.info(" inside Ticket controller :: get ticket details by Id");
			return ticketService.findById(ticketId);
		}
		
		@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllTickets",
				AuthConstants.INFRA_USER_BASEPATH + "/getAllTickets",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllTickets" })
		public List<Ticket> getAllTicketDetails() {
			logger.info(" inside Ticket controller :: get ticket details of all");
			return ticketService.findAll();
		}
		
		//duration/projectName/EmpName/
		  @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllTktByDuration/{date1}/{date2}",
		  AuthConstants.INFRA_USER_BASEPATH +"/getAllTktByDuration/{date1}/{date2}",
		  AuthConstants.DEVELOPER_BASEPATH + "/getAllTktByDuration/{date1}/{date2}" }) 
		  public  ApiResponse getAllTicketDetailsByDuration(Pageable pageable,@PathVariable String date1, @PathVariable String date2) {
				  logger.info("inside Report controller :: getAllTicket By date function"); 
				  
				  return ticketService.getAllTicketsByDuration(pageable, date1, date2);
		  }
		  
		  @GetMapping(value =  AuthConstants.ADMIN_BASEPATH + "/getTicketTotalCount")
				  public  ApiResponse getTicketTotalCount(Pageable pageable) {
						  logger.info("inside Report controller :: getAllTicket By total count"); 
						  return ticketService.getTicketStatusCountWithProject(pageable);
				  }
				  
		
		
}