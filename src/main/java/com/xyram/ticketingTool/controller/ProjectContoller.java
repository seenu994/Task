package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * a
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

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createProject",AuthConstants.INFRA_ADMIN_BASEPATH + "/createProject" })
	public ApiResponse addproject(@RequestBody Projects project) {
		logger.info("Received request to add project");
		return projectService.addproject(project);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllProjects",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllProjects",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllProjects", AuthConstants.DEVELOPER_BASEPATH + "/getAllProjects" })
	public ApiResponse getAllProjects(Pageable pageable) {
		logger.info("indide ProjectContoller :: getAllProjects");
		return projectService.getAllProjects(pageable);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editProejct",
			AuthConstants.INFRA_USER_BASEPATH + "/editProejct",AuthConstants.INFRA_ADMIN_BASEPATH + "/editProejct"  })
	public ApiResponse editProject(@RequestBody Projects projectRequest) {
		logger.info("indide ProjectContoller :: editProejct");
		return projectService.editEmployee(projectRequest);
	}

	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getProjectDetails/{projectId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getProjectDetails/{projectId}"})
	public Optional<Projects> getProjectDetailsById(@PathVariable String projectId) {
		return projectService.findById(projectId);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchProject/{searchString}",
			AuthConstants.INFRA_USER_BASEPATH + "/searchProject/{searchString}",
			AuthConstants.DEVELOPER_BASEPATH + "/searchProject/{searchString}",AuthConstants.INFRA_ADMIN_BASEPATH + "/searchProject/{searchString}" })
	public ApiResponse searchProject(@PathVariable String searchString) {
		logger.info("inside ProjectContoller :: searchProject ");
		return projectService.searchProject(searchString);
	}

}