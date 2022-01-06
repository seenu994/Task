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
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
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
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/CreateSprint", AuthConstants.ADMIN_BASEPATH + "/CreateSprint", AuthConstants.INFRA_ADMIN_BASEPATH + "/CreateSprint", AuthConstants.INFRA_USER_BASEPATH + "/CreateSprint", AuthConstants.HR_ADMIN_BASEPATH + "/CreateSprint", AuthConstants.HR_BASEPATH + "/CreateSprint"})
	public Sprint createSprint(@RequestBody Sprint sprint) {
		
		return sprintService.createSprint(sprint);
	} 
	
	@PutMapping(value = {AuthConstants.INFRA_ADMIN_BASEPATH + "/editSprint/{sId}", AuthConstants.ADMIN_BASEPATH + "/editSprint/{sId}",AuthConstants.INFRA_USER_BASEPATH + "/editSprint/{sId}",AuthConstants.DEVELOPER_BASEPATH + "/editSprint/{sId}",AuthConstants.HR_ADMIN_BASEPATH + "/editSprint/{sId}",AuthConstants.HR_BASEPATH + "/editSprint/{sId}"})
	public Sprint editSprint(@RequestBody Sprint sprint,@PathVariable String sId) {
		
		return sprintService.editSprint(sprint,sId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getSprintprojectId/{Id}",AuthConstants.DEVELOPER_BASEPATH + "/getSprintprojectId/{Id}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getSprintprojectId/{Id}",AuthConstants.INFRA_USER_BASEPATH + "/getSprintprojectId/{Id}",AuthConstants.HR_ADMIN_BASEPATH + "/getSprintprojectId/{Id}",AuthConstants.HR_BASEPATH + "/getSprintprojectId/{Id}"})
	public  IssueTrackerResponse getSprintByProject(@PathVariable String Id) {
		
		return sprintService.getSprintByProject(Id);
	}
	
	@GetMapping(value = {AuthConstants.DEVELOPER_BASEPATH + "/getlatestSprintByproject/{Id}",AuthConstants.ADMIN_BASEPATH + "/getlatestSprintByproject/{Id}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getlatestSprintByproject/{Id}",AuthConstants.INFRA_USER_BASEPATH + "/getlatestSprintByproject/{Id}",AuthConstants.HR_ADMIN_BASEPATH + "/getlatestSprintByproject/{Id}",AuthConstants.HR_BASEPATH + "/getlatestSprintByproject/{Id}"})
	public Sprint getLatestSprint(@PathVariable String Id) {
		return  sprintService.getLatestSprintByProject(Id);
	}
	
	@PostMapping(value = {AuthConstants.DEVELOPER_BASEPATH + "/changeStatus/{Id}/{status}",AuthConstants.ADMIN_BASEPATH + "/changeStatus/{Id}/{status}",AuthConstants.INFRA_ADMIN_BASEPATH + "/changeStatus/{Id}/{status}",AuthConstants.INFRA_USER_BASEPATH + "/changeStatus/{Id}/{status}",AuthConstants.HR_ADMIN_BASEPATH + "/changeStatus/{Id}/{status}",AuthConstants.HR_BASEPATH + "/changeStatus/{Id}/{status}"})
	public Sprint changeStatusBySprintId(@PathVariable String Id,@PathVariable String status) {
		return  sprintService.changeStatusBySprintId(Id,status);
	}
	
}
