package com.xyram.ticketingTool.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;

@Repository
public interface TicketAssignRepository extends JpaRepository<TicketAssignee, String>{

	@Query("SELECT t from TicketAssignee t Where t.ticketId = :ticketId")
	public List<TicketAssignee> findByTicketId(String ticketId);
	
	@Query(value ="SELECT t.employeeId from TicketAssignee t Where t.ticketId = :ticketId and t.status='INACTIVE'")
	public String getAssigneeId(Object ticketId);
	
	@Query(value ="SELECT t.employeeId from TicketAssignee t Where t.ticketId = :ticketId and t.status='ACTIVE'")
	public String getActiveAssigneeId(Object ticketId);

	@Query(value ="SELECT t.employeeId from TicketAssignee t Where t.ticketId = :id and t.employeeId = :geteId")
	public String getAssigneeIdForDeveloper(String id, String geteId);
	@Query(value ="SELECT t.employeeId from TicketAssignee t Where t.ticketId = :ticketId and t.employeeId = :employeeId")
	public String getAssigneeIdForDevelopers(String employeeId, String ticketId);

	@Query("SELECT t from TicketAssignee t Where t.ticketId = :ticketId and t.status='INACTIVE'")
	public TicketAssignee getDuplicateTickate(String ticketId);
	
//	
//	@Query(value ="SELECT t.employeeId from TicketAssignee t Where t.ticketId = :ticketId and t.status='INACTIVE'")
//	public String getAssigneeId(Object ticketId);
	

	
	  

	
}


