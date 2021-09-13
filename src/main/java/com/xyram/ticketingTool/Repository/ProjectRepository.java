package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Projects;

public interface  ProjectRepository extends  JpaRepository<Projects,String> {

	
	@Query("Select new map(e.pId as id,e.projectName as projectName,e.projectDescritpion as projectDescritpion,e.clientId as clientId,e.inHouse as inHouse,e.status as status) from Projects e")
	Page<Map> getAllProjectLsit(Pageable pageable);
	
	@Query("Select p from Projects p where p.pId=:id")
            Projects getProjecById(String id);
	
	
	
	
	@Query(value = "Select p.project_id as pId, p.project_name as projectName, p.project_description as projectDescritpion,\r\n"
			+ "p.client_id as clientId, c.client_name, p.inhouse as inHouse, p.projectstatus as status from project p join client c ON p.client_id = c.client_id", nativeQuery = true)
	List<Map> getAllProjectsList();
}
