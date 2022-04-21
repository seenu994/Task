package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.EmployeeLocation;

@Repository
@Transactional
public interface EmployeeLocationRepository extends JpaRepository<EmployeeLocation, String>{

	//EmployeeLocation createLocation(String location);

	
}
