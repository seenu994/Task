package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.ReportService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
public class ReportController {
	
	@Autowired
	TicketService ticketService;
	@Autowired
	ReportService reportService;
	private final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	
	  @GetMapping(value = AuthConstants.ADMIN_BASEPATH + "/getAllTickets11") public
	  Map getReports() {
	  logger.info(" inside Report controller :: get ticket details by Id"); return
	  reportService.prepareReport(); }
	 
	
	 
	
	
	

	
	 
	

}
