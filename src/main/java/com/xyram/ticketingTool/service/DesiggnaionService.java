package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Designation;

public interface DesiggnaionService {

	// Page<Designation> getAllgetAllDesignationRole(Pageable pageable);

	
	ApiResponse addDesignation(Designation designation);
	
	ApiResponse editDesignation(Designation Request, String Id);
	ApiResponse getAllDesignation(Pageable pageable);
	ApiResponse searchDesignationByName(String designationName);
	}


