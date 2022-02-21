package com.xyram.ticketingTool.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.service.TimesheetService;
import com.xyram.ticketingTool.util.AuthConstants;


@RestController
@CrossOrigin
public class TimesheetController {
	
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	TimesheetService timesheetService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/createTimeSheets",
			AuthConstants.HR_BASEPATH + "/createTimeSheets",
			})
	public ApiResponse createTimeSheets(@RequestBody List<TimeSheet> timesheets) {
		logger.info("Creating new Time sheets");
		return timesheetService.createTimeSheets(timesheets);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/editTimeSheets",
			AuthConstants.HR_BASEPATH + "/editTimeSheets",
			})
	public ApiResponse editTimeSheets(@RequestBody List<TimeSheet> timesheets) {
		logger.info("Editing Time sheets");
		return timesheetService.editTimeSheets(timesheets);
	} 
	
	public 	ApiResponse approveTimeSheets(@RequestBody List<String> timesheets) {
		logger.info("Approving Time sheets");
		return timesheetService.approveTimeSheets(timesheets);

	}


}
