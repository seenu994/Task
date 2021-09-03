
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.UserService;

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
	UserService userService;
	
	private static Map<String, com.xyram.ticketingTool.admin.model.User> userCache = new HashMap<>();

	@Override
	public Employee addemployee(Employee employee) {

		User user = new User();
		user.setUsername(employee.getEmail());
		String encodedPassword = new BCryptPasswordEncoder().encode(employee.getPassword());
		user.setPassword(encodedPassword);
       user.setStatus(UserStatus.ACTIVE);
		if (employee.getRole() != null && employee.getRole().getId() == 2) {
			user.setUserRole(UserRole.INFRA);
		} else if (employee.getRole() != null && employee.getRole().getId() == 3) {
			user.setUserRole(UserRole.DEVELOPER);
		} else {
			throw new ResourceNotFoundException("invalid user role ");
		}
		employee.setUserCredientials(userRepository.save(user));

		return employeeRepository.save(employee);
	}

	@Override
	public Page<Employee> getAllEmployee(Pageable pageable) {

		return employeeRepository.findAll(pageable);
	}

	@Override
	public String updateEmployeeStatus(int employeeID, UserStatus userstatus) {
		return employeeRepository.findById(employeeID).map(employee -> {
			employee.setStatus(userstatus);
			User user = employee.getUserCredientials();
			User userDetails = userService.getUserByUsername(user.getUsername());
			 user.setStatus(userstatus);
			 userRepository.save(user);
			return "{\"response\":\"Success\"}";

		}).orElseThrow(() -> new ResourceNotFoundException("ticket not foung with id: " + employeeID));

//		/return employeeDao.updateEmployeeStatus(employeeID, userstatus);
	}

	@Override
	public Employee editEmployee(Integer employeeId, Employee employeeRequest) {
		// logger.info("Received request to update healthDevice for healthDeviceId: " +
		// healthDeviceId.toString());
		return employeeRepository.findById(employeeId).map(employee -> {
			// if(employeEditEmployee!=null) {
			// employee.setDesignation(employee.getDesignation());
			employee.setEmail(employeeRequest.getEmail());
			employee.setFirstName(employeeRequest.getFirstName());
			employee.setLastName(employeeRequest.getLastName());
			employee.setLastUpdatedAt(new Date());
			employee.setMiddleName(employeeRequest.getMiddleName());
			employee.setMobileNumber(employeeRequest.getMobileNumber());
			employee.setPassword(employeeRequest.getPassword());
			employee.setRole(employee.getRole());
			employee.setUpdatedBy(employeeRequest.getUpdatedBy());
			return employeeRepository.save(employee);
		}).orElseThrow(() -> new ResourceNotFoundException("healthDevice  not found with id: "));
	}

}