package com.xyram.ticketingTool.service;

import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.CompanyWings;

public interface CompanyWingService {
	
	
	ApiResponse getAllWing(Map<String, Object> filter);

	ApiResponse createWing(CompanyWings wings);
	
	ApiResponse updateWing(String id, CompanyWings wings);
	
	//ApiResponse deleteWing(String id);

}
