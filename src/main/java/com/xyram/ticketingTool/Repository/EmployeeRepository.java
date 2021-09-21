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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.UserStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e "
			+ "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id")
	Page<Map> getAllEmployeeList(Pageable pageable);

	@Query("Select new map(e.eId as id, e.firstName as firstName, e.lastName as lastName, case when p.projectId = :projectId then 1 else 0 end as projectAssignedStatus) "
			+ "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where e.status = 'ACTIVE'")
	List<Map> getAllEmpByProject(@Param("projectId") String projectId);

	@Query(value = "SELECT e.employee_id, e.frist_name, e.last_name, count(e.employee_id) assigned_cnt FROM ticketdbtool.employee e "
			+ "left join ticketdbtool.ticket_assignee a on e.employee_id = a.employee_id "
			+ "where e.employee_status = 'ACTIVE' and a.ticket_assignee_status = 'ACTIVE' and e.role_id = 'R2' group by e.employee_id", nativeQuery = true)
	Page<Map> getAllInfraUserList(Pageable pageable);

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R2'", nativeQuery = true)
	List<Employee> getAllInfraList();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R3'", nativeQuery = true)
	List<Map> getListOfDeveloper();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id in('R2','R3')", nativeQuery = true)
	List<Map> getListOfDeveloperInfra();

}
