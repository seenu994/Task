package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.SoftwareMasterRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.SoftwareMaster;
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
	private ApiResponse response;

	@Override
	public ApiResponse addSoftwareMaster(SoftwareMaster softwareMaster) {

		
		//ApiResponse response = new ApiResponse(false);
		//response = validateSoftwareMaster(softwareMaster);
		if (response.isSuccess()) {
			SoftwareMaster softwareMasterSave = softwareRepository.save(softwareMaster);
			if (softwareMasterSave != null) {
				
				Map content = new HashMap<>();
				content.put("vendorDetails", softwareMasterSave);
				response.setSuccess(true);
				response.setContent(content);
			}
			
//				softwareRepository.save(softwareMaster);
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
		
		

			ApiResponse response = validateSoftwareMasterId(SoftwareMasterRequest);
			if (response.isSuccess()) {

				if (SoftwareMasterRequest != null) {
					SoftwareMasterRequest.setSoftwareId(SoftwareMasterRequest.getSoftwareId());
					SoftwareMasterRequest.setSoftwareName(SoftwareMasterRequest.getSoftwareName());
					SoftwareMasterRequest.setSoftwareMasterStatus(SoftwareMasterRequest.getSoftwareMasterStatus());
					
					
					SoftwareMaster softwareMasterAdded = softwareRepository.save(SoftwareMasterRequest);

					response.setSuccess(true);
					response.setMessage(ResponseMessages.PROJECT_EDIT);
					Map content = new HashMap();
					content.put("projectId", softwareMasterAdded.getSoftwareId());
					response.setContent(content);
				} else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.PROJECT_ID_VALID);
					response.setContent(null);
				}
			}
			return response;
		}
		
		
		
	
	

	private ApiResponse validateSoftwareMasterId(SoftwareMaster softwareId) {
		// ApiResponse response = new ApiResponse(false);
		if (softwareId.getSoftwareId() != null) {
			response.setMessage("success");
			response.setSuccess(true);
		} else {
			response.setSuccess(false);

		}

	return response;
	}

	private ApiResponse validateStatus(SoftwareEnum SoftwareEnum) {
		ApiResponse response = new ApiResponse(false);
		if (SoftwareEnum == SoftwareEnum.ACTIVE || SoftwareEnum == SoftwareEnum.INACTIVE) {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}

		else {
			response.setMessage(ResponseMessages.VENDORSTATUS_INVALID);
			response.setSuccess(false);

		}

		return response;
	}
	
	

}
