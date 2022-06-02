package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.ProjectStatus;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.util.AuthConstants;

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

	@PostMapping("/createProject")
	public ApiResponse addproject(@RequestBody Projects project) throws Exception{
		logger.info("Received request to add project");
		return projectService.addproject(project);
	}

	@GetMapping("/getAllProjects")
	public ApiResponse getAllProjects(Pageable pageable)  throws Exception {
		logger.info("inside ProjectContoller :: getAllProjects");
		return projectService.getAllProjects(pageable);
	}

	@GetMapping("/getAllProjectsForTickets/{serachString}")
	public ApiResponse getAllProjectsForTickets(@PathVariable String serachString)  throws Exception {
		logger.info("inside ProjectContoller :: getAllProjectsForTickets");
		return projectService.getAllProjectsForTickets(serachString);
	}

	@GetMapping("/genericIssues")
	public ApiResponse getgenericIssues() throws Exception{
		logger.info("inside ProjectContoller :: getAllProjects");
		return projectService.getgenericIssues();
	}

	@PutMapping("/editProejct")
	public ApiResponse editProject(@RequestBody Projects projectRequest) throws Exception {
		logger.info("inside ProjectContoller :: editProejct");
		return projectService.editProject(projectRequest);
	}

	@GetMapping("/getProjectDetails/{projectId}")
	public ApiResponse getProjectDetailsById(@PathVariable String projectId) throws Exception {
		return projectService.getProjectDetailsById(projectId);
	}

	@GetMapping("/searchProject/{searchString}")
	public ApiResponse searchProject(@PathVariable String searchString) throws Exception {
		logger.info("inside ProjectContoller :: searchProject ");
		return projectService.searchProject(searchString);
	}

	@GetMapping("/getAllProjectList")
	public ApiResponse getAllProjectList()  throws Exception{
		logger.info("inside ProjectContoller :: getAllProjects");
		return projectService.getAllProjectList();
	}

	@PutMapping("/updateProjectStatus/{ProjectId}/status/{projectStatus}")
	public ApiResponse updateProjectStatus(@PathVariable String ProjectId, @PathVariable ProjectStatus projectStatus)  throws Exception{
		logger.info("Received request to change proejct status to: " + projectStatus + "for projectId: " + ProjectId);
		return projectService.updateProjectStatus(ProjectId, projectStatus);
	}
}