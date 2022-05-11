package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.CompanyLocation;

@Repository
@Transactional
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, String> {

	// EmployeeLocation createLocation(String location);

	@Modifying
	@Query("Delete from CompanyLocation l where l.id =:id")
	void deleteLocation(String id);

	@Query("Select l from CompanyLocation l where l.id =:id")
	CompanyLocation getCompanyLocation(String id);

	@Query("Select new map(e.id as id,e.locationName as location_name)from CompanyLocation e  where "
			+ "(:searchString is null Or lower(e.locationName) LIKE %:searchString% )order by e.locationName ASC")
	List<Map> getAllLocation(String searchString);

	//CompanyLocation getCompanyLocations(String location);

	
	@Query("select l from CompanyLocation l where l.locationName =:locationName")
	CompanyLocation getCompanyLocations(String locationName);
	
	
	
	//String getLocationName(String id);

}
