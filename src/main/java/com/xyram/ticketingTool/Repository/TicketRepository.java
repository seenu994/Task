package com.xyram.ticketingTool.Repository;


import java.util.ArrayList;
import java.util.Date;

import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;

import java.util.List;
import java.util.Map;

public interface TicketRepository extends JpaRepository<Ticket, String> {
	
	@Query(value = "SELECT * from ticket t where t.created_by =:createdBy AND t.ticket_status = :ticketStatus ", nativeQuery = true)
	List<Ticket> findAllByNameAndCreatedAndTicketStatus(@Param("createdBy") String createdBy, @Param("ticketStatus")String ticketStatus);
	
	@Query(value = "SELECT t.ticket_status from ticket t where t.ticket_id = :ticketId ", nativeQuery = true)
	String getTicketById(String ticketId);
	
	@Query("Select new map(t.Id as id,t.ticketDescription as ticketDescription,t.projectId as projectId,t.createdBy as createdBy,"
			+ "t.priorityId as priorityId,t.status as status) from Ticket t ")
	Page<Map> getAllTicketList(Pageable pageable); 
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticketdbtool.ticket a "
			+ "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "where a.ticket_description like %:searchString% ", nativeQuery = true)
	List<Map> searchTicket(@Param("searchString") String searchString );
	
	/*
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
			+ "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id "
			+ "left join employee e on b.employee_id = e.employee_id where (('INFRA' = :roleId and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and b.employee_id = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.ticket_status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.created_by = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status NOT IN ('COMPLETED', 'CANCELLED')))" , nativeQuery = true) 
	Page<Map> getAllTicketsByStatus(Pageable pageable, @Param("createdBy") String createdBy, @Param("roleId")String roleId);
	*/	 
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.last_updated_at as last_updated_at, "
			+ "a.created_by as created_by, a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "FROM ticketdbtool.ticket a "
			+ "left join ticketdbtool.employee ee on a.created_by = ee.user_id "
			+ "left join ticketdbtool.ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join ticketdbtool.employee e on b.employee_id = e.employee_id "
			+ "where ('INFRA' = 'INFRA' and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and e.user_id = :createdBy)", nativeQuery = true)
	Page<Map> getAllTicketsForInfraUser(Pageable pageable, @Param("createdBy") String createdBy);
	
	@Query("SELECT distinct new map(a.Id as ticket_id, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, "
			+ "a.priorityId as priority_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "FROM Ticket a left join Employee ee on a.createdBy = ee.userCredientials left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE' "
			+ "left join Employee e on b.employeeId = e.eId where  (('INFRA' = :roleId and a.status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and b.employeeId  = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.createdBy = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.status NOT IN ('COMPLETED', 'CANCELLED'))) ORDER BY a.createdAt DESC")
	Page<Map> getAllTicketsByStatus(Pageable pageable, @Param("createdBy") String createdBy, @Param("roleId")String roleId);
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, a.last_updated_at as last_updated_at, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, a.resolved_at as completed_at, a.cancelled_at as cancelled_at, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
			+ "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id  and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join employee e on b.employee_id = e.employee_id where (('INFRA' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED') and e.user_id = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED') and a.created_by = :createdBy ) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED'))) "
			+ "and a.last_updated_at >= DATE_ADD(curdate(), INTERVAL - 10 DAY) order by a.last_updated_at desc", nativeQuery = true)
	List<Map> getAllCompletedTickets(@Param("createdBy") String createdBy, @Param("roleId")String roleId);
	
	@Query(value = "SELECT t.*, concat(e.frist_name, ' ', e.last_name) as createdByEmp from ticket_comment_log t inner join employee e on t.updated_by = e.user_id where t.ticket_id = :ticketId ", nativeQuery = true)
	List<Map> getTktcommntsById(String ticketId);
	
	@Query(value = "SELECT * from ticket_attachment t where t.ticket_id = :ticketId  ", nativeQuery = true)
	List<Map> getTktAttachmentsById(String ticketId);
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, a.last_updated_at as last_updated_at, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp, a.project_id, p.project_name as projectName "
			+ "FROM ticket a left join project p on a.project_id = p.project_id left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id and b.ticket_assignee_status = 'ACTIVE' "
			+ "left join employee e on b.employee_id = 	e.employee_id where a.ticket_id = :ticketId", nativeQuery = true)
	List<Map> getTicketSearchById(@Param("ticketId") String ticketId);

	
	
	//where t.ticket_status != 'REASSIGNED'p.status != 'INACTIVE'
	/*
	 * @Query(value="SELECT t from Ticket t  where t.ticket_status != 'REASSIGNED' "
	 * ) List<Ticket> getTicketDetailsExceptStatus();
	 */

	
	@Query("SELECT distinct new map(a.Id as ticket_id, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, a.projectId as project_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "from Ticket a left join Employee ee on a.createdBy = ee.userCredientials left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE'\r\n" + 
			"left join Employee e on b.employeeId = e.eId where (a.createdAt between :startTime and :endTime)")
	Page<Map> getAllTicketsByDuration(Pageable pageable,  @Param("startTime") Date startTime,	@Param("endTime") Date endTime);
	
	
	
	@Query(value="SELECT t.ticket_status, count(t.ticket_status) as TotalCount, p.project_name from ticket t \r\n" + 
			"left join  project p on t.project_id=p.project_id \r\n" + 
			"group by t.ticket_status, p.project_name",nativeQuery=true)
	Page<Map> getTicketStatusCountWithProject(Pageable pageable);
	
	@Query(value=" select c.employee_id from( "
			+ " select e.employee_id, count(e.employee_id) as emp_cnt from employee e join ticket_assignee t on e.employee_id = t.employee_id"
			+ " where ticket_assignee_status = 'ACTIVE' and ticket_id in (select ticket_id from ticket where ticket_status in ('ASSIGNED','INPROGRESS'))"
			+ " group by e.employee_id"
			+ " union"
			+ " select e.employee_id, count(e.employee_id) as emp_cnt from employee e where e.role_id = 'R2' "
			+ " and e.employee_id not in (select t.employee_id from ticket_assignee t where e.employee_id = t.employee_id)"
			+ " group by e.employee_id) c "
			+ " order by c.emp_cnt"
			+ " limit 1 ",nativeQuery=true)
	String getElgibleAssignee();
	
	@Query("SELECT distinct new map(a.Id as ticket_id, a.ticketDescription as ticket_description, a.status as ticket_status, a.createdAt as created_at, a.createdBy as created_by, a.lastUpdatedAt as last_updated_at, a.projectId as project_id, b.employeeId as assigneeId, concat(e.firstName,' ', e.lastName) as assigneeName, concat(ee.firstName,' ', ee.lastName) as createdByEmp) "
			+ "from Ticket a left join Employee ee on a.createdBy = ee.userCredientials left join TicketAssignee b ON a.Id = b.ticketId and b.status = 'ACTIVE'\r\n" + 
			"left join Employee e on b.employeeId = e.eId")
	Page<Map> getAllTicketsDetails(Pageable pageable);
	
	@Query(value="SELECT a.ticket_id, a.ticket_description, a.ticket_status, a.created_at, a.created_by, a.last_updated_at, a.project_id, b.ticket_assignee_id, concat(e.frist_name,' ', e.last_name) as assigneeName, concat(ee.frist_name,' ', ee.last_name) as createdByEmp "
			+ "from ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id and b.ticket_assignee_status = 'ACTIVE'\r\n" + 
			"left join employee e on b.employee_id = e.employee_id where (a.ticket_status='' OR a.ticket_status =:status)"
			+ "and  (a.project_id='' OR a.project_id =:projectId)",nativeQuery = true)
	Page<Map> getTicketDataByStatusProjectName(Pageable pageable, @Param("projectId") String projectId, @Param("status") Object status);
}