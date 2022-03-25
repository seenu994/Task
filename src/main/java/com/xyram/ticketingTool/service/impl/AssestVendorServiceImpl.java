package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.AssetVendorRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.service.AssetvendorService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssestVendorServiceImpl implements AssetvendorService {
	/*private final Logger logger = LoggerFactory.getLogger(AssestVendorServiceImpl.class);

	@Autowired
	AssetVendorRepository assetVendorRepository;

	@Override
	public ApiResponse addAssestVendor(AssetVendor vendor) {
		ApiResponse response = new ApiResponse(false);
		
		
       AssetVendor vendorSave=assetVendorRepository.save(vendor);
       if(vendorSave!=null)
       {
    	   Map content = new HashMap<>();
			content.put("vendorDetails", vendorSave);			
			response.setSuccess(true);
			response.setContent(content);
			return response;  

       }
	return response;
	
	}*/

	

}
