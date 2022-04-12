package com.xyram.ticketingTool.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.RoleService;
import com.xyram.ticketingTool.ticket.config.PermissionConfig;

/**
 * 
 * @author sahana.neelappa
 *
 */
@Service
public class RoleServiceImpl implements RoleService {


	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PermissionConfig permissionConfig;
	

	@Override
	public ApiResponse getAllRole() {
       List<Map> roleList =   roleRepository.getAllRoleList();
       Map content = new HashMap();
       content.put("roleList", roleList);
       ApiResponse response = new ApiResponse(true);
       response.setSuccess(true);
       response.setContent(content);
       return  response;
	}



	@Override
	public Integer getAllRolePermission(String roleName) {
		
		return permissionConfig.setDefaultPermissions(roleName);
	}
}