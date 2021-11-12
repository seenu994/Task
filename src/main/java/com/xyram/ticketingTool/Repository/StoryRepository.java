package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {
	
	

}
