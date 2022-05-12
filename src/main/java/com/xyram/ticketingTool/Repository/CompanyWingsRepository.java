package com.xyram.ticketingTool.Repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.CompanyWings;


public interface CompanyWingsRepository extends JpaRepository<CompanyWings, String> {
	@Query("SELECT j from CompanyWings j WHERE j.Id = :id ")
	CompanyWings getWingById(String id);

	@Query("Select new map(e.Id as wing_id,e.wingName as wing_name)from CompanyWings e  where "
			+ "(:searchString is null Or lower(e.wingName) LIKE %:searchString% )order by e.wingName ASC")
	List<Map> getAllWing( String searchString);
	
	@Query(value = "SELECT j from CompanyWings j WHERE j.Id = :wing_id ")
	CompanyWings getWingByIds(String wing_id);
	
	
	@Query("select j from CompanyWings j where j.Id =:wingName")
	CompanyWings getWingName(String string) ;
	}