
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
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
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

	@Override
	public ApiResponse addproject(Projects project) {
		ApiResponse response = validateProjects(project);
		if (response.isSuccess()) { //
			projectRepository.save(project);
			project.setCreatedAt(new Date());

			Projects projetAdded = projectRepository.save(project);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.PROJECT_ADDED);
			Map content = new HashMap();
			content.put("projectId", projetAdded.getpId());
			response.setContent(content);
			return response;

		}
		return response;
	}

	private ApiResponse validateProjects(Projects projects) {
		ApiResponse response = new ApiResponse(false);
		if (projects.getClient().getId() != null) {
			response.setMessage("success");
			response.setSuccess(true);
			response.setContent(null);
		} else {

			response.setMessage(ResponseMessages.ClIENT_ID_VALID);

			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public ApiResponse getAllProjects(Pageable pageable) {

		Page<Map> projectList =   projectRepository.getAllProjectLsit(pageable);
	       ApiResponse response = new ApiResponse(true);
	       response.setSuccess(true);
	       response.setContent((Map)projectList);
	       return  response;
		}
	
	@Override
	public ApiResponse editEmployee(String projectId, Projects projectRequest) {

		ApiResponse response = validateProjects(projectRequest);
		if (response.isSuccess()) {
			  Projects projects = projectRepository.getById(projectId);
			if (projects != null) { 
			
		
				projects.setUpdatedBy(projectRequest.getUpdatedBy());
				projects.setLastUpdatedAt(new Date());
				projects.setInHouse(projectRequest.getInHouse());
				projects.setProjectDescritpion(projectRequest.getProjectDescritpion());
				projects.setProjectName(projectRequest.getProjectName());
				projects.setStatus(projectRequest.getStatus()); 
				projectRepository.save(projectRequest);
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

	
}