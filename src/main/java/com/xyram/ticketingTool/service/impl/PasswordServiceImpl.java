package com.xyram.ticketingTool.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.service.PasswordService;
import com.xyram.ticketingTool.service.UserService;

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
	public Map<String, Object> resetPassword(Map passwordRequest) {

		Map<String, Object> response = new HashMap<>();

		logger.info("Received request for reset password");
		return userRepository.findById((String) passwordRequest.get("userId")).map(user -> {

			String oldPassword = passwordRequest.containsKey("existingPassword")
					&& !StringUtils.isEmpty(passwordRequest.get("existingPassword"))
							? passwordRequest.get("existingPassword").toString()
							: null;
			String newPassword = passwordRequest.containsKey("newPassword")
					&& !StringUtils.isEmpty(passwordRequest.get("newPassword"))
							? passwordRequest.get("newPassword").toString()
							: null;

			if (oldPassword != null && newPassword != null) {
				if (user.getPassword().toString().equals(oldPassword.toString())) {
					user.setPassword(newPassword);
					response.put("message", "Password reset successfully");

					return response;
				} else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Existing Password did not match with our records");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid password request : " + passwordRequest.containsKey("userId"));
			}
		}).orElseThrow(
				() -> new ResourceNotFoundException("User not found with id " + passwordRequest.containsKey("userId")));

	}
}
