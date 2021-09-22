package com.xyram.ticketingTool.Repository;


import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyram.ticketingTool.entity.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketRepository extends JpaRepository<Ticket, String> {
	
	@Query(value = "SELECT * from ticket t where t.created_by =:createdBy AND t.ticket_status = :ticketStatus ", nativeQuery = true)
	List<Ticket> findAllByNameAndCreatedAndTicketStatus(@Param("createdBy") String createdBy, @Param("ticketStatus")String ticketStatus);
	
	@Query(value = "SELECT t.ticket_status from ticket t where t.ticket_id = :ticketId ", nativeQuery = true)
	String getTicketById(String ticketId);
	
	@Query("Select new map(t.Id as id,t.ticketDescription as ticketDescription,t.projectId as projectId,t.createdBy as createdBy,t.priorityId as priorityId,t.status as status) from Ticket t")
	Page<Map> getAllTicketList(Pageable pageable);
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
			+ "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id "
			+ "left join employee e on b.employee_id = e.employee_id where (('INFRA' = :roleId and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN') and b.employee_id = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.ticket_status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN') and a.created_by = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status NOT IN ('COMPLETED', 'CANCELLED')))", nativeQuery = true)
	List<Map> getAllTicketsByStatus(@Param("createdBy") String createdBy, @Param("roleId")String roleId);
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description, a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
			+ "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id "
			+ "left join employee e on b.employee_id = e.employee_id where (('INFRA' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED') and b.employee_id = :createdBy) "
			+ "OR ('DEVELOPER' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED') and a.created_by = :createdBy) "
			+ "OR ('TICKETINGTOOL_ADMIN' = :roleId and a.ticket_status IN ('COMPLETED', 'CANCELLED'))) "
			+ "and a.last_updated_at >= DATE_ADD(curdate(), INTERVAL - 1 DAY) order by a.last_updated_at desc", nativeQuery = true)
	List<Map> getAllCompletedTickets(@Param("createdBy") String createdBy, @Param("roleId")String roleId);
	
	@Query(value = "SELECT * from ticket_comment_log t where t.ticket_id = :ticketId ", nativeQuery = true)
	List<Map> getTktcommntsById(String ticketId);
	
	@Query(value = "SELECT * from ticket_attachment t where t.ticket_id = :ticketId ", nativeQuery = true)
	List<Map> getTktAttachmentsById(String ticketId);
	
	@Query(value = "SELECT a.ticket_id as ticket_id, a.ticket_description as ticket_description , a.ticket_status as ticket_status, a.created_at as created_at, a.created_by as created_by, "
			+ "a.priority_id as priority_id, b.employee_id as assigneeId, concat(e.frist_name, ' ', e.last_name) as assigneeName, concat(ee.frist_name, ' ', ee.last_name) as createdByEmp "
			+ "FROM ticket a left join employee ee on a.created_by = ee.user_id left join ticket_assignee b ON a.ticket_id = b.ticket_id "
			+ "left join employee e on b.employee_id = e.employee_id where a.ticket_id = :ticketId", nativeQuery = true)
	List<Map> getTicketSearchById(@Param("ticketId") String ticketId);

	//String getTicketById(Integer ticketId);
}