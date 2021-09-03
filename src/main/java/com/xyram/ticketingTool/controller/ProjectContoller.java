package com.xyram.ticketingTool.controller;


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

import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**a
 * 
 * @author sahana.neelappa
 *
 */
@RestController
@CrossOrigin
//@RequestMapping("/api/project")
class ProjectContoller {
	private final Logger logger = LoggerFactory.getLogger(ProjectContoller.class);

	@Autowired
	ProjectService projectService;

	
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH + "/createProject"})
	public Projects addproject(@RequestBody Projects project) {
		logger.info("Received request to add project");
		return projectService.addproject(project);
	}

	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllProjects",AuthConstants.DEVELOPER_BASEPATH +"/getAllProjects",AuthConstants.INFRA_USER_BASEPATH +"/getAllProjects"})
	public Page<Projects> getAllProjects(Pageable pageable) {
		logger.info("indide ProjectContoller :: getAllProjects");
		return projectService.getAllProjects(pageable);
	}
	@PutMapping(value= {AuthConstants.ADMIN_BASEPATH + "/editProejct/{projectId}",AuthConstants.INFRA_USER_BASEPATH+"/editProejct/{projectId}"})
	public Projects editProejct(@RequestBody Projects projectRequest,@PathVariable Integer projectId ) {
		logger.info("indide ProjectContoller :: editProejct");
		return projectService.editEmployee(projectId,projectRequest);
	}
	
}