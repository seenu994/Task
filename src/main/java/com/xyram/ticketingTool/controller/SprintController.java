package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.service.SprintService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class SprintController {

	
	@Autowired
	SprintService sprintService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/CreateSprint"})
	public Sprint createSprint(@RequestBody Sprint sprint) {
		
		return sprintService.createSprint(sprint);
	} 
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editSprint/{sId}"})
	public Sprint editSprint(@RequestBody Sprint sprint,@PathVariable String sId) {
		
		return sprintService.editSprint(sprint,sId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getSprintprojectId/{Id}"})
	public  List<Map> getSprintByProject(@PathVariable String Id) {
		
		return sprintService.getSprintByProject(Id);
	}
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getlatestSprintByproject/{Id}"})
	public Sprint getLatestSprint(@PathVariable String Id) {
		return  sprintService.getLatestSprintByProject(Id);
	}
	
}
