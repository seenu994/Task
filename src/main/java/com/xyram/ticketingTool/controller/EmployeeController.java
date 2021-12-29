
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.Repository.RoleMasterRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.RoleMasterTable;
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
	
	@Autowired
	RoleMasterRepository masterrepo;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createEmployee",AuthConstants.HR_ADMIN_BASEPATH + "/createEmployee",AuthConstants.INFRA_ADMIN_BASEPATH + "/createEmployee" })
	public ApiResponse addemployee(@RequestBody Employee employee) {
		logger.info("Received request to add Employee");
		return employeeService.addemployee(employee);
	}

	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/createJobVendor",AuthConstants.ADMIN_BASEPATH + "/createJobVendor" })
	public ApiResponse createJobVendor(@RequestBody JobVendorDetails vendorDetails) {
		logger.info("Received request to add Employee");
		return employeeService.createJobVendor(vendorDetails);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getJobVendor",AuthConstants.ADMIN_BASEPATH + "/getJobVendor" })
	public ApiResponse getJobVendor() {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendor();
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getJobVendorType",AuthConstants.ADMIN_BASEPATH + "/getJobVendorType" })
	public ApiResponse getJobVendorType() {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendorType();
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getJobVendor/{vendorId}",AuthConstants.ADMIN_BASEPATH + "/getJobVendor/{vendorId}" })
	public ApiResponse getJobVendorById(@PathVariable String vendorId) {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendorById(vendorId);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmployee",AuthConstants.HR_ADMIN_BASEPATH+ "/getAllEmployee",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllEmployee",AuthConstants.DEVELOPER_BASEPATH + "/getAllEmployee",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllEmployee" })
	public ApiResponse getAllEmployee(Pageable pageable) {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployee(pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmployee/currentMonth",AuthConstants.HR_ADMIN_BASEPATH+ "/getAllEmployee/currentMonth",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllEmployee/currentMonth",AuthConstants.DEVELOPER_BASEPATH + "/getAllEmployee/currentMonth",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllEmployee/currentMonth" })
	public ApiResponse getAllEmployeeCurrentMonth(Pageable pageable) {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployeeCurrentMonth(pageable);
	}

	@PutMapping("/profile/image/{employeeId}")
	public ApiResponse updateProfileImage(@RequestPart(name = "file", required = true) MultipartFile file,@PathVariable String userId) {
		logger.info("Received request for update doctor profile");
		return employeeService.updateProfileImage(file,userId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editEmployee/{employeeId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/editEmployee/{employeeId}",
			AuthConstants.DEVELOPER_BASEPATH + "/editEmployee/{employeeId}",AuthConstants.HR_ADMIN_BASEPATH + "/editEmployee/{employeeId}"})
	public ApiResponse editEmployee(@RequestBody Employee employeeRequest, @PathVariable String employeeId) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.editEmployee(employeeId, employeeRequest);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getEmployee/{employeeId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getEmployee/{employeeId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getEmployee/{employeeId}" })
	public ApiResponse getEmployeeDetails(@PathVariable String employeeId) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.getEmployeeDetails(employeeId);
	}
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getEmployeeDetails/{employeeId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getEmployeeDetails/{employeeId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getEmployeeDetails/{employeeId}" ,AuthConstants.INFRA_USER_BASEPATH + "/getEmployeeDetails/{employeeId}",AuthConstants.HR_ADMIN_BASEPATH + "/getEmployeeDetails/{employeeId}",AuthConstants.HR_BASEPATH + "/getEmployeeDetails/{employeeId}",})
	public ApiResponse getEmployeeDetailsById(@PathVariable String employeeId) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.getEmployeeDetailsById(employeeId);
	}


	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateEmployeeStatus/{employeeID}/status/{userstatus}",AuthConstants.INFRA_ADMIN_BASEPATH + "/updateEmployeeStatus/{employeeID}/status/{userstatus}" })
	public ApiResponse updateEmployeeStatus(@PathVariable String employeeID, @PathVariable UserStatus userstatus) {
		logger.info("Received request to change category status to: " + userstatus + "for employeeId: " + employeeID);
		return employeeService.updateEmployeeStatus(employeeID, userstatus);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}",AuthConstants.HR_ADMIN_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}",AuthConstants.DEVELOPER_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}",AuthConstants.INFRA_USER_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllEmpByProject/{projectid}/clientId/{clientid}" })
	public ApiResponse getAllEmpByProject(@PathVariable String projectid, @PathVariable String clientid) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.getAllEmpByProject(projectid, clientid);
	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchEmployeeNotAssignedToProject/{projectid}/clientId/{clientid}/searchString/{searchString}", AuthConstants.INFRA_ADMIN_BASEPATH + "/searchEmployeeNotAssignedToProject/{projectid}/clientId/{clientid}/searchString/{searchString}" })
	public ApiResponse searchEmployeeNotAssignedToProject(@PathVariable String projectid, @PathVariable String clientid, @PathVariable String searchString) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.searchEmployeeNotAssignedToProject(projectid, clientid, searchString);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchInfraUser/{searchString}",
			AuthConstants.INFRA_USER_BASEPATH + "/searchInfraUser/{searchString}",AuthConstants.INFRA_ADMIN_BASEPATH + "/searchInfraUser/{searchString}" })
	public ApiResponse searchInfraUser(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchInfraUser ");
		return employeeService.searchInfraUser(searchString);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchEmployee/{searchString}",
			AuthConstants.INFRA_USER_BASEPATH + "/searchEmployee/{searchString}",
			AuthConstants.DEVELOPER_BASEPATH + "/searchEmployee/{searchString}",AuthConstants.INFRA_ADMIN_BASEPATH + "/searchEmployee/{searchString}",AuthConstants.HR_ADMIN_BASEPATH + "/searchEmployee/{searchString}"})
	public ApiResponse searchEmployee(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchEmployee ");
		return employeeService.searchEmployee(searchString);
	}
	
	@GetMapping(value = { AuthConstants.INFRA_USER_BASEPATH + "/searchInfraUsersForInfraUser/{searchString}",AuthConstants.INFRA_ADMIN_BASEPATH + "/searchInfraUsersForInfraUser/{searchString}"  })
	public ApiResponse searchInfraUsersForInfraUser(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchInfraUsersForInfraUser ");
		return employeeService.searchInfraUsersForInfraUser(searchString);
	}
 
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllInfraUser",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllInfraUser",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllInfraUser" })
	public ApiResponse getAllInfraUser() {
		logger.info("indside CatagoryController :: getAllInfraUser");
		return employeeService.getAllInfraUser();
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllPermissions",AuthConstants.HR_ADMIN_BASEPATH + "/getAllPermissions",AuthConstants.HR_BASEPATH + "/getAllPermissions",AuthConstants.DEVELOPER_BASEPATH + "/getAllPermissions",AuthConstants.JOB_VENDOR_BASEPATH + "/getAllPermissions",AuthConstants.INFRA_USER_BASEPATH + "/getAllPermissions" })
	public ApiResponse getAllPermissions() {
		logger.info("indside :: getAllPermissions");
		return employeeService.getAllPermissions();
	}
	
	@PutMapping(value = { AuthConstants.INFRA_USER_BASEPATH +"/updateProfile",AuthConstants.DEVELOPER_BASEPATH +"/updateProfile",AuthConstants.INFRA_ADMIN_BASEPATH +"/updateProfile"})
	public ApiResponse editEmployee(@RequestBody Map employeeRequest) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.updateEmployee( employeeRequest);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getEmployeeByAccessToken",AuthConstants.ADMIN_BASEPATH + "/getEmployeeByAccessToken",AuthConstants.DEVELOPER_BASEPATH + "/getEmployeeByAccessToken",AuthConstants.HR_BASEPATH + "/getEmployeeByAccessToken",AuthConstants.INFRA_ADMIN_BASEPATH + "/getEmployeeByAccessToken",AuthConstants.INFRA_USER_BASEPATH + "/getEmployeeByAccessToken",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getEmployeeByAccessToken"})
	public ApiResponse getListByAccessToken() {
		logger.info("Received request to add Employee");
		return employeeService.getListByAccessToken();
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editJobVendor/{vendorId}",AuthConstants.HR_ADMIN_BASEPATH + "/editJobVendor/{vendorId}" })
	public ApiResponse editJobVendor(@RequestBody JobVendorDetails vendorRequest, @PathVariable String vendorId) {
		logger.info("indide ProductController :: getAllemployee");
		return employeeService.editJobVendor(vendorId, vendorRequest);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getEmployeeByReportingId/{reportingId}",AuthConstants.ADMIN_BASEPATH + "/getEmployeeByReportingId/{reportingId}",AuthConstants.DEVELOPER_BASEPATH + "/getEmployeeByReportingId/{reportingId}",AuthConstants.HR_BASEPATH + "/getEmployeeByReportingId/{reportingId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getEmployeeByReportingId/{reportingId}",AuthConstants.INFRA_USER_BASEPATH + "/getEmployeeByReportingId/{reportingId}",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getEmployeeByReportingId/{reportingId}"})
	public ApiResponse getEmployeeByReportingId(@PathVariable String reportingId) {
		logger.info("Received request to add Employee");
		return employeeService.getEmployeeByReportingId(reportingId);
	}
	

	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getInfraEmployee",AuthConstants.ADMIN_BASEPATH + "/getInfraEmployee",AuthConstants.DEVELOPER_BASEPATH + "/getInfraEmployee",AuthConstants.HR_BASEPATH + "/getInfraEmployee",AuthConstants.INFRA_ADMIN_BASEPATH + "/getInfraEmployee",AuthConstants.INFRA_USER_BASEPATH + "/getInfraEmployee",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getInfraEmployee}"})
	public ApiResponse getInfraEmployee() {
		logger.info("Received request to add Employee");
		return employeeService.getInfraEmployee();
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllRolePermissions/{roleId}"})
	public ApiResponse getAllRolePermissions(@PathVariable String roleId) {
		logger.info("Received request to add Employee");
		return employeeService.getAllRolePermissions(roleId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateRolePermissions"})
	public ApiResponse updateRolePermissions(@RequestParam String roleId,@RequestParam String modules,@RequestBody RoleMasterTable request) {
		logger.info("Received request to add Employee");
		return employeeService.updateRolePermissions(roleId,modules,request);
	}
//	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateRolePermissions"})
//	public ApiResponse updateRolePermissions(@RequestBody RoleMasterTable request) {
//		logger.info("Received request to add Employee");
//		return employeeService.updateRolePermissions(null,null,request);
//	}
	
	@PutMapping(value = { AuthConstants.INFRA_USER_BASEPATH +"/updateOfflineStatus/{infraUserId}",AuthConstants.INFRA_ADMIN_BASEPATH +"/updateOfflineStatus/{infraUserId}"})
	public ApiResponse updateOfflineStatus(@PathVariable String infraUserId) {
		return employeeService.updateOfflineStatus(infraUserId);
	}
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllEmployeeList",AuthConstants.HR_ADMIN_BASEPATH+ "/getAllEmployeeList",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllEmployeeList",AuthConstants.DEVELOPER_BASEPATH + "/getAllEmployeeList",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllEmployee" })
	public ApiResponse getAllEmployeeList() {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployeeList();
	}
	
}
