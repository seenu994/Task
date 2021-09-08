package com.xyram.ticketingTool.Repository;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.UserStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

	@Query("Select new map(e.eId as id,e.password as password,e.email as email,e.firstName as firstName,e.lastName as lastName,e.roleId as roleId ,e.designationId as designationId, e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e  JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id")
	Page<Map> getAllEmployeeList(Pageable pageable);
}
	
	
