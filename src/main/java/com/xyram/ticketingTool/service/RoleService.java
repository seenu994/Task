package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;

public interface RoleService {
	
 ApiResponse getAllRole() ;

Integer getAllRolePermission(String roleName);
		

}
