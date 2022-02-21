package com.xyram.ticketingTool.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;

import java.util.List;
import java.util.Map;

public interface TicketRepository extends JpaRepository<Ticket, String> {

	@Query(value = "SELECT * from ticket_info t where t.created_by =:createdBy AND t.ticket_status = :ticketStatus ", nativeQuery = true)
	List<Ticket> findAllByNameAndCreatedAndTicketStatus(@Param("createdBy") String createdBy,
			@Param("ticketStatus") String ticketStatus);

	@Query(value = "SELECT t.ticket_status from ticket_info t where t.ticket_id = :ticketId ", nativeQuery = true)
	String getTicketById(String ticketId);
	
	@Query(value = "SELECT * from ticket_info t where t.ticket_id = :ticketId ", nativeQuery = true)
	Ticket getTicketDetailById(String ticketId);

	@Query("Select new map(t.Id as id,t.ticketDescription as ticketDescription,t.resolution as resolution,p.pId as projectId,t.createdBy as createdBy,"
			+ "t.type as type, p.projectName as projectName, t.priorityId as priorityId,t.status as status) from Ticket t "
			+ "left join Projects p on t.projectId=p.pId ORDER BY t.createdAt DESC")
	Page<Map> getAllTicketList(Pageable pageable);
/*
 *  java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the 
 *  right syntax to use near 'concat(eu.frist_name,' ', eu.last_name) as updatedName, ro.roleName as updatedRo'
 */
//	@Query("SELECT a.ticket_id as ticket,a.updated_by as UpdatedBy,a.resolution as resolution, a.type as type, "
//			+ "p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, "
//			+ "a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
//			+ "a.created_by as created_by, a.priority_id as priority_id,p1.priority_name as priority_name, b.employee_id as assigneeId, "
//			+ "concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp, "
//			+ "concat(eu.frist_name,' ', eu.last_name) as updatedName, ro.role_name as updatedRole "
//			+ "FROM ticketdbtool.ticket_info a " + "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
//			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
//			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
//			+ "left join ticketdbtool.employee eu on a.updated_by = eu.user_id "
//			+ "left join ticketdbtool.role ro on eu.role_id = ro.role_id "
//			+ "left join ticketdbtool.project p ON a.project_id = p.project_id "
//			+ "left join ticketdbtool.priority p1 on a.priority_id = p1.priority_id " + "where"
//			+ "  (:priority is null OR (p1.priority_name) LIKE %:priority%) AND " + " (:searchString is null "
//			+ " OR lower(a.ticket_description) LIKE %:searchString% LIMIT :startIndex,:pageSize"
//			+ " OR   lower(a.ticket_id) LIKE %:searchString%) ")
//	List<Map> searchAllTicket(String searchString, String priority, int pageSize, int startIndex);
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy,a.resolution as resolution, p.pId as projectId,p.projectName as projectName, a.type as type, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id , b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp "
			+ " ,concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole) FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials "
			+ " left join Projects p ON a.projectId = p.pId "
			+ " left join Priority pt  on  a.priorityId =pt.id "
			+ " left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ " left join Role ro on ro.Id = eu.roleId "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ " left join Employee e on b.employeeId = e.eId where "
			+ "(:priority is null or lower(pt.designationName) = :priority) and "
			+ "(:searchQuery is null or lower(a.ticketDescription) LIKE %:searchQuery%"
			+ " or lower(a.Id) like %:searchQuery% ) ORDER BY a.createdAt DESC")
	Page<List<Map>> searchAllTicket(String searchQuery, String priority,  Pageable pageable);

	@Query(value = "SELECT a.ticket_id as ticket_id,a.resolution as resolution, a.type as type,p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id,p1.priority_name as priority_name, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticketdbtool.ticket_info a " + "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "left join ticketdbtool.project p ON a.project_id = p.project_id "
			+ "left join ticketdbtool.priority p1 on a.priority_id = p1.priority_id " + "where"
			+ "  (:priority is null OR (p1.priority_name) LIKE %:priority%) AND " + " (:searchString is null "
			+ " OR lower(a.ticket_description) LIKE %:searchString%  "
			+ " OR   lower(a.ticket_id) LIKE %:searchString%) AND a.created_by= :employeeId ", nativeQuery = true)
	List<Map> searchSelfTicket(String searchString, String priority, String employeeId);
	/*
	 * SELECT a.ticket_id as ticket_id,a.resolution as resolution, a.type as type,p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, 
			a.created_by as created_by, a.priority_id as priority_id,p1.priority_name as priority_name, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp
			FROM ticketdbtool.ticket_info a left join ticketdbtool.employee ee on a.created_by = ee.user_id
			left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE'
			left join ticketdbtool.employee e on b.employee_id = e.employee_id
			left join ticketdbtool.project p ON a.project_id = p.project_id
			left join ticketdbtool.priority p1 on a.priority_id = p1.priority_id 
			where p1.priority_id = 'P1'
            AND a.ticket_status='ASSIGNED' AND
			a.created_by= 'USR_NyjLKIy352' AND 
			b.employee_id= 'emp003' AND
			a.project_id= 'PRO_w5cCaFc108' AND
			Date(a.created_at) >= STR_TO_DATE('2022-02-17', '%Y-%m-%d') AND
			Date(a.created_at) >= STR_TO_DATE('2022-02-17', '%Y-%m-%d')
	 */
	@Query(value = "SELECT a.ticket_id as ticket_id,a.resolution as resolution, a.type as type,p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id,p1.priority_name as priority_name, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticketdbtool.ticket_info a " + "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "left join ticketdbtool.project p ON a.project_id = p.project_id "
			+ "left join ticketdbtool.priority p1 on a.priority_id = p1.priority_id " + 
			"where (:fromDate is null OR (Date(a.created_at) >= STR_TO_DATE(:fromDate, '%Y-%m-%d'))) AND "
			+ "(:priority is null OR (p1.priority_id = :priority)) AND "
			+ "(:status is null OR lower(a.ticket_status)=:status) AND "
			+ "(:createrId is null OR a.created_by= :createrId) AND "
			+ "(:assigneeId is null OR b.employee_id= :assigneeId) AND "
			+ "(:projectId is null OR a.project_id= :projectId) AND "
			+ "(:toDate is null OR (Date(a.created_at) <= STR_TO_DATE(:toDate, '%Y-%m-%d'))) ", nativeQuery = true)
	Page<List<Map>> searchAllTickets(String projectId, String fromDate, String toDate, String status,
			String createrId, String assigneeId, String priority, Pageable pageable);

	@Query(value = "SELECT a.ticket_id as ticket_id, a.type as type,a.resolution as resolution,p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id,p1.priority_name as priority_name, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticketdbtool.ticket_info a " + "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "left join ticketdbtool.project p ON a.project_id = p.project_id "
			+ "left join ticketdbtool.priority p1 on a.priority_id = p1.priority_id " + "where"
			+ "  (:priority is null OR (p1.priority_name) LIKE %:priority%) AND " + " (:searchString is null "
			+ " OR lower(a.ticket_description) LIKE %:searchString%  "
			+ " OR   lower(a.ticket_id) LIKE %:searchString%) AND b.employee_id= :employeeId", nativeQuery = true)
	List<Map> searchSelfAssignedTicket(String searchString, String priority, String employeeId);
	/*
	 * @Query(value =
	 * "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, "
	 * +
	 * "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
	 * +
	 * "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id "
	 * +
	 * "left join employee e on b.employee_id = e.employee_id where (('INFRA' = :roleId and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and b.employee_id = :createdBy) "
	 * +
	 * "OR ('DEVELOPER' = :roleId and a.ticket_status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.created_by = :createdBy) "
	 * +
	 * "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status NOT IN ('COMPLETED', 'CANCELLED')))"
	 * , nativeQuery = true) Page<Map> getAllTicketsByStatus(Pageable
	 * pageable, @Param("createdBy") String createdBy, @Param("roleId")String
	 * roleId);
	 */

	@Query(value = "SELECT a.ticket_id as ticket_id, a.type as type,a.resolution as resolution,p.project_id as projectId, p.project_name as projectName, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticket_info a " + "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
					+ "left join ticketdbtool.project p ON a.project_id = p.project_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "where ('INFRA_USER' = 'INFRA_USER' and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and e.user_id = :createdBy) ORDER BY a.created_at DESC", nativeQuery = true)
	Page<Map> getAllTicketsForInfraUser(Pageable pageable, @Param("createdBy") String createdBy);

	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy, p.pId as projectId,a.resolution as resolution,p.projectName as projectName, a.type as type, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId,concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials "
			+ "left join Projects p ON a.projectId = p.pId "
			+ "left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ "left join Role ro on eu.roleId = ro.Id "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ " left join Employee e on b.employeeId = e.eId " + "where "
			+ "(:status is null or a.status = :status) and"
			+ "(:priority is null or a.priorityId = :priority) and "
			+"(:isUser is false or a.status Not IN ('COMPLETED','CANCELLED' ) ) and "
			+ " (('INFRA_USER' = :roleId and ((e.userCredientials.id = :createdBy)  "
			+ " OR (a.createdBy = :createdBy )) ) " + "OR ('DEVELOPER' = :roleId   and a.createdBy = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId )" + "OR ('HR' = :roleId  and a.createdBy = :createdBy) "
			+ "OR ('HR_ADMIN' = :roleId and a.createdBy = :createdBy)"
			+ " OR ('INFRA_ADMIN' = :roleId)) ORDER BY a.createdAt DESC")
	Page<Map> getAllTicketsByStatus(Pageable pageable, @Param("createdBy") String createdBy,
			@Param("roleId") String roleId, TicketStatus status,boolean isUser, String priority);
	
	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy, p.pId as projectId,a.resolution as resolution,p.projectName as projectName, a.type as type, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId,concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials "
			+ "left join Projects p ON a.projectId = p.pId "
			+ "left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ "left join Role ro on eu.roleId = ro.Id "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ " left join Employee e on b.employeeId = e.eId " + "where "
			+ "(:priority is null or a.priorityId = :priority) and "
			+"(:status is null or a.status IN ('COMPLETED','CANCELLED' ) ) and "
			+ " (('INFRA_USER' = :roleId and ((e.userCredientials.id = :createdBy)  "
			+ " OR (a.createdBy = :createdBy )) ) " + "OR ('DEVELOPER' = :roleId   and a.createdBy = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId )" + "OR ('HR' = :roleId  and a.createdBy = :createdBy) "
			+ "OR ('HR_ADMIN' = :roleId and a.createdBy = :createdBy)"
			+ " OR ('INFRA_ADMIN' = :roleId)) ORDER BY a.createdAt DESC")
	Page<Map> getAllTicketsByCompleted(Pageable pageable, @Param("createdBy") String createdBy,
			@Param("roleId") String roleId, TicketStatus status, String priority);
	
	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy,a.resolution as resolution, p.pId as projectId,p.projectName as projectName, a.type as type, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp "
			+ " ,concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole) FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials "
			+ "left join Projects p ON a.projectId = p.pId "
			+ "left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ "left join Role ro on ro.Id = eu.roleId "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId where "
			+ "(:priority is null or a.priorityId = :priority) and "
			+ "(a.status Not IN ('COMPLETED','CANCELLED')) and "
			+ " a.createdBy = :createdBy ORDER BY a.createdAt DESC")
	Page<Map> getAllActiveTickets(Pageable pageable, @Param("createdBy") String createdBy, String priority);

	@Query("SELECT distinct new map(uu.userRole as updatedRole,a.resolution as resolution, a.Id as ticket_id, a.type as type , p.pId as projectId,p.projectName as projectName, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by,a.UpdatedBy as UpdatedBy, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join User uu on a.UpdatedBy = uu.id left join Employee ee on a.createdBy = ee.userCredientials left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ " left join Projects p ON a.projectId = p.pId "
			+ "left join Employee e on b.employeeId = e.eId where "
			+ " (('INFRA_USER' = :roleId and a.status IN ('COMPLETED', 'CANCELLED') and (a.createdBy = :createdBy or b.employeeId = (select eId from Employee where userCredientials.id = :createdBy) )) "
			+ "OR ('DEVELOPER' = :roleId and a.status IN ('COMPLETED', 'CANCELLED')   and a.createdBy = :createdBy)"
			+ "OR ('HR_ADMIN' = :roleId and a.status IN ('COMPLETED', 'CANCELLED')  and a.createdBy = :createdBy)"
			+ " OR ('HR' = :roleId and a.status IN ('COMPLETED', 'CANCELLED') " + "  and a.createdBy = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.status IN ('COMPLETED', 'CANCELLED')) OR ('INFRA_ADMIN' = :roleId and a.status IN ('COMPLETED', 'CANCELLED'))) ORDER BY a.createdAt DESC")
	Page<Map> getAllCompletedTickets(Pageable pageable, @Param("createdBy") String createdBy,
			@Param("roleId") String roleId);

	@Query(value = "SELECT t.*, concat(e.frist_name, ' ', e.last_name) as createdByEmp from ticket_comment_log t inner join employee e on t.updated_by = e.user_id where t.ticket_id = :ticketId ORDER BY t.created_at DESC", nativeQuery = true)
	List<Map> getTktcommntsById(String ticketId);

	@Query(value = "SELECT * from ticket_attachment t where t.ticket_id = :ticketId  ", nativeQuery = true)
	List<Map> getTktAttachmentsById(String ticketId);

	@Query(value = "SELECT a.ticket_id as ticket_id,a.resolution as resolution, a.type as type,p.project_id as projectId, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, a.last_updated_at as last_updated_at,a.updated_by as updated_by, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp, p.project_name as projectName "
			+ "FROM ticket_info a "
			+ "left join ticketdbtool.project p ON a.project_id = p.project_id left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join employee e on b.employee_id = 	e.employee_id where a.ticket_id = :ticketId", nativeQuery = true)
	List<Map> getTicketSearchById(@Param("ticketId") String ticketId);

	// where t.ticket_status != 'REASSIGNED'p.status != 'INACTIVE'
	/*
	 * @Query(value="SELECT t from Ticket t  where t.ticket_status != 'REASSIGNED' "
	 * ) List<Ticket> getTicketDetailsExceptStatus();
	 */

	@Query("SELECT distinct new map(a.Id as ticket_id,a.resolution as resolution, a.type as type,p.projectName as projectName, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, a.projectId as project_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "from Ticket a left join Employee ee on a.createdBy = ee.userCredientials  left join Projects p ON a.projectId = p.pId  left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId where (a.createdAt between :startTime and :endTime)")
	Page<Map> getAllTicketsByDuration(Pageable pageable, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	@Query(value = "SELECT t.ticket_status, count(t.ticket_status) as TotalCount, p.project_name from ticket_info t  "
			+ "left join  project p on t.project_id=p.project_id  "
			+ "group by t.ticket_status, p.project_name", nativeQuery = true)
	Page<Map> getTicketStatusCountWithProject(Pageable pageable);

	@Query(value = " select e.employee_id from (select d.employee_id, sum(d.emp_cnt) as tkt_cnt from(select c.employee_id, c.emp_cnt from(  "
			+ "			 select e.employee_id, count(e.employee_id) as emp_cnt from ticketdbtool.employee e join ticketdbtool.ticket_assignee t on e.employee_id = t.employee_id  "
			+ "			 where  e.role_id = 'R3' and e.employee_status='ACTIVE'  and ticket_assignee_status = 'ACTIVE'  and ticket_id in (select ticket_id from ticketdbtool.ticket_info where ticket_status in ('ASSIGNED','INPROGRESS','REOPEN'))  "
			+ "			 group by e.employee_id   union  "
			+ "			 select e.employee_id, 0 as emp_cnt from ticketdbtool.employee e where e.role_id = 'R3' and e.employee_status='ACTIVE' "
			+ "			 and e.employee_id not in (select t.employee_id from ticketdbtool.ticket_assignee t where e.employee_id = t.employee_id)) c  "
			+ "			 UNION   select e.employee_id as emp_id, 0 as emp_cnt from ticketdbtool.employee e   "
			+ "			 join ticketdbtool.ticket_assignee t on e.employee_id = t.employee_id Join ticketdbtool.ticket_info i on t.ticket_id 	= i.ticket_id  "
			+ "			 where ticket_assignee_status = 'ACTIVE' and e.role_id = 'R3' and e.employee_status='ACTIVE'  and i.ticket_status in ('COMPLETED','CANCELLED'))d  group by d.employee_id) e order by e.tkt_cnt  "
			+ "			 limit 1 ", nativeQuery = true)
	String getElgibleAssignee();

	@Query("SELECT distinct new map(a.Id as ticket_id, a.type as type,a.resolution as resolution, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, a.projectId as project_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "from Ticket a left join Employee ee on a.createdBy = ee.userCredientials left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId")
	Page<Map> getAllTicketsDetails(Pageable pageable);

	@Query("SELECT distinct new map(a.Id as ticket_id, a.type as type,a.resolution as resolution, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, a.projectId as project_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp)  "
			+ "from Ticket a left join Employee ee on a.createdBy = ee.userCredientials left join Projects p ON a.projectId = p.pId left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' left join Employee e on b.employeeId = e.eId  WHERE (:projectId='' OR a.projectId LIKE concat('%', :projectId ,'%'')) "
			+ "AND (:status='' OR a.status LIKE concat('%', :status ,'%''))")
	Page<Map> getUU(Pageable pageable, @Param("projectId") String projectId);

	List<Ticket> findAll(Specification<Ticket> specification);

	@Query(value = "SELECT new map(a.Id as ticketId,a.type as type,a.resolution as resolution,p.pId as projectId,a.ticketDescription as ticketDescription,a.status as ticketStatus,a.createdAt as createdAt,a.createdBy as createdBy,a.lastUpdatedAt as lastUpdatedAt,p.projectName as projectName,b.Id as ticketAsigneeId,concat(e.firstName,' ', e.lastName) as assignedTo,concat(ee.firstName,' ',ee.lastName) as createdByEmp,DATEDIFF(a.resolvedAt,a.createdAt) as duration) "
			+ "from Ticket a left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId left join Employee ee on a.createdBy = ee.userCredientials.id left join Projects p ON a.projectId = p.pId where (:status is null or a.status = :status)"
			+ "and  (:projectName is null or p.projectName = :projectName) and  (cast(:parsedFromDate as date) is null or DATE(a.createdAt) >= :parsedFromDate ) and (cast(:parsedToDate as date) is null or DATE(a.createdAt) <= :parsedToDate ) AND (:searchQuery is null OR lower(e.firstName) LIKE %:searchQuery% OR lower(e.lastName) LIKE %:searchQuery%  OR e.eId LIKE %:searchQuery%) ORDER BY a.createdAt DESC ")
	Page<Map> getTicketDataByStatusProjectName(String projectName, TicketStatus status, Date parsedFromDate,
			Date parsedToDate, String searchQuery, Pageable pageable);

	@Query("SELECT distinct new map(a.Id as ticket_id,a.type as type,a.resolution as resolution,p.pId as projectId,p.projectName as projectName, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials "
			+ "left join Projects p ON a.projectId = p.pId "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId where  "
			+ "(('INFRA_USER' = :roleId and a.status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and b.employeeId  = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.createdBy = :createdBy) OR ('INFRA_ADMIN' = :roleId and a.status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN'))"
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.status NOT IN ('COMPLETED', 'CANCELLED')) OR ('HR_ADMIN' = :roleId and a.status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.createdBy = :createdBy)) ORDER BY a.createdAt DESC")
	Page<Map> getAllTicketsByStatusMobile(Pageable pageable, @Param("createdBy") String createdBy,
			@Param("roleId") String roleId);

//	@Query(value="select a.ticket_status as status, count(a.ticket_status) as count "
//			+ "FROM ticket_info a "
//			+ "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
//			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
//			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
//			+ "where ('INFRA_USER' = :roleId and a.ticket_status in ('ASSIGNED', 'INPROGRESS', 'REOPEN') and e.user_id = :createdBy) "
//			+ "OR ('DEVELOPER' = :roleId and a.ticket_status in ('ASSIGNED', 'INPROGRESS', 'REOPEN') and a.created_by = :createdBy)"
//			+ "OR ('HR_ADMIN' = :roleId and a.ticket_status in ('ASSIGNED', 'INPROGRESS', 'REOPEN') and a.created_by = :createdBy) "
//			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status in ('ASSIGNED', 'INPROGRESS', 'REOPEN')) "
//			+ "OR ('INFRA_ADMIN' = :roleId and a.ticket_status in ('ASSIGNED', 'INPROGRESS', 'REOPEN')) "
//			+ "group by ticket_status",nativeQuery = true)
//	List<Map> getTicketCount(String roleId, @Param("createdBy") String createdBy);

	@Query("select t.status as status ,count( t.Id) as count from Ticket  t "
			+ "left join TicketAssignee ta ON ta.ticketId = t.Id And ta.status='ACTIVE'"
			+ " left join Employee e on ta.employeeId = e.eId " + "  "
		 + "where "
		 + "(:role is null  "
			+ "or (:role='TICKETINGTOOL_ADMIN') "
			+ "  Or (:role='INFRA_ADMIN' )  "
			+ " OR ('INFRA_USER' = :role or ((e.userCredientials.id = :createdBy)  "
			+ " OR (t.createdBy = :createdBy ) ) ) ) "
		    + "     group by t.status  ")
	List<Map> getTicketCount(String role, @Param("createdBy") String createdBy);
	
	@Query(value = "select t.ticket_status as status ,count( t.ticket_id) as count from ticket_info  t \r\n"
			+ "left join ticket_assignee ta ON ta.ticket_id = t.ticket_id And ta.ticket_assignee_status='ACTIVE'\r\n"
			+ "left join employee e on ta.employee_id = e.employee_id \r\n"
			+ "where (:role = 'INFRA_USER' \r\n"
			+ "AND ( (t.ticket_status = 'INITIATED' ) OR  (e.user_id = :createdBy and t.ticket_status != 'INITIATED' ) \r\n"
			+ "OR (t.created_by = :createdBy))) group by t.ticket_status", nativeQuery = true)
	List<Map> getTicketCountForInfra(String role, @Param("createdBy") String createdBy);

	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy,a.resolution as resolution,a.type as type,p.pId as projectId,p.projectName as projectName, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials.id "
			+ "left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ "left join Role ro on ro.Id = eu.roleId "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE'"
			+ " left join Employee e on a.createdBy = e.userCredientials.id "
			+ "left join Projects p ON a.projectId = p.pId "
			+ " left join Employee e on b.employeeId = e.eId " + "WHERE "
					+ "(:priority is null or a.priorityId = :priority) and "
			+ "(:status is null or a.status = :status) and " + " :roleId!=null ORDER BY a.createdAt DESC")
	Page<Map> getAllTicketsForAdmin(Pageable pageable, String roleId, TicketStatus status, String priority);
	
	@Query("SELECT distinct new map(a.Id as ticket_id,a.UpdatedBy as UpdatedBy,a.resolution as resolution,a.type as type,p.pId as projectId,p.projectName as projectName, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(eu.firstName,' ', eu.lastName) as updatedName,ro.roleName as updatedRole, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials.id "
			+ "left join Employee eu on a.UpdatedBy = eu.userCredientials.id "
			+ "left join Role ro on ro.Id = eu.roleId "
			+ "left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE'"
			+ " left join Employee e on a.createdBy = e.userCredientials.id "
			+ "left join Projects p ON a.projectId = p.pId "
			+ " left join Employee e on b.employeeId = e.eId " + "WHERE "
					+ "(:priority is null or a.priorityId = :priority) and "
			+ "(:status is null or a.status IN ('COMPLETED', 'CANCELLED')) and " + " :roleId!=null ORDER BY a.createdAt DESC")
	Page<Map> getAllCompletedTicketsForAdmin(Pageable pageable, String roleId, TicketStatus status, String priority);

//	@Query("SELECT DISTINCT b FROM Branch b WHERE " + " (:name is null or lower(b.name) LIKE %:name%)"
//			+ " AND (:contactno is null or lower(b.primaryContactNumber) LIKE %:contactno%)"
//			+ "AND (:clinicNpi is null or lower(b.clinicNPI)  LIKE %:clinicNpi%)"
//			+ "AND (:status is null or b.status = :status)"
//			+ "AND (:practice is null or lower(b.organization.name) LIKE %:practice%)" 
//			+ "AND (:city is null or lower(b.address.city) LIKE %:city%)" 
//			+ "AND (:searchQuery is null"
//			+ " OR lower(b.name) LIKE %:searchQuery%" + " OR lower(b.emailId) LIKE %:searchQuery% "
//			+ " OR lower(b.primaryContactNumber) LIKE %:searchQuery%"
//			+ " OR lower(b.primaryContactNumber) LIKE %:searchQuery% OR lower(b.primaryContactPersonName) LIKE %:searchQuery%"
//			+ " OR lower(b.primaryPersonContactNumber) LIKE %:searchQuery% OR lower(b.emergencyContactNumber) LIKE %:searchQuery%"
//			+ " OR lower(b.address.city) LIKE %:searchQuery% OR lower(b.address.state) LIKE %:searchQuery%"
//			+ " OR STR(b.address.zipCode) LIKE %:searchQuery% OR lower(b.organization.name) LIKE %:searchQuery%"
//			+ " OR lower(b.clinicNPI) LIKE %:searchQuery% )")
}