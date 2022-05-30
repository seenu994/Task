
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
	String TICKET_NOT_IN_REVIEW = "Ticket is not in review state.";
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

	String RETURN_FROM_REPAIR = "asset is not underwarrenty && asset is return from repair";

	String ASSET_BILL_EDIT_SUCCESSFULLY = "asset bill edited successfully";

	String ASSET_ID_INVALID = "Asset id is invalid";

	String ASSET_PURCHASE_BILL_ADDED_SUCCESSFULLY = "Asset is underWarrenty and asset purchase bill added successsfully";

	String ASSET_PURCHASE_BILL_EDIT_SUCCESSFULLY = "Asset is underWarrenty and Asset purchase bill edited successfully";

	String ASSET_REPAIR_BILL_EDITED_SUCCESSFULLY = "Asset is underwarrenty and repair bill edited successfully";

	String ID_INVALID = "id is invalid";

	String ASSET_ISSUES_ADDED_SUCCESSFULLY = "Asset issues added successfully";

	String ASSET_ISSUES_EDIT_SUCCESSFULLY = "Asset issues edited successfully";

	String ASSET_ISSUES_ID_IS_INVALID = "invalid asset issueId";

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
	/* Note Response Message */

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
	String BRAND_ADDED = "Brand Added succesfully";

	String BRAND_NOT_ADDED = "Brand not added";

	String BRAND_EDITED = "Brand edited successfully";

	String BRAND_NOT_EDITED = "Invalid Brand Id";

	String BRAND_LIST_RETRIVED = "List retrieved successfully";

	String RAM_ADDED = "Ram size added successfully";

	String RAM_NOT_ADDED = "Ram size not added";

	String RAM_SIZE_EDITED = "Ram size edited successfully";

	String RAM_NOT_EDITED = "Ram size not edited";

	String RAM_LIST_RETRIVED = "Ram list retrieved successfully";

	// Download of asset
	String ASSET_DIRECTORY = "/Documents/assetDetails/";

	String BASE_DIRECTORY = "../webapps";

	String BILL_EDITED_SUCCESSFULLY = "asset is not underwarrenty and purchase bill edited successfully";

	String ASSET_RETURN_FROM_REPAIR = "Asset is underwarrenty and asset is returned from reapir";

	String BILL_ADDED_SUCCESSFULLY = "asset is not underwarrenty and purchase bill added successfully";

	String RETURNS_REPAIR = "asset is returned from repair";

	String REPAIR_BILL_EDITED_SUCCESSFULLY = "Asset is not underwarrenty and repair bill edited successfully";

	String TIME_SHEET_DELETED = "Time sheet deleted successfully";

	String TIME_SHEET_NOT_DELETED = "time sheet not deleted";

	String HR_CALENDAR_DIRECTORY = "/Documents/hrCalendarDetails/";

	String PURCHASE_BILL_EDITED_SUCCESSFULLY = "asset is not underwarrenty and purchase bill edited successfully";

	String CITY_EDITED = "city details edited Successfully";

	String CITY_DETAILS_INVALID = "Invalid City Details ";

	String CITY_ADDED = "City Added Successfully";

	String CITY_NOT_ADDED = "City not added";
	String COUNTRY_ADDED = "Country Added Successfully";
	String COUNTRY_NOT_ADDED = "Country not added";
	String COUNTRY_EDITED = "Country Details  edited Successfully";
	String CITY_LIST_RETRIVED = "List retrieved Successfully";
	String COUNTRY_DETAILS_INVALID = "Country details Invalid";
	String COUNTRY_LIST_RETRIVED = "List retrieved Successfully";

	String WINGS_ADDED = "Wings added successfully";
	String WINGS_NOT_ADDED = "Can't able to add Wings ";
	String WINGS_UPDATED = "Wing is updated successfully";
	String WINGS_NOT_UPDATED = "Check Wings Id";

	// Location Response Messages
	String LOC_ADDED = "Location added successfully";
	String DELETE_LOCATION = "Location deleted successfully";
	String LOC_NOT_ADDED = "can't able to add loc";
	String LOC_UPDATED = "Location updated successfully";
	String LOCATION_NOT_FOUND = "Location not found";

	String ADDED_ISSUE = "Issue is added Successfully";

	String ISSUE_NOT_ADDED = "Issue not added";

	String EDIT_ISSUE = "Issue is edited Successfully";

	String ISSUES_LIST_RETRIVED = "List retrieved Successfully";

	String SOFTWAREMASTER_NOT_ADDED = "software master not added";

	String DESIGNATION_EDITED = "Designation is edited Successfully";

	String DESIGNATION_DETAILS_INVALID = "Invalid Designation details";

	String ASSET_REPAIR_BILL_ADDED_SUCCESSFULLY = "Asset is underwarrenty and asset repair bill added successfully";

	String REPAIR_BILL_ADDED_SUCCESSFULLY = "Asset is not underwarrenty and asset repair bill added successfully";

	String ASSET_PURCHASE_BILL_EDITED_SUCCESSFULLY = "Asset is underwarrenty and purchase bill edited successfully";

	String HRCALENDER_LIST_RETRIVED = "List retrieved Successfully";

	// job openings
	String JOB_TITLE = "Job title is mandatory";

	String JOB_TITLE_CHAR = "Title should be character only";

	String JOB_TITLE_LEN = "Job title length should be Min 3 cahracter and Max 100 character";

	String JOB_TITLE_DES = "Description is mandatory";

	String JOB_TITLE_DES_LEN = "Description length should be Min 7 and Max 5000 ";

	String JOB_CODE = "Job code is mandatory";

	String JOB_TTOTAL_OPENINGS = "Total openings is mandatory";

	String WINGS_MAN = "Wing is mandatory";

	String WINGS_EXI = "Wing does not exist";

	String JOB_SKILL = "Job skills are mandatory";

	String JOB_SKILL_EXI = "Skill does not exist";

	// Employee service
	String EMP_CODE = "Employee code already Assigned to Existing employee ";

	String FIRST_NAME_MAN = "FirstName is mandatory";

	String FIRST_NAME_CHAR = "Name should be character only";

	String LAST_NAME_CHAR = "LastName should be character only";

	String LAST_NAME_MAN = "LastName is mandatory";

	String LOC_MAN = "Location is mandatory";

	String LOC_NOT_VALID = "Location is not valid";

	String ROLE_ID_MAN = "RoleId is mandatory";

	String ROLE_ID_NOT_VAL = "RoleId is not valid";

	String DES_ID_MAN = "Designation is mandatory";

	String DES_ID_NOT_VAL = "Designation is not valid";

	String POSITION_MAN = "position is mandatory";

	String POSITION_NOT_VAL = "Position is not available";

	String WING_MAN = "Wing is mandatory";

	String WING_NOT_EXI = "Wing does not exist";

	String MOB_NUM_MAN = "MobileNumber is mandatory";

	String INCORRECT_MOB = "In correct mobile number";

	String MAILID_MAN = "Mail id is mandatory";

	String INVAL_MAIL_ID = "Invalid EmailId";

	String MAIL_ID_EXI = "email already exists!!!";

	String Reminder_ADDED = "Reminder added successfully";
	String Reminder_NOT_ADDED = "Reminder not added. Object is not proper";
	String Reminder_DELETED = "Reminder deleted successfully";
	String Reminder_NOT_DELETED = "Reminder not deleted";
	String Reminder_UPDATED = "Reminder updated successfully";
	String Reminder_NOT_UPDATED = "Reminder not updated";
	String Reminder_LIST_RETRIEVED = "Reminder list retrieved successfully";
	String Reminder_LIST_NOT_RETRIEVED = "Reminder list not retrieved";

	String UPLOAD_IMAGE = "unable to upload image";

	String NOT_VALID = "Reportor is not valid";

	String Pro_Name_Man = "Project name is mandatory";

	String Pro_desc_man = "Project description is mandatory";

	String Pro_desc_len = "Project description length should be minimum 15 and maximum 5000";

	String Pro_name_len = "Project name should be minimum 3 characters";

	String STATUS_UPDATED_SUCCESSFULLY = "asset issue status updated successfully";

	String TIME_SHEET_NOT_FOUND = "TimeSheet not found!!";

	String Join_date_man = "Date of join is mandatory";

	String DOJ_NOT_VAL = "Date Of Join is not valid";

//	String ANNOUNCEMENT_STATUS_CHANGED = "Announcement status changed successfully";
//	String ANNOUNCEMENT_STATUS_NOT_CHANGED = "Announcement status not changed";
//	String ANNOUNCEMENT_DELETED = "Announcement deleted successfully";
//	String ANNOUNCEMENT_NOT_DELETED = "Announcement not deleted";
//	String ANNOUNCEMENT_LIST_RETREIVED = "Announcement list retreived successfully";
//	String ANNOUNCEMENT_LIST_NOT_RETREIVED = "Announcement list not retreived";
//	String ANNOUNCEMENT_DETAILS_RETREIVED = "Announcement details retreived successfully";
//	String ANNOUNCEMENT_DETAILS_NOT_RETREIVED = "Announcement details not retreived";

}
