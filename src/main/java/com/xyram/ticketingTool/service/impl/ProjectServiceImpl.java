package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.ProjectService;

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
	public Projects addproject(Projects project) {
		return projectRepository.save(project);
	}

	@Override
	public Page<Projects> getAllProjects(Pageable pageable) {

		return projectRepository.findAll(pageable);

	}

	@Override
	public Projects editEmployee(Integer projectId, Projects projectRequest) {

		// logger.info("Received request to update healthDevice for healthDeviceId: " +
		// healthDeviceId.toString());
		return projectRepository.findById(projectId).map(projects -> {

			projects.setUpdatedBy(projectRequest.getUpdatedBy());
			projects.setLastUpdatedAt(new Date());
			projects.setInHouse(projectRequest.getInHouse());
			projects.setProjectDescritpion(projectRequest.getProjectDescritpion());
			projects.setProjectName(projectRequest.getProjectName());
			projects.setStatus(projectRequest.getStatus());
			return projectRepository.save(projects);
		}).orElseThrow(() -> new ResourceNotFoundException("project is not found with id:" + projectRequest.getpId()));

	}
}