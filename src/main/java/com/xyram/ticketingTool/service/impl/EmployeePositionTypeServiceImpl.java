package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeePositionTypeRepository;
import com.xyram.ticketingTool.entity.EmployeePositionType;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.EmployeePositionTypeService;

@Service
@Transactional
public class EmployeePositionTypeServiceImpl  implements EmployeePositionTypeService{

	
	
	@Autowired
	EmployeePositionTypeRepository employeePositionTypeRepository;

	@Autowired
	CurrentUser currentUser;

	@Override
	public EmployeePositionType CreateEmployeePositionType(EmployeePositionType employeePositionTypRequest) {

		if (employeePositionTypRequest.getPositionType() !=null) {
			employeePositionTypRequest.setPositionStatus("ACTIVE");
			return employeePositionTypeRepository.save(employeePositionTypRequest);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Position Type   is mandatory ");
		}

	}

	@Override
	public EmployeePositionType updateEmployeePositionType(String EmployeePositionTypeId, EmployeePositionType employeePositionTypRequest) {

		return employeePositionTypeRepository.findById(EmployeePositionTypeId).map(EmployeePositionType -> {

			EmployeePositionType.setPositionType(employeePositionTypRequest.getPositionType());

			return employeePositionTypeRepository.save(EmployeePositionType);

		}).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Position not found with id " + EmployeePositionTypeId));
	}

	
	@Override
	public String deleteEmployeePositionTypeByid(String id) {
		String message = null;
		if (id != null) {
			employeePositionTypeRepository.deleteById(id);
			message = "Deleted Successfully";
			return message;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Position  not  found with id "+id);
		}

	}

	@Override
public List<EmployeePositionType> getAllEmployeePosition(Map<String,Object> filter) {
	
	String searchString=filter.containsKey("searchString") ?((String)( filter.get("searchString"))).toLowerCase():null;
	
return 	employeePositionTypeRepository.getAllEmployeePosition(searchString );
}
}
