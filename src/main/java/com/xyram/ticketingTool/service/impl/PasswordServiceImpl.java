package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.PasswordService;
import com.xyram.ticketingTool.service.UserService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {

	private final Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

	/*
	 * @Autowired RPMUserRepository rpmuserRepository;
	 */
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;

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

	}

