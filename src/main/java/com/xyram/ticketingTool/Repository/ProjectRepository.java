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
			+ "p.inHouse as inHouse, p.status as status) from Projects p join Client c ON p.clientId = c.Id and p.status != 'INACTIVE' ORDER BY p.createdAt DESC")
	Page<Map> getAllProjectsList(Pageable pageable);

	@Query("Select distinct new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ "p.inHouse as inHouse, p.status as status) from Projects p join  ProjectMembers m On p.pId=m.projectId and m.status = 'ACTIVE' ORDER BY p.createdAt DESC ")
	Page<Map> getAllProjectByEmployee(Pageable pageable);
	

	@Query("Select distinct new map(p.pId as id,p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, "
			+ " p.inHouse as inHouse, p.status as status) from ProjectMembers "
			+ " e left join  Projects p On e.projectId = p.pId where e.status = 'ACTIVE' and e.employeeId = :employeeId ")
	Page<Map> getAllProjectByDeveloper(Pageable pageable, String employeeId);
	

	
	@Query("SELECT p.projectName FROM Projects p WHERE p.pId=:pId")
	String getProjectNameByProjectId(Object pId);
	
	@Query("SELECT p.pId from Projects p  WHERE p.projectName=:projectName")
	String getProjectId(String projectName);
	

	@Query("Select distinct new map(e.pId as id,e.projectName as PName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,c.clientName as clientname,e.inHouse as inHouse,"
			+ "e.status as status) from Projects e join Client c ON e.clientId = c.Id where e.projectName like %:searchString% and e.status != 'INACTIVE'")
	List<Map> searchProject(@Param("searchString") String searchString);




}
