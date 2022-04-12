package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
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
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.enumType.AssetVendorEnum;
import com.xyram.ticketingTool.enumType.SoftwareEnum;
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

	@Override
	public ApiResponse addSoftwareMaster(SoftwareMaster softwareMaster) {

		ApiResponse response = new ApiResponse(false);
		response = validateSoftwareMasterId(softwareMaster);
		if (true) {

			// SoftwareMaster softwareMasterSave = softwareRepository.save(softwareMaster);
			if (softwareMaster != null) {

				// Map content = new HashMap<>();
				SoftwareMaster softwareMasterSave = softwareRepository.save(softwareMaster);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_SOFTWARE_ADDED);

			}

			softwareRepository.save(softwareMaster);
//				response.setSuccess(true);
//				response.setMessage(ResponseMessages.ASSET_SOFTWARE_ADDED);
//			}
//		
//			else {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.ASSET_SOFTWARE_NOT_ADDED);
//			}
//		
//		}
		}
		return response;
	}

	@Override
	public ApiResponse editSoftwareMaster(SoftwareMaster SoftwareMasterRequest) {

		ApiResponse response = new ApiResponse();
		response = validateSoftwareMasterId(SoftwareMasterRequest);

		if (SoftwareMasterRequest != null) {
			SoftwareMasterRequest.setSoftwareId(SoftwareMasterRequest.getSoftwareId());
			SoftwareMasterRequest.setSoftwareName(SoftwareMasterRequest.getSoftwareName());
			SoftwareMasterRequest.setSoftwareMasterStatus(SoftwareMasterRequest.getSoftwareMasterStatus());
		}
		SoftwareMaster softwareMasterAdded = softwareRepository.save(SoftwareMasterRequest);

		response.setSuccess(true);
		response.setMessage(ResponseMessages.EDITED_SOFTWAREMASTER);
		Map content = new HashMap();
		content.put("softwareId", softwareMasterAdded.getSoftwareId());
		response.setContent(content);
//				} else {
//					response.setSuccess(false);
//					response.setMessage(ResponseMessages.INVALID_SOFTWAREDETAILS);
//					response.setContent(null);
//				}

		return response;
	}

	private ApiResponse validateSoftwareMasterId(SoftwareMaster software) {
		ApiResponse response = new ApiResponse(false);
		if (software.getSoftwareId().equals("")) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SoftwareId is manditory");
		}

		if (software.getSoftwareName().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SoftwareName is manditory");
		}
		return response;
	}

//	private ApiResponse validateStatus(SoftwareEnum SoftwareEnum) {
//		ApiResponse response = new ApiResponse(false);
//		if (SoftwareEnum == SoftwareEnum.ACTIVE || SoftwareEnum == SoftwareEnum.INACTIVE) {
//			response.setMessage(ResponseMessages.STATUS_UPDATE);
//			response.setSuccess(true);
//			
//		}
//
//		else {
//			response.setMessage(ResponseMessages.VENDORSTATUS_INVALID);
//			response.setSuccess(false);
//		}
//
//		response.setSuccess(true);
//
//		return response;
//
//	}

	@Override
	public ApiResponse getAllsoftwareMasterList(Pageable peageble) {

		Page<Map> softwareMasterList = softwareRepository.getAllsoftwareMasterList(peageble);

		Map content = new HashMap();
		content.put("SoftwareMasterList", softwareMasterList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		
		response.setContent(content);
		response.setMessage(ResponseMessages.GETALL_SOFTWAREMATER_LIST);
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
			response.setMessage(ResponseMessages.SOFTWARE_DETAILS_INVALID);
			response.setContent(null);
		}

		return response;

	}
}
