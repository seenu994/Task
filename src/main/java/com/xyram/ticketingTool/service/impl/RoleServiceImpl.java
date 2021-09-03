package com.xyram.ticketingTool.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.RoleService;

/**
 * 
 * @author sahana.neelappa
 *
 */
@Service
public class RoleServiceImpl implements RoleService {


	@Autowired
	RoleRepository roleRepository;

	@Override
	public  Page<Role> getAllRole(Pageable pageable) {

		return roleRepository.findAll(pageable);
	}

	
}