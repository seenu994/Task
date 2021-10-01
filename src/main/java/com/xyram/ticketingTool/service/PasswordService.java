package com.xyram.ticketingTool.service;

import java.util.HashMap;
import java.util.Map;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.apiresponses.ApiResponse;

public interface PasswordService {

	ApiResponse resetPassword(Map<String, Object> passwordRequest);

	ApiResponse forgotPassword(String userName);

	User updatePasswordByAccestoken(String accessToken, Map<String, Object> passwordRequest);

	HashMap<String, String> setPasswordByAccestoken(Map<String, Object> passwordRequest);

	// Map forgotPassword(String userName);

	// Map forgotPassword(String userName);

}
