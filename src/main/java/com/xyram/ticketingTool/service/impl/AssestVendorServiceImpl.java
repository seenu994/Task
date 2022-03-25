package com.xyram.ticketingTool.service.impl;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.VendorType;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssestVendorServiceImpl implements AssetvendorService {
	//private static final String VendorList = null;

	//private static final org.springframework.data.domain.Pageable Pageable = null;

	private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	AssetVendorRepository assetVendorRepository;
	
	
	
	
	
	@Override
	public ApiResponse addAssestVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		response=validateVendorEmail(vendor);
		
		if (response.isSuccess()) {
			
			
		
       AssetVendor vendorSave = assetVendorRepository.save(vendor);
       
       if(vendorSave != null)
       {
    	   Map content = new HashMap<>();
			content.put("vendorDetails", vendorSave);			
			response.setSuccess(true);
			response.setContent(content);
			return response;  
       }
		}
	//return response;
		return response;
       }
      /* @Override
       public ApiResponse getAllAssetVendorList(java.awt.print.Pageable pageable) {
   		
   		return ApiResponse AssetVendorRepository.getAllAssetVendorList(Pageable pageable);
   	
   	
	return response;
	
	}
	}*/


	/*public ApiResponse editassetVendorList(AssetVendor AssetVendorRequest) {
		// TODO Auto-generated method stub
		return null;
	}


	

	/*
	 * @Override public ApiResponse getAllByvendorId(String vendorID) {
	 * 
	 * ApiResponse response = new ApiResponse(false);
	 * 
	 * // if (employeeId != null) {
	 * 
	 * List<Map> vendorList; try { vendorList =
	 * AssetVendorRepository.getAllByvendorId(Vendor.getVendorID()); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); } if
	 * (vendorList != null && vendorList.size() > 0) { Map content = new HashMap();
	 * content.put("vendorList", vendorList); response.setSuccess(true);
	 * response.setContent(content);
	 * response.setMessage(ResponseMessages.vendorList); } else {
	 * response.setSuccess(false);
	 * response.setMessage(ResponseMessages.PROJECT_NOT_ASSIGNED); Map content = new
	 * HashMap(); content.put("vendorList", vendorList);
	 * response.setContent(content);
	 * 
	 * }
	 * 
	 * return response;
	 * 
	 * 
	 * 
	 * //return null; }
	 */



	private ApiResponse validateVendorEmail(AssetVendor employee) {
		ApiResponse response = new ApiResponse(false);
		String email = assetVendorRepository.filterByEmail(employee.getEmail());
		if (!emailValidation(employee.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);

			response.setSuccess(false);
		}

		else if (employee.getMobileNo().length() != 10) {
			response.setMessage(ResponseMessages.MOBILE_INVALID);

			response.setSuccess(false);
		}

		else if (email != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists!!!");
		}

		else {
			response.setMessage(ResponseMessages.EMPLOYEE_ADDED);

			response.setSuccess(true);
		}

		return response;
	}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
}

	
	

	
	

	


