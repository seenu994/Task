package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.util.ResponseMessages;

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
	EmployeeRepository employeeRepository;

	@Autowired
	ProjectServiceImpl projectServiceImpl;
	
	@Autowired
	CurrentUser user;

	@Override
	public ProjectMembers addprojectMember(ProjectMembers projectMembers) {
		return projectMemberRepository.save(projectMembers);
	}

	@Override
	public Page<ProjectMembers> getAllProjectMembers(Pageable pageable) {
		return projectMemberRepository.findAll(pageable);
	}

	@Override
	public ApiResponse assignProjectToEmployee(Map<Object,Object> request) {
		// TODO Auto-generated method stub

		ApiResponse response = new ApiResponse(false);
		if(request!=null&&request.containsKey("projectId")) {
			Projects project = projectRepository.getProjecById((String) request.get("projectId"));
			if(project!=null) {
			if(request.containsKey("employeeId")){
				List<String> employeeIds=(List<String>) request.get("employeeId");
				
				for (String employeeId : employeeIds) {
					ProjectMembers projectMember=new ProjectMembers();
					projectMember.setCreatedAt(new Date());

					projectMember.setLastUpdatedAt(new Date()) ;
					projectMember.setUpdatedBy(user.getUserId());
					projectMember.setCreatedBy(user.getUserId());

					projectMember.setStatus(ProjectMembersStatus.ACTIVE);
					projectMember.setProjectId(project.getpId());
					projectMember.setEmployeeId(employeeId);
					projectMemberRepository.save(projectMember);
				}
				
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);
			}
			}else{
				response.setSuccess(false);
				response.setMessage(ResponseMessages.PROJECT_NOTEXIST);
				response.setContent(null);

				}}else {
			}
			response.setSuccess(true);
			response.setMessage(ResponseMessages.PROJECT_MEMBERS_ADDED);
			response.setContent(null);
			return response;
		}

		

	/*
	 * @Override public ProjectMembers assignProjectToEmployee(Map<String, Integer>
	 * requestMap) { ProjectMembers projectMembers = new ProjectMembers(); if
	 * (requestMap.containsKey("employeeId") && requestMap.containsKey("projectId"))
	 * { projectMembers.setEmployee(employeeRepository.getById(requestMap.get(
	 * "employeeId")));
	 * projectMembers.setProject(projectRepository.getById(requestMap.get(
	 * "projectId"))); projectMembers.setStatus(ProjectMembersStatus.ACTIVE); return
	 * projectMemberRepository.save(projectMembers); } else { if
	 * (requestMap.containsKey("employeId")) { throw new
	 * ResourceNotFoundException("provide the EmployeeId to assign project for employee"
	 * ); } else { throw new
	 * ResourceNotFoundException("provide the ProjectId to assign project to employee"
	 * ); } }
	 * 
	 * }
	 */

	@Override
	public ApiResponse unassignProjectToEmployee(ProjectMembers member) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Optional<Projects> project = projectRepository.findById(member.getProjectId());

		if (project != null) {
			member.setStatus(ProjectMembersStatus.INACTIVE);
			projectMemberRepository.save(member);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.PROJECT_MEMBER_REMOVED);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_ID_VALID);
			response.setContent(null);
		}
		return response;
	}

	/*
	 * private ApiResponse validateProjectId(Projects projects) { ApiResponse
	 * response = new ApiResponse(false); if (projects.getpId()= null) {
	 * response.setMessage("success"); response.setSuccess(true);
	 * response.setContent(null); } else {
	 * response.setMessage(ResponseMessages.ClIENT_ID_VALID);
	 * response.setSuccess(false); response.setContent(null); } return response; }
	 */
	@Override
	public ApiResponse getAllProjectByEmployeeId(String employeeId) {

		ApiResponse response = new ApiResponse(false);

	
		if (employeeId != null) {


			List<Map> projectList = projectMemberRepository.getAllProjectByEmployeeId(employeeId);
			if(projectList!=null&&projectList.size()>0) {
			Map content = new HashMap();
			content.put("ProjectList", projectList);
			response.setSuccess(true);
			response.setContent(content);
			response.setMessage(ResponseMessages.PROJECT_LIST);
			}else {
				response.setSuccess(true);
				response.setMessage(ResponseMessages.PROJECT_NOT_ASSIGNED);
			}
		} else {
			response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
			response.setSuccess(false);

		}
		return response;

	}
}

//	@Override
//	public ProjectMembers assignProjectToEmployee(Map<String, Integer> requestMap) {
//		ProjectMembers projectMembers = new ProjectMembers();
//		if (requestMap.containsKey("employeeId") && requestMap.containsKey("projectId")) {
//			projectMembers.setEmployee(employeeRepository.getById(requestMap.get("employeeId")));
//			projectMembers.setProject(projectRepository.getById(requestMap.get("projectId")));
//			projectMembers.setStatus(ProjectMembersStatus.ACTIVE);
//			return projectMemberRepository.save(projectMembers);
//		} else {
//			if (requestMap.containsKey("employeId")) {
//				throw new ResourceNotFoundException("provide the EmployeeId to assign project for employee");
//			} else {
//				throw new ResourceNotFoundException("provide the ProjectId to assign project to employee");
//			}
//		}
//
//	}
//
//	@Override
//	public ProjectMembers unassignProjectToEmployee(Map<String, Integer> requestMap) {
//		if (requestMap.containsKey("employeeId") && requestMap.containsKey("projectId")) {
//			List<ProjectMembers> projectMembers = projectMemberRepository
//					.findByProject_pIdAndEmployee_eId(requestMap.get("projectId"), requestMap.get("employeeId"));
//
//			if (projectMembers != null && projectMembers.size() > 0) {
//				projectMembers.get(0).setStatus(ProjectMembersStatus.INACTIVE);
//				return projectMemberRepository.save(projectMembers.get(0));
//			} else {
//				throw new ResourceNotFoundException("no mapping with respect to projectId and enployeeId");
//			}
//		} else {
//			if (requestMap.containsKey("employeId")) {
//
//				throw new ResourceNotFoundException("provide the EmployeeId to assign project for employee");
//			} else {
//				throw new ResourceNotFoundException("provide the ProjectId to assign project to employee");
//			}
//		}
//
//	}
