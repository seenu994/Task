package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	@Query("Select distinct new map(e.eId as id,e.email as email,e. profileUrl as profileUrl, e.userCredientials.id as userId , e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.location as location,c.locationName as locationName,e.position as position,e.wings as wings,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo  "
			+ " left JOIN  CompanyLocation c On e.location = c.Id left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")
	Page<Map> getAllEmployeeList(Pageable pageable);

	@Query("Select distinct new map(e.eId as id,e.email as email, e. profileUrl as profileUrl, e.userCredientials.id as userId , e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.location as location,e.position as position,e.wings as wings,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo " + " left join e.wings w "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id "
			+ " where (:role is null or r.id=:role) and " + "(:designation is null or d.Id=:designation) and "
			+ "(:position is null or lower(e.position) like %:position%) and " + "(:wing is null or w.id=:wing) and"
			+ "(:searchString is null" + " or lower(e.email) like %:searchString% "
			+ " or lower(e.firstName) like %:searchString% " + "or lower(e.middleName) like %:searchString% "
			+ "or lower(e.lastName) like %:searchString%) " + " ORDER BY e.createdAt DESC")
	Page<Map> getAllEmployeeListByFilter(Pageable pageable, String searchString, String role, String designation,
			String position, String wing);

	// Select e.`employee_id` as id, e.`frist_name` as firstName, e.`last_name` as
	// lastName
	// from employee e left JOIN project_members p On e.`employee_id` =
	// p.`employee_id` where e.`employee_status` = 'ACTIVE' and e.`role_id` = 'R3'
	// and p.`project_id` = '2c9fab1f7c3eebc6017c4073c8770010'

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName,e.status as status) "
			+ "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where p.status = 'ACTIVE' and e.status = 'ACTIVE' and p.projectId = :projectId")
	List<Map> getAllEmpByProject(@Param("projectId") String projectId);

	/*
	 * SELECT e.* FROM employees_tbl e WHERE e.id NOT IN (SELECT employee_id FROM
	 * project_assignment_tbl WHERE project_id=1234)
	 */
//	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
//			+ "inner join ProjectMembers p ON e.eId = p.employeeId and p.status = 'INACTIVE' and p.projectId = :projectId "
//			+ " Where e.status = 'ACTIVE' and e.email like %:searchString% and e.roleId = 'R4' ")
//	

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName,e.status as status) from Employee e Where"
			+ " e.status = 'ACTIVE' and e.email like %:searchString%  and  e.eId NOT IN("
			+ "select p.employeeId from ProjectMembers p where p.status = 'ACTIVE' and p.projectId = :projectId )")

	List<Map> searchEmployeeNotAssignedToProject(@Param("projectId") String projectId,
			@Param("searchString") String searchString);

//	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) "
//			+ "from Employee e left JOIN ProjectMembers p On e.eId = p.employeeId where e.status = 'ACTIVE' and e.roleId = 'R3' "
//			+ "and (p.projectId != :projectId and not exists (Select 1 from ProjectMembers p1 where e.eId = p1.employeeId "
//			+ "and p1.projectId = :projectId)) and e.email like %:searchString%")

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName,e.status as status) from Employee e "
			+ "where e.status = 'ACTIVE' and (e.email like %:searchString%  or e.firstName like %:searchString% or e.middleName like %:searchString% or e.lastName like %:searchString%) and e.roleId = 'R3' ")
	List<Map> searchInfraUser(@Param("searchString") String searchString);

	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName,e.status as status) from Employee e "
			+ "where e.status = 'ACTIVE' and e.roleId = 'R3' and e.userCredientials.id != :userId and (e.email like %:searchString%  or e.firstName like %:searchString% or e.middleName like %:searchString% or e.lastName like %:searchString%)")
	List<Map> searchInfraUsersForInfraUser(@Param("searchString") String searchString, @Param("userId") String userId);

//	@Query("Select distinct new map(e.eId as id, e.firstName as firstName, e.lastName as lastName) from Employee e "
//			+ "where e.status = 'ACTIVE' and e.email like %:searchString% ")
	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.location as location,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,c.locationName as locationName,d.designationName as designationName,e.location as location,e.position as position,e.wings as wings,e.profileUrl as profileUrl,e.userCredientials.id as userId,e.reportingTo as reportingTo) from Employee e "
			+ "left join e.wings w left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id left JOIN  CompanyLocation c On e.location = c.id where r.Id !='R1' and (e.email like %:searchString% or e.firstName like %:searchString% or e.middleName like %:searchString% or e.lastName like %:searchString% or d.designationName like %:searchString%  or e.id like %:searchString% "
			+ "  or e.mobileNumber like %:searchString%  or w.wingName like %:searchString% or e.location like %:searchString%  or e.position like %:searchString%)")
	List<Map> searchEmployee(@Param("searchString") String searchString);

	@Query(value = "SELECT e.employee_id, e.frist_name, e.last_name,e.employee_status as status, count(e.employee_id) assigned_cnt FROM ticketdbtool.employee e "
			+ "left join ticketdbtool.ticket_assignee a on e.employee_id = a.employee_id "
			+ /*
				 * \"where e.employee_status = 'ACTIVE' and a.ticket_assignee_status = 'ACTIVE'
				 * and
				 */"where e.role_id = 'R3' group by e.employee_id", nativeQuery = true)
	List<Map> getAllInfraUserList();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid,e.employee_status as status"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R3'", nativeQuery = true)
	List<Employee> getAllInfraList();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid,e.employee_status as status"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R2'", nativeQuery = true)

	List<Map> getAllInfraAdmins();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid,e.employee_status as status"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id = 'R4'", nativeQuery = true)

	List<Map> getListOfDeveloper();

	@Query(value = "SELECT e.employee_id as employeeId, e.frist_name as firstname, e.last_name as lastname, u.uid as uid,e.employee_status as status"
			+ " FROM employee e inner join user u on u.user_id=e.user_id  "

			+ "where e.employee_status = 'ACTIVE'  and e.role_id in('R2','R3')", nativeQuery = true)
	List<Map> getListOfDeveloperInfra();

	@Query("SELECT e from Employee e where e.userCredientials.id = :userId")
	Employee getbyUserId(String userId);

	@Query("SELECT e from Employee e where e.eId = :employeeId")
	Employee getbyUserEmpId(String employeeId);

	@Query(value = "SELECT concat(e.frist_name,' ', e.last_name) as assigneeName FROM employee e WHERE e.user_id = :userId", nativeQuery = true)
	String getEmpName(String userId);
	// String userid;

	@Query("SELECT DISTINCT b FROM Employee b WHERE b.userCredientials.id = :employeeId")
	Employee getbyUserByUserId(String employeeId);

	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e  "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id where e.userCredientials.id = :userId")
	Map getAllEmployeeUserList(String userId);

	@Query("Select e.email from Employee e where e.email = :email")
	String filterByEmail(String email);

	@Query("Select distinct new map(e.eId as id,e.email as email,e. profileUrl as profileUrl, e.userCredientials.id as userId , e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.password as password,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.createdBy as createdBy,e.location as location,c.locationName as locationName,e.position as position,e.wings as wings,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo  "
			+ " left JOIN  CompanyLocation c On e.location = c.Id left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id where e.id=:employeeId")
	List<Map> getbyEmpId(String employeeId);

	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e. profileUrl as profileUrl, e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId,e.location as location,e.position as position,e.wings as wings, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,c.locationName as locationName,d.designationName as designationName,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName,ee.profileUrl as ReporterURL,e.createdAt as createdAt,e.createdBy as createdBy) from Employee e left join Employee ee On ee.eId = e.reportingTo  "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  CompanyLocation c On e.location=c.id left JOIN  Designation d On e.designationId=d.Id where e.id=:id")
	Map getEmployeeBYId(String id);

	@Query("SELECT e from Employee e where DATE_TRUNC('month', e.createdAt) = DATE_TRUNC('month', CURRENT_DATE)")
	List<Map> getAllEmployeeCurrentMonth(Pageable pageable);

	@Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.profileUrl as profileUrl, e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId,e.location as location,e.position as position,e.wings as wings, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName,ee.profileUrl as ReporterURL) from Employee e "
			+ "left join Employee ee On ee.eId = e.reportingTo " + "left JOIN Role r On e.roleId = r.Id "
			+ "left JOIN Designation d On e.designationId=d.Id where e.userCredientials.id=:accessToken")
	Map getbyAccessToken(String accessToken);

	@Query("SELECT new map(CONCAT(e.firstName ,' ', e.lastName) as ReporterName,e.eId as Id,e.profileUrl as profileUrl,e.userCredientials.id as userId) from Employee e where e.reportingTo = :reportingId")
	List<Map> getReportingList(String reportingId);

	@Query("SELECT new map(CONCAT(e.firstName ,' ', e.lastName) as ReporterName,e.eId as Id,e.profileUrl as profileUrl,e.userCredientials.id as userId) "
			+ "from Employee e where e.reportingTo = :reportingId and (lower(e.firstName) like %:searchString% or lower(e.lastName) like %:searchString%)")
	List<Map> searchEmployeeByReportingId(String reportingId, String searchString);

	@Query("Select e from Employee e "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")
	List<Employee> employeeListForReporting();

	@Query("Select e from Employee e "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id WHERE r.roleName = 'INFRA_USER' "
			+ "and e.status ='ACTIVE' and " + "(:searchString is null" + " or lower(e.email) like %:searchString% "
			+ " or lower(e.firstName) like %:searchString% " + "or lower(e.middleName) like %:searchString% "
			+ "or lower(e.lastName) like %:searchString%) " + "ORDER BY e.createdAt DESC")
	List<Employee> getInfraEmployee(String searchString);

	@Query("Select distinct new map(e.eId as id,e.userCredientials.uid as uid,e.email as email,e. profileUrl as profileUrl , e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")
	List<Map> getAllEmployeeListNotify();

	@Query("Select new map(e.eId as id,e.email as email,e.userCredientials.uid as uid,e.firstName as firstName,e. profileUrl as profileUrl, e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName,ee.profileUrl as ReporterURL) from Employee e left join Employee ee On ee.eId = e.reportingTo  "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id  where e.eId=:reportingTo")

	List<Map> getEmployeeBYReportingToId(String reportingTo);

	/*
	 * @Query("Select new map(e.eId as id,e.email as email,e.userCredientials.uid as uid,e.firstName as firstName,e. profileUrl as profileUrl, e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
	 * +
	 * "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName,ee.profileUrl as ReporterURL) from Employee e left join Employee ee On ee.eId = e.reportingTo  "
	 * +
	 * "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id JOIN CurrentUser c On e.eId=c.userId  where c.userId=:userId"
	 * ) List<Map> getEmployeeBYReportingToIds(String userId);
	 */
	@Query(value = "select * from employee", nativeQuery = true)
	List<Employee> getAllEmployeeNotification();

	@Query("Select e from Employee e "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id WHERE r.roleName = 'HR_ADMIN' ORDER BY e.createdAt DESC")

	List<Employee> getEmployeeByRole();

	@Query("Select distinct new map(e.eId as id,e.email as email,e. profileUrl as profileUrl,e.userCredientials.uid as uid,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")

	List<Map> getAllEmployeeLists();

	@Query("select new map(e.eId as id,e.userCredientials.uid as uid,e.email as email) from Employee e")
	List<Map> getListOfALlEmployee();

	@Query("Select distinct new map(e.eId as id,e.email as email,e. profileUrl as profileUrl , e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
			+ "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName,e.profileUrl as profileUrl,e.createdAt as createdAt,e.reportingTo as reportingTo,CONCAT(ee.firstName ,' ', ee.lastName) as ReporterName) from Employee e "
			+ "left JOIN Employee ee ON ee.eId = e.reportingTo "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id ORDER BY e.createdAt DESC")

	List<Map> getAllEmployee();

	@Query("select e from Employee e left join User u On e.eId=u.id where e.eId= :employeeId ")
	Employee getByEmpId(String employeeId);

	@Query("select e from User e  where e.id= :userId ")
	Employee getEmployeeByUSerId(String userId);

	@Query("select e from User e  where e.id= :userId ")
	User getUserByUSerId(String userId);

	@Query("SELECT e from Employee e  left join JobApplication j On e.eId= j.referredEmployeeId where e.eId = :geteId")
	Employee getEmployeeNameByScoleID(String geteId);

	@Query("SELECT e from Employee e  where e.eId = :referredEmployeeId")
	Employee getEmployeeNameByScoleId(String referredEmployeeId);

	@Query("SELECT e from Employee e ")
	Employee getAllEmployy();

	@Query("SELECT e from Employee e  left join JobApplication j On e.eId=j.referredEmployeeId and  j.referredEmployeeId=:referredEmployeeId")
	List<Employee> getRefereEmployee(String referredEmployeeId);

	@Query("Select e from Employee e "
			+ "left JOIN Role r On e.roleId = r.Id left JOIN  Designation d On e.designationId=d.Id WHERE r.roleName in( 'HR_ADMIN','TICKETINGTOOL_ADMIN') ORDER BY e.createdAt DESC")
	List<Employee> getVendorNotification();

	@Query("Select e from Employee e  left join Ticket t On e.userCredientials.id=t.createdBy where t.createdBy=:createdBy")
	List<Employee> getCreatedBy(String createdBy);

	@Query("Select e from Employee e  left join TicketAssignee t On e.eId=t.employeeId where t.employeeId=:employeeId")
	Employee getAssigneeNotify(String employeeId);

	@Query("Select e from Employee e  left join TicketAssignee t On e.eId=t.employeeId where t.employeeId=:employeeId")
	Employee getAssigneeNotification(String employeeId);

	@Query("select e from Employee e left join User u On e.userCredientials.id=u.id where e.eId= :geteId ")
	Employee getByEmpIdss(String geteId);

	@Query("select e from Employee e left join User u On e.userCredientials.id=u.id where e.eId= :userId ")
	Employee getByEmpIdssss(String userId);

	@Query("select e from Employee e where e.firstName = :firstName")
	Employee getByEmpName(String firstName);

	@Query("select distinct (CONCAT(e.firstName ,' ', e.lastName)) from Asset a "
			+ "left join AssetEmployee b on b.assetId = a.assetId AND b.assetEmployeeStatus != 'INACTIVE' "
			+ "left join Employee e on e.eId = b.empId where a.assetId =:assetId")
	String getEmpNameById(String assetId);

	@Query("select e from Employee e where e.eId =:eId")
	Employee getByEmpIdE(String eId);

	/*
	 * @Query("Select new map(e.eId as id,e.email as email,e.firstName as firstName,e.lastName as lastName,e.middleName as middleName ,e.roleId as roleId ,e.designationId as designationId, "
	 * +
	 * "e.status as status,e.mobileNumber as mobileNumber,r.roleName as rolename,d.designationName as designationName) from Employee e  "
	 * +
	 * "JOIN Role r On e.roleId = r.Id JOIN  Designation d On e.designationId=d.Id where e.eId= :userId"
	 * )
	 */

	/*
	 * @Query("Select new map(c.eID as userId) from  Employee c JOIN CurrentUser e on c.eId=e.userId where c.userId=:userId"
	 * ) List<Map> getAllEmployeeUserUidList(String userId);
	 */

}
