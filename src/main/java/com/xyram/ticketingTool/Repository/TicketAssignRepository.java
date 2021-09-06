package com.xyram.ticketingTool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.TicketAssignee;
public interface TicketAssignRepository extends JpaRepository< TicketAssignee, Integer>{

	public List<TicketAssignee> findByTicket_IdAndEmployee_Id(Integer ticketId,Integer employeeId);

}


