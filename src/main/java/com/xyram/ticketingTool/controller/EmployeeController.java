
package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.util.AuthConstants;

import ch.qos.logback.core.pattern.color.ANSIConstants;

/**
 * 
 * @author sahana.neelappa
 *
 */
@RestController
@CrossOrigin
class EmployeeController {
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	UserRepository userRepository;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createEmployee" })
	public ApiResponse addemployee(@RequestBody Employee employee) {
		logger.info("Received request to add Employee");
		return employeeService.addemployee(employee);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmployee",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllEmployee" })
	public ApiResponse getAllEmployee(Pageable pageable) {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployee(pageable);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editEmployee/{employeeId}",
			AuthConstants.DEVELOPER_BASEPATH + "/editEmployee/{employeeId}" })
	public ApiResponse editEmployee(@RequestBody Employee employeeRequest, @PathVariable String employeeId) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.editEmployee(employeeId, employeeRequest);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateEmployeeStatus/{employeeID}/status/{userstatus}" })
	public ApiResponse updateEmployeeStatus(@PathVariable String employeeID, @PathVariable UserStatus userstatus) {
		logger.info("Received request to change category status to: " + userstatus + "for employeeId: " + employeeID);
		return employeeService.updateEmployeeStatus(employeeID, userstatus);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}" })
	public ApiResponse getAllEmpByProject(@PathVariable String projectid, @PathVariable String clientid) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.getAllEmpByProject(projectid, clientid);
	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchEmployeeNotAssignedToProject/{projectid}/clientId/{clientid}/searchString/{searchString}" })
	public ApiResponse searchEmployeeNotAssignedToProject(@PathVariable String projectid, @PathVariable String clientid, @PathVariable String searchString) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.searchEmployeeNotAssignedToProject(projectid, clientid, searchString);
	}
 
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllInfraUser",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllInfraUser" })
	public ApiResponse getAllInfraUser() {
		logger.info("indide CatagoryController :: getAllInfraUser");
		return employeeService.getAllInfraUser();
	}
}
