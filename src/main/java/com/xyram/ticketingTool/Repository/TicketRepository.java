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
	
	@Query(value = "SELECT a.ticket_id FROM ticketdbtool.ticket a where (('R2' = :roleId and a.ticket_status IN ('ASSIGNED', 'INPROGRESS', 'REOPEN')) \r\n"
			+ "OR ('R3' = :roleId and a.ticket_status IN ('INITIATED', 'ASSIGNED', 'INPROGRESS', 'REOPEN')) OR ('R1' = :roleId)) and a.created_by = :createdBy", nativeQuery = true)
	List<String> getAllTicketsByStatus(@Param("createdBy") String createdBy, @Param("roleId")String roleId);

	//String getTicketById(Integer ticketId);
}
