package com.xyram.ticketingTool.util;

public interface AuthConstants {
	/** Role Constants */
	String ROLE_ADMIN = "TICKETINGTOOL_ADMIN";
	String ROLE_INFRA = "INFRA";
	String ROLE_DEVELOPER = "DEVELOPER";
	
	
	/** URL Constants */
	String ADMIN_BASEPATH = "/admin-resource";
	String INFRA_USER_BASEPATH = "/infra-resource";
	String DEVELOPER_BASEPATH = "/developer-resource";
	

	/** User Constants */
	String NAME = "name";
	String USERNAME = "username";
	String USER_ID = "userId";
	String USER_ROLE = "userRole";
	/** Token Constants */
	long JWT_TOKEN_VALIDITY = 24 * 60 * 60; // token format: hour * minutes * seconds
	String TOKEN_ISSUER = "xyramsoft";
}

	
