package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.EmployeeLocation;

public interface EmployeeLocationService {

	ApiResponse createLocation(EmployeeLocation locationReq);
	
}
