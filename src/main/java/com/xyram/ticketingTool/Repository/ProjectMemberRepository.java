package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.ProjectMembers;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, String> {
	@Query("Select new map(e.employeeId as employeeId) from ProjectMembers e")

	List<Map> getAllProjectByEmployeeId(String employeeId);
//	public List<ProjectMembers> findByProject_pIdAndEmployee_eId(Integer pId,Integer eId);

}
