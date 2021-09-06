package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.TicketComments;

public interface TicketCommentRepository extends JpaRepository<TicketComments, String> {

}
