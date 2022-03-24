package com.xyram.ticketingTool.Repository;

import java.util.List;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Projects;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, String> {

	@Query("Select distinct new map(e.pId as id,e.projectName as projectName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,e.inHouse as inHouse,"
			+ "e.status as status) from Projects e where e.status != 'INACTIVE'")
	Page<Map> getAllProjectLsit(Pageable pageable);

	@Query("Select distinct p from Projects p where p.pId=:id and p.status != 'INACTIVE'")
	Projects getProjecById(String id);
	
	
	

	@Query("Select distinct new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, c.clientName as clientname, "
			+ "p.inHouse as inHouse, p.status as status,p.createdAt as createdAt) from Projects  p "
			+ "Inner join ProjectMembers pm on p.pId =pm.projectId  and pm.status='ACTIVE' "
			+ " left join Client c ON p.clientId = c.Id and p.status != 'INACTIVE'" + "where pm.employeeId=:scopeId and p.status!='INACTIVE'  "
			+ "ORDER BY p.createdAt DESC")
	Page<Map> getAllProjectsList(String scopeId, Pageable pageable);
	

	@Query("Select distinct new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ "p.inHouse as inHouse, p.status as status,p.createdAt as createdAt) from Projects p join  ProjectMembers m On p.pId=m.projectId and m.status = 'ACTIVE' ORDER BY p.createdAt DESC ")
	Page<Map> getAllProjectByEmployee(Pageable pageable);

	@Query("Select distinct new map(p.pId as id,p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ " p.inHouse as inHouse, p.status as status) from Projects "
			+ " p left join  ProjectMembers e On p.pId=e.projectId where e.status = 'ACTIVE' and e.employeeId = :scopeId ")
	Page<Map> getAllProjectByDeveloper(Pageable pageable, String scopeId);

	@Query("SELECT p.projectName FROM Projects p WHERE p.pId=:pId")
	String getProjectNameByProjectId(Object pId);

	@Query("SELECT p.pId from Projects p  WHERE p.projectName=:projectName")
	String getProjectId(String projectName);

	@Query("Select distinct new map(p.pId as id,p.projectName as PName,p.projectDescritpion as projectDescritpion,p.clientId as clientId,c.clientName as clientname,p.inHouse as inHouse,"
			+ "p.status as status) from Projects p"
			+ " left join Client c ON p.clientId = c.Id"
			+ " left join ProjectMembers pm on p.pId =pm.projectId where "
			+ " (:userRole is null Or( "
			+ " (:userRole  IN ('TICKETINGTOOL_ADMIN','INFRA_ADMIN') )  "
			+ " OR (pm.employeeId=:scopeId and p.status!='INACTIVE' and pm.status='ACTIVE'))) And"
			+ "(:searchString is null " + " OR lower(p.projectName) LIKE %:searchString%)")
	

	List<Map> searchProject(@Param("searchString") String searchString ,String userRole ,String scopeId);

	@Query("SELECT new map(p as others) from Projects p  WHERE p.allotToAll= 1")
	List<Map> getgenericIssues();

	@Query("Select distinct new map(e.pId as id,e.projectName as PName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,c.clientName as clientname,e.inHouse as inHouse,"
			+ "e.status as status) from Projects e join Client c ON e.clientId = c.Id where e.pId = :id")
	List<Map> getByProjectClietName(String id);

	@Query("Select distinct new map(e.pId as id,e.projectName as PName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,e.inHouse as inHouse,"
			+ "e.status as status) from Projects e where e.status != 'INACTIVE'")
	List<Map> getAllProject();

	@Query("Select distinct new map(e.pId as id,e.projectName as PName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,e.inHouse as inHouse,"
			+ "e.status as status, e.createdAt as createdDate) from Projects e ORDER BY e.createdAt DESC")
	List<Map> getAllProjectforAdmin();

	@Query("Select distinct new map(p.pId as id,p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ " p.inHouse as inHouse, p.status as status , p.createdAt as createdDate) from Projects "
			+ " p left join  ProjectMembers e On  p.pId=e.projectId where p.status!='INACTIVE' and e.status = 'ACTIVE' and e.employeeId = :employeeId "
			+ " ORDER BY p.createdAt DESC")
	List<Map> getAllProjectByDeveloperList(String employeeId);

	@Query("Select distinct new map(p.pId as projectId,p.projectName as PName, p.createdAt as createdDate) from Projects p where p.allotToAll = 1 ORDER BY p.createdAt DESC")
	List<Map> getAllAllottedProjects();

	@Query("Select distinct new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, c.clientName as clientname, p.createdAt as createdDate, "
			+ "p.inHouse as inHouse, p.status as status,p.createdAt as createdAt) from Projects p join Client c ON p.clientId = c.Id ORDER BY p.createdAt DESC ")
	Page<Map> getAllForAdmins(Pageable pageable);
	
	@Query("Select distinct new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, c.clientName as clientname, p.createdAt as createdDate, "
			+ "p.inHouse as inHouse, p.status as status,p.createdAt as createdAt) from Projects p join Client c ON p.clientId = c.Id where p.projectName like %:searchString% ORDER BY p.projectName,p.createdAt DESC ")
	List<Map> getAllProjectsForTickets(String searchString);

}
