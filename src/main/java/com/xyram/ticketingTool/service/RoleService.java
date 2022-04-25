package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Role;

public interface RoleService {
	
	ApiResponse addRole(Role role);
	ApiResponse editRoleById(Role Request, String Id);
	
	
 ApiResponse getAllRole() ;

Integer getAllRolePermission(String roleName);
		

}
