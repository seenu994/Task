package com.xyram.ticketingTool.Repository;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	@Query("Select new map(e.eId as id,e.firstName as firstName,e.lastName as lastName,e.status as status,e.mobileNumber as mobileNumber) from Employee e")
	Page<Map> getAllEmployeeList(Pageable pageable);
	
	 

}
