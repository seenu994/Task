package com.xyram.ticketingTool.service;

import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.EmployeeLocation;


public interface EmployeeLocationService {

	ApiResponse createLocation(EmployeeLocation location);
	
	ApiResponse updateLocation(String id,EmployeeLocation location);
	
	//ApiResponse deleteLocation(String id);
	
	ApiResponse getAllLocation(Map<String, Object> filter);
	
	//ApiResponse validate(EmployeeLocation location);
	
}
