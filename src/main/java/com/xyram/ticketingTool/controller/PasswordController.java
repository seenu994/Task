package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.service.PasswordService;
import com.xyram.ticketingTool.util.AuthConstants;


@CrossOrigin
@RestController
public class PasswordController {

	private final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	PasswordService passwordService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/resetpassword",AuthConstants.DEVELOPER_BASEPATH + "/resetpassword"})
	public ApiResponse resetPassword(@RequestBody Map<String, Object> passwordRequest) {

		logger.info("Received request for reset password");

		return passwordService.resetPassword(passwordRequest);
	}
}
/*
 * @PostMapping("/forgotPassword/{userName}") public Map
 * forgotPassword(@PathVariable String userName) {
 * 
 * logger.info("Received request for forgot password");
 * 
 * return passwordService.forgotPassword(userName); } }
 */
	