package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;

public interface ProjectService {

	ApiResponse addproject(Projects project); 
		
	

	ApiResponse getAllProjects(Pageable pageable );



	ApiResponse editEmployee(Integer projectId, Projects projectRequest); 
		

}
