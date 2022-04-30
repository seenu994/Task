package com.xyram.ticketingTool.Repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {
	@Query("Select new map(e.Id as id,e.designationName as designationName) from Designation e where Id != 'D7'")
	Page<Map> getAllDesignationList(Pageable pageable);

	
	
	@Query("select distinct e from Designation e where"
			+ "( e.designationName LIKE %:searchString% OR e.Id like  %:searchString% ) ")
			
	Designation searchDesignation(String searchString);

	
//	@Query("Select distinct e from  Designation e where e.Id=:Id")
//	Designation getDesignationById(String id);

}
