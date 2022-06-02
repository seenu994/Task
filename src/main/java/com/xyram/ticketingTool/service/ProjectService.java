package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.ProjectStatus;

public interface ProjectService {

	ApiResponse addproject(Projects project)  throws Exception;

	ApiResponse getAllProjects(Pageable pageable)  throws Exception;
	
	ApiResponse getAllProjectsForTickets(String serachString)  throws Exception;

	ApiResponse editProject(Projects projectRequest)  throws Exception;

	ApiResponse getAllProjectsByDeveloper(Pageable pageable)  throws Exception;
	

	IssueTrackerResponse findById(String id)  throws Exception;

	ApiResponse searchProject(String searchString)  throws Exception;

	Projects getprojectById(String projectId)  throws Exception;

	ApiResponse getgenericIssues()  throws Exception;

	ApiResponse getAllProjectList()  throws Exception;

	ApiResponse updateProjectStatus(String projectId, ProjectStatus projectStatus) throws Exception; 
	
	ApiResponse getProjectDetailsById(String projectId) throws Exception;


}
