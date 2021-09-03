package com.xyram.ticketingTool.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.DesignationRepository;
import com.xyram.ticketingTool.entity.Designation;
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
	public  Page<Designation> getAllgetAllDesignationRole(Pageable pageable) {

		return designationRepository.findAll(pageable);
	}

}