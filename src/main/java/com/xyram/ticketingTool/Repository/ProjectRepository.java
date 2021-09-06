package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Projects;

public interface  ProjectRepository extends  JpaRepository<Projects,String> {

	
	@Query("Select new map(e.pId as id,e.projectName as projectName,e.projectDescritpion as projectDescritpion,e.client as client,e.inHouse as inHouse,e.status as status) from Projects e")
	Page<Map> getAllProjectLsit(Pageable pageable);
}
