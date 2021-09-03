package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;

public interface ProjectService {

	Projects addproject(Projects project); 
		
	

	Page<Projects> getAllProjects(Pageable pageable);



	Projects editEmployee(Integer projectId, Projects projectRequest); 
		

}
