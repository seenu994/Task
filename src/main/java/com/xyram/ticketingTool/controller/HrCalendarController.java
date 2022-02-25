package com.xyram.ticketingTool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.Repository.HrCalendarRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HrCalendar;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class HrCalendarController {

	private final Logger logger = LoggerFactory.getLogger(HrCalendarController.class);
	
	@Autowired
	HrCalendarRepository hrRepo;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/scheduleCall",
			AuthConstants.HR_ADMIN_BASEPATH + "/scheduleCall",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/scheduleCall",
			AuthConstants.INFRA_USER_BASEPATH + "/scheduleCall",
			AuthConstants.HR_BASEPATH + "/scheduleCall",
			AuthConstants.DEVELOPER_BASEPATH + "/scheduleCall",
			})
	public ApiResponse scheduleCall(Pageable pageable) {
		logger.info("Creating new Time sheets");
//		return timesheetService.createTimeSheets(timesheets);
		Page<Map> sheetList = hrRepo.getAllHrCalendarSchedules(pageable);
		ApiResponse response = new ApiResponse(false);
		
		Map content = new HashMap();
		content.put("sheetList", sheetList);
		response.setMessage("Success");
		response.setContent(content);
		response.setSuccess(true);
		
		return null;
	}
}
