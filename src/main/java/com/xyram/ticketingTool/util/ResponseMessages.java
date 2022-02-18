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
	String TICKET_RESOLUTION_UPDATED = "Ticket resolution updated successfully.";
	String TICKET_RESOLUTION_NOT_UPDATED = "Ticket resolution not updated.";

	String TICKET_LIST = "Successfully get the list of tickets";

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

	String PROJECT_LIST = "Successfully get the list of projects";
	String PROJECT_NOT_ASSIGNED = "No project assigned";

	String ClIENT_ID_VALID = "Client Id Is Invalid";

	String PROJECT_EDIT = "Project Edited Successfully";

	String PROJECT_ID_VALID = "Project Id Is Not Exists";

	String PROJECT_MEMBERS_ADDED = "Successfuly added members to the project.";

	String PROJECT_MEMBER_REMOVED = "Successfuly removed member from the project.";

	String CLIENT_LIST = "Successfully get the list of Clients";

	String TICKET_ASSIGNED = "Ticket assigned successfully";
	String TICKET_INPROGRESS = "Ticket status changed to Inprogress";
	String TICKET_ALREADY_INPROGRESS = "Ticket already Inprogress";
	String TICKET_NOT_IN_ASSIGNE_STATE = "Ticket is not in assigne status";
	String TICKET_REASSIGNED = "Ticket Rassigned successfully";

	String NOT_AUTHORISED = "You are not authorised to access this information.";
	String UN_AUTHORISED = "You are not authorised to modify this information.";

	String ONHOLD_STATUS = "Ticket Status changed successfully.";

	String PASSWORD_RESET = "Password is reset successfully.";
	String PASSWORD_INCORRECT = "Existing password did not match.";
	String INVALID_USERID = "User id is invalid";
	String INVALID_PASSWORD = "Invalid password request : ";

	String FORGOT_PASSOWRD = "Mail Sent Successfully : ";

	String INVALID_EMAIL_ID = "invalid  email id : ";

	String TEST_SAMPLE_1 = "gggggggggggggggg";
	String TEST_SAMPLE_2 = "hhhhhhhhhhhhhhhh";

	String NOTIFICATIONS_EXISTS = "list of notifications";
	String NOTIFICATIONS_NOT_EXISTS = "There is no notifications exists";
	String NOTIFICATIONS_CLEARED = "All notifications cleared";

	String DELETE_ATTACHMENT = "Attachments Deleted Successfully";

	String EMPLOYEE_PROFILE_UPDATION = "Profile Updated Successfully";
	String SCHEDULEINIERVIEW_UPDATED = "ScheduleInterview Updated Successfully";
	String SCHEDULEINIERVIEW_INVALID = "Invalid interview Id";
	String TICKET_CREATED = "Ticket created successfully";
	
	String ARTICLE_ADDED = "Artcle created successfully";
	String ARTICLE_NOT_ADDED = "Artcle not created";
	String ARTICLE_EDITED = "Artcle edited successfully";
	String ARTICLE_NOT_EDITED = "Artcle not edited";
	String ARTICLE_STATUS_CHANGED = "Artcle status changed successfully";
	String ARTICLE_STATUS_NOT_CHANGED = "Artcle status not changed";
	String ARTICLE_DELETED = "Artcle deleted successfully";
	String ARTICLE_NOT_DELETED = "Artcle not deleted";
	String NOT_AUTHORIZED = "Not authorised";
	String ARTICLE_LIST_RETREIVED = "Artcle list retreived successfully";
	String ARTICLE_LIST_NOT_RETREIVED = "Artcle list not retreived";
	String ARTICLE_DETAILS_RETREIVED = "Artcle details retreived successfully";
	String ARTICLE_DETAILS_NOT_RETREIVED = "Artcle details not retreived";
	
	String ANNOUNCEMENT_ADDED = "Announcement created successfully";
	String ANNOUNCEMENT_NOT_ADDED = "Announcement not created";
	String ANNOUNCEMENT_EDITED = "Announcement edited successfully";
	String ANNOUNCEMENT_NOT_EDITED = "Announcement not edited";
	String ANNOUNCEMENT_STATUS_CHANGED = "Announcement status changed successfully";
	String ANNOUNCEMENT_STATUS_NOT_CHANGED = "Announcement status not changed";
	String ANNOUNCEMENT_DELETED = "Announcement deleted successfully";
	String ANNOUNCEMENT_NOT_DELETED = "Announcement not deleted";
	String ANNOUNCEMENT_LIST_RETREIVED = "Announcement list retreived successfully";
	String ANNOUNCEMENT_LIST_NOT_RETREIVED = "Announcement list not retreived";
	String ANNOUNCEMENT_DETAILS_RETREIVED = "Announcement details retreived successfully";
	String ANNOUNCEMENT_DETAILS_NOT_RETREIVED = "Announcement details not retreived";
	
	String SHEETS_ADDED = "Time Sheets added successfully";
	String SHEETS_NOT_ADDED = "Time Sheets not added";
	String SHEETS_OBJECT_ISSUE = "Time Sheets records are not proper or empty";
//	String ANNOUNCEMENT_EDITED = "Announcement edited successfully";
//	String ANNOUNCEMENT_NOT_EDITED = "Announcement not edited";
//	String ANNOUNCEMENT_STATUS_CHANGED = "Announcement status changed successfully";
//	String ANNOUNCEMENT_STATUS_NOT_CHANGED = "Announcement status not changed";
//	String ANNOUNCEMENT_DELETED = "Announcement deleted successfully";
//	String ANNOUNCEMENT_NOT_DELETED = "Announcement not deleted";
//	String ANNOUNCEMENT_LIST_RETREIVED = "Announcement list retreived successfully";
//	String ANNOUNCEMENT_LIST_NOT_RETREIVED = "Announcement list not retreived";
//	String ANNOUNCEMENT_DETAILS_RETREIVED = "Announcement details retreived successfully";
//	String ANNOUNCEMENT_DETAILS_NOT_RETREIVED = "Announcement details not retreived";
	
	
}
