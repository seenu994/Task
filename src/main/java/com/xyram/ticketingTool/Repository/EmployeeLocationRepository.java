package com.xyram.ticketingTool.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.EmployeeLocation;

@Repository
@Transactional
public interface EmployeeLocationRepository extends JpaRepository<EmployeeLocation, String> {

	// EmployeeLocation createLocation(String location);

	@Modifying
	@Query("Delete from EmployeeLocation l where l.id =:id")
	void deleteLocation(String id);

	@Query("Select l from EmployeeLocation l where l.id =:id")
	EmployeeLocation getEmployeeLocation(String id);

	//String getLocationName(String id);

}
