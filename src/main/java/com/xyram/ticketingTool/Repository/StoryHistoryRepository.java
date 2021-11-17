package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.entity.StoryHistory;
import com.xyram.ticketingTool.entity.TicketStatusHistory;

public interface StoryHistoryRepository extends JpaRepository<StoryHistory, String> {

}
