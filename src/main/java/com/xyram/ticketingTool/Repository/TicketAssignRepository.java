package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.TicketAssignee;
public interface TicketAssignRepository extends JpaRepository<TicketAssignee, Integer>{

	@Query("SELECT t from TicketAssignee t Where t.ticketId = :ticketId AND t.employeeId = :employeeId")
	public List<TicketAssignee> findByTicket_IdAndEmployee_Id(Integer ticketId,Integer employeeId);

}


