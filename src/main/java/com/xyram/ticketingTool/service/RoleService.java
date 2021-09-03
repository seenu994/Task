package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Role;

public interface RoleService {
 Page<Role> getAllRole(Pageable pageable) ;
		

}
