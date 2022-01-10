package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.xyram.ticketingTool.Repository.SprintRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.service.SprintService;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {

	@Autowired
	SprintRepository sprintRepository;

	@Autowired
	ProjectService projectService;
	@Override
	public Sprint createSprint(Sprint sprint) {
		sprint.setSprintStatus("ACTIVE");
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
	
	@Override
	public Sprint editSprint(Sprint sprint,String sId) {
		Sprint Id = sprintRepository.getById(sId);
		if(Id !=null) {
			System.out.println(!sprint.getSprintStartDate().before(new Date()));
			if(sprint.getSprintStartDate()!=null && sprint.getSprintEndDate()!=null && !sprint.getSprintStartDate().before(new Date())) {
				Id.setSprintStartDate(sprint.getSprintStartDate());
				Id.setSprintEndDate(sprint.getSprintEndDate());
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sprint Start date and end date is mandatory");
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is mandatory");
		}
		
		
		return sprintRepository.save(Id);
            
	}
	
	@Override
	public IssueTrackerResponse getSprintByProject(String projectId){
		IssueTrackerResponse response = new IssueTrackerResponse();
		if(sprintRepository.getById(projectId)!=null) {
			
			response.setContent(sprintRepository.getByprojectId(projectId));

			response.setStatus("success");
			return response;
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project Id is mandatory ");
		}

	}
	
	@Override
	public Sprint getLatestSprintByProject(String projectId){
		
		if(sprintRepository.getById(projectId)!=null) {
			return  sprintRepository.getLatestSprintByProject(projectId);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project Id is mandatory ");
		}

	}
	

	@Override
	public Sprint changeStatusBySprintId(String sprintId,String status ){
		
		if(sprintRepository.getById(sprintId)!=null) {
			Sprint newSprint = sprintRepository.getById(sprintId);
			newSprint.setSprintStatus(status);
			return sprintRepository.save(newSprint);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SprintId is mandatory ");
		}

	}
	
	
	
}
