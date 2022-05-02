package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.CompanyLocationRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.CompanyLocation;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.CompanyLocationService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class CompanyLocationServiceImpl implements CompanyLocationService {

	@Autowired
	CompanyLocationRepository companyLocationRepository;

	@Autowired
	CompanyLocationService companyLocationService;

	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse createLocation(CompanyLocation location) {
		ApiResponse response = new ApiResponse(false);
		response = validateLocation(location);
		if (location != null) {
			location.setCreatedAt(new Date());
			location.setCreatedBy(currentUser.getName());
			location.setStatus(true);
			companyLocationRepository.save(location);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.LOC_ADDED);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOC_NOT_ADDED);
		}
		return response;
	}

//	@Override
//	public ApiResponse deleteLocation(String id) {
//		ApiResponse response = new ApiResponse(false);
//		EmployeeLocation locationObj = employeeLocationRepository.getEmployeeLocation(id);
//		if (locationObj != null) {
//			employeeLocationRepository.deleteLocation(id);
//			response.setSuccess(true);
//			response.setMessage(ResponseMessages.DELETE_LOCATION);
//			response.setContent(null);
//		} else {
//			response.setSuccess(false);
//			response.setMessage(ResponseMessages.LOCATION_NOT_FOUND);
//		}
//		return response;
//	}

	@Override
	public ApiResponse updateLocation(String id, CompanyLocation location) {
		ApiResponse response = new ApiResponse(false);
		response = validateLocation(location);
		CompanyLocation locationObj = companyLocationRepository.getCompanyLocation(id);
		if (locationObj != null) {
			locationObj.setLastUpdatedAt(new Date());
			locationObj.setUpdatedBy(currentUser.getName());
			locationObj.setLocationName(location.getLocationName());
			locationObj.setStatus(location.isStatus());
			companyLocationRepository.save(locationObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.LOC_UPDATED);
			response.setContent(null);

		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOCATION_NOT_FOUND);
		}
		return response;
	}

	private ApiResponse validateLocation(CompanyLocation location) {
		ApiResponse response = new ApiResponse(false);
		if (location.getLocationName() == null || location.getLocationName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Location is mandatory");
		}
		return response;
	}

	@Override
	public ApiResponse getAllLocation(Map<String, Object> filter) {
		String searchString = filter.containsKey("searchString") ? filter.get("searchString").toString().toLowerCase()
				: null;
		List<Map> locationList = companyLocationRepository.getAllLocation(searchString);
		Map content = new HashMap();
		content.put("locationList", locationList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

}
