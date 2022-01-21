package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.EmployeePositionType;

public interface EmployeePositionTypeRepository extends JpaRepository<EmployeePositionType, String> {

	
	@Query("select p from EmployeePositionType p  where"
			+ "(:searchString  is null or lower(p.positionType) LIKE %:searchString% )")
	List<EmployeePositionType> getAllEmployeePosition(String searchString);

}
