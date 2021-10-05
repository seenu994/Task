package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Projects;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, String> {

	@Query("Select new map(e.pId as id,e.projectName as projectName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,e.inHouse as inHouse,"
			+ "e.status as status) from Projects e where e.status != 'INACTIVE'")
	Page<Map> getAllProjectLsit(Pageable pageable);

	@Query("Select p from Projects p where p.pId=:id and p.status != 'INACTIVE'")
	Projects getProjecById(String id);

	@Query("Select new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, c.clientName as clientname, "
			+ "p.inHouse as inHouse, p.status as status) from Projects p join Client c ON p.clientId = c.Id and p.status != 'INACTIVE'")
	Page<Map> getAllProjectsList(Pageable pageable);

	@Query("Select new map(p.pId as id, p.projectName as PName, p.projectDescritpion as projectDescritpion, p.clientId as clientId, c.clientName as clientname, "
			+ "p.inHouse as inHouse, p.status as status) from Projects p join Client c ON p.clientId = c.Id   join  ProjectMembers m On p.pId=m.projectId")
	Page<Map> getAllProjectByEmployee(Pageable pageable);
	
	

}
