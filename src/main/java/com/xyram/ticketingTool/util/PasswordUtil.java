package com.xyram.ticketingTool.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtil {

	
	public static String generateRandomPassword() {

		return  RandomStringUtils.randomAlphanumeric(8); 
}
}