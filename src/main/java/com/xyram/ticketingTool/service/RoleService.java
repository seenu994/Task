package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Role;

public interface RoleService {
 ApiResponse getAllRole(Pageable pageable) ;

Integer getAllRolePermission(String roleName);
		

}
