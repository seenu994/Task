package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.ProjectMembers;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, Integer> {
//	public List<ProjectMembers> findByProject_pIdAndEmployee_eId(Integer pId,Integer eId);

}
