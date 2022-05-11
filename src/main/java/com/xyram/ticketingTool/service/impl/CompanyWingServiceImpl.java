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

import com.xyram.ticketingTool.Repository.CompanyWingsRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.CompanyLocation;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.CompanyWingService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class CompanyWingServiceImpl implements CompanyWingService {

	@Autowired
	CompanyWingsRepository wingRepo;

	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse createWing(CompanyWings wings) {
		ApiResponse response = new ApiResponse(false);
		//response = validateWing(wings);
		if (wings.getWingName() == null|| wings.getWingName().equals("")  ) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.WINGS_NOT_UPDATED+":"+"Wing Name is Mandatory");
			return response;
		}
		if (wings != null) {
			wings.setCreatedAt(new Date());
			wings.setCreatedBy(currentUser.getName());
			wings.setStatus(true);
			wingRepo.save(wings);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.WINGS_ADDED);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.WINGS_NOT_ADDED);
		}
		return response;
	}

	@Override
	public ApiResponse getAllWing(Map<String, Object> filter) {
		String searchString = filter.containsKey("searchString") ? filter.get("searchString").toString().toLowerCase()
				: null;
		List<Map> wingList = wingRepo.getAllWing(searchString);
		Map content = new HashMap();
		content.put("wingList", wingList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse updateWing(String id, CompanyWings wings) {
		ApiResponse response = new ApiResponse();
//		response = validateWing(wings);
		CompanyWings wingObj = wingRepo.getWingById(id);
		if (wings.getWingName() == null|| wings.getWingName().equals("")  ) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.WINGS_NOT_UPDATED+":"+"Wing Name is Mandatory");
			return response;
		}
		if (wingObj != null) {
			    wingObj.setUpdatedBy(currentUser.getName());
				wingObj.setLastUpdatedAt(new Date());
				wingObj.setWingName(wings.getWingName());
				wingObj.setStatus(wings.isStatus());
				wingRepo.save(wingObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.WINGS_UPDATED);
				response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.WINGS_NOT_UPDATED);
		}

		return response;
	}
	
//	private ApiResponse validateWing(CompanyWings wings) {
//		ApiResponse response = new ApiResponse(false);
//		if (wings.getWingName() == null|| wings.getWingName().equals("")  ) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wing name is mandatory");
//		}
//		return response;
//	}
}
