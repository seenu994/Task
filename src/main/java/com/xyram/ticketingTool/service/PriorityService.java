package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Priority;

public interface PriorityService {

	 Page<Priority> getAllPriority(Pageable pageable); 
		
}
