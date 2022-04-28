package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.AssetEmployeeStatus;
import com.xyram.ticketingTool.enumType.ModuleStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.AssetEmployeeService;
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
	
	@Autowired 
	CurrentUser currentUser;

	@Override
	public ApiResponse addAssetEmployee(AssetEmployee assetEmployee) {
		ApiResponse response = new ApiResponse(false);
		response = validateAssetEmployee(assetEmployee);
		if (response.isSuccess()) {
			if (assetEmployee != null) {
				assetEmployee.setCreatedAt(new Date());
				assetEmployee.setCreatedBy(currentUser.getUserId());
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
			Asset asset = assetRepository.getAssetById(assetEmployee.getAssetId());
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
			AssetEmployee assetEmployeeObj = assetEmployeeRepository.getEmpById(assetEmployee.getEmpId());
			AssetEmployee assetEmpObj = assetEmployeeRepository.getAssetById(assetEmployee.getAssetId());
			if (employee == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee id is not valid");
			}
			else if(assetEmployeeObj != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asset is already assigned to that Employee");
			}
			else if (assetEmpObj != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Asset is already assigned to other Employee");
			}
		}
		
		// issued date Validating
		if (assetEmployee.getIssuedDate() == null || assetEmployee.getIssuedDate().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "issued date is mandatory");
		}
		else {
			Date d1 = assetEmployee.getIssuedDate();
			Date d2 = new Date();
			if(d1.after(d2)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issued date should be less than or equal to current Date");
			}
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
	public ApiResponse editAssetEmployee(AssetEmployee assetEmployee, String assetEmpId) {
	
		ApiResponse response = new ApiResponse();
		
		AssetEmployee assetObj1 = assetEmployeeRepository.getByAssetEmpId(assetEmpId);			
		if (assetObj1 != null) {
			
			if(assetEmployee.getEmpId() != null) {
		    	checkEmpId(assetEmployee.getEmpId());
		    	assetObj1.setEmpId(assetEmployee.getEmpId());
		    }
			if(assetEmployee.getIssuedDate() != null) {
				checkIssuedDate(assetEmployee.getIssuedDate());
				assetObj1.setIssuedDate(assetEmployee.getIssuedDate());
		    }
			assetObj1.setAssetEmployeeStatus(assetEmployee.getAssetEmployeeStatus());
			assetObj1.setBagIssued(assetEmployee.isBagIssued());
			assetObj1.setMouseIssued(assetEmployee.isMouseIssued());
        	assetObj1.setPowercordIssued(assetEmployee.isPowercordIssued());
        	
        	assetObj1.setLastUpdatedAt(new Date());
        	assetObj1.setUpdatedBy(currentUser.getUserId());
			
            assetEmployeeRepository.save(assetObj1);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ASSET_EMPLOYEE_EDITED);
		} 
		else {
			response.setSuccess(false);
			//response.setMessage(ResponseMessages.ASSET_NOT_EDITED);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "assetEmployee id is not valid");
		}
		return response;
	}

	private boolean checkIssuedDate(Date issuedDate) {
		Date d1 = issuedDate;
		Date d2 = new Date();
		if(d1.after(d2)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issued date should be less than or equal to current Date");
		}
		else {
			return true;
		}	
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
	public ApiResponse updateAssetEmployee(AssetEmployee assetEmployee, String assetEmpId) {
		ApiResponse response = new ApiResponse();
		AssetEmployee empObj = assetEmployeeRepository.getByAssetEmpId(assetEmpId);
		if(empObj != null) {
			if(assetEmployee.getReturnDate() != null) {
				checkReturnDate(assetEmployee.getReturnDate(), assetEmpId);
				empObj.setReturnDate(assetEmployee.getReturnDate());
			}
			if(assetEmployee.getReturnType() != null) {
			  empObj.setReturnType(assetEmployee.getReturnType());
			}
			if(assetEmployee.getReturnReason() != null) {
				empObj.setReturnReason(assetEmployee.getReturnReason());
			}
			
		    empObj.setLastUpdatedAt(new Date());
        	empObj.setUpdatedBy(currentUser.getUserId());
        	empObj.setAssetEmployeeStatus(ModuleStatus.INACTIVE);
			assetEmployeeRepository.save(empObj);
			response.setSuccess(true);
			response.setMessage("Asset Employee updated Successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("Invalid AssetEmp Id");
		}
		return response;
	}

	private boolean checkReturnDate(Date returnDate, String assetEmpId) {
		Date assetEmployee = assetEmployeeRepository.getIssuedDateById(assetEmpId);
		Date d1 = assetEmployee;
		Date d2 = returnDate;
		Date d3 = new Date();
		if (d1.after(d2) || d1.equals(d2)) 
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date should be greater than issued date");
		}
		if(d2.after(d3)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "return date should be less than or equal to current date");
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
