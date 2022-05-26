package com.xyram.ticketingTool.ticket.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyram.ticketingTool.Repository.EmployeePermissionRepository;
import com.xyram.ticketingTool.entity.EmployeePermission;
import com.xyram.ticketingTool.request.CurrentUser;

@Component
public class EmployeePermissionConfig {

	@Autowired
	CurrentUser currentUser;
	
	@Autowired
	EmployeePermissionRepository empPermissionRepo;
	
	@SuppressWarnings("unchecked")
	public boolean isHavingpersmission(String permission) throws Exception, IllegalAccessException, NoSuchFieldException, SecurityException {
		EmployeePermission ep = empPermissionRepo.getbyUserId(currentUser.getUserId());
		
		ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(ep, Map.class);
        if(map.containsKey(permission)) {
        	return (boolean) map.get(permission);
        }else {
        	return false;
        }
	
	}
}
