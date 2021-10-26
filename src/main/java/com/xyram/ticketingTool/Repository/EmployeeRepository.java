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

import com.xyram.ticketingTool.entity.Employee;





@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	@Query("Select distinct new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt) from Employee e "
			+ "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")
	Page<Map> getAllEmployeeList(Pageable pageable);

	// Select e.`employee_id` as id, e.`frist_name` as firstName, e.`last_name` as
	// lastName
	// from employee e left JOIN project_members p On e.`employee_id` =
	// p.`employee_id` where e.`employee_status` = 'ACTIVE' and e.`role_id` = 'R3'
	// and p.`project_id` = '2c9fab1f7c3eebc6017c4073c8770010'

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) "
			+ "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where p.status = 'ACTIVE' and e.status = 'ACTIVE' and e.roleId = 'R3' and p.projectId = :projectId")
	List<Map> getAllEmpByProject(@Param("projectId") String projectId);

	/*
	 * SELECT e.* FROM employees_tbl e WHERE e.id NOT IN (SELECT employee_id FROM
	 * project_assignment_tbl WHERE project_id=1234)
	 */
	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
			+ "left join ProjectMembers p ON e.eId = p.employeeId and p.status = 'INACTIVE' and p.projectId = :projectId "
			+ " Where e.status = 'ACTIVE' and e.email like %:searchString% and e.roleId = 'R3' ")
//	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) "
//			+ "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where e.status = 'ACTIVE' and e.roleId = 'R3' "
//			+ "and (p.projectId != :projectId and not exists (Select 1 from ProjectMembers p1 where e.eId = p1.employeeId "
//			+ "and p1.projectId = :projectId)) and e.email like %:searchString%")
	List<Map> searchEmployeeNotAssignedToProject(@Param("projectId") String projectName,@Param("searchString") String searchString);

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
			+ "where e.status = 'ACTIVE' and e.email like %:searchString% and e.roleId = 'R2' ")
	List<Map> searchInfraUser(@Param("searchString") String searchString);

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
			+ "where e.status = 'ACTIVE' and e.email like %:searchString% and e.roleId = 'R2' and e.userCredientials.id != :userId")
	List<Map> searchInfraUsersForInfraUser(@Param("searchString") String searchString, @Param("userId") String userId);

//	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
//			+ "where e.status = 'ACTIVE' and e.email like %:searchString% ")
	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e "
			+ "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id where e.email like %:searchString%")
	List<Map> searchEmployee(@Param("searchString") String searchString);

	@Query(value = "SELECT e.employee_id, e.frist_name, e.last_name, count(e.employee_id) assigned_cnt FROM ticketdbtool.employee e "
			+ "left join ticketdbtool.ticket_assignee a on e.employee_id = a.employee_id "
			+ /*
				 * \"where e.employee_status = 'ACTIVE' and a.ticket_assignee_status = 'ACTIVE'
				 * and
				 */"where e.role_id = 'R2' group by e.employee_id", nativeQuery = true)
	List<Map> getAllInfraUserList();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R2'", nativeQuery = true)
	List<Employee> getAllInfraList();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R3'", nativeQuery = true)

	List<Map> getAllInfraAdmins();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R1'", nativeQuery = true)

	List<Map> getListOfDeveloper();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id in('R2','R3')", nativeQuery = true)
	List<Map> getListOfDeveloperInfra();

	@Query("SELECT e from Employee e where e.userCredientials.id = :userId")
	Employee getbyUserId(String userId);

	@Query("SELECT e from Employee e where e.eId = :employeeId")
	Employee getbyUserEmpId(String employeeId);

	@Query(value = "SELECT concat(e.frist_name,' ', e.last_name) as assigneeName FROM employee e WHERE e.user_id = :userId", nativeQuery = true)
	String getEmpName(String  userId);
	//String userid;

	@Query("SELECT DISTINCT b FROM Employee b WHERE b.userCredientials.id = :userId")
	Employee getbyUserByUserId(String userId);
     
	

	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e  "
			+ "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id where e.userCredientials.id = :userId")
	Map getAllEmployeeUserList(String userId);

	


}
