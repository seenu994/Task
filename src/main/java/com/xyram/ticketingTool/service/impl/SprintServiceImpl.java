package com.xyram.ticketingTool.service.impl;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.SprintRepository;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.service.SprintService;

@Service
public class SprintServiceImpl implements SprintService {

	@Autowired
	SprintRepository sprintRepository;

	@Autowired
	ProjectService projectService;
	@Override
	public Sprint createSprint(Sprint sprint) {
		if (sprint.getProject() != null && sprint.getProject().getpId() != null) {
			Projects project = projectService.getprojectById(sprint.getProject().getpId());
			if (project != null) {
				sprint.setProject(project);
			} else {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project id is mandatory");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project id is mandatory");
		}
		
		return sprintRepository.save(sprint);
            
	}
}
