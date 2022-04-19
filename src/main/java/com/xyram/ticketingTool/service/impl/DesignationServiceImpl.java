package com.xyram.ticketingTool.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.DesignationRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.DesiggnaionService;

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
}