package com.xyram.ticketingTool.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.stringtemplate.v4.compiler.CodeGenerator.list_return;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.response.ProjectAdminResponse;
import com.xyram.ticketingTool.response.ProjectMemberresponse;

import antlr.collections.List;

public interface ProjectMemberService {

	Page<ProjectMembers> getAllProjectMembers(Pageable pageable);

	ProjectMembers addprojectMember(ProjectMembers projectMembers);
	
	

//	ProjectMembers assignProjectToEmployee(Map<String, Integer> requestMap);
//
//	ProjectMembers unassignProjectToEmployee(Map<String, Integer> requestMap);
	

	ApiResponse unassignProjectToEmployee(ProjectMembers member);

	ApiResponse getAllProjectByEmployeeId(String employeeId);

	ApiResponse assignProjectToEmployee(Map<Object, Object> request);

	ApiResponse getAllProjectByEmployeeId();

	ProjectMembers getProjectMembersInProject(String employeeId, String projectId);

	IssueTrackerResponse makeProjectAdmin(String employeeId, String projectId);

	IssueTrackerResponse removeProjectAdmin(String employeeId, String projectId);

	ProjectAdminResponse isProjectAdmin(String employeeId, String projectId);

	IssueTrackerResponse getProjectMembersInProject(String projectId);

	ApiResponse projectMemberService(String projectid, String searchString);

	ApiResponse searchProjectMembersByProjectId(String projectid, String searchString);

	ProjectMemberresponse isProjectMember(String employeeId, String projectId);

	//ProjectMembers unassignProjectToEmployee(Map<String, String> requestMap);

}
