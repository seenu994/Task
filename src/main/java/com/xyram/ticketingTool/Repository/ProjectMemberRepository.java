package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.ProjectMembers;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, String> {

	@Query("Select distinct new map(e.projectId as projectId,p.projectName as projectName) from ProjectMembers "
			+ "e left join  Projects p On e.projectId = p.pId where e.status = 'ACTIVE' and e.employeeId = :employeeId ")
	List<Map> getAllProjectByEmployeeId(String employeeId);
	
	@Query("Select distinct new map(p.pId as projectId,p.projectName as projectName) from Projects p where p.allotToAll = 1")
	List<Map> getAllAllottedProjects();
	
//	public List<ProjectMembers> findByProject_pIdAndEmployee_eId(Integer pId,Integer eId);

	@Query("Select new map(e.projectId as projectId,p.projectName as projectName) from ProjectMembers e left join  Projects p On e.projectId = p.pId ")
	List<Map> getAllProjectByEmployee();

	@Query("Select distinct p from ProjectMembers p where p.projectId = :projectId and p.employeeId = :employeeId and p.status = 'ACTIVE'")
	ProjectMembers findByEmployeeIdAndProjectId(String employeeId, String projectId);

	

@Query("select p from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
		+ " where p.projectId = :projectId and p.employeeId = :employeeId and  p.status='ACTIVE' ")
	ProjectMembers getMemberInProject(String employeeId,String projectId);




@Query("select p from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
		+ " where p.projectId = :projectId and p.employeeId = :employeeId and  p.status='ACTIVE' and p.isAdmin='1' ")
	ProjectMembers checkProjectAdmin(String employeeId,String projectId);


            @Query("update ProjectMembers set isAdmin=:isAdmin  where id=:projectMemberId ")
             Integer updateProjectAdmin(String projectMemberId, String isAdmin);
}
/*
 * //
 * ("Select new map(e.eId as id, e.firstName as firstName, e.lastName as lastName, case when p.projectId = :projectId then 1 else 0 end as projectAssignedStatus) "
 * "
 * "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where e.status = 'ACTIVE'"
 * )
 */