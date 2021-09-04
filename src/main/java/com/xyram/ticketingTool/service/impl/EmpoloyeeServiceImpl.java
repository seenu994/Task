
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */

@Service
@Transactional
public class EmpoloyeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public ApiResponse addemployee(Employee employee) {

		ApiResponse response = validateEmployee(employee);
		if (response.isSuccess()) {
			User user = new User();
			user.setUsername(employee.getEmail());
			String encodedPassword = new BCryptPasswordEncoder().encode(employee.getPassword());
			user.setPassword(encodedPassword);
			// Employee employeere=new Employee();
			if (employee.getRole() != null && employee.getRole().getId() == 2) {
				// if(user.getUserRole().equals("INFRA")) {
				user.setUserRole(UserRole.INFRA);
			} else if (employee.getRole() != null && employee.getRole().getId() == 3) {
				user.setUserRole(UserRole.DEVELOPER);
			} else {
				throw new ResourceNotFoundException("invalid user role ");
			}
			userRepository.save(user);
			Employee employeeNew = employeeRepository.save(employee);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.EMPLOYEE_ADDED);
			Map content = new HashMap();
			content.put("employeeId", employeeNew.geteId());
			response.setContent(content);
			return response;

		}

		return response;
	}

	private ApiResponse validateEmployee(Employee employee) {
		ApiResponse response = new ApiResponse(false);
		if (!emailValidation(employee.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);
			;
			response.setSuccess(false);
		}

		else if (employee.getMobileNumber().length() != 10) {
			response.setMessage(ResponseMessages.MOBILE_INVALID);
			;
			response.setSuccess(false);
		}

		else if (employee.getRole() != null && employee.getRole().getId() != null) {
			Optional<Role> role = roleRepository.findById(employee.getRole().getId());
			if (role == null) {
				response.setMessage(ResponseMessages.ROLE_INVALID);
				response.setSuccess(false);
			}

		}

		else {
			response.setMessage(ResponseMessages.EMPLOYEE_ADDED);
			response.setSuccess(true);
			response.setContent(null);
		}

		return response;
	}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	@Override
	public ApiResponse getAllEmployee(Pageable pageable) {
       Page<Map> employeeList =   employeeRepository.getAllEmployeeList(pageable);
       ApiResponse response = new ApiResponse(true);
       response.setSuccess(true);
       response.setContent((Map)employeeList);
       return  response;
	}

	@Override
	public ApiResponse updateEmployeeStatus(int employeeID, UserStatus userstatus) {
		ApiResponse response = validateStatus(userstatus);
		if (response.isSuccess()) {
			Employee employee = employeeRepository.getById(employeeID);
			if (employee != null) {
				employee.setStatus(userstatus);
				employeeRepository.save(employee);
				// Employee employeere=new Employee();

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);
			}

		}
		return response;
	}

	private ApiResponse validateStatus(UserStatus userstatus) {
		ApiResponse response = new ApiResponse(false);
		if (userstatus != UserStatus.ACTIVE || userstatus != UserStatus.INACTIVE) {
			response.setMessage(ResponseMessages.USERSTATUS_INVALID);
			response.setSuccess(false);
		}

		else {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}

		return response;
	}

	@Override
	public ApiResponse editEmployee(Integer employeeId, Employee employeeRequest) {
		ApiResponse response = validateEmployee(employeeRequest);
		if (response.isSuccess()) {
			Employee employee = employeeRepository.getById(employeeId);
			if (employee != null) {
				employee.setFirstName(employeeRequest.getFirstName());
				employee.setLastName(employeeRequest.getLastName());
				employee.setLastUpdatedAt(new Date());
				employee.setMiddleName(employeeRequest.getMiddleName());
				employee.setMobileNumber(employeeRequest.getMobileNumber());
				employeeRepository.save(employee);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.EMPLOYEE_UPDATION);
				response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);
			}

		}

		return response;
	}

}