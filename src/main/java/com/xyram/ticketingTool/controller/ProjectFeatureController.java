package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.ProjectFeature;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.request.AssignFeatureRequest;
import com.xyram.ticketingTool.request.ProjectFeatureRequest;
import com.xyram.ticketingTool.service.ProjectFeatureService;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
public class ProjectFeatureController {

	
	@Autowired
	ProjectFeatureService projectFeatureService;

	
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH +"assignFeaturesToProject",AuthConstants.DEVELOPER_BASEPATH +"/assignFeaturesToProject"})
	public List<ProjectFeature> assignProjectToEmployee(@RequestBody AssignFeatureRequest request) {
	//	logger.info("Received request to add project Members");
		return  projectFeatureService.assignFeatureToProject(request);
	}	

	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/getAllProjectFeatures/{projectId}"
			,AuthConstants.DEVELOPER_BASEPATH +"/getAllProjectFeatures/{projectId}"})
	public IssueTrackerResponse getAllProjectFeatures(@PathVariable String projectId) {
	//	logger.info("indide ProjectMembersController :: getAllProjectMembers");
		return projectFeatureService.getAllfeatureByProject(projectId);
	}
	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH +"/updateProjectFeature/{featureId}"
			,AuthConstants.DEVELOPER_BASEPATH +"/updateProjectFeature/{featureId}"})
	public ProjectFeature getAllProjectFeatures(@PathVariable String featureId ,@RequestBody ProjectFeature projectFeature) {
	//	logger.info("indide ProjectMembersController :: getAllProjectMembers");
		return projectFeatureService.updateProjectFeature(featureId, projectFeature);
	}


	
	
	@PostMapping(value= {AuthConstants.ADMIN_BASEPATH +"/addProjectFeature",AuthConstants.DEVELOPER_BASEPATH +"/addProjectFeature"})
	public ProjectFeature assignProjectToEmployee(@RequestBody ProjectFeatureRequest projectFeatureRequest) {
	//	logger.info("Received request to add project Members");
		return  projectFeatureService.addNewProjectFeature(projectFeatureRequest);
	}	
}
