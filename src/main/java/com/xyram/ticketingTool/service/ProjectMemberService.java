package com.xyram.ticketingTool.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.ProjectMembers;

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

	//ProjectMembers unassignProjectToEmployee(Map<String, String> requestMap);

}
