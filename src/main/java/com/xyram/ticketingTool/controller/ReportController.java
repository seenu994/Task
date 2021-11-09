package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	  @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTicketReport/{date1}/{date2}", 
			  AuthConstants.INFRA_USER_BASEPATH + "/getTicketReport/{date1}/{date2}", 
			  AuthConstants.DEVELOPER_BASEPATH + "/getTicketReport/{date1}/{date2}" }) 
	  public Map getReports(Pageable pageable,@PathVariable String date1, @PathVariable String date2) {
	  logger.info(" inside Report controller :: get ticket details by duration"); 
	  return reportService.prepareReport(pageable,date1,date2); 
	  }
	  
	 
	  
	 
	  @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getSummaryReport", 
			  AuthConstants.INFRA_USER_BASEPATH + "/getSummaryReport", 
			  AuthConstants.DEVELOPER_BASEPATH + "/getSummaryReport" }) 
	  public Map getSummaryReports(Pageable pageable) {
	  logger.info(" inside Report controller :: get Summary Report"); 
	  return reportService.getSummaryReportData( pageable);
	  }
	  
	  
	  @GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getTicketDataReport",AuthConstants.INFRA_ADMIIN_BASEPATH + "/getTicketDataReport",
			  AuthConstants.INFRA_USER_BASEPATH +"/getTicketDataReport",
			  AuthConstants.DEVELOPER_BASEPATH + "/getTicketDataReport" }) 
			  public  Map getTicketDataByProjectNameAndStatus(@RequestParam Map<String, Object> filter,Pageable pageable) {
					  logger.info("inside Report controller :: getAllTicket Report By projectName and status function"); 
					  
					  return reportService.prepareReportOnProjectNameAndTksStatus(filter,pageable);
			  }
}
