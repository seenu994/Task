package com.xyram.ticketingTool.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Role;

public interface DesiggnaionService {

	 Page<Designation> getAllgetAllDesignationRole(Pageable pageable); 
	}


