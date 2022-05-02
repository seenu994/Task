package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {
	@Query("Select new map(e.Id as id,e.designationName as designationName) from Designation e where Id != 'D7'")
	Page<Map> getAllDesignationList(Pageable pageable);

	
	
	@Query("select distinct new map(e.Id as id,e.designationName as designationName) from Designation e where "
			+ "lower(e.designationName) LIKE %:searchString% ")
			
	List<Map> searchDesignation(String searchString);


	@Query("select e from Designation e where e.designationName =:designationName")
	Designation getDesignationName(String designationName);

	
//	@Query("Select distinct e from  Designation e where e.Id=:Id")
//	Designation getDesignationById(String id);

}
