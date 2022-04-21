package com.xyram.ticketingTool.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.EmployeeLocationRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.EmployeeLocation;
import com.xyram.ticketingTool.service.EmployeeLocationService;

@Service
@Transactional
public class EmployeeLocationServiceImpl implements EmployeeLocationService {

	@Autowired
	EmployeeLocationRepository employeeLocationRepository;

	@Override
	public ApiResponse createLocation(EmployeeLocation locationReq) {
		ApiResponse response = new ApiResponse(false);
		if (locationReq.getLocation() != null) {
			locationReq.setLocation(locationReq.getLocation());
		}
		
		return response;
	}


}
