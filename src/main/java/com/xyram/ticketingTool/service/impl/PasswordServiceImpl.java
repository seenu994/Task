package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ForgotPasswordToken;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.email.EmailService;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.ForgotPasswordKey;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.PasswordService;
import com.xyram.ticketingTool.service.UserService;
import com.xyram.ticketingTool.util.PasswordUtil;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {

	private final Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

	/*
	 * @Autowired RPMUserRepository rpmuserRepository;
	 */
	@Value("${APPLICATION_URL}")
	private String application_url;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ForgotPasswordToken tokenRepository;


	@Override
	public  ApiResponse resetPassword(Map passwordRequest) {
		
		ApiResponse responseMessage=new ApiResponse(false);
		Map<String, Object> response = new HashMap<>();

		logger.info("Received request for reset password");
		User user = userRepository.getById((String) passwordRequest.get("userId"));
		

			String oldPassword = passwordRequest.containsKey("existingPassword")
					&& !StringUtils.isEmpty(passwordRequest.get("existingPassword"))
							? passwordRequest.get("existingPassword").toString()
							: null;
			String newPassword = passwordRequest.containsKey("newPassword")
					&& !StringUtils.isEmpty(passwordRequest.get("newPassword"))
							? passwordRequest.get("newPassword").toString()
							: null;

			if (oldPassword != null && newPassword != null) {
				if (new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
					 String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
					 user.setPassword(encodedPassword);
					responseMessage.setSuccess(true);
					responseMessage.setMessage(ResponseMessages.PASSWORD_RESET);
					

					return responseMessage;
				} else {
					responseMessage.setSuccess(false);
					responseMessage.setMessage(ResponseMessages.PASSWORD_INCORRECT);
					

					return responseMessage;
				}
			} else {
				responseMessage.setSuccess(false);
				responseMessage.setMessage(ResponseMessages.INVALID_PASSWORD+passwordRequest.containsKey("userId"));
				

				return responseMessage;
				
			}
	}
	/*@Override
	public ApiResponse forgotPassword(String userName) {
		ApiResponse response=new ApiResponse(false);
		User user = userService.getUserByUsername(userName.toLowerCase());
		if (userName != null && userName.equals(user.getUsername())) {
			user.setPassword(PasswordUtil.generateRandomPassword());
			userRepository.save(user);
			HashMap mailDetails = new HashMap();
			mailDetails.put("toEmail", user.getUsername());
			mailDetails.put("subject", user.getUsername() + ", " + "Here's your new PASSWORD");
			mailDetails.put("message", "Hi " + user.getUsername()
					+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD: \n "
					+ user.getPassword()
					+ "\n\n Thanks for helping us keep your account secure.,\n Xyram Software Solutions Pvt Ltd.");
			emailService.sendMail(mailDetails);
			if (userCache.isPresent("USER", userName.toLowerCase()))
				userCache.remove("USER", userName.toLowerCase());

			//HashMap message = new HashMap();
			response.setMessage(ResponseMessages.FORGOT_PASSOWRD);
			response.setSuccess(true);
			response.setContent(null);
			
			return response;

		}

		else {
			response.setMessage(ResponseMessages.INVALID_EMAIL_ID);
			response.setSuccess(false);
			response.setContent(null);
			
			return response;
		}
	}*/
	@Override
	public ApiResponse forgotPassword(String userName) {
		ApiResponse response=new ApiResponse(false);
	  User user = userService.getUserByUsername(userName);
		/*if (userCache.isPresent("USER", userName.toLowerCase()))
			userCache.remove("USER", userName.toLowerCase());*/
	  
	  ForgotPasswordKey forgotKeyDetails = new ForgotPasswordKey();

		if (user != null) {
			UserRole userrole = user.getUserRole();
			System.out.println("  userrole.value() ==================>  " + userrole.value());
			String userid = user.getId();
			String name = null;
			if (userrole.value() == "DEVELOPER") {
				System.out.println("  DEVELOPER ==================>  " + userrole.value());
				Employee employee = employeeRepository.getbyUserId(userid);
				if (employee != null) {
					name = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
				}
				System.out.println("  name ==================>  " + name);
			}

			if (userrole.value() == "INFRA_USER") {
				Employee employee = employeeRepository.getbyUserId(userid);
				if (employee != null) {
					name = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
				}
				System.out.println("  name ==================>  " + name);
			}

			
			
			if (userrole.value() == "INFRA_ADMIN") {
				System.out.println("  INFRA_ADMIN ==================>  " + userrole.value());
				Employee employee = employeeRepository.getbyUserId(userid);
				if (employee != null) {
					name = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
				}
				System.out.println("  name ==================>  " + name);
			}

			
			/*if (userrole.toString() == "RPM_ADMIN") {
				name = "";
			}*/

			/*if (userCache.isPresent("USER", userName.toLowerCase()))
				userCache.remove("USER", userName.toLowerCase());*/

			UUID uuid = UUID.randomUUID();
			String uuidAsString = uuid.toString();
			if (userName != null && userName.equals(user.getUsername())) {

				forgotKeyDetails.setCreatedAt(new Date());
				forgotKeyDetails.setResetPasswordToken(uuidAsString);
				forgotKeyDetails.setUsername(userName);
				tokenRepository.save(forgotKeyDetails);
/*
				if (userCache.isPresent("USER", userName.toLowerCase()))
					userCache.remove("USER", userName.toLowerCase());*/

				HashMap mailDetails = new HashMap();
				mailDetails.put("toEmail", user.getUsername());
				mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
				mailDetails.put("message", "Hi " + name
						+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
						+  application_url + "/update-password" + "?key=" + uuidAsString
						+ "\n\n Thanks for helping us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
				emailService.sendMail(mailDetails);

				response.setMessage(ResponseMessages.FORGOT_PASSOWRD);
				response.setSuccess(true);
				response.setContent(null);
				
				return response;


			}

			else {
				response.setMessage(ResponseMessages.INVALID_EMAIL_ID);
				response.setSuccess(false);
				response.setContent(null);
				
				return response;

			}
		} else {
			response.setMessage(ResponseMessages.INVALID_EMAIL_ID);
			response.setSuccess(false);
			response.setContent(null);
			
			return response;

		}
	}

	
	@Override
	public User updatePasswordByAccestoken(String accessToken, Map<String, Object> passwordRequest) {

             ForgotPasswordKey token =  tokenRepository.findByAccestoken(accessToken);
             if(token != null) {
            	 
             User user = userService.getUserByUsername(token.getUsername());
			/*if (userCache.isPresent("USER", user.getUsername().toLowerCase()))
				userCache.remove("USER", user.getUsername().toLowerCase());
*/
			Date now = new Date();
			long diff = now.getTime() - token.getCreatedAt().getTime();
			// String diffminutes = map.get("minutes");
			long duration = diff / (60 * 1000);

			if (duration <= 10) {

				String password = passwordRequest.containsKey("password")
						&& !StringUtils.isEmpty(passwordRequest.get("password"))
								? passwordRequest.get("password").toString()
								: null;

				String repassword = passwordRequest.containsKey("repassword")
						&& !StringUtils.isEmpty(passwordRequest.get("repassword"))
								? passwordRequest.get("repassword").toString()
								: null;
				HashMap<String, String> map = new HashMap<String, String>();
				if (password.equalsIgnoreCase(repassword)) {
					//if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
						 String encodedPassword = new BCryptPasswordEncoder().encode(password);
						 user.setPassword(encodedPassword);
					//user.setPassword(password);
					
					UUID uuid = UUID.randomUUID();
					String uuidAsString = uuid.toString();
					user.setAccesskey(null);
					//user.setUniqueDeviceCode(PasswordUtil.generateRandomDeviceCode());

				}
				
				else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "passwords dont match,please try again");
					// return map;
				}

				
			} 
			return userRepository.save(user);
             }
             
             else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token has expired");
			}

		
	}

	
	@Override
	public HashMap<String, String> setPasswordByAccestoken(Map<String, Object> passwordRequest) {

		String password = passwordRequest.containsKey("password")
				&& !StringUtils.isEmpty(passwordRequest.get("password")) ? passwordRequest.get("password").toString()
						: null;
		String repassword = passwordRequest.containsKey("repassword")
				&& !StringUtils.isEmpty(passwordRequest.get("repassword"))
						? passwordRequest.get("repassword").toString()
						: null;
		String username = passwordRequest.containsKey("username")
				&& !StringUtils.isEmpty(passwordRequest.get("username")) ? passwordRequest.get("username").toString()
						: null;
		HashMap<String, String> map = new HashMap<String, String>();
		if (password.equalsIgnoreCase(repassword)) {
			return updatePasswordcg(username, password);

		} else {
			map.put("status", "Password missmatched !.");
			return map;
		}
	}

	public HashMap<String, String> updatePasswordcg(String username, String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (userRepository.updatePassword(username, password) == 1) {
				map.put("status", "password is set successfull!.");
			} else {
				map.put("status", "username is invalid");
			}
		} catch (Exception e) {
			map.put("status", "username is invalid.");

		}
		return map;

	}

	

}

