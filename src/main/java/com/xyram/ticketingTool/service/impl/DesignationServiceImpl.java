package com.xyram.ticketingTool.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.DesignationRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.SoftwareMaster;
import com.xyram.ticketingTool.service.DesiggnaionService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */
@Service
public class DesignationServiceImpl implements DesiggnaionService {

	@Autowired
	DesignationRepository designationRepository;
	

	
	
		@Override
		public ApiResponse getAllDesignation(Pageable pageable) {
	       Page<Map> DesignationList =   designationRepository.getAllDesignationList(pageable);
	       Map content = new HashMap();
	       content.put("DesignationList", DesignationList);
	       ApiResponse response = new ApiResponse(true);
	       response.setSuccess(true);
	       response.setContent(content);
	       return  response;
		}




		@Override
		public ApiResponse addDesignation(Designation designation) {
			ApiResponse response = new ApiResponse(false);
			
			if (designation.getDesignationName() != null) {
				
				if (designation.getDesignationName().equals("") || designation.getDesignationName() == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation is manditory");
				}
				
				Designation designations = designationRepository.save(designation);
				response.setMessage(ResponseMessages.ADDED_DESIGNATION);
				response.setSuccess(true);

				Map content = new HashMap();
				content.put("designationId", designation.getId());
				content.put("designationName",designation.getDesignationName());
				response.setContent(content);
			}
			
			return response;
		}
		
		
		
		@Override
		public  ApiResponse editDesignation(Designation Request, String Id) {

			ApiResponse response = new ApiResponse();
				Designation designationRequest = designationRepository.getById(Id);
				if (designationRequest != null) {
					//designationRequest.setId(designationRequest.getId());
					designationRequest.setDesignationName(Request.getDesignationName());

//					designationRequest.setLastUpdatedAt(new Date());
//					designationRequest.setUpdatedBy(currentUser.getName());

					designationRepository.save(designationRequest);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.SOFTWAREMASTER_EDITED);

				} else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.SOFTWARE_DETAILS_INVALID);
					// response.setContent(null);
				}
			    return response;

		}
}