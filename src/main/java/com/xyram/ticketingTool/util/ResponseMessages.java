	
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
	String SHEETS_EDITED = "Time Sheets edited successfully";
	String SHEETS_NOT_EDITED = "Time Sheets not edited";
	String SHEETS_APPROVED = "Time Sheets are approved successfully";
	String SHEETS_NOT_APPROVED = "Few or All Time Sheets are not approved";
	String SHEETS_REJECTED = "Time Sheets rejected successfully";
	String SHEETS_NOT_REJECTED = "Few or All Time Sheets not rejected";

	String ASSET_NOT_EDITED = "Asset not edited";

	String ASSET_EDITED = "Asset edited successfully";

	String ASSET_ISSUES_ADDED = "Asset Issues added";
	
	String ASSET_ISSUES_NOT_ADDED = "Asset Issues added";
	
    String ASSET_ISSUES_EDITED = "Asset Issues added";
	
	String ASSET_ISSUES_NOT_EDITED = "Asset Issues added";
	
	String ASSET_ISSUES_STATUS_CHANGED = "Asset issues status changed";
	
	 String ASSET_ISSUES_STATUS__NOT_CHANGED = "Asset issues status not changed";

	String ASSET_BILL_ADDED = "Asset bill added";
	String ASSET_BILL_ADDED_SUCCESSFULLY = "Asset bill added successfully";
	
	String ASSET_BILL_NOT_ADDED = "Asset bill not  added";
	
    String ASSET_BILL_EDITED = "Asset bill added";
    String ASSET_IS_UNDERWARRENTY_NO_NEED_TO_PAY_ANY_AMOUNT = "asset is underwarrenty no need to pay any amount";

	
	
	String VENDORID_IN_VALID = "VendorId is in valid";

	String ADD_ISSUEID = "Enter issueId";

	String ISSUEID_IN_VALID = "issue id is invalid";

	String RETURN_FROM_REPAIR = "return from repair";

	String ASSET_BILL_EDIT_SUCCESSFULLY = "asset bill edited successfully";

	String ASSET_ID_INVALID = "Asset id is invalid";

	String ASSET_PURCHASE_BILL_ADDED_SUCCESSFULLY = "Asset purchase bill added successsfully";

	String ASSET_PURCHASE_BILL_EDIT_SUCCESSFULLY = "Asset purchase bill edited successfully";

	String ASSET_REPAIR_BILL_ADDED_SUCCESSFULLY = "Asset repair bill added successsfully";

	String ASSET_REPAIR_BILL_EDITED_SUCCESSFULLY = "Asset repair bill edited successfully";

	String ID_INVALID = "id is invalid";

	String ASSET_ISSUES_ADDED_SUCCESSFULLY = "Asset issues added successfully";

	String ASSET_ISSUES_EDIT_SUCCESSFULLY = "Asset issues edited successfully";

	String ASSET_ISSUES_ID_IS_INVALID = "A sset issue id is invalid";

	String ASSET_ADDED = "Asset added successfully";

	String ASSET_NOT_ADDED = "Asset not added";

	String ASSET_SOFTWARE_ADDED = "Asset software added successfully";

	String ASSET_SOFTWARE_NOT_ADDED = "Asset Softwarec not added";

	String ASSET_EMPLOYEE_ADDED = "Asset Employee added successfully";

	String ASSET_EMPLOYEE_NOT_ADDED = "Asset Employee not added";

	String ASSET_EMPLOYEE_EDITED = "Asset Employee edited successfully";

	String VENDOR_ADDED = "Vendor added successfully";

	String VENDOR_DETAILS_EDIT = "Vendor details edited successfully";

	String VENDOR_DETAILS_INVALID = "Invalid Vendor Details";

	String VENDORSTATUS_INVALID = "Invalid status";

	String ASSETVENDOR_STATUS_UPDATED = " Status Updated Successfully";

	String ADDED_SOFTWAREMASTER = "Software  added Successfully";

	String SOFTWAREMASTER_EDITED = "Software master details is edited Successfully";

	String SOFTWARE_STATUS_UPDATED = "Software master status updated Successfully";

	String SOFTWARE_DETAILS_INVALID = "Software Master Details Invalid";

	String GETALL_SOFTWAREMATER_LIST = "Get all software master list";

	String ASSET_RETURNED_FROM_REPAIR_SUCCESSFULLY = "Asset is returned from repair successfully";

	String RETURN_REPAIR = "return asset from repair";

	String ASSET_ISSUE_STATUS_INVALID = "Asset issue status is invalid";

	String ASSET_ISSUE_STATUS_UPDATE = "Asset issue status in updated";

	String GETALL_VENDOR_LIST = "Asset vendor list";
	String RETURN_DAMAGE = "Asset returned from damage";
	
	String ASSET_ISSUE_DIRECTORY = "asset issue directory";

	String VENDOR_NOT_ADDED = "Vendor not added successfully";
                /* Note Response Message*/
	
	String NOTES_CREATED = "Created notes successfully";
	String NOTES_NOT_CREATED = "Notes not created";

	String NOTES_UPDATED = "Notes updated successfully";
	String NOTES_NOT_UPDATED = "Notes are not updated";
	
	String NOTES_RETRIEVED = "Notes Retrieved Successfully";
	String NOTES_NOT_RETRIEVED = "Could not retrieve data";

	String DELETE_NOTES = "Notes deleted succuessfull";
	String NOTES_NOT_FOUND = "Notes not found";

	String DATE_FEILD = "Date feild cannot be empty";
	String ADDED_DESIGNATION = "Designation is added Successfully";

	

	
	//Download of asset
String ASSET_DIRECTORY ="/Documents/assetDetails/";
	
	String BASE_DIRECTORY ="../webapps";

	

	

	

	
	
	
//	String ANNOUNCEMENT_STATUS_CHANGED = "Announcement status changed successfully";
//	String ANNOUNCEMENT_STATUS_NOT_CHANGED = "Announcement status not changed";
//	String ANNOUNCEMENT_DELETED = "Announcement deleted successfully";
//	String ANNOUNCEMENT_NOT_DELETED = "Announcement not deleted";
//	String ANNOUNCEMENT_LIST_RETREIVED = "Announcement list retreived successfully";
//	String ANNOUNCEMENT_LIST_NOT_RETREIVED = "Announcement list not retreived";
//	String ANNOUNCEMENT_DETAILS_RETREIVED = "Announcement details retreived successfully";
//	String ANNOUNCEMENT_DETAILS_NOT_RETREIVED = "Announcement details not retreived";
	
	
}
