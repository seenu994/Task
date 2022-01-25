package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.ProjectMembers;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, String> {

	@Query("Select distinct new map(e.projectId as projectId,p.projectName as projectName) from ProjectMembers "
			+ "e left join  Projects p On e.projectId = p.pId  where e.status = 'ACTIVE' and e.employeeId = :employeeId ")
	List<Map> getAllProjectByEmployeeId(String employeeId);

	@Query("Select distinct new map(p.pId as projectId,p.projectName as projectName) from Projects p where p.allotToAll = 1")
	List<Map> getAllAllottedProjects();

//	public List<ProjectMembers> findByProject_pIdAndEmployee_eId(Integer pId,Integer eId);

	@Query("Select new map(e.projectId as projectId,p.projectName as projectName) from ProjectMembers e left join  Projects p On e.projectId = p.pId ")
	List<Map> getAllProjectByEmployee();

	@Query("Select distinct p from ProjectMembers p where p.projectId = :projectId and p.employeeId = :employeeId and p.status = 'ACTIVE'")
	ProjectMembers findByEmployeeIdAndProjectId(String employeeId, String projectId);

	@Query("select p from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
			+ " where p.projectId = :projectId and p.employeeId = :employeeId ")
	ProjectMembers getMemberInProject(String employeeId, String projectId);

	@Query("select new map( p.id as id , e.eId as employeeId ,CONCAT(e.firstName ,' ', e.lastName) as employeeName,p.isAdmin as isAdmin) "
			+ "from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
			+ " where p.projectId = :projectId  and  p.status='ACTIVE'"
			+ "and (:userRole is null or ((lower(:userRole)!='developer') or (p.employeeId=:scopeId)))")
	List<Map> getAllMemberByProject(String projectId, String  userRole,String scopeId);
	
	@Query("select new map( p.id as id , e.eId as employeeId ,CONCAT(e.firstName ,' ', e.lastName) as employeeName,p.isAdmin as isAdmin) "
			+ "from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
			+ " where p.projectId = :projectId  and  p.status='ACTIVE'")
	List<Map> getMemberByProject(String projectId);

	@Query("select p from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
			+ " where p.projectId = :projectId and p.employeeId = :employeeId and  p.status='ACTIVE' and p.isAdmin='1' ")
	ProjectMembers checkProjectAdmin(String employeeId, String projectId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update project_members p set p.is_admin=:isAdmin  where p.project_member_id=:projectMemberId ", nativeQuery = true)
	Integer updateProjectAdmin(String projectMemberId, String isAdmin);

	@Query("select count(e)>0 from ProjectMembers e where e. employeeId=:employeeId")
	boolean findb(String employeeId);

	@Query("select p from ProjectMembers p where p.projectId= :projectId and p.employeeId= :employeeId")
	ProjectMembers getProjectMemberByEmployeeId(String projectId, String employeeId);

	@Query("Select distinct new map(p.pId as id,p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ " p.inHouse as inHouse, p.status as status) from ProjectMembers "
			+ " e left join  Projects p On e.projectId = p.pId where e.status = 'ACTIVE' and e.employeeId = :scopeId ")
	Page<Map> getAllProjectByDeveloper(Pageable pageable, String scopeId);

	@Query("select new map( p.id as id , e.eId as employeeId ,CONCAT(e.firstName ,' ', e.lastName) as employeeName,r.roleName as roleName,p.projectId as projectId,p.createdAt as createdAt,p.createdBy as createdBy,p.lastUpdatedAt as lastUpdatedAt,p.UpdatedBy as UpdatedBy,d.designationName as designationName,p.isAdmin as isAdmin) from ProjectMembers p left join Employee e on p.employeeId=e.eId left join Projects pr On p.id=pr.pId left join Role r On e.roleId=r.Id left join Designation d On e.designationId=d.Id where e.firstName like %:searchString% and p.projectId=:projectId")

	List<Map> searchProjectMembersByProjectId(@Param("projectId") String projectId,
			@Param("searchString") String searchString);

	@Query("select p from ProjectMembers p inner join Employee e on p.employeeId=e.eId "
			+ " where p.projectId = :projectId and p.employeeId = :employeeId and  p.status='ACTIVE' ")
	ProjectMembers checkIsProjectMember(String employeeId, String projectId);

}
/*
 * //
 * ("Select new map(e.eId as id, e.firstName as firstName, e.lastName as lastName, case when p.projectId = :projectId then 1 else 0 end as projectAssignedStatus) "
 * "
 * "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where e.status = 'ACTIVE'"
 * )
 */