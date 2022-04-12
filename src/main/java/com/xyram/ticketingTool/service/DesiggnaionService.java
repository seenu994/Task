package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;

public interface DesiggnaionService {

	// Page<Designation> getAllgetAllDesignationRole(Pageable pageable);

	ApiResponse getAllDesignation(Pageable pageable); 
	}


