package com.xyram.ticketingTool.controller;


import java.util.ArrayList;
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

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.ticket.Model.AuthConstant;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@RestController
@CrossOrigin
//@RequestMapping("/api/projectmembers")
class ProjectMemberContoller {
	private final Logger logger = LoggerFactory.getLogger(ProjectMemberContoller.class);

	@Autowired
	ProjectMemberService projectMemberService;

	
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH +"/assignProjectToEmployee",AuthConstants.INFRA_ADMIN_BASEPATH +"/assignProjectToEmployee",AuthConstants.DEVELOPER_BASEPATH +"/assignProjectToEmployee"})
	public ApiResponse assignProjectToEmployee(@RequestBody Map<Object,Object> request) {
		logger.info("Received request to add project Members");
		return  projectMemberService.assignProjectToEmployee(request);
	}	

	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllProjectMembers",AuthConstants.INFRA_ADMIN_BASEPATH +"/getAllProjectMembers",AuthConstants.DEVELOPER_BASEPATH +"/getAllProjectMembers",AuthConstants.INFRA_USER_BASEPATH +"/getAllProjectMembers"})
	public Page<ProjectMembers> getAllProjectMembers(Pageable pageable) {
		logger.info("indide ProjectMembersController :: getAllProjectMembers");
		return projectMemberService.getAllProjectMembers(pageable);
	}
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH +"/unassignProjectToEmployee",AuthConstants.INFRA_ADMIN_BASEPATH +"/unassignProjectToEmployee",AuthConstants.DEVELOPER_BASEPATH +"/unassignProjectToEmployee"})
	public ApiResponse unassignProjectToEmployee(@RequestBody ProjectMembers member) {
		logger.info("Received request to add project Members");
		return  projectMemberService.unassignProjectToEmployee(member);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllProjectByEmployeeId/{EmployeeId}",AuthConstants.DEVELOPER_BASEPATH + "/getAllProjectByEmployeeId/{EmployeeId}",AuthConstants.INFRA_USER_BASEPATH + "/getAllProjectByEmployeeId/{EmployeeId}",AuthConstants.HR_ADMIN_BASEPATH + "/getAllProjectByEmployeeId/{EmployeeId}" })
	public ApiResponse getAllProjectByEmployeeId(@PathVariable String EmployeeId) {
		logger.info("inside ProjectMemberContoller :: getAllProjectByEmployeeId ");
		return projectMemberService.getAllProjectByEmployeeId(EmployeeId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/	",AuthConstants.DEVELOPER_BASEPATH + "/getAllProjectByEmployeeId",AuthConstants.INFRA_USER_BASEPATH + "/getAllProjectByEmployeeId" })
	public ApiResponse getAllProjectByEmployeeId() {
		logger.info("inside ProjectMemberContoller :: getAllProjectByEmployeeId ");
		return projectMemberService.getAllProjectByEmployeeId();
		
	}	
	
	
	@PutMapping(value= {AuthConstants.INFRA_ADMIN_BASEPATH +"/addProjectAdmin",AuthConstants.INFRA_USER_BASEPATH +"/addProjectAdmin"})
	public IssueTrackerResponse MakeProjectAdmin(@RequestParam String employeeId, @RequestParam String projectId) {
		logger.info("indide ProjectMembersController :: MakeProjectAdmin");
		return projectMemberService.makeProjectAdmin(employeeId, projectId);
	}
	
	@PutMapping(value= {AuthConstants.INFRA_ADMIN_BASEPATH +"/removeProjectAdmin",AuthConstants.INFRA_USER_BASEPATH +"/removeProjectAdmin"})
	public IssueTrackerResponse removeProjectAdmin(@RequestParam String employeeId, @RequestParam String projectId) {
		logger.info("indide ProjectMembersController :: removeProjectAdmin");
		return projectMemberService.removeProjectAdmin(employeeId, projectId);
	}

	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH +"/isProjectAdmin",AuthConstants.INFRA_USER_BASEPATH +"/isProjectAdmin"})
	public IssueTrackerResponse isProjectAdmin(@RequestParam String employeeId, @RequestParam String projectId) {
		logger.info("indide ProjectMembersController :: MakeProjectAdmin");
		return projectMemberService.isProjectAdmin(employeeId, projectId);
	}
	
	
	
	@PutMapping(value= {AuthConstants.INFRA_ADMIN_BASEPATH +"/getAllProjectMemberByProject/{projectId}"
			,AuthConstants.INFRA_USER_BASEPATH +"/getAllProjectMemberByProject/{projectId}",
			AuthConstants.DEVELOPER_BASEPATH +"/getAllProjectMemberByProject/{projectId}"})
	public IssueTrackerResponse getAllProjectMemberById(@PathVariable String projectId) {
		logger.info("indide ProjectMembersController :: MakeProjectAdmin");
		return projectMemberService.getProjectMembersInProject(projectId);
	}

	
}
	
