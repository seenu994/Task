package com.xyram.ticketingTool.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.AccountActivate;
import com.xyram.ticketingTool.entity.CheckToken;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.service.PasswordService;
import com.xyram.ticketingTool.util.AuthConstants;

@CrossOrigin
@RestController
public class PasswordController {

	private final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	PasswordService passwordService;

	@PostMapping("/resetpassword" )
	public ApiResponse resetPassword(@RequestBody Map<String, Object> passwordRequest) {

		logger.info("Received request for reset password");

		return passwordService.resetPassword(passwordRequest);
	}

	@PostMapping("/forgotPassword/{userName}")

	// @HystrixCommand(fallbackMethod="care365method")
	public ApiResponse forgotPassword(@PathVariable String userName) {

		logger.info("Received request for forgot password");
		return passwordService.forgotPassword(userName);
	}

	@PostMapping("/updatePassword/{accestoken}")
	// @HystrixCommand(fallbackMethod="care365method")
	public Map updatePassword(@PathVariable String accestoken,
			@RequestBody(required = true) Map<String, Object> passwordRequest) {

		logger.info("Received request for update password");

		Map response = new HashMap<>();
		User user = passwordService.updatePasswordByAccestoken(accestoken, passwordRequest);

		if (user != null) {
			return response;
		}

		return response;
	}
	
	@PostMapping("/checkTokenValid")
	public HashMap<String, String> checkTokenValidcg(@RequestBody CheckToken requestBody) {
		return passwordService.checkTokenValid(requestBody.getKey());
	}

}

