package com.xyram.ticketingTool.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.service.RoleService;
import com.xyram.ticketingTool.ticket.config.PermissionConfig;
import com.xyram.ticketingTool.util.ResponseMessages;

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



	@Override
	public ApiResponse addRole(Role role) {
		
		ApiResponse response = new ApiResponse(false);
		
		if (role.getRoleName() != null) {
			
			if (role.getRoleName().equals("") || role.getRoleName() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is manditory");
			}
			
		 Role roles = roleRepository.save(role);
			response.setMessage(ResponseMessages.ADDED_DESIGNATION);
			response.setSuccess(true);

			Map content = new HashMap();
			//content.put("designationId", role.getId()I);
			content.put("designationName",role.getRoleName());
			response.setContent(content);
		}
		
		return response;
	}
}