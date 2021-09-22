package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.TicketAssignee;
public interface TicketAssignRepository extends JpaRepository<TicketAssignee, String>{

	@Query("SELECT t from TicketAssignee t Where t.ticketId = :ticketId")
	public List<TicketAssignee> findByTicketId(String ticketId);
	
	@Query("SELECT t.employeeId from TicketAssignee t Where t.ticketId = :ticketId")
	public String getAssigneeId(String ticketId);

}


