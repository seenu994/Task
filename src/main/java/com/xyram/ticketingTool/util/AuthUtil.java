package com.xyram.ticketingTool.util;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.exception.TciketExpetion;

public interface AuthUtil {
	
	String[] NON_SECURE_PATHS = { "/authenticate","/api/user/createAdmin","/updatePassword/{accestoken} ","/forgotPassword/{userName}"};
	String[] ADMIN_PATHS = { "/admin-resource/**" };
	String[] INFRA_PATHS = { "/infra-resource/**" };
	String[] DEVELOPER_PATHS = { "/developer-resource/**" };

	

	public static String getBaseResourcePath(String userRole)  {

		switch (userRole) {	

		case AuthConstants.ROLE_ADMIN :
			return AuthConstants.ADMIN_BASEPATH;
		case AuthConstants.ROLE_INFRA:
			return AuthConstants.INFRA_USER_BASEPATH;
		case AuthConstants.ROLE_DEVELOPER:
			return AuthConstants.DEVELOPER_BASEPATH;
		
		
			
		default:
			throw new TciketExpetion("Basepath not defined for the userRole: " + userRole);
		}
	}



	}
