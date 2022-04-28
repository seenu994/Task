package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeLocationRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.EmployeeLocation;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.EmployeeLocationService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class EmployeeLocationServiceImpl implements EmployeeLocationService {

	@Autowired
	EmployeeLocationRepository employeeLocationRepository;

	@Autowired
	EmployeeLocationService employeeLocationService;

	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse createLocation(EmployeeLocation location) {
		ApiResponse response = new ApiResponse(false);
		if (location != null) {
			location.setCreatedAt(new Date());
			location.setCreatedBy(currentUser.getName());
			employeeLocationRepository.save(location);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.LOC_ADDED);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOC_NOT_ADDED);
		}
		return response;
	}

	@Override
	public ApiResponse deleteLocation(String id) {
		ApiResponse response = new ApiResponse(false);
		EmployeeLocation locationObj = employeeLocationRepository.getEmployeeLocation(id);
		if (locationObj != null) {
			employeeLocationRepository.deleteLocation(id);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.DELETE_LOCATION);
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOCATION_NOT_FOUND);
		}
		return response;
	}

	@Override
	public ApiResponse updateLocation(String id, EmployeeLocation location) {
		ApiResponse response = new ApiResponse(false);
		response = validateLocation(location);
		EmployeeLocation locationObj = employeeLocationRepository.getEmployeeLocation(id);
		if (locationObj != null) {
			locationObj.setLocationName(locationObj.getLocationName());
			employeeLocationRepository.save(location);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.LOC_UPDATED);
			response.setContent(null);

		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOCATION_NOT_FOUND);
		}
		return response;
	}

	private ApiResponse validateLocation(EmployeeLocation location) {
		ApiResponse response = new ApiResponse(false);
		if (location.getLocationName() == null || location.getLocationName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Location is mandatory");
		}//else if (employeeLocationRepository.getEmployeeLocation(location.getId().equals(null)))
		return response;
	}

}
