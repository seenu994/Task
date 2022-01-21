package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.EmployeePositionType;

public interface EmployeePositionTypeService {

	EmployeePositionType CreateEmployeePositionType(EmployeePositionType employeePositionTypRequest);

	EmployeePositionType updateEmployeePositionType(String EmployeePositionTypeId,
			EmployeePositionType employeePositionTypRequest);

	String deleteEmployeePositionTypeByid(String id);

	List<EmployeePositionType> getAllEmployeePosition(Map<String, Object> filter);

}
