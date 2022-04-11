package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		
//		// return date Validating
//		if (assetEmployee.getReturnDate() != null) {
//			
//			Date d1 = assetEmployee.getIssuedDate();
//			Date d2 = assetEmployee.getReturnDate();
//			if (d1.after(d2) || d1.equals(d2)) 
//			{
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date should be greater than issued date");
//			}
//		 }
//		
//		// return type Validating
//		if (assetEmployee.getReturnType() == null || assetEmployee.getReturnType().equals("")) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return type is mandatory");
//		}

		
		// assetEmployeestatus Validating
		if (assetEmployee.getAssetEmployeeStatus() == null || assetEmployee.getAssetEmployeeStatus().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is mandatory");
		}
		
		response.setSuccess(true);
		return response;
	}
	
	@Override
	public ApiResponse editAssetEmployee(AssetEmployee assetEmployee, String assetId) {
	
		ApiResponse response = new ApiResponse();
		
		AssetEmployee assetObj1 = assetEmployeeRepository.getByAssetId(assetId);			
		if (assetObj1 != null) {
			
			if(assetEmployee.getEmpId() != null) {
		    	checkEmpId(assetEmployee.getEmpId());
		    	assetObj1.setEmpId(assetEmployee.getEmpId());
		    }
			if(assetEmployee.getIssuedDate() != null) {
				assetObj1.setIssuedDate(new Date());
		    }
			assetObj1.setAssetEmployeeStatus(assetEmployee.getAssetEmployeeStatus());
			assetObj1.setBagIssued(assetEmployee.isBagIssued());
			assetObj1.setMouseIssued(assetEmployee.isMouseIssued());
        	assetObj1.setPowercordIssued(assetEmployee.isPowercordIssued());
			
            assetEmployeeRepository.save(assetObj1);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_EMPLOYEE_EDITED);
		} 
		else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset id is not valid");
		}
		return response;
	}

	private boolean checkEmpId(String empId) {
		Employee employee = employeeRepository.getByEmpId(empId);
		if (employee == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
		}
		else {
		 return true;
		}
	}

	@Override
	public ApiResponse updateAssetEmployee(AssetEmployee assetEmployee, String assetId) {
		ApiResponse response = new ApiResponse();
		AssetEmployee empObj = assetEmployeeRepository.getByAssetId(assetId);
		if(empObj != null) {
			if(assetEmployee.getReturnDate() != null) {
				checkReturnDate(assetEmployee.getReturnDate(), assetId);
				empObj.setReturnDate(new Date());
			}
			if(assetEmployee.getReturnType() != null) {
			  empObj.setReturnType(assetEmployee.getReturnType());
			}
			if(assetEmployee.getReturnReason() != null) {
				empObj.setReturnReason(assetEmployee.getReturnReason());
			}
			assetEmployeeRepository.save(empObj);
			response.setSuccess(true);
			response.setMessage("Asset Employee updated Successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("Invalid Asset Id");
		}
		return response;
	}

	private boolean checkReturnDate(Date returnDate, String assetId) {
		Date assetEmployee = assetEmployeeRepository.getIssuedDateById(assetId);
		Date d1 = assetEmployee;
		Date d2 = returnDate;
		if (d1.after(d2) || d1.equals(d2)) 
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date should be greater than issued date");
		}
		else {
		  return true;
		} 
	}

	@Override
	public ApiResponse getAssetEmployeeById(String assetId, Pageable pageable) {
		ApiResponse response = new ApiResponse();
		Page<Map> asset = assetEmployeeRepository.getAssetEmployeeById(assetId, pageable);
		Map content = new HashMap();
		content.put("asset", asset);
		if(content != null) {
			response.setSuccess(true);
			response.setMessage("Asset Employee Retrieved Successfully");
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
		}
		return response;
	}
	

}
