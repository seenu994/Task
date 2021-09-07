
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */

@Service

@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	CurrentUser userDetail;

	@Override
	public ApiResponse addproject(Projects project) {
		ApiResponse response = validateClientId(project);
		if (response.isSuccess()) { 
			//projectRepository.save(project);
			project.setCreatedAt(new Date());
			project.setCreatedBy(userDetail.getUserId());
			project.setLastUpdatedAt(new Date());
			project.setUpdatedBy(userDetail.getUserId());
			//System.out.println("project.getCreatedBy - " + project.getCreatedBy());
			//System.out.println("userDetail.getUserId() - " + userDetail.getUserId());
			Projects projetAdded = projectRepository.save(project);
			
			response.setSuccess(true);
			response.setMessage(ResponseMessages.PROJECT_ADDED);
			Map<String, String> content = new HashMap<String, String>();
			content.put("projectId", projetAdded.getpId());
			response.setContent(content);
			return response;
		}
		return response;
	}

	private ApiResponse validateClientId(Projects projects) {
		ApiResponse response = new ApiResponse(false);
		if (projects.getClientId()!= null) {
			response.setMessage("success");
			response.setSuccess(true);
			response.setContent(null);
		} else {
			response.setMessage(ResponseMessages.ClIENT_ID_VALID);
			response.setSuccess(false);
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse getAllProjects(Pageable pageable) {
		Page<Map> projectList =   projectRepository.getAllProjectLsit(pageable);
		Map content = new HashMap();
	    content.put("projectList", projectList);
	    ApiResponse response = new ApiResponse(true);
	    response.setSuccess(true);
	    response.setContent(content);
	    return  response;
	}
	
	
	@Override
	public ApiResponse editEmployee( Projects projectRequest) {

		ApiResponse response = validateClientIdProjectId(projectRequest);
		if (response.isSuccess()) {
		
			if (projectRequest != null) { 
				projectRequest.setpId(projectRequest.getpId());
				projectRequest.setUpdatedBy(projectRequest.getUpdatedBy());
				projectRequest.setLastUpdatedAt(new Date());
				projectRequest.setInHouse(projectRequest.getInHouse());
				projectRequest.setProjectDescritpion(projectRequest.getProjectDescritpion());
				projectRequest.setProjectName(projectRequest.getProjectName());
				projectRequest.setStatus(projectRequest.getStatus()); 
				//projectRepository.save(projectRequest);			
				Projects projetAdded = projectRepository.save(projectRequest);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.PROJECT_EDIT);
				Map content = new HashMap();
				content.put("projectId", projetAdded.getpId());
				response.setContent(content);
			}
			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.PROJECT_ID_VALID);
				response.setContent(null);
			}
		}
		return response;
	}
	private ApiResponse validateClientIdProjectId(Projects projects) {
		ApiResponse response = new ApiResponse(false);
		if (projects.getClientId()== null ) {
			/*response.setMessage("success");
			response.setSuccess(true);
			response.setContent(null);*/
		
			response.setMessage(ResponseMessages.ClIENT_ID_VALID );
			response.setSuccess(false);
			response.setContent(null);
		}
	
		Projects projectid=projectRepository.getById(projects.getpId());
		 if ( projectid==null ) {
			/*
			 * response.setMessage("success"); response.setSuccess(true);
			 * response.setContent(null); } else {
			 */
			response.setMessage(ResponseMessages.PROJECT_ID_VALID );
			response.setSuccess(false);
			response.setContent(null);
		} 
		else {
			response.setMessage(ResponseMessages.PROJECT_EDIT);
		
			
			response.setSuccess(true);
		}
	
		
		return response;
	}
		
}