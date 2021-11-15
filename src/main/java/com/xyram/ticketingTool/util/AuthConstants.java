package com.xyram.ticketingTool.util;

public interface AuthConstants {
	/** Role Constants */
	String ROLE_ADMIN = "TICKETINGTOOL_ADMIN";
	String ROLE_INFRA = "INFRA";
	String ROLE_INFRA_ADMIN = "INFRA_ADMIN";
	String ROLE_INFRA_USER="INFRA_USER";
	String ROLE_DEVELOPER = "DEVELOPER";
	String ROLE_HR_ADMIN = "HR_ADMIN";
	String ROLE_HR = "HR";
	String ROLE_JOB_VENDOR = "JOB_VENDOR";
	String ROLE_ACCOUNTANT = "ACCOUNTANT";

	/** URL Constants */
	String ADMIN_BASEPATH = "/admin-resource";
	String INFRA_ADMIN_BASEPATH = "/infra-admin-resource";
	String INFRA_USER_BASEPATH = "/infra-resource";
	String DEVELOPER_BASEPATH = "/developer-resource";
	String HR_ADMIN_BASEPATH = "/hr-admin-resource";
	String HR_BASEPATH = "/hr-resource";
	String JOB_VENDOR_BASEPATH = "/job-vendor-resource";
	String ACCOUNTANT_BASEPATH = "/account-resource";
	

	/** User Constants */
	String NAME = "name";
	String USERNAME = "username";
	String USER_ID = "userId";
	String USER_ROLE = "userRole";
	String SCOPE_ID="scopeId";
	
	
	/** Token Constants */
	long JWT_TOKEN_VALIDITY = 24 * 60 * 60; // token format: hour * minutes * seconds
	String TOKEN_ISSUER = "xyramsoft";

}
