
package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.PermissionRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.Repository.UserPermissionRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.Repository.VendorRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.UserPermissions;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.ticket.config.PermissionConfig;
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
	PermissionRepository permissionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CurrentUser currentUser;

	@Autowired
	ProjectServiceImpl ProjectSerImpl;

	@Autowired
	ProjectMemberRepository projectMemberRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	PermissionConfig permissionConfig;

	@Autowired
	UserPermissionRepository userPermissionConfig;

	static ChannelSftp channelSftp = null;
	static Session session = null;
	static Channel channel = null;
	static String PATHSEPARATOR = "/";

//	private static Map<String, com.xyram.ticketingTool.admin.model.User> userCache = new HashMap<>();

	@Override
	public ApiResponse addemployee(Employee employee) {

		ApiResponse response = new ApiResponse(false);

		response = validateEmployee(employee);
		System.out.println("username::" + currentUser.getName());

		if (response.isSuccess()) {
			try {
				User user = new User();
				user.setUsername(employee.getEmail());
				String encodedPassword = new BCryptPasswordEncoder().encode(employee.getPassword());
				user.setPassword(encodedPassword);
				// Employee employeere=new Employee();
				Role role = roleRepository.getById(employee.getRoleId());
				if (role != null) {
					try {

						user.setUserRole(role.getRoleName());
					} catch (Exception e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
								role.getRoleName() + " is not a valid status");
					}
				} else {
					throw new ResourceNotFoundException("invalid user role ");
				}
				Integer permission = permissionConfig.setDefaultPermissions(user.getUserRole().toString());
				user.setPermission(permission);
				user.setStatus(UserStatus.ACTIVE);
				System.out.println(user.getEmail() + "::" + user.getUsername() + "::" + user.getCreatedAt());
				User newUser = userRepository.save(user);
				UserPermissions permissions = new UserPermissions();
				permissions.setEmpModPermission(permissionConfig.getEMPLOYEES_PERMISSION());
				permissions.setProjectModPermission(permissionConfig.getPROJECTS_PERMISSION());
				permissions.setTicketModPermission(permissionConfig.getTICKETS_PERMISSION());
				permissions.setJobOpeningModPermission(permissionConfig.getJOBOPENINGS_PERMISSION());
				permissions.setJobInterviewsModPermission(permissionConfig.getJOBINTERVIEWS_PERMISSION());
				permissions.setJobAppModPermission(permissionConfig.getJOBAPPLICATIONS_PERMISSION());
				permissions.setJobOfferModPermission(permissionConfig.getJOBOFFERS_PERMISSION());
				permissions.setJobVendorsModPermission(permissionConfig.getJOBVENDORS_PERMISSION());
				permissions.setUserId(newUser.getId());
				userPermissionConfig.save(permissions);
				employee.setCreatedBy(currentUser.getUserId());
				employee.setUpdatedBy(currentUser.getUserId());
				employee.setCreatedAt(new Date());
				employee.setLastUpdatedAt(new Date());
				employee.setUserCredientials(user);
				employee.setProfileUrl("https://covidtest.xyramsoft.com/image/ticket-attachment/user-default-pic.png");
				Employee employeeNew = employeeRepository.save(employee);
                 User useredit = userRepository.getById(user.getId());
                 useredit.setScopeId(employeeNew.geteId());
                 userRepository.save(useredit);
				// Assigning default project to Developer
//				if (employee.getRoleId().equals("R3")) {
//					System.out.println("Inside employee.getRoleId() - " + employee.getRoleId());
//					ProjectMembers projectMember = new ProjectMembers();
//					projectMember.setCreatedAt(new Date());
//					projectMember.setLastUpdatedAt(new Date());
//					projectMember.setUpdatedBy(currentUser.getUserId());
//					projectMember.setCreatedBy(currentUser.getUserId());
//					projectMember.setStatus(ProjectMembersStatus.ACTIVE);
//					projectMember.setProjectId("2c9fab1f7bbeee88017bbf22f0af0002");
//					projectMember.setEmployeeId(employee.geteId());
//					projectMemberRepository.save(projectMember);
//				}
				response.setSuccess(true);
				response.setMessage(ResponseMessages.EMPLOYEE_ADDED);
				Map content = new HashMap();
				content.put("employeeId", employeeNew.geteId());
				response.setContent(content);
			} catch (Exception e) {
				System.out.println("Error Occured :: " + e.getMessage());
			}

			return response;

		}

		return response;
	}

	private ApiResponse validateEmployee(Employee employee) {
		ApiResponse response = new ApiResponse(false);
		String email = employeeRepository.filterByEmail(employee.getEmail());
		if (!emailValidation(employee.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);

			response.setSuccess(false);
		}

		else if (employee.getMobileNumber().length() != 10) {
			response.setMessage(ResponseMessages.MOBILE_INVALID);

			response.setSuccess(false);
		}

		else if (email != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists!!!");
		}

		else {
			response.setMessage(ResponseMessages.EMPLOYEE_ADDED);

			response.setSuccess(true);
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
		Page<Map> employeeList = employeeRepository.getAllEmployeeList(pageable);
		Map content = new HashMap();

		content.put("employeeList", employeeList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse updateEmployeeStatus(String employeeID, UserStatus userstatus) {
		ApiResponse response = validateStatus(userstatus);
		if (response.isSuccess()) {
			Employee employee = employeeRepository.getById(employeeID);
			if (employee != null) {

				employee.setStatus(userstatus);
				employeeRepository.save(employee);
				User user = userRepository.getById(employee.getUserCredientials().getId());
				user.setStatus(userstatus);
				userRepository.save(user);

				// Employee employeere=new Employee();z

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				response.setContent(null);
			}
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
			response.setContent(null);
		}

		return response;
	}

	private ApiResponse validateStatus(UserStatus userstatus) {
		ApiResponse response = new ApiResponse(false);
		if (userstatus == UserStatus.ACTIVE || userstatus == UserStatus.INACTIVE) {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}

		else {
			response.setMessage(ResponseMessages.USERSTATUS_INVALID);
			response.setSuccess(false);

		}

		return response;
	}

	@Override
	public ApiResponse editEmployee(String employeeId, Employee employeeRequest) {
		ApiResponse response = new ApiResponse(false);
		Employee employee = employeeRepository.getById(employeeId);
		if (employee != null) {
			employee.setFirstName(employeeRequest.getFirstName());
			employee.setLastName(employeeRequest.getLastName());
			employee.setLastUpdatedAt(new Date());
			employee.setMiddleName(employeeRequest.getMiddleName());
			employee.setMobileNumber(employeeRequest.getMobileNumber());
			employee.setPassword(employeeRequest.getPassword());
			employee.setRoleId(employeeRequest.getRoleId());
			employee.setDesignationId(employeeRequest.getDesignationId());
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

		return response;
	}

	@Override
	public ApiResponse getAllEmpByProject(String projectid, String clientid) {
		ApiResponse response = new ApiResponse(false);
		Projects projectRequest = new Projects();
		projectRequest.setpId(projectid);
		projectRequest.setClientId(clientid);
		ApiResponse projvalres = ProjectSerImpl.validateClientIdProjectId(projectRequest);
		if (projvalres.isSuccess()) {
			List<Map> employeeList = employeeRepository.getAllEmpByProject(projectid);
			Map content = new HashMap();
			content.put("EmployeeList", employeeList);
			response.setSuccess(true);
			response.setContent(content);
		} else {
			response.setMessage(ResponseMessages.ClIENT_ID_VALID);
			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public ApiResponse searchEmployeeNotAssignedToProject(String projectid, String clientid, String searchString) {
		ApiResponse response = new ApiResponse(false);
		Projects projectRequest = new Projects();
		projectRequest.setpId(projectid);
		projectRequest.setClientId(clientid);
		ApiResponse projvalres = ProjectSerImpl.validateClientIdProjectId(projectRequest);
		List<Map> employeeList = employeeRepository.searchEmployeeNotAssignedToProject(projectid, searchString);
		Map content = new HashMap();
		content.put("EmployeeList", employeeList);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse searchInfraUser(String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Map> employeeList = employeeRepository.searchInfraUsersForInfraUser(searchString, currentUser.getUserId());
		Map content = new HashMap();
		if (employeeList.size() > 0) {
			content.put("EmployeeList", employeeList);
			response.setSuccess(true);
			response.setContent(content);
		} else {
			content.put("EmployeeList", employeeList);
			response.setSuccess(false);
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse searchEmployee(String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Map> employeeList = employeeRepository.searchEmployee(searchString);
		Map content = new HashMap();
		if (employeeList.size() > 0) {
			content.put("EmployeeList", employeeList);
			response.setSuccess(true);
			response.setContent(content);
		} else {
			content.put("EmployeeList", employeeList);
			response.setSuccess(false);
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse searchInfraUsersForInfraUser(String searchString) {
		ApiResponse response = new ApiResponse(false);
//		List<Map> employeeList = employeeRepository.searchInfraUsersForInfraUser(searchString,currentUser.getUserId());
		List<Map> employeeList = employeeRepository.searchInfraUsersForInfraUser(searchString, currentUser.getUserId());

		Map content = new HashMap();
		if (employeeList.size() > 0) {
			content.put("EmployeeList", employeeList);
			response.setSuccess(true);
			response.setContent(content);
		} else {
			content.put("EmployeeList", employeeList);
			response.setSuccess(false);
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse getAllInfraUser() {
		List<Map> infraUserList = employeeRepository.getAllInfraUserList();
		Map content = new HashMap();

		content.put("infraUserList", infraUserList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse getAllPermissions() {
		List<Map> permissionList = permissionRepository.getAllPermissions();
		Map content = new HashMap();

		content.put("PermissionList", permissionList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public List<Employee> getListOfInfraUSer() {
		List<Employee> infraList = employeeRepository.getAllInfraList();
		Map content = new HashMap();

		content.put("infraList", infraList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return infraList;
	}

	@Override
	public List<Map> getListOfInfraAdmins() {
		List<Map> infraList = employeeRepository.getAllInfraAdmins();
		Map content = new HashMap();

		content.put("infraList", infraList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return infraList;
	}

	@Override
	public List<Map> getListOfDeveloper() {
		List<Map> developerList = employeeRepository.getListOfDeveloper();
		Map content = new HashMap();

		content.put("developerList", developerList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return developerList;
	}

	@Override
	public List<Map> getListOfDeveloperInfra() {
		List<Map> developerInfraList = employeeRepository.getListOfDeveloper();
		Map content = new HashMap();

		content.put("developerList", developerInfraList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return developerInfraList;
	}

	@Override
	public ApiResponse updateEmployee(Map employeeRequest) {
		ApiResponse response = new ApiResponse(true);

		Employee employeeObj = employeeRepository.getbyUserByUserId(currentUser.getUserId());

		if (employeeObj != null) {

			employeeObj.setFirstName((String) employeeRequest.get("firstName"));
			employeeObj.setLastName((String) employeeRequest.get("lastName"));
			// employeeObj.setLastUpdatedAt(new Date());
			employeeObj.setMiddleName((String) employeeRequest.get("middleName"));
			employeeObj.setMobileNumber((String) employeeRequest.get("mobileNumber"));

			employeeRepository.save(employeeObj);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.EMPLOYEE_UPDATION);
			response.setContent(null);
			return response;
		}

		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
			response.setContent(null);

			return response;
		}
	}

	public ApiResponse getAllProfile() {
		Map ProfileList = employeeRepository.getAllEmployeeUserList(currentUser.getUserId());
		Map content = new HashMap();

		content.put("ProfileList", ProfileList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse updateProfileImage(MultipartFile file, String employeeId) {
		ApiResponse response = new ApiResponse(true);
		byte[] filearray;
		try {
			filearray = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	       System.out.println(file.);
		String fileextension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String filename = getRandomFileName() + fileextension;// constentFile.getOriginalFilename();

		if (addFileAdmin(file, filename) != null) {
			Employee employeeObj = employeeRepository.getById(employeeId);
			employeeObj.setProfileUrl("https://covidtest.xyramsoft.com/image/ticket-attachment/" + filename);
			employeeRepository.save(employeeObj);
			response.setSuccess(true);
			response.setMessage(ResponseMessages.EMPLOYEE_PROFILE_UPDATION);
			response.setContent(null);
			return response;
		}

		else {

			response.setSuccess(false);
			response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
			response.setContent(null);
		}

		return response;
	}

	public String getRandomFileName() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public String addFileAdmin(MultipartFile file, String fileName) {
		System.out.println("bjsjsjn");
		String SFTPHOST = "13.229.55.43"; // SFTP Host Name or SFTP Host IP Address
		int SFTPPORT = 22; // SFTP Port Number
		String SFTPUSER = "ubuntu"; // User Name
		String SFTPPASS = ""; // Password
		String SFTPKEY = "/home/ubuntu/tomcat/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
		String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat/webapps/image/ticket-attachment";// Source Directory on SFTP
																							// server
		String fileNameOriginal = fileName;
		try {
			JSch jsch = new JSch();
			if (SFTPKEY != null && !SFTPKEY.isEmpty()) {
				jsch.addIdentity(SFTPKEY);
			}
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//	        session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(); // Create SFTP Session
			channel = session.openChannel("sftp"); // Open SFTP Channel
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIRAADMIN);// Change Directory on SFTP Server
			channelSftp.put(file.getInputStream(), fileName);
			System.out.println("added");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (channelSftp != null)
				channelSftp.disconnect();
			if (channel != null)
				channel.disconnect();
			if (session != null)
				session.disconnect();
		}
		return fileNameOriginal;
	}

	@Override
	public ApiResponse createJobVendor(JobVendorDetails vendorDetails) {
		ApiResponse response = new ApiResponse(false);

//		response = validateEmployee(vendorDetails);
		System.out.println("username::" + currentUser.getName());

//		if (response.isSuccess()) {
		try {
			User user = new User();
			user.setUsername(vendorDetails.getEmail());
			String encodedPassword = new BCryptPasswordEncoder().encode(vendorDetails.getPassword());
			user.setPassword(encodedPassword);
			// Employee employeere=new Employee();
			Role role = roleRepository.getById(vendorDetails.getRoleId());
			if (role != null) {
				try {

					user.setUserRole(role.getRoleName());
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							role.getRoleName() + " is not a valid status");
				}
			} else {
				throw new ResourceNotFoundException("invalid user role ");
			}
			user.setStatus(UserStatus.ACTIVE);
			System.out.println(user.getEmail() + "::" + user.getUsername() + "::" + user.getCreatedAt());
			userRepository.save(user);

//				vendorDetails.setCreatedBy(currentUser.getUserId());
//				vendorDetails.setUpdatedBy(currentUser.getUserId());
//				vendorDetails.setCreatedAt(new Date());
//				vendorDetails.setLastUpdatedAt(new Date());
			vendorDetails.setUserCredientials(user);
			vendorDetails.setProfileUrl("https://covidtest.xyramsoft.com/image/ticket-attachment/user-default-pic.png");
			JobVendorDetails vendorNew = vendorRepository.save(vendorDetails);

			// Assigning default project to Developer
//				if (employee.getRoleId().equals("R3")) {
//					System.out.println("Inside employee.getRoleId() - " + employee.getRoleId());
//					ProjectMembers projectMember = new ProjectMembers();
//					projectMember.setCreatedAt(new Date());
//					projectMember.setLastUpdatedAt(new Date());
//					projectMember.setUpdatedBy(currentUser.getUserId());
//					projectMember.setCreatedBy(currentUser.getUserId());
//					projectMember.setStatus(ProjectMembersStatus.ACTIVE);
//					projectMember.setProjectId("2c9fab1f7bbeee88017bbf22f0af0002");
//					projectMember.setEmployeeId(employee.geteId());
//					projectMemberRepository.save(projectMember);
//				}
			response.setSuccess(true);
			response.setMessage(ResponseMessages.EMPLOYEE_ADDED);
			Map content = new HashMap();
			content.put("vendorId", vendorNew.getvId());
			response.setContent(content);
		} catch (Exception e) {
			System.out.println("Error Occured :: " + e.getMessage());
		}

		return response;
	}

	@Override
	public ApiResponse getEmployeeDetails(String employeeId) {
		ApiResponse response = new ApiResponse(false);
		Map employee = employeeRepository.getbyEmpId(employeeId);
		Map content = new HashMap();
		content.put("employeeDetails", employee);
		if (employee != null) {
			response.setSuccess(true);
			response.setMessage("Employee Retrieved Successfully");
			response.setContent(content);
		}

		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse getJobVendor() {
		ApiResponse response = new ApiResponse(false);
		List<Map> jobVendors = vendorRepository.getJobVendors();
		Map content = new HashMap();
		content.put("vendorDetails", jobVendors);
		if (jobVendors != null) {
			response.setSuccess(true);
			response.setMessage("Vendor Retrieved Successfully");
			response.setContent(content);
		}

		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse getAllEmployeeCurrentMonth(Pageable pageable) {
		ApiResponse response = new ApiResponse(false);
		List<Map> employeeList = employeeRepository.getAllEmployeeCurrentMonth(pageable);
		Map content = new HashMap();
		content.put("employeeList", employeeList);
		if (employeeList != null) {
			response.setSuccess(true);
			response.setMessage("Employee Retrieved Successfully");
			response.setContent(content);
		}

		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse getJobVendorById(String vendorId) {
		ApiResponse response = new ApiResponse(false);
	    Map employee = vendorRepository.getJobVendorById(vendorId);
		Map content = new HashMap();
		content.put("employee", employee);
		if (employee != null) {
			response.setSuccess(true);
			response.setMessage("Employee Retrieved Successfully");
			response.setContent(content);
		}

		else {
			response.setSuccess(false);
			response.setMessage("Could not retrieve data");
			response.setContent(null);
		}

		return response;
	}

}
