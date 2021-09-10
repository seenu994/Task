package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.TicketStatusHistory;

public interface TicketStatusHistRepository extends JpaRepository<TicketStatusHistory, String>{

}
