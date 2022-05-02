package com.xyram.ticketingTool.service;

import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.CompanyLocation;


public interface CompanyLocationService {

	ApiResponse createLocation(CompanyLocation location);
	
	ApiResponse updateLocation(String id,CompanyLocation location);
	
	//ApiResponse deleteLocation(String id);
	
	ApiResponse getAllLocation(Map<String, Object> filter);
	
	//ApiResponse validate(EmployeeLocation location);
	
}
