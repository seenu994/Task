package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.EmployeePositionType;
import com.xyram.ticketingTool.service.EmployeePositionTypeService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class EmployeePositionTypeController {

	@Autowired
	EmployeePositionTypeService employeePositionTypeService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeePositionTypeController.class);
	
	 @PostMapping(value = { AuthConstants.ADMIN_BASEPATH +
			  "/createEmployeePositionType"
			 ,AuthConstants.HR_ADMIN_BASEPATH +  "/createEmployeePositionType", 
			  AuthConstants.INFRA_ADMIN_BASEPATH + "/createEmployeePositionType",
			  AuthConstants.INFRA_USER_BASEPATH +"/createEmployeePositionType",
			  AuthConstants.HR_BASEPATH +"/createEmployeePositionType", 
			  AuthConstants.DEVELOPER_BASEPATH +"/createEmployeePositionType"})
	public EmployeePositionType createEmployeePositionType(EmployeePositionType employeePositionType)
	{
		return employeePositionTypeService .CreateEmployeePositionType(employeePositionType);
	}
	
	

	 @PutMapping(value = { AuthConstants.ADMIN_BASEPATH +
			  "/updateEmployeePositionType/{employeePositionTypeId}"
			 ,AuthConstants.HR_ADMIN_BASEPATH +  "/updateEmployeePositionType/{employeePositionTypeId}", 
			  AuthConstants.INFRA_ADMIN_BASEPATH + "/updateEmployeePositionType/{employeePositionTypeId}",
			  AuthConstants.INFRA_USER_BASEPATH +"/updateEmployeePositionType/{employeePositionTypeId}",
			  AuthConstants.HR_BASEPATH +"/updateEmployeePositionType/{employeePositionTypeId}", 
			  AuthConstants.DEVELOPER_BASEPATH +"/updateEmployeePositionType/{employeePositionTypeId}"})
	public EmployeePositionType updateEmployeePositionType(String employeePositionTypeId ,EmployeePositionType employeePositionType)
	{
		return employeePositionTypeService.updateEmployeePositionType(employeePositionTypeId, employeePositionType);
	}

	 

	 @PutMapping(value = { AuthConstants.ADMIN_BASEPATH +
			  "/getAllEmployeePositionType"
			 ,AuthConstants.HR_ADMIN_BASEPATH +  "/getAllEmployeePositionType", 
			  AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllEmployeePositionType",
			  AuthConstants.INFRA_USER_BASEPATH +"/getAllEmployeePositionType",
			  AuthConstants.HR_BASEPATH +"/getAllEmployeePositionType", 
			  AuthConstants.DEVELOPER_BASEPATH +"/getAllEmployeePositionType"})
	public List<EmployeePositionType> GetAllEmployeePositionType(@RequestParam  Map<String ,Object> filter)
	{
		return employeePositionTypeService.getAllEmployeePosition(filter);
	}

	
}
