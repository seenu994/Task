package com.xyram.ticketingTool.util;

public interface ResponseMessages {

	String PROJECT_NOTEXIST = "Project is not exist.";
	
	String TICKET_EXIST = "Ticket Id is  exist";

	String TICKET_NOT_EXIST = "Ticket Id is not exist";
	String TICKET_REOPENED = "Ticket re-opened Sucessfully";
	String TICKET_EDITED = "Ticket edited Sucessfully";
	String TICKET_CANCELLED = "Ticket cancelled Sucessfully";
	String TICKET_COMMENTS_ADDED = "Ticket comments added Sucessfully";
	String TICKET_COMMENTS_EDITED = "Ticket comments edited Sucessfully";
	String TICKET_COMMENTS_DELETED = "Ticket comments deleted Sucessfully";
	String TICKET_COMMENTS_RECORD_NOTEXIST = "Ticket comments not exist";
	String TICKET_RESOLVED = "Ticket resolved Sucessfully";
	String TICKET_NOT_RESOLVED = "Ticket is not resolved state. Can't Re-open.";
	String TICKET_COMMENTS_NOT_EXIST = "Comments are missing.";
	String TICKET_ALREADY_REOPEND = "Ticket is already Re-opened.";
	String TICKET_ALREADY_CANCELLED = "Ticket is already Cancelled.";
	String TICKET_ALREADY_RESOLVED = "Ticket is already Resolved.";
	String TICKET_ADDED = "Ticket Added Successfully.";
	String TICKET_NOT_IN_REVIEW = "Ticket is notin review state.";
	
	String TICKET_LIST="Successfully get the list of tickets";
	
	

	String EMPLOYEE_ADDED = "Employee Added Successfully";

	String EMAIL_INVALID = "Email is Incorrect";

	String MOBILE_INVALID = "Mobile Number Incorrect";

	String ROLE_INVALID = "Role is Incorrect";

	String EMPLOYEE_UPDATION = "Employee Updated Sucessfully";

	String EMPLOYEE_INVALID = "Invalid Employee Id";

	String USERSTATUS_INVALID = "Invalid Status";

	String STATUS_UPDATE = "Status Updated Successfully";

	String CLIENT_CREATED = "Client Created Successfully";

	String CLIENT_UPDATED = "Client Updated Successfully";

	String CLIENT_INVALID = "Invalid Client Id";

	String PROJECT_ADDED = "Project Added Successfully";
	
	String PROJECT_LIST="Successfully get the list of projects";
	
	String ClIENT_ID_VALID = "Client Id Is Invalid";

	String PROJECT_EDIT = "Project Edited Successfully";

	String PROJECT_ID_VALID = "Project Id Is Not Exists";

	String PROJECT_MEMBERS_ADDED = "Successfuly added members to the project.";

	String PROJECT_MEMBER_REMOVED = "Successfuly removed member from the project.";

}
