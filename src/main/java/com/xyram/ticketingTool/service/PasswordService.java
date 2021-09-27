package com.xyram.ticketingTool.service;

import java.util.Map;

import com.xyram.ticketingTool.apiresponses.ApiResponse;



public interface PasswordService {

	ApiResponse resetPassword(Map<String, Object> passwordRequest);

	//Map forgotPassword(String userName);

	//Map forgotPassword(String userName);



}
