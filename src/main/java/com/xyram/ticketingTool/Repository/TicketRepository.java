package com.xyram.ticketingTool.Repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyram.ticketingTool.entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
	
	@Query(value = "SELECT * from ticket t where t.created_by =:createdBy AND t.ticket_status = :ticketStatus ", nativeQuery = true)
	List<Ticket> findAllByNameAndCreatedAndTicketStatus(@Param("createdBy") String createdBy, @Param("ticketStatus")String ticketStatus);
}
