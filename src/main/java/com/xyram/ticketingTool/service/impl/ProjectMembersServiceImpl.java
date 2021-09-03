package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.ProjectMemberService;

/**
 * 
 * @author sahana.neelappa
 *
 */

@Service
@Transactional
public class ProjectMembersServiceImpl implements ProjectMemberService {

	@Autowired
	ProjectMemberRepository projectMemberRepository;
	@Autowired
	ProjectRepository  projectRepository;
	@Autowired
	EmployeeRepository  employeeRepository;

	@Override
	public ProjectMembers addprojectMember(ProjectMembers projectMembers) {
		return projectMemberRepository.save(projectMembers);
	}

	@Override
	public Page<ProjectMembers> getAllProjectMembers(Pageable pageable) {

		return projectMemberRepository.findAll(pageable);

	}

	@Override
	public ProjectMembers assignProjectToEmployee(Map<String, Integer> requestMap) {
		ProjectMembers projectMembers = new ProjectMembers();
		if (requestMap.containsKey("employeeId") && requestMap.containsKey("projectId")) {
			projectMembers.setEmployee(employeeRepository.getById(requestMap.get("employeeId")));
			projectMembers.setProject(projectRepository.getById(requestMap.get("projectId")));
			projectMembers.setStatus(ProjectMembersStatus.ACTIVE);
			return projectMemberRepository.save(projectMembers);
		} else {
			if (requestMap.containsKey("employeId")) {
				throw new ResourceNotFoundException("provide the EmployeeId to assign project for employee");
			} else {
				throw new ResourceNotFoundException("provide the ProjectId to assign project to employee");
			}
		}

	}

	@Override
	public ProjectMembers unassignProjectToEmployee(Map<String, Integer> requestMap) {
		if (requestMap.containsKey("employeeId") && requestMap.containsKey("projectId")) {
			List<ProjectMembers> projectMembers = projectMemberRepository
					.findByProject_pIdAndEmployee_eId(requestMap.get("projectId"), requestMap.get("employeeId"));

			if (projectMembers != null && projectMembers.size() > 0) {
				projectMembers.get(0).setStatus(ProjectMembersStatus.INACTIVE);
				return projectMemberRepository.save(projectMembers.get(0));
			} else {
				throw new ResourceNotFoundException("no mapping with respect to projectId and enployeeId");
			}
		} else {
			if (requestMap.containsKey("employeId")) {

				throw new ResourceNotFoundException("provide the EmployeeId to assign project for employee");
			} else {
				throw new ResourceNotFoundException("provide the ProjectId to assign project to employee");
			}
		}

	}

}
