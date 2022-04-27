package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.SoftwareMasterRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetIssues;
import com.xyram.ticketingTool.entity.AssetSoftware;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.AssetIssueStatus;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.enumType.SoftwareEnum;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.SoftwareMasterService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class SoftwareMasterServiceImpl implements SoftwareMasterService {
	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	SoftwareMasterRepository softwareRepository;

	@Autowired
	SoftwareMasterService softwareMasterServices;

	@Autowired
	CurrentUser currentUser;

	@Override
	public ApiResponse addSoftwareMaster(SoftwareMaster softwareMaster) {

		ApiResponse response = new ApiResponse(false);

		response = validateSoftwareMaster(softwareMaster);
		if (softwareMaster.getSoftwareName() != null) {
			softwareMaster.setCreatedAt(new Date());
			softwareMaster.setCreatedBy(currentUser.getName());
			
			SoftwareMaster softwaremaster = softwareRepository.save(softwareMaster);
			response.setMessage(ResponseMessages.ADDED_SOFTWAREMASTER);
			response.setSuccess(true);

			Map content = new HashMap();
			content.put("softwareId", softwareMaster.getSoftwareId());
			content.put("softwareName",softwareMaster.getSoftwareName());
			content.put("softwareStatus", softwareMaster.getSoftwareMasterStatus());
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse editSoftwareMaster(SoftwareMaster software, String softwareId) {

		ApiResponse response = new ApiResponse();
		response = validateSoftwareMaster(software);
		if (response.isSuccess()) {
			SoftwareMaster softwareMasterRequest = softwareRepository.getSoftwareById(softwareId);
			if (softwareMasterRequest != null) {
				// softwareMasterRequest.setSoftwareId(software.getSoftwareId());
				softwareMasterRequest.setSoftwareName(software.getSoftwareName());

				softwareMasterRequest.setLastUpdatedAt(new Date());
				softwareMasterRequest.setUpdatedBy(currentUser.getName());

				softwareRepository.save(softwareMasterRequest);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.SOFTWAREMASTER_EDITED);

			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SOFTWARE_DETAILS_INVALID);
				// response.setContent(null);
			}

		}
		return response;

	}

	private ApiResponse validateSoftwareMaster(SoftwareMaster software) {
		ApiResponse response = new ApiResponse(false);

		if (software.getSoftwareName().equals("") || software.getSoftwareName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SoftwareName is manditory");
		}
	
			
		
		response.setSuccess(true);
		return response;
	}
	
	@Override
	public ApiResponse getAllsoftwareMaster(Map<String, Object> filter, Pageable peageble) {

		ApiResponse response = new ApiResponse(false);

		String softwareMasterStatus = filter.containsKey("softwareMasterStatus")
				? ((String) filter.get("softwareMasterStatus")).toUpperCase()
				: null;

		SoftwareEnum softwareEnum = null;
		if (softwareMasterStatus != null) {
			try {
				softwareEnum = softwareMasterStatus != null ? SoftwareEnum.toEnum(softwareMasterStatus) : null;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						filter.get("status").toString() + " is not a valid status");
			}
		}

		Page<SoftwareMaster> softwareMaster = softwareRepository.getAllsoftwareMaster(softwareEnum, peageble);
		if (softwareMaster.getSize() > 0) {
//		Map content = new HashMap();
//		content.put("SoftwareMasterList", softwareMaster);
//		//ApiResponse response = new ApiResponse(true);
//		response.setSuccess(true);
//		
//		response.setContent(content);
//		response.setMessage(ResponseMessages.GETALL_SOFTWAREMATER_LIST);
//		return response;
//	}
			Map content = new HashMap();
			content.put("softwareMaster", softwareMaster);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}
		return response;
	}

	@Override
	public ApiResponse updatesoftwareMasterStatus(String softwareId, SoftwareEnum softwareEnum) {

		ApiResponse response = new ApiResponse(false);

		SoftwareMaster softwareMaster = softwareRepository.getSoftwareById(softwareId);

		if (softwareMaster != null) {

			softwareMaster.setSoftwareMasterStatus(softwareEnum);
			System.out.print(softwareEnum.toString());
			softwareRepository.save(softwareMaster);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.SOFTWARE_STATUS_UPDATED);
			response.setContent(null);

		} else {
			response.setSuccess(false);
			// response.setMessage(ResponseMessages.SOFTWARE_DETAILS_INVALID);
			response.setContent(null);
			response.setMessage(ResponseMessages.SOFTWARE_DETAILS_INVALID);
		}

		return response;

	}

	@Override
	public ApiResponse searchsoftwareId(String softwareId) {
		ApiResponse response = new ApiResponse();
		SoftwareMaster softwareMaster = new SoftwareMaster();
		softwareMaster.setSoftwareId(softwareId);

		List<SoftwareMaster> softwareDetails = softwareRepository.searchsoftwareId(softwareId);
		Map content = new HashMap();

		content.put("softwareDetails", softwareDetails);
		if (content != null && softwareDetails.size() > 0) {

			response.setSuccess(true);
			response.setMessage("softwareId Retrieved successfully");
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve softwareId");
			// response.setContent(content);
		}
		return response;
	}

}
