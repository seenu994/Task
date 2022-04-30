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
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.City;
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
			response = validateDesignation(designation);
			if (designation.getDesignationName() != null) {
				
//				if (designation.getDesignationName().equals("") || designation.getDesignationName() == null) {
//					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation is manditory");
//				}
				
				Designation designations = designationRepository.save(designation);
				response.setMessage(ResponseMessages.ADDED_DESIGNATION);
				response.setSuccess(true);

				Map content = new HashMap();
				content.put("designationId", designation.getId());
				content.put("designationName",designation.getDesignationName());
			//	content.put("designationStatus", designation.getStatus());
				response.setContent(content);
			}
			
			return response;
		}
		
		
		
		private ApiResponse validateDesignation(Designation designation) {
			ApiResponse response = new ApiResponse(false);

			Designation designationObj  = designationRepository.getDesignationName(designation.getDesignationName());
			if (designation.getDesignationName().equals("") || designation.getDesignationName() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation is manditory");
			}
			
			if(designationObj != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " DesignationName already exists!");
			}
			response.setSuccess(true);
			return response;
		}
		
		
		@Override
		public  ApiResponse editDesignation(Designation Request, String Id) {

			ApiResponse response = new ApiResponse();
			response = validateDesignation(Request);
				Designation designationRequest = designationRepository.getById(Id);
				if (designationRequest.getDesignationName() != null) {
//					if (designationRequest.getDesignationName().equals("") || designationRequest.getDesignationName() == null) {
//						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation is manditory");
//					}
					
					designationRequest.setDesignationName(Request.getDesignationName());

//					designationRequest.setLastUpdatedAt(new Date());
//					designationRequest.setUpdatedBy(currentUser.getName());

					designationRepository.save(designationRequest);
					response.setSuccess(true);
					response.setMessage(ResponseMessages.DESIGNATION_EDITED);
						
				} else {
					response.setSuccess(false);
					response.setMessage(ResponseMessages.DESIGNATION_DETAILS_INVALID);
					// response.setContent(null);
				}
				
			    return response;

		}




		@Override
		public ApiResponse searchDesignation(String searchString) {
			
			ApiResponse response = new ApiResponse(false);
			Page<Map> designations = designationRepository.searchDesignation(searchString);

			Map content = new HashMap();
			content.put("designations", designations);
			if (content != null) {

				
				response.setSuccess(true);
				response.setMessage("Designation retrived successfully");
				response.setContent(content);
			} else {
				
				response.setSuccess(false);
				
				response.setMessage("Not retrived the data");
			}

			return response;

		}

}