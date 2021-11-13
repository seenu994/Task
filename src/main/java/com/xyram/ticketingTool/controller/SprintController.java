package com.xyram.ticketingTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.service.SprintService;
import com.xyram.ticketingTool.util.AuthConstants;

public class SprintController {

	
	@Autowired
	SprintService sprintService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/CreateSprint"})
	public Sprint createSprint(@RequestBody Sprint sprint) {
		
		return sprintService.createSprint(sprint);
	} 
}
