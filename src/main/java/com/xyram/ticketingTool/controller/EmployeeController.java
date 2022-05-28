
package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.Repository.EmployeePermissionRepository;
import com.xyram.ticketingTool.Repository.RoleMasterRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.EmployeePermission;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.RoleMasterTable;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.EmployeeService;

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

	@PostMapping("/createEmployee")
	public ApiResponse addemployee(@RequestBody Employee employee) throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.addemployee(employee);
	}

	@PostMapping("/changeEmployeeAllPermission")
	public ApiResponse changeEmployeeAllPermission(@RequestBody EmployeePermission employeePermission)
			throws Exception {
		logger.info("Changing Employee All Permission");
		return employeeService.changeEmployeeAllPermission(employeePermission);
	}

	@PostMapping("/changeEmployeePermissions/{userId}/{permission}/{flag}")
	public ApiResponse changeEmployeePermissions(@PathVariable String userId, @PathVariable String permission,
			@PathVariable boolean flag) throws Exception {
		logger.info("Changing Employee Permission");
		return employeeService.changeEmployeePermission(userId, permission, flag);
	}

	@GetMapping("/getEmployeePermissions/{userId}")
	public ApiResponse getEmployeePermission(@PathVariable String userId) throws Exception {
		logger.info("Get Employee Permission");
		return employeeService.getEmployeePermission(userId);
	}

	@PostMapping("/changeAllEmployeePermissionsToDefault")
	public ApiResponse changeAllEmployeePermissionsToDefault() throws Exception {
		logger.info("Changing All Employee Permission to default");
		return employeeService.changeAllEmployeePermissionsToDefault();
	}

	@PostMapping("/createJobVendor")
	public ApiResponse createJobVendor(@RequestBody JobVendorDetails vendorDetails) throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.createJobVendor(vendorDetails);
	}

	@GetMapping("/getJobVendor")
	public ApiResponse getJobVendor(@RequestParam Map<String, Object> filter, Pageable pageable) throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendor(filter, pageable);
	}

	@GetMapping("/searchJobVendors/{vendorName}")
	public ApiResponse searchJobVendors(@PathVariable String vendorName) throws Exception {
		logger.info("Received request to search job Vendor");
		return employeeService.serachJobVendor(vendorName);
	}

	@GetMapping("/getJobVendorType")
	public ApiResponse getJobVendorType() throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendorType();
	}

	@GetMapping("/getJobVendor/{vendorId}")
	public ApiResponse getJobVendorById(@PathVariable String vendorId) throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.getJobVendorById(vendorId);
	}

	@PostMapping("/getAllEmployee")
	public ApiResponse getAllEmployee(@RequestBody(required = false) Map<String, Object> filter, Pageable pageable)
			throws Exception {
		logger.info("inside getAllEmployee");
		return employeeService.getAllEmployee(filter, pageable);
	}

	@GetMapping("/getAllEmployee/currentMonth")
	public ApiResponse getAllEmployeeCurrentMonth(Pageable pageable) {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployeeCurrentMonth(pageable);
	}

	@PutMapping("/profile/image/{employeeId}")
	public ApiResponse updateProfileImage(@RequestPart(name = "file", required = true) MultipartFile file,
			@PathVariable String employeeId) {
		logger.info("Received request for update doctor profile");
		return employeeService.updateProfileImage(file, employeeId);
	}

	@PutMapping("/editEmployee/{employeeId}")
	public ApiResponse editEmployee(@RequestBody Employee employeeRequest, @PathVariable String employeeId)
			throws Exception {
		logger.info("indide edit employee");
		return employeeService.editEmployee(employeeId, employeeRequest);
	}

	@GetMapping("/getEmployee/{employeeId}")
	public ApiResponse getEmployeeDetails(@PathVariable String employeeId) {
		logger.info("indide getEmployeeDetails");
		return employeeService.getEmployeeDetails(employeeId);
	}

	@GetMapping("/getEmployeeDetails/{employeeId}")
	public ApiResponse getEmployeeDetailsById(@PathVariable String employeeId) {
		logger.info("indide getEmployeeDetailsById ");
		return employeeService.getEmployeeDetailsById(employeeId);
	}

	@PutMapping("/updateEmployeeStatus/{employeeID}/status/{userstatus}")
	public ApiResponse updateEmployeeStatus(@PathVariable String employeeID, @PathVariable UserStatus userstatus)
			throws Exception {
		logger.info("Received request to change category status to: " + userstatus + "for employeeId: " + employeeID);
		return employeeService.updateEmployeeStatus(employeeID, userstatus);
	}

	@GetMapping("/getAllEmpByProject/{projectid}/clientId/{clientid}")
	public ApiResponse getAllEmpByProject(@PathVariable String projectid, @PathVariable String clientid) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.getAllEmpByProject(projectid, clientid);
	}

	@GetMapping("/searchEmployeeNotAssignedToProject/{projectid}/clientId/{clientid}/searchString/{searchString}")
	public ApiResponse searchEmployeeNotAssignedToProject(@PathVariable String projectid, @PathVariable String clientid,
			@PathVariable String searchString) {
		logger.info("inside EmployeeController :: getAllEmpByProject ");
		return employeeService.searchEmployeeNotAssignedToProject(projectid, clientid, searchString);
	}

	@GetMapping("/searchInfraUser/{searchString}")
	public ApiResponse searchInfraUser(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchInfraUser ");
		return employeeService.searchInfraUser(searchString);
	}

	@GetMapping("/searchEmployee/{searchString}")
	public ApiResponse searchEmployee(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchEmployee ");
		return employeeService.searchEmployee(searchString);
	}

	@GetMapping("/searchInfraUsersForInfraUser/{searchString}")
	public ApiResponse searchInfraUsersForInfraUser(@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchInfraUsersForInfraUser ");
		return employeeService.searchInfraUsersForInfraUser(searchString);
	}

	@GetMapping("/getAllInfraUser")
	public ApiResponse getAllInfraUser() {
		logger.info("indside CatagoryController :: getAllInfraUser");
		return employeeService.getAllInfraUser();
	}

	@GetMapping("/getAllPermissions")
	public ApiResponse getAllPermissions() {
		logger.info("indside :: getAllPermissions");
		return employeeService.getAllPermissions();
	}

	@PutMapping("/updateProfile")
	public ApiResponse editEmployee(@RequestBody Map employeeRequest) throws Exception {
		logger.info("indide ProductController :: editEmployee");
		return employeeService.updateEmployee(employeeRequest);
	}

	@GetMapping("/getEmployeeByAccessToken")
	public ApiResponse getListByAccessToken() {
		logger.info("Received request to getEmployeeByAccessToken");
		return employeeService.getListByAccessToken();
	}

	@PutMapping("/editJobVendor/{vendorId}")
	public ApiResponse editJobVendor(@RequestBody JobVendorDetails vendorRequest, @PathVariable String vendorId) throws Exception {
		logger.info("indide ProductController :: editJobVendor");
		return employeeService.editJobVendor(vendorId, vendorRequest);
	}

	@GetMapping("/getEmployeeByReportingId/{reportingId}")
	public ApiResponse getEmployeeByReportingId(@PathVariable String reportingId) {
		logger.info("Received request to add Employee");
		return employeeService.getEmployeeByReportingId(reportingId);
	}

	@GetMapping("/getEmployeeByReportingId/{reportingId}/{searchString")
	public ApiResponse getEmployeeByReportingId(@PathVariable String reportingId, @PathVariable String searchString) {
		logger.info("Received request to search Employee");
		return employeeService.searchEmployeeByReportingId(reportingId, searchString);
	}
	
	@GetMapping("/getInfraEmployee/{searchString}")
	public ApiResponse getInfraEmployee(@PathVariable String searchString) {
		logger.info("Received request to get Employee");
		return employeeService.getInfraEmployee(searchString);
	}

	@GetMapping("/getAllRolePermissions/{roleId}")
	public ApiResponse getAllRolePermissions(@PathVariable String roleId) {
		logger.info("Received request to get rolepermission");
		return employeeService.getAllRolePermissions(roleId);
	}

	@PutMapping("/updateRolePermissions")
	public ApiResponse updateRolePermissions(@RequestParam String roleId, @RequestParam String modules,
			@RequestBody RoleMasterTable request) throws Exception {
		logger.info("Received request to add Employee");
		return employeeService.updateRolePermissions(roleId, modules, request);
	}
//	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateRolePermissions"})
//	public ApiResponse updateRolePermissions(@RequestBody RoleMasterTable request) {
//		logger.info("Received request to add Employee");
//		return employeeService.updateRolePermissions(null,null,request);
//	}

	@PutMapping("/updateOfflineStatus/{infraUserId}")
	public ApiResponse updateOfflineStatus(@PathVariable String infraUserId) throws Exception {
		return employeeService.updateOfflineStatus(infraUserId);
	}

	@GetMapping("/getAllEmployeeList")
	public ApiResponse getAllEmployeeList() {
		logger.info("indide CatagoryController :: getAllCatagory");
		return employeeService.getAllEmployeeList();
	}

	@GetMapping("/searchEmployeeNotAssignedToProject/{projectid}/searchString/{searchString}")
	public ApiResponse searchEmployeeNotAssignedToByProject(@PathVariable String projectid,
			@PathVariable String searchString) {
		logger.info("inside EmployeeController :: searchEmployeeNotAssignedToProject ");
		return employeeService.searchEmployeeNotAssignedToByProject(projectid, searchString);
	}

	@PostMapping("/employeeBulkUpload")
	public Map<String, Object> employeeBulkUpload(MultipartFile file) throws Exception {
		logger.info("Received request for get all employeeBulkUpload");
		return employeeService.employeeBulkUpload(file);
	}

}
