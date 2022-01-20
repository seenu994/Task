package com.xyram.ticketingTool.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.CompanyWings;


public interface CompanyWingsRepository extends JpaRepository<CompanyWings, String> {
	@Query(value = "SELECT j from CompanyWings j WHERE j.Id = :id ")
	
	CompanyWings getWingById(String id);
}