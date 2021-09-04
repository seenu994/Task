package com.xyram.ticketingTool.util;

public interface ResponseMessages {
	
	
	String TICKET_NOT_EXIST = "Ticket Id is not exist";
	String TICKET_REOPENED = "Ticket re-opened Sucessfully";
	String TICKET_NOT_RESOLVED = "Ticket is not resolved state. Can't Re-open.";
	String TICKET_COMMENTS_NOT_EXIST = "Comments are missing.";
	String TICKET_ALREADY_REOPEND = "Ticket is already Re-opened.";
	
	String EMPLOYEE_ADDED = "Employee Added Successfully";
	
	String EMAIL_INVALID = "Email is Incorrect";
	
	String MOBILE_INVALID = "Mobile Number Incorrect";
	
	String ROLE_INVALID = "Role is Incorrect";
	
	String EMPLOYEE_UPDATION = "Employee Updated Sucessfully";
	
	String EMPLOYEE_INVALID ="Invalid Employee Id";

	String USERSTATUS_INVALID = "Invalid Status";

	String STATUS_UPDATE = "Status Updated Successfully";

}
