package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {
	@Query("Select new map(e.Id as id,e.designationName as designationName,"
			+"e.designationStatus as designationStatus ) from Designation e where e.designationStatus !=  'INACTIVE'")
	Page<Map> getAllDesignationList(Pageable pageable);

	
	
//	@Query("select new map(p.softwareId as softwareId,p.softwareName as softwareName," 
//			+ "p.softwareMasterStatus as softwareMasterStatus) from SoftwareMaster p where p.softwareMasterStatus != 'INACTIVE'")
//			Page<Map> getAllsoftwareMasterList(Pageable peageble);
//	
	
	@Query("select distinct e from Designation e where"

			+ "( e.designationName LIKE %:searchString%) ")

			
	List<Map> searchDesignation(String searchString);


	@Query("select e from Designation e where e.designationName =:designationName")
	Designation getDesignationName(String designationName);

	
//	@Query("Select distinct e from  Designation e where e.Id=:Id")
//	Designation getDesignationById(String id);

}
