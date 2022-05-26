
package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.CompanyLocationRepository;
import com.xyram.ticketingTool.Repository.CompanyWingsRepository;
import com.xyram.ticketingTool.Repository.DesignationRepository;
import com.xyram.ticketingTool.Repository.EmployeePermissionRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.PermissionRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.RoleMasterRepository;
import com.xyram.ticketingTool.Repository.RoleRepository;
import com.xyram.ticketingTool.Repository.UserPermissionRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.Repository.VendorRepository;
import com.xyram.ticketingTool.Repository.VendorTypeRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.email.EmailService;
import com.xyram.ticketingTool.entity.AssetVendor;
import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.CompanyLocation;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.Designation;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.EmployeePermission;
import com.xyram.ticketingTool.entity.JobVendorDetails;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Role;
import com.xyram.ticketingTool.entity.RoleMasterTable;
import com.xyram.ticketingTool.entity.UserPermissions;
import com.xyram.ticketingTool.entity.VendorType;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.fileupload.FileTransferService;
import com.xyram.ticketingTool.helper.EmployeePojo;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.EmployeeService;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.service.TicketAttachmentService;
import com.xyram.ticketingTool.ticket.config.EmployeePermissionConfig;
import com.xyram.ticketingTool.ticket.config.PermissionConfig;
import com.xyram.ticketingTool.util.BulkUploadExcelUtil;
import com.xyram.ticketingTool.util.EmployeeUtil;
import com.xyram.ticketingTool.util.ResponseMessages;


@Service
public class EmpoloyeeServiceImpl implements EmployeeService { 

	private static final Logger logger = LoggerFactory.getLogger(EmpoloyeeServiceImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	DesignationRepository designationRepository;

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

	@Autowired
	RoleMasterRepository masterRepo;

	@Autowired
	VendorTypeRepository vendorRepo;

	@Autowired
	CompanyLocationRepository companyLocationRepository;

	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;
	@Autowired
	PushNotificationCall pushNotificationCall;
	@Autowired
	PushNotificationRequest pushNotificationRequest;

	@Autowired
	CurrentUser userDetail;

	@Autowired
	NotificationService notificationService;

	@Autowired
	FileTransferService fileUploadService;

	@Autowired
	EmailService emailService;

	@Autowired
	TicketAttachmentService attachmentService;

	@Autowired
	CompanyWingsRepository wingRepo;
	
	@Autowired
	EmployeePermissionRepository empPermissionRepo;
	
	@Autowired
	EmployeePermissionConfig empPerConfig;

	@Value("${APPLICATION_URL}")
	private String application_url;

	@Value("${ticket-attachment-base-url}")
	private String ticketAttachmentBaseUrl;

	static ChannelSftp channelSftp = null;
	static Session session = null;
	static Channel channel = null;
	static String PATHSEPARATOR = "/";

//	private static Map<String, com.xyram.ticketingTool.admin.model.User> userCache = new HashMap<>();

	@SuppressWarnings("unused")
	@Override
	public ApiResponse addemployee(Employee employee) throws Exception {

		ApiResponse response = new ApiResponse(false);
		
		if(!empPerConfig.isHavingpersmission("empAdmin")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create employee");
			return response;
		}

		response = validateEmployee(employee);
		
		if (response.getMessage() != null && response.getMessage() != "") {
			return response;
		}
		// Email Validation starts here

		if (employee.getEmail() == null || employee.getEmail().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.MAILID_MAN);
			return response;
		}
		if (!emailValidation(employee.getEmail())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.INVAL_MAIL_ID);
			return response;
		}

		String email = employeeRepository.filterByEmail(employee.getEmail());
		if (email != null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.MAIL_ID_EXI);
			return response;
		}
		System.out.println("username::" + currentUser.getName());

//		if (response.isSuccess()) {
		try {

			if (!employeeRepository.getbyEmpId(employee.geteId()).isEmpty()) {

				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMP_CODE);
				return response;
			}

			User user = new User();
			user.setUsername(employee.getEmail());
			String encodedPassword = new BCryptPasswordEncoder().encode(employee.getPassword());
			user.setPassword(encodedPassword);
			if (employee.getFirstName().length() >= 3 && employee.getLastName().length() >= 3) {

				employee.setFirstName(employee.getFirstName().trim());
				String name = employee.getFirstName();
				String firstLetter = name.substring(0, 1);
				String remainingLetters = name.substring(1, name.length());
				firstLetter = firstLetter.toUpperCase();
				employee.setFirstName(firstLetter + remainingLetters);

				user.setName(employee.getFirstName() + " " + employee.getLastName());

				// Employee employeere=new Employee();
				Role role = roleRepository.getById(employee.getRoleId());
				user.setUserRole(role != null ? role.getRoleName() : null);

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
				CompanyWings wing = wingRepo.getWingById(employee.getWings().getId());
				employee.setWings(wing);
				employee.setCreatedAt(new Date());
				employee.setLastUpdatedAt(new Date());
				employee.setUserCredientials(user);
				employee.setProfileUrl("https://tool.xyramsoft.com:444/image/ticket-attachment/user-default-pic.png");
				employee.setDateOfJoin(employee.getDateOfJoin());
				Employee employeeNew = employeeRepository.save(employee);
				User useredit = userRepository.getById(user.getId());
				useredit.setScopeId(employeeNew.geteId());
				userRepository.save(useredit);
				
				// New Permissions
				EmployeePermission empPermission = new EmployeePermission();
				empPermission.setUserId(employeeNew.geteId());
				empPermissionRepo.save(empPermission);

				// sending notification starts here..!

				List<Map> EmployeeList = employeeRepository.getEmployeeBYReportingToId(employee.getReportingTo());

				if (!EmployeeList.isEmpty()) {

					for (Map employeeNotification : EmployeeList) {
						Map request = new HashMap<>();
						request.put("id", employeeNotification.get("id"));
						request.put("uid", employeeNotification.get("uid"));
						request.put("title", "EMPLOYEE CREATED");
						request.put("body", " employee Created - " + employeeNew.getFirstName());
						pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request,
								12, NotificationType.EMPLOYEE_CREATED.toString()));

					}
					// inserting notification details
					Notifications notifications = new Notifications();
					notifications.setNotificationDesc("employee created - " + employeeNew.getFirstName());
					notifications.setNotificationType(NotificationType.EMPLOYEE_CREATED);
					notifications.setSenderId(employeeNew.getReportingTo());
					notifications.setReceiverId(userDetail.getUserId());
					notifications.setSeenStatus(false);
					notifications.setCreatedBy(userDetail.getUserId());
					notifications.setCreatedAt(new Date());
					notifications.setUpdatedBy(userDetail.getUserId());
					notifications.setLastUpdatedAt(new Date());

					notificationService.createNotification(notifications);
				}
				UUID uuid = UUID.randomUUID();
				String uuidAsString = uuid.toString();

				if (employeeNew != null & false) {
					String name2 = null;

					HashMap mailDetails = new HashMap();
					mailDetails.put("toEmail", employeeNew.getEmail());
					mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
					mailDetails.put("message", "Hi " + name2
							+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
							+ application_url + "/update-password" + "?key=" + uuidAsString
							+ "\n\n Thanks for helping us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
					emailService.sendMail(mailDetails);
				}
				// end of the notification part...!

				response.setSuccess(true);
				response.setMessage(ResponseMessages.EMPLOYEE_ADDED);
				Map content = new HashMap();
				content.put("employeeId", employeeNew.geteId());
				response.setContent(content);
			}
		} catch (ResponseStatusException re) {
			throw new ResponseStatusException(re.getStatus(), re.getReason());
		} catch (Exception e) {
			System.out.println("Error Occured :: " + e.getMessage());
		}

		return response;

//		}
	}
	
	public ApiResponse getEmployeePermission(String userId) {
		ApiResponse response = new ApiResponse(true);
		EmployeePermission ep  = empPermissionRepo.getbyUserId(userId);
		if(ep != null) {
			Map content = new HashMap();
			content.put("permissions", ep);
			response.setContent(content);
			response.setMessage("Permissions retreived successfully..!");
			response.setSuccess(true);
		}
		else {
			response.setMessage("Employee details are not exist");
			response.setSuccess(false);
		}
		return response;
	}
	
	public ApiResponse changeEmployeePermission(String userId,String permission, boolean flag) throws Exception, SecurityException {
		ApiResponse response = new ApiResponse(true);
		EmployeePermission ep = empPermissionRepo.getbyUserId(currentUser.getUserId());
		if(ep != null) {
			if(!ep.getEmpAdmin()) {
				response.setMessage("Not authorized to edit employee permission 1");
				response.setSuccess(false);
				return response;
			}
		}else {
			response.setMessage("Not authorized to edit employee permission 2");
			response.setSuccess(false);
			return response;
		}
		Employee employee = employeeRepository.getbyUserId(userId);
		if (employee != null) {
			
//	        EmployeePermission.class.getField(permission).set(ep, flag);
			EmployeePermission ep1 = empPermissionRepo.getbyUserId(employee.getUserCredientials().getId());
			ObjectMapper oMapper = new ObjectMapper();
	        Map<String, Object> map = oMapper.convertValue(ep1, Map.class);
	        if(map.containsKey(permission)) {
	        	map.put(permission, flag);
	        	
	        	Gson gson = new Gson();
	        	JsonElement jsonElement = gson.toJsonTree(map);
	        	EmployeePermission newObj = gson.fromJson(jsonElement, EmployeePermission.class);
	        	newObj.setId(ep1.getId());
	        	empPermissionRepo.saveAndFlush(newObj);
	        	
	        	response.setMessage("Employee Permissions Updated");
				response.setSuccess(true);
				return response;
				
	        }else {
	        	response.setMessage("Permissions Key not exist");
				response.setSuccess(true);
				return response;
	        }
			
			
		}else {
			response.setMessage("Employee Not Exist");
			response.setSuccess(false);
			return response;
		}
	}
	
	public ApiResponse changeEmployeeAllPermission(EmployeePermission employeePermission) { 
		ApiResponse response = new ApiResponse(true);
		EmployeePermission ep = empPermissionRepo.getbyUserId(currentUser.getUserId());
		if(ep != null) {
			if(!ep.getEmpAdmin()) {
				response.setMessage("Not authorized to edit employee permission");
				response.setSuccess(false);
				return response;
			}
		}else {
			response.setMessage("Not authorized to edit employee permission");
			response.setSuccess(false);
			return response;
		}
		Employee employee = employeeRepository.getById(employeePermission.getUserId());
		if (employee != null) {
			empPermissionRepo.save(employeePermission);
			response.setMessage("Employee Permissions Updated");
			response.setSuccess(true);
		}else {
			response.setMessage("Employee Not Exist");
			response.setSuccess(false);
			return response;
		}
		return response;
	}

	private ApiResponse validateEmployee(Employee employee) throws Exception {
		ApiResponse response = new ApiResponse(true);

		String regex = "[a-z A-Z]+";
		if (!emailValidation(employee.getEmail())) {
			response.setMessage(ResponseMessages.EMAIL_INVALID);
			response.setSuccess(false);
			return response;
		}

		if (employee.getFirstName() == null || employee.getFirstName().equals("")
				|| employee.getFirstName().length() < 3) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.FIRST_NAME_MAN);
			return response;
		}
		if (!employee.getFirstName().matches(regex)) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.FIRST_NAME_CHAR);
			return response;
		}

		if (employee.getLastName() == null || employee.getLastName().equals("")
				|| employee.getLastName().length() <= 0) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LAST_NAME_MAN);
			return response;
		}
		if (!employee.getLastName().matches(regex)) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LAST_NAME_CHAR);
			return response;
		}

		if (employee.getLocation() == null || employee.getLocation().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.LOC_MAN);
			return response;
		} else {

			CompanyLocation companyLocation = companyLocationRepository.getCompanyLocations(employee.getLocation());
			if (companyLocation != null) {
				employee.setLocation(employee.getLocation());
			}
			if (companyLocation == null) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.LOC_NOT_VALID);
				return response;
			}
		}

		if (employee.getRoleId() == null || employee.getRoleId().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ROLE_ID_MAN);
		} else {

			Role role = roleRepository.getRoleName(employee.getRoleId());
			if (role == null) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ROLE_ID_NOT_VAL);
				return response;
			}

		}

		if (employee.getDesignationId() == null || employee.getDesignationId().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.DES_ID_MAN);
			return response;
		} else {

			Designation designation = designationRepository.getDesignationNames(employee.getDesignationId());
			if (designation == null) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.DES_ID_NOT_VAL);
				return response;
			}
		}

		if (employee.getPosition() == null || employee.getPosition().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.POSITION_MAN);
			return response;
		}

		if (employee.getPosition() != null) {
			boolean isExist = false;

			for (String position : EmployeeUtil.position) {
				if (position.equalsIgnoreCase(employee.getPosition())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.POSITION_NOT_VAL);
				return response;
			}

		}

		if (employee.getReportingTo() != null && employee.getReportingTo().length() > 0) {
			Employee empObj = employeeRepository.getByEmpIdE(employee.getReportingTo());
			if (empObj == null) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.NOT_VALID);
				return response;
			}

		}

		if (employee.getWings() == null || employee.getWings().getId().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.WING_MAN);
			return response;
		}
		if (employee.getWings() != null && employee.getWings().getId() != null) {
			CompanyWings companyWings = wingRepo.getWingName(employee.getWings().getId());

			if (companyWings != null) {
				employee.setWings(companyWings);
			} else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.WING_NOT_EXI);
				return response;
			}
		}

		if (employee.getMobileNumber() == null || employee.getMobileNumber().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.MOB_NUM_MAN);
			return response;

		}

		else if (employee.getMobileNumber().length() != 10) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.INCORRECT_MOB);
			return response;
		}

		if (employee.getDateOfJoin() != null && !employee.getDateOfJoin().equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(employee.getDateOfJoin());
			Date tmDate = null;
			try {
				tmDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new Exception("Date format should be 'yyyy-MM-dd'");			
				//response.setSuccess(false);
				//response.setMessage(ResponseMessages.DOJ_NOT_VAL);
				//return response;
			}
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Join_date_man);
			return response;
		}
		response.setSuccess(true);
		return response;
	}

	public static Date getDateWithoutTimeUsingFormat() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(formatter.format(new Date()));
	}

	private boolean emailValidation(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse getAllEmployee(Map<String, Object> filter, Pageable pageable) {

		Page<Map> employeeList = null;

		if (filter == null) {
			employeeList = employeeRepository.getAllEmployeeList(pageable);
		} else {

			String searchString = filter.containsKey("searchString")
					? ((String) filter.get("searchString")).toLowerCase()
					: null;
			String role = filter.containsKey("role") ? ((String) filter.get("role")) : null;
			String designation = filter.containsKey("designation") ? ((String) filter.get("designation")) : null;
			String position = filter.containsKey("position") ? ((String) filter.get("position")).toLowerCase() : null;
			String wing = filter.containsKey("wing") ? ((String) filter.get("wing")) : null;
			employeeList = employeeRepository.getAllEmployeeListByFilter(pageable, searchString, role, designation,
					position, wing);
		}

		Map content = new HashMap();
		content.put("employeeList", employeeList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setMessage("Employee Retrieved Successfully");
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
	public ApiResponse editEmployee(String employeeId, Employee employeeRequest) throws Exception {
		ApiResponse response = new ApiResponse(false);

	
			response = validateEmployee(employeeRequest);
		

		if (response.getMessage() != null && response.getMessage() != "") {
			return response;
		}

		Employee employee = employeeRepository.getById(employeeId);
		User user = userRepository.getById(employee.getUserCredientials().getId());

		if (employee != null) {
			employee.setFirstName(employeeRequest.getFirstName());
			employee.setLastName(employeeRequest.getLastName());
			employee.setLastUpdatedAt(new Date());

			employee.setFirstName(employee.getFirstName().trim());
			String name = employee.getFirstName();
			String firstLetter = name.substring(0, 1);
			String remainingLetters = name.substring(1, name.length());
			firstLetter = firstLetter.toUpperCase();
			employee.setFirstName(firstLetter + remainingLetters);

			user.setName(employeeRequest.getFirstName() + " " + employeeRequest.getLastName());
			employee.setMiddleName(employeeRequest.getMiddleName());
			employee.setMobileNumber(employeeRequest.getMobileNumber());
			employee.setPassword(employeeRequest.getPassword());
			employee.setReportingTo(employeeRequest.getReportingTo());
			employee.setLocation(employeeRequest.getLocation());
			employee.setPosition(employeeRequest.getPosition());
			employee.setWings(employeeRequest.getWings());
			employee.setDateOfJoin(employeeRequest.getDateOfJoin());
			employee.setRoleId(employeeRequest.getRoleId());

			Role role = roleRepository.getById(employeeRequest.getRoleId());
			employee.setDesignationId(employeeRequest.getDesignationId());

			user.setUserRole(role.getRoleName());
			userRepository.save(user);
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
			response.setMessage("Employee Retrieved Successfully");
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
	public ApiResponse searchEmployeeNotAssignedToByProject(String projectid, String searchString) {

		ApiResponse response = new ApiResponse(false);
		Projects projectRequest = new Projects();
		projectRequest.setpId(projectid);

		ApiResponse projvalres = ProjectSerImpl.validateProjectId(projectRequest);
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

		// Map employee = employeeRepository.getEmployeeBYId(searchString);
		// List<Map> reportees = employeeRepository.getReportingList(employeeId);

		List<Map> employeeList = employeeRepository.searchEmployee(searchString);
		Map content = new HashMap();
		if (employeeList.size() > 0) {
			content.put("EmployeeList", employeeList);
			response.setSuccess(true);
			response.setMessage("Employee Retrieved successfully");
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
			response.setMessage("Employee Retrieved successfully");
			response.setContent(content);
		} else {
			content.put("EmployeeList", employeeList);
			response.setSuccess(false);
			response.setMessage("Employee Not Found!!");
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
		response.setMessage("Employee Retrieved successfully");
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
		response.setMessage("List of Infra Admins");
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
	public ApiResponse updateProfileImage(MultipartFile file, String userId) {

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
		String filename = getRandomFileName() + System.currentTimeMillis();
		boolean succesResponse = false;
		try {
			succesResponse = fileUploadService.uploadFile(file, ticketAttachmentBaseUrl, filename + "" + fileextension);

		} catch (Exception e) {

			logger.info(e.toString());
		}

		if (succesResponse) {

			Employee employeeObj = employeeRepository.getbyUserByUserId(userId);
			if (employeeObj != null) {
				// employeeObj=new Employee();
				employeeObj.setProfileUrl(
						"https://tool.xyramsoft.com:444" + ticketAttachmentBaseUrl + "/" + filename + fileextension);
				employeeRepository.save(employeeObj);
				response.setSuccess(true);
				response.setMessage(ResponseMessages.EMPLOYEE_PROFILE_UPDATION);
				response.setContent(null);
				return response;
			} else {

				response.setSuccess(false);
				response.setMessage(ResponseMessages.EMPLOYEE_INVALID);
				response.setContent(null);

			}
			return response;
		}
		{
			response.setSuccess(false);
			response.setMessage(ResponseMessages.UPLOAD_IMAGE);
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
		String SFTPKEY = "/home/ubuntu/tomcat-be/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
		String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat-be/webapps/image/ticket-attachment";// Source Directory on
																								// SFTP
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
			channelSftp.getSession().setTimeout(15000);
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
			UserPermissions permissions = new UserPermissions();
			permissions.setEmpModPermission(permissionConfig.getEMPLOYEES_PERMISSION());
			permissions.setProjectModPermission(permissionConfig.getPROJECTS_PERMISSION());
			permissions.setTicketModPermission(permissionConfig.getTICKETS_PERMISSION());
			permissions.setJobOpeningModPermission(permissionConfig.getJOBOPENINGS_PERMISSION());
			permissions.setJobInterviewsModPermission(permissionConfig.getJOBINTERVIEWS_PERMISSION());
			permissions.setJobAppModPermission(permissionConfig.getJOBAPPLICATIONS_PERMISSION());
			permissions.setJobOfferModPermission(permissionConfig.getJOBOFFERS_PERMISSION());
			permissions.setJobVendorsModPermission(permissionConfig.getJOBVENDORS_PERMISSION());
			permissions.setUserId(user.getId());
			userPermissionConfig.save(permissions);

//				vendorDetails.setCreatedBy(currentUser.getUserId());
//				vendorDetails.setUpdatedBy(currentUser.getUserId());
//				vendorDetails.setCreatedAt(new Date());
//				vendorDetails.setLastUpdatedAt(new Date());
			vendorDetails.setUserCredientials(user);
			vendorDetails.setProfileUrl("https://tool.xyramsoft.com:444/image/ticket-attachment/user-default-pic.png");
			JobVendorDetails vendorNew = vendorRepository.save(vendorDetails);
			if (vendorNew != null) {
				Employee empObj = new Employee();
				List<Employee> EmployeeByRole = employeeRepository.getVendorNotification();

				for (Employee employeeNotification : EmployeeByRole) {
					Map request = new HashMap<>();
					request.put("id", employeeNotification.geteId());
					request.put("uid", employeeNotification.getUserCredientials().getUid());
					request.put("title", "JOB_VENDOR_CREATED");
					request.put("body", "JOB_VENDOR_CREATED - " + employeeNotification.getRoleId());
					pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 12,
							NotificationType.JOB_VENDOR_CREATED.toString()));

					// inserting notification details
					Notifications notifications = new Notifications();
					notifications.setNotificationDesc("JOB_VENDOR_CREATED - " + employeeNotification.getFirstName());
					notifications.setNotificationType(NotificationType.JOB_VENDOR_CREATED);
					notifications.setSenderId(empObj.getReportingTo());
					notifications.setReceiverId(userDetail.getUserId());
					notifications.setSeenStatus(false);
					notifications.setCreatedBy(userDetail.getUserId());
					notifications.setCreatedAt(new Date());
					notifications.setUpdatedBy(userDetail.getUserId());
					notifications.setLastUpdatedAt(new Date());

					notificationService.createNotification(notifications);
					UUID uuid = UUID.randomUUID();
					String uuidAsString = uuid.toString();
					if (employeeNotification != null) {
						String name = null;

						HashMap mailDetails = new HashMap();
						mailDetails.put("toEmail", employeeNotification.getEmail());
						mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
						mailDetails.put("message", "Hi " + name
								+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
								+ application_url + "/update-password" + "?key=" + uuidAsString
								+ "\n\n Thanks for helpRing us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
						emailService.sendMail(mailDetails);
					}
				}

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
			}
		} catch (Exception e) {
			System.out.println("Error Occured :: " + e.getMessage());
		}

		return response;
	}

	@Override
	public ApiResponse getEmployeeDetails(String employeeId) {
		ApiResponse response = new ApiResponse(false);
		List<Map> employee = employeeRepository.getbyEmpId(employeeId);
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
	public ApiResponse getEmployeeDetailsById(String employeeId) {
		ApiResponse response = new ApiResponse(false);
		Map employee = employeeRepository.getEmployeeBYId(employeeId);
		List<Map> reportees = employeeRepository.getReportingList(employeeId);
		Map content = new HashMap();
		content.put("employeeDetails", employee);
		content.put("reportees", reportees);
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
	public ApiResponse getJobVendor(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		Page<JobVendorDetails> jobVendors = vendorRepository.getJobVendors(searchString, pageable);
		Map content = new HashMap();
		content.put("jobVendors", jobVendors);
		if (content != null) {
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
	public ApiResponse serachJobVendor(String vendorName) {
		ApiResponse response = new ApiResponse(false);
		List<JobVendorDetails> jobVendors = vendorRepository.serachJobVendors(vendorName);
		Map content = new HashMap();
		content.put("jobVendors", jobVendors);
		if (content != null) {
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
	public ApiResponse changeAllEmployeePermissionsToDefault() {
		ApiResponse response = new ApiResponse(false);
		List<Employee> employeeList = employeeRepository.getAllEmployees();
		for (Employee employee : employeeList) {
			EmployeePermission empPermission = empPermissionRepo.getbyUserId(employee.getUserCredientials().getId());
			if(empPermission != null)
				empPermissionRepo.save(empPermission);
			else
			{
				EmployeePermission empPerObj = new EmployeePermission();
				if(employee.getUserCredientials().getId() != null) {
					empPerObj.setUserId(employee.getUserCredientials().getId());
					empPermissionRepo.save(empPerObj);
				}
			}
		}
		response.setSuccess(true);
		response.setMessage("Changed Permissions to Default");
		return response;
	}

	@Override
	public ApiResponse getJobVendorById(String vendorId) {
		ApiResponse response = new ApiResponse(false);
		JobVendorDetails employee = vendorRepository.getJobVendorById(vendorId);
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

	@Override
	public ApiResponse getListByAccessToken() {
		ApiResponse response = new ApiResponse(false);
		String id = currentUser.getUserId();
		if (id != null) {
			Map employee = employeeRepository.getbyAccessToken(id);

			response.setSuccess(true);
			response.setMessage("Employee Retrieved Successfully");
			response.setContent(employee);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access token is required");
		}
		return response;
	}

	@Override
	public ApiResponse editJobVendor(String vendorId, JobVendorDetails vendorRequest) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		JobVendorDetails vendor = vendorRepository.getById(vendorId);
		if (vendor != null) {
			Employee empObj = new Employee();
			List<Employee> EmployeeByRole = employeeRepository.getVendorNotification();

			for (Employee employeeNotification : EmployeeByRole) {
				Map request = new HashMap<>();
				request.put("id", employeeNotification.geteId());
				request.put("uid", employeeNotification.getUserCredientials().getUid());
				request.put("title", "JOB_VENDOR_EDITED");
				request.put("body", "JOB_VENDOR_EDITED - " + employeeNotification.getRoleId());
				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 12,
						NotificationType.JOB_VENDOR_CREATED.toString()));

				// inserting notification details
				Notifications notifications = new Notifications();
				notifications.setNotificationDesc("JOB_VENDOR_CREATED - " + employeeNotification.getFirstName());
				notifications.setNotificationType(NotificationType.JOB_VENDOR_EDITED);
				notifications.setSenderId(empObj.getReportingTo());
				notifications.setReceiverId(userDetail.getUserId());
				notifications.setSeenStatus(false);
				notifications.setCreatedBy(userDetail.getUserId());
				notifications.setCreatedAt(new Date());
				notifications.setUpdatedBy(userDetail.getUserId());
				notifications.setLastUpdatedAt(new Date());

				notificationService.createNotification(notifications);
				UUID uuid = UUID.randomUUID();
				String uuidAsString = uuid.toString();
				if (employeeNotification != null) {
					String name = null;

					HashMap mailDetails = new HashMap();
					mailDetails.put("toEmail", employeeNotification.getEmail());
					mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
					mailDetails.put("message", "Hi " + name
							+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
							+ application_url + "/update-password" + "?key=" + uuidAsString
							+ "\n\n Thanks for helpRing us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
					emailService.sendMail(mailDetails);
				}
			}
			vendor.setName(vendorRequest.getName());
			vendor.setEmail(vendorRequest.getEmail());
			vendor.setMobileNumber(vendorRequest.getMobileNumber());
			vendorRepository.save(vendor);
			response.setSuccess(true);
			response.setMessage("Edit successful");

		} else {
			response.setSuccess(false);
			response.setMessage("Vendor Id is required");
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse getJobVendorType() {
		ApiResponse response = new ApiResponse(false);
		List<VendorType> jobVendors = vendorRepo.getJobVendorType();
		Map content = new HashMap();
		content.put("jobVendorType", jobVendors);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("VendorType Retrieved Successfully");
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
	public ApiResponse getEmployeeByReportingId(String reportingId) {
		ApiResponse response = new ApiResponse(false);
		List<Map> reportees = employeeRepository.getReportingList(reportingId);
		Map content = new HashMap();
		content.put("reportees", reportees);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("Reportees Retrieved Successfully");
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
	public ApiResponse searchEmployeeByReportingId(String reportingId, String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Map> reportees = employeeRepository.searchEmployeeByReportingId(reportingId, searchString);
		Map content = new HashMap();
		content.put("reportees", reportees);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("Reportees Retrieved Successfully");
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
	public ApiResponse getInfraEmployee(String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Employee> infraUsers = employeeRepository.getInfraEmployee(searchString);
		Map content = new HashMap();
		content.put("infraUsers", infraUsers);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("infraUsers Retrieved Successfully");
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
	public ApiResponse getAllRolePermissions(String roleId) {
		ApiResponse response = new ApiResponse(false);
		List<RoleMasterTable> rolePermissions = masterRepo.getAllRolePermissions(roleId);
		Map content = new HashMap();
		content.put("rolePermission", rolePermissions);
		if (content != null) {
			response.setSuccess(true);
			response.setMessage("infraUsers Retrieved Successfully");
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
	public ApiResponse updateRolePermissions(String roleId, String modules, RoleMasterTable request) {
		ApiResponse response = new ApiResponse(false);
		RoleMasterTable rolePermissions = masterRepo.updateRolePermissions(roleId, modules);
		List<UserPermissions> permissionUser = userPermissionConfig.getByRole(roleId);
		List<String> permissionRole = userPermissionConfig.getDetailsyRole(roleId);

		for (UserPermissions permissions : permissionUser) {
			if (modules.equals("EMPLOYEES_PERMISSION")) {
				permissions.setEmpModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("PROJECTS_PERMISSION")) {
				permissions.setProjectModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("TICKETS_PERMISSION")) {
				permissions.setTicketModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("JOBOPENINGS_PERMISSION")) {
				permissions.setJobOpeningModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("JOBAPPLICATIONS_PERMISSION")) {
				permissions.setJobAppModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("JOBINTERVIEWS_PERMISSION")) {
				permissions.setJobInterviewsModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("JOBOFFERS_PERMISSION")) {
				permissions.setJobOfferModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}

			else if (modules.equals("JOBVENDORS_PERMISSION")) {
				permissions.setJobVendorsModPermission(request.getPermissions());
				userPermissionConfig.save(permissions);
			}
		}
		if (rolePermissions != null) {
//			rolePermissions.setModules(request.getModules());
			rolePermissions.setPermissions(request.getPermissions());
			masterRepo.save(rolePermissions);
			response.setSuccess(true);
			response.setMessage("Permissions Updated Successfully");
		}

		else {
			response.setSuccess(false);
			response.setMessage("Could not update data");
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse updateOfflineStatus(String infraUserId) {
		ApiResponse response = new ApiResponse(false);
		Employee employee = employeeRepository.getById(infraUserId);
		if (employee != null) {
			if (currentUser.getUserRole().equals("INFRA_ADMIN")
					|| employee.getUserCredientials().getUserRole().equals("INFRA_USER")) {
				employee.setStatus(UserStatus.OFFLINE);
				employeeRepository.save(employee);
//				User user = userRepository.getById(employee.getUserCredientials().getId());
//				user.setStatus(UserStatus.OFFLINE);
//				userRepository.save(user);

				// Employee employeere=new Employee();z

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage("Please send valid employee id ");
				response.setContent(null);
			}
		}

		else {
			response.setSuccess(false);
			response.setMessage("Invalid Employee Id");
			response.setContent(null);
		}

		return response;
	}

	@Override
	public ApiResponse getAllEmployeeList() {
		List<Map> employeeList = employeeRepository.getAllEmployee();
		Map content = new HashMap();
		content.put("employeeList", employeeList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		response.setMessage("Retrieved successfully");
		return response;
	}

	@Override
	public Map<String, Object> employeeBulkUpload(MultipartFile file) {

		Map<String, Object> response = new HashMap<>();

		if (BulkUploadExcelUtil.hasEmployeeHeader(file)) {

			List<EmployeePojo> employeeList = null;
			try {
				employeeList = BulkUploadExcelUtil.excelToEmployeePojo(file.getInputStream());
			} catch (IOException e) {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "", e);
			}

			for (EmployeePojo employeeData : employeeList) {
				User user = new User();
				user.setCreatedAt(new Date());
				user.setUsername(employeeData.getEmail());
				String encodedPassword = new BCryptPasswordEncoder().encode(employeeData.getPassword());
				user.setPassword(encodedPassword);
				// Employee employeere=new Employee();
				Role role = roleRepository.getById(employeeData.getRoleId());
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
				Employee emp = new Employee();
				emp.setCreatedAt(new Date());
				emp.setCreatedBy(userDetail.getUserId());
				emp.seteId(employeeData.geteId());
				emp.setDesignationId(employeeData.getDesignationId());
				emp.setEmail(employeeData.getEmail());
				emp.setFirstName(employeeData.getFirstName());
				emp.setLastName(employeeData.getLastName());
				emp.setLastUpdatedAt(new Date());
				emp.setLocation(employeeData.getLocation());
				emp.setMiddleName(employeeData.getMiddleName());
				emp.setMobileNumber(employeeData.getMobileNumber());
				emp.setPassword(employeeData.getPassword());
				emp.setPosition(employeeData.getPosition());
				emp.setProfileUrl(employeeData.getProfileUrl());
				emp.setReportingTo(employeeData.getReportingTo());
				emp.setRoleId(employeeData.getRoleId());
				emp.setUpdatedBy(userDetail.getUserId());
				/*
				 * User use = userRepository.getUserByIds(employeeData.getUserId()); if (use !=
				 * null) {
				 */ emp.setUserCredientials(user);

				// emp.setUserCredientials(employeeData.getUserId());
				CompanyWings wing = wingRepo.getWingByIds(employeeData.getWing_id());
				if (wing != null) {

					emp.setWings(wing);
				}
				employeeRepository.save(emp);
				response.put("success", "Blocks are updated successfully");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "header data passed in xls is invalid ");
		}

		return response;
	}

}
