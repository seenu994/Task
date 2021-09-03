package com.xyram.ticketingTool.Repository;


	import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Ticket;

	

	public interface  TicketRepository extends  JpaRepository<Ticket,Integer> {
}
