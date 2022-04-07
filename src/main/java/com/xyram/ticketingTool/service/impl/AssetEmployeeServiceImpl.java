package com.xyram.ticketingTool.service.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.AssetEmployeeRepository;
import com.xyram.ticketingTool.Repository.AssetRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Asset;
import com.xyram.ticketingTool.entity.AssetEmployee;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.service.AssetEmployeeService;
import com.xyram.ticketingTool.service.AssetService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class AssetEmployeeServiceImpl implements AssetEmployeeService{

	@Autowired
	AssetEmployeeRepository assetEmployeeRepository;

	@Autowired
	AssetEmployeeService assetEmployeeService;
	
	@Autowired
	AssetRepository assetRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	
	
	
	@Override
	public ApiResponse addAssetEmployee(AssetEmployee assetEmployee) {
		ApiResponse response = new ApiResponse(false);
		response = validateAssetEmployee(assetEmployee);
		if (response.isSuccess()) {
			if (assetEmployee != null) {
				assetEmployeeRepository.save(assetEmployee);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_EMPLOYEE_ADDED);
			}

				else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ASSET_EMPLOYEE_NOT_ADDED);
			}
		}

		return response;
	}

	private ApiResponse validateAssetEmployee(AssetEmployee assetEmployee) {
		
		ApiResponse response = new ApiResponse(false);
		
//		private String assetId;
//		private String empId;
//		private Date issuedDate;
//		private boolean bagIssued;
//		private boolean powercordIssued;
//		private boolean mouseIssued;
//		private Date returnDate;
//		private String returnReason;
//		private String returnType;
//		private String assetEmployeeStatus;
		
		// Validate assetId
		if (assetEmployee.getAssetId() == null || assetEmployee.getAssetId().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is mandatory");	
		} 
		else {
		    // checking asset id in asset table
			Asset asset = assetRepository.getByassetId(assetEmployee.getAssetId());
			if (asset == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
			}
		}
		
		// Validate employeeID
		if (assetEmployee.getEmpId() == null || assetEmployee.getEmpId().equals("") ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is mandatory");	
		} 
		else {
			//checking emp_id in employee table
			Employee employee = employeeRepository.getByEmpId(assetEmployee.getEmpId());
			if (employee == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
			}
		}
		
		// issued date Validating
		if (assetEmployee.getIssuedDate() == null || assetEmployee.getIssuedDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issued date is mandatory");
		}
		
		// return date Validating
		if (assetEmployee.getReturnDate() != null) {
			
			Date d1 = assetEmployee.getIssuedDate();
			Date d2 = assetEmployee.getReturnDate();
			if (d1.after(d2) || d1.equals(d2)) 
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date should be greater than issued date");
			}
		 }
		
		// return type Validating
		if (assetEmployee.getReturnType() == null || assetEmployee.getReturnType().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return type is mandatory");
		}

		
		// assetEmployeestatus Validating
		if (assetEmployee.getAssetEmployeeStatus() == null || assetEmployee.getAssetEmployeeStatus().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is mandatory");
		}
		
		response.setSuccess(true);
		return response;
	}
	
	@Override
	public ApiResponse editAssetEmployee(AssetEmployee assetEmployee, String assetId) {
	
		ApiResponse response = new ApiResponse(false);
		
		response = validateAssetEmployee(assetEmployee);
		
		AssetEmployee assetObj1 = assetEmployeeRepository.getByassetId(assetId);
		
		//AssetEmployee empObj = assetEmployeeRepository.getByempId(assetEmployee.getEmpId());
		
		if (response.isSuccess()) {
			
		if (assetObj1 != null)  //|| empObj != null) 
			{
				
	            assetEmployeeRepository.save(assetEmployee);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ASSET_EMPLOYEE_EDITED);
		} 
		else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
		}
		}
		return response;
	}

}
