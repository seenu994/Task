package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyram.ticketingTool.entity.StoryHistory;

public interface StoryHistoryRepository extends JpaRepository<StoryHistory, String> {

}
