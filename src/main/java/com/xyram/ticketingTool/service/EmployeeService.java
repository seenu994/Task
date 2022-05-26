
package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.EmployeePermission;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.RoleMasterTable;
import com.xyram.ticketingTool.enumType.UserStatus;

public interface EmployeeService {

	ApiResponse addemployee(Employee employee) throws Exception;
	
	ApiResponse changeEmployeeAllPermission(EmployeePermission employeePermission);
	
	ApiResponse changeEmployeePermission(String userId,String permission, boolean flag) throws Exception, SecurityException;
	
	ApiResponse getEmployeePermission(String userId);

	ApiResponse getAllEmployee(Map<String, Object> filter, Pageable pageable);

	ApiResponse editEmployee(String employeeId, Employee employee) throws Exception;

	ApiResponse updateEmployeeStatus(String employeeID, UserStatus userstatus);
	
	ApiResponse getAllEmpByProject(String projectid, String clientid); 
	
	ApiResponse searchEmployeeNotAssignedToProject(String projectid,String clientid, String searchString); 
	
	ApiResponse searchInfraUser(String searchString);
	
	ApiResponse searchEmployee(String searchString); 
	
	ApiResponse searchInfraUsersForInfraUser(String searchString);

	ApiResponse getAllInfraUser();
	
	ApiResponse getAllPermissions();
	 
	List<Employee> getListOfInfraUSer();
	 
	List<Map> getListOfDeveloper();
	
	List<Map>  getListOfDeveloperInfra();

	List<Map> getListOfInfraAdmins();

	ApiResponse updateEmployee(Map employeeRequest);

	ApiResponse getAllProfile();

	//ApiResponse updateProfileImage(MultipartFile file, String employeeId);

	ApiResponse createJobVendor(JobVendorDetails vendorDetails);

	//ApiResponse getEmployeeDetails(Employee employeeId);



	ApiResponse getAllEmployeeCurrentMonth(Pageable pageable);

	ApiResponse getJobVendorById(String vendorId);
	
	ApiResponse changeAllEmployeePermissionsToDefault();

	ApiResponse getListByAccessToken();

	ApiResponse getEmployeeDetailsById(String employeeId);

	ApiResponse editJobVendor(String vendorId, JobVendorDetails vendorRequest);

	ApiResponse getJobVendorType();

	ApiResponse getEmployeeByReportingId(String reportingId);
	
	ApiResponse searchEmployeeByReportingId(String reportingId, String searchString);

	ApiResponse getInfraEmployee(String searchString);

	ApiResponse getAllRolePermissions(String roleId);

	ApiResponse updateRolePermissions(String roleId, String modules, RoleMasterTable request);

	ApiResponse updateOfflineStatus(String infraUserId);

	ApiResponse getAllEmployeeList();

	ApiResponse updateProfileImage(MultipartFile file, String userId);

	ApiResponse getEmployeeDetails(String employeeId);

	ApiResponse searchEmployeeNotAssignedToByProject(String projectid, String searchString);

	ApiResponse serachJobVendor(String vendorName);

	Map<String, Object> employeeBulkUpload(MultipartFile file);

	ApiResponse getJobVendor(Map<String, Object> filter, Pageable pageable);

	//ApiResponse updateProfileImage(MultipartFile file, String employeeId);

}
