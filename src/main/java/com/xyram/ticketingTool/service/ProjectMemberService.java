package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.ProjectMembers;

public interface ProjectMemberService {

	Page<ProjectMembers> getAllProjectMembers(Pageable pageable);

	ProjectMembers addprojectMember(ProjectMembers projectMembers);

	ProjectMembers assignProjectToEmployee(Map<String, Integer> requestMap);

	ProjectMembers unassignProjectToEmployee(Map<String, Integer> requestMap);

}
