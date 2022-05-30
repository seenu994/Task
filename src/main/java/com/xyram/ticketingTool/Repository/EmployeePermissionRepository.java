package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.EmployeePermission;

@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, String>{

	@Query("SELECT  distinct e from EmployeePermission e where e.userId = :userId")
	EmployeePermission getbyUserId(String userId);
}
