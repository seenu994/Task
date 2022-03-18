package com.xyram.ticketingTool.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectMemberRepository;
import com.xyram.ticketingTool.Repository.StoryAttachmentsRespostiory;
import com.xyram.ticketingTool.Repository.TimesheetRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.TimesheetStatus;
import com.xyram.ticketingTool.fileupload.FileTransferService;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.response.ReportExportResponse;
import com.xyram.ticketingTool.service.TimesheetService;
import com.xyram.ticketingTool.util.ExcelUtil;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class TimesheetServiceImpl implements TimesheetService{

	@Autowired
	CurrentUser currentUser;

	@Autowired
	TimesheetServiceImpl timesheetServiceImpl;

	@Autowired
	TimesheetRepository timesheetRepository;
	
	@Autowired
	EmployeeRepository empRepository;
	
	@Value("${time-sheet-base-url}")
	private String timeSheetAttachmentBaseUrl;

	@Autowired
	FileTransferService fileUploadService;
	
	@Autowired
	ProjectMemberRepository projectMemberRepository;
	
	@Override
	public ApiResponse createTimeSheets(List<TimeSheet> timesheets) {
		
		ApiResponse response = new ApiResponse(false);
		if(timesheets != null) {
			Boolean problemEncountered = false;
			String problemIs = "";
			if(timesheets.size() > 0) {
				for (TimeSheet sheet : timesheets) {
					
					if(sheet.getHoursSpent() > 24 || sheet.getTaskName().length() == 0 || sheet.getTaskDescription().length() == 0) {
						problemEncountered = true;
						problemIs = "HoursSPent Should not exceed 24 Hrs or TaskName-Description are missing";
				    	break;
				    }
					
				    sheet.setCreatedAt(new Date());
				    sheet.setCreatedBy(currentUser.getUserId());
				    sheet.setUpdatedBy(currentUser.getUserId());
				    sheet.setLastUpdatedAt(new Date());	
				    
				    if(sheet.getProjectId()!=null) {
				    	ProjectMembers projectMember = null;
						projectMember = projectMemberRepository.getMemberInProject(currentUser.getScopeId(), sheet.getProjectId(),null);
						if (projectMember == null) {
							problemEncountered = true;
							problemIs += "Project not exist";
							break;
						}
				    }
				    
				    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				    String strDate = dateFormat.format(sheet.getTimeSheetDate());  
				    Date tmDate = null;
				    try {
				    	tmDate=new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						problemEncountered = true;
						problemIs += "-Date";
						break;
					}
				    if(tmDate!=null) {
				    	try {
							if (tmDate.after(getDateWithoutTimeUsingFormat())) {
								problemEncountered = true;
							    break;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	sheet.setTimeSheetDate(tmDate);
				    }
				    else
				    	break;
				    
				    List<TimeSheet> timeSheetList = timesheetRepository.getAllSheetsByDate(sheet.getTimeSheetDate());
			    	if(timeSheetList != null) {
			    		Float totalHours = sheet.getHoursSpent();
			    		for (final TimeSheet sheetEntity : timeSheetList) {
			    			totalHours += sheetEntity.getHoursSpent();
			    		}
			    		if(totalHours > 24) {
			    			problemEncountered = true;
			    			problemIs += "Per day 24 Hours allowed";
			    			break;
			    		}
			    	}
				    
				    Employee employee = empRepository.getByEmpId(currentUser.getScopeId());
				    Employee reportor = empRepository.getByEmpId(employee.getReportingTo());
				    sheet.setApproverId(reportor.getUserCredientials().getId());
				    if(sheet.getEmployeeId() == null) {
				    	sheet.setEmployeeId(currentUser.getUserId());
				    }
				    timesheetRepository.save(sheet);
				}
				if(problemEncountered)
					response.setMessage("Some thing went Wrong"+" : "+problemIs);
				else
					response.setMessage("Sheets saved successfully");

			    response.setSuccess(true);
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
			}
			
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
		}
		return response;
	}
	
	public static Date getDateWithoutTimeUsingFormat() 
	  throws ParseException {
	    SimpleDateFormat formatter = new SimpleDateFormat(
	      "yyyy-MM-dd");
	    return formatter.parse(formatter.format(new Date()));
	}

	@Override
	public ApiResponse editTimeSheets(List<TimeSheet> timesheets) {
		ApiResponse response = new ApiResponse(false);
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				Boolean problemEncountered = false;
				String problemIs = "";
				for (TimeSheet sheet : timesheets) {
					
					TimeSheet sheetEntity = timesheetRepository.getById(sheet.getTimeSheetId());
					if(sheetEntity == null){
						problemEncountered = true;
						problemIs += "Sheet Not Found";
						break;
					}
				    if(sheet.getHoursSpent() > 24 || sheet.getTaskName().length() == 0 || sheet.getTaskDescription().length() == 0) {
				    	problemEncountered = true;
						problemIs += "Wrong Details in Hours Spent-TaskName-Description";
				    	break;
				    }
				    if(sheet.getTaskId() != null) {
				    	sheetEntity.setTaskId(sheet.getTaskId());
				    }
					
				    sheetEntity.setUpdatedBy(currentUser.getUserId());
				    sheetEntity.setLastUpdatedAt(new Date());	
				    sheetEntity.setStatus(TimesheetStatus.PENDING);
				    Employee employee = empRepository.getByEmpId(currentUser.getScopeId());
				    Employee reportor = empRepository.getByEmpId(employee.getReportingTo());
				    sheetEntity.setApproverId(reportor.getUserCredientials().getId());
				    
				    sheetEntity.setHoursSpent(sheet.getHoursSpent());
				    sheetEntity.setTaskName(sheet.getTaskName());
				    sheetEntity.setTaskDescription(sheet.getTaskDescription());
				    
				    if(sheet.getProjectId()!=null) {
				    	ProjectMembers projectMember = null;
						projectMember = projectMemberRepository.getMemberInProject(currentUser.getScopeId(), sheet.getProjectId(),null);
						if (projectMember == null) {
							problemEncountered = true;
							problemIs += "Wrong in Project";
							break;
						}
						sheetEntity.setProjectId(sheet.getProjectId());
				    }
				    
				    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				    String strDate = dateFormat.format(sheet.getTimeSheetDate());  
				    Date tmDate = null;
				    try {
				    	tmDate=new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
				    if(tmDate!=null) {
				    	try {
							if (tmDate.after(getDateWithoutTimeUsingFormat())) {
								problemEncountered = true;
								problemIs += "Wrong in Date Format";
							    break;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	sheetEntity.setTimeSheetDate(tmDate);
				    }
				    else
				    	break;
				    
				    List<TimeSheet> timeSheetList = timesheetRepository.getAllSheetsByDate(sheet.getTimeSheetDate());
			    	if(timeSheetList != null) {
			    		Float totalHours = sheet.getHoursSpent();
			    		for (final TimeSheet entity : timeSheetList) {
			    			if(sheet.getTimeSheetId() != entity.getTimeSheetId())
			    			totalHours += entity.getHoursSpent();
			    		}
			    		if(totalHours > 24) {
			    			problemEncountered = true;
							problemIs += "Total Hours are Greater than 24 Hours";
			    			break;
			    		}
			    	}
				    
				    timesheetRepository.save(sheetEntity);
				    
				}
				if(problemEncountered)
					response.setMessage("Some thing went Wrong"+" : "+problemIs);
				else
					response.setMessage("Sheet Saved Successfully");
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
			}
			
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
		}
		return response;
	}

	@Override
	public ApiResponse approveTimeSheets(List<String> timesheets) {
		ApiResponse response = new ApiResponse(false);
		
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				Boolean sheetHaveIssues = false;
				for (String sheetId : timesheets) {
				   
				    TimeSheet sheet = timesheetRepository.getById(sheetId);
				    if(sheet != null) {
				    	if(!sheet.getApproverId().equals(currentUser.getUserId())) {
				    		response.setSuccess(false);
							response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
							return response;
				    	}
				    	sheet.setStatus(TimesheetStatus.APPROVED);
					    timesheetRepository.save(sheet);
				    }else {
				    	sheetHaveIssues = true;
				    }
				}
				if(sheetHaveIssues) {
					response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_NOT_APPROVED);
				}else {
					response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_APPROVED);
				}
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
			}
			
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
		}
		return response;
	}

	@Override
	public ApiResponse rejectTimeSheets(List<String> timesheets,String reason) {
		ApiResponse response = new ApiResponse(false);
		
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				Boolean sheetHaveIssues = false;

				for (String sheetId : timesheets) {
				   
				    TimeSheet sheet = timesheetRepository.getById(sheetId);
				    if(sheet != null) {
				    	if(!sheet.getApproverId().equals(currentUser.getUserId())) {
				    		response.setSuccess(false);
							response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
							return response;
				    	}
				    	sheet.setReason(reason);
				    	sheet.setStatus(TimesheetStatus.REJECTED);
					    timesheetRepository.save(sheet);
				    }else {
				    	sheetHaveIssues = true;
				    }
				    
				}
				if(sheetHaveIssues){
					response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_NOT_REJECTED);
				}else {
					response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_REJECTED);
				}
			}else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
			}
			
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.SHEETS_OBJECT_ISSUE);
		}
		return response;
	}

	@Override
	public ApiResponse getAllMyTimeSheets(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(true);

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setSuccess(false);
		}
		
		String projectId = filter.containsKey("projectId") ? ((String) filter.get("projectId"))
				: null;
		String statusStr = filter.containsKey("status") ? ((String) filter.get("status"))
				: null;

				
		TimesheetStatus status = null;
		try {
			status = filter.containsKey("status") ? TimesheetStatus.toEnum((String) filter.get("status")) : null;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					filter.get("status").toString() + " is not a valid status");
		}
		
		Page<Map> timeSheetList = timesheetRepository.getAllMyTimeSheets(currentUser.getUserId(), projectId, fromDateStr, toDateStr, status, pageable);
		Map content = new HashMap();
		content.put("timeSheetList", timeSheetList);
//		content.put("totalCount", timesheetRepository.getAllMyTimeSheetsCount(currentUser.getUserId(), projectId, fromDateStr, toDateStr, statusStr));
		// ApiResponse response = new ApiResponse(true);
		response.setMessage("List Retrieved");
		response.setSuccess(true);
		response.setContent(content);
		
		return response;
	}

	@Override
	public ApiResponse getAllMyTeamTimeSheets(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(true);

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setSuccess(false);
		}
		
		String projectId = filter.containsKey("projectId") ? ((String) filter.get("projectId"))
				: null;
		String statusStr = filter.containsKey("status") ? ((String) filter.get("status"))
				: null;
		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId"))
				: null;
				
		TimesheetStatus status = null;
		try {
			status = filter.containsKey("status") ? TimesheetStatus.toEnum((String) filter.get("status")) : null;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					filter.get("status").toString() + " is not a valid status");
		}
		
		Page<Map> timeSheetList = timesheetRepository.getAllMyTeamTimeSheets(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, status, pageable);
		Map content = new HashMap();
		content.put("timeSheetList", timeSheetList);
//		content.put("totalCount", timesheetRepository.getAllMyTeamTimeSheetsCount(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, statusStr));
		// ApiResponse response = new ApiResponse(true);
		response.setMessage("List Retrieved");
		response.setSuccess(true);
		response.setContent(content);
		
		return response;
	}

	@Override
	public ReportExportResponse downloadAllMyTimeSheets(Map<String, Object> filter) {
		ReportExportResponse response = new ReportExportResponse();

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setStatus("failure");
		}
		
		String projectId = filter.containsKey("projectId") ? ((String) filter.get("projectId"))
				: null;
		String statusStr = filter.containsKey("status") ? ((String) filter.get("status"))
				: null;

				
		TimesheetStatus status = null;
		try {
			status = filter.containsKey("status") ? TimesheetStatus.toEnum((String) filter.get("status")) : null;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					filter.get("status").toString() + " is not a valid status");
		}
		
		List<Map> timeSheetList = timesheetRepository.downloadAllMyTimeSheets(currentUser.getUserId(), projectId, fromDateStr, toDateStr, status);
		
		Map<String, Object> fileResponse = new HashMap<>();

		Workbook workbook = prepareExcelWorkBook(timeSheetList);
		
		byte[] blob = ExcelUtil.toBlob(workbook);

		fileResponse.put("fileName", "timesheet-"+fromDateStr+"-"+toDateStr+".xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		response.setStatus("success");
		response.setMessage("report exported Successfully");
		
		return response;
	}
	


	

	@Override
	public ReportExportResponse downloadAllMyTeamTimeSheets(Map<String, Object> filter) {
		// TODO Auto-generated method stub downloadAllMyTeamTimeSheets
		ReportExportResponse response = new ReportExportResponse();

		String fromDateStr = filter.containsKey("fromDate") ? ((String) filter.get("fromDate")).toLowerCase()
				: null;
		Date fromDate = null;
		if(fromDateStr!=null) {
			try {
				fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		String toDateStr = filter.containsKey("toDate") ? ((String) filter.get("toDate")).toLowerCase()
				: null;
		Date toDate = null;
		if(toDateStr!=null) {
			try {
				toDate=new SimpleDateFormat("yyyy-MM-dd").parse(toDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		if(toDate == null || fromDate == null) {
			response.setMessage("From or To dates are missing");
			response.setStatus("failure");
		}
		
		String projectId = filter.containsKey("projectId") ? ((String) filter.get("projectId"))
				: null;
		String statusStr = filter.containsKey("status") ? ((String) filter.get("status"))
				: null;
		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId"))
				: null;
				
		TimesheetStatus status = null;
		try {
			status = filter.containsKey("status") ? TimesheetStatus.toEnum((String) filter.get("status")) : null;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					filter.get("status").toString() + " is not a valid status");
		}
		
		List<Map> timeSheetList = timesheetRepository.downloadAllMyTeamTimeSheets(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, status);
		Map<String, Object> fileResponse = new HashMap<>();

		Workbook workbook = prepareExcelWorkBook(timeSheetList);
		
		byte[] blob = ExcelUtil.toBlob(workbook);

		fileResponse.put("fileName", "timesheet-"+fromDateStr+"-"+toDateStr+".xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		response.setStatus("success");
		response.setMessage("report exported Successfully");
		
		return response;
	}
	
	private Workbook prepareExcelWorkBook(List<Map> storyDetails) {

		/*
		 * t.timesheet_id as timeSheetId,t.employee_id as employeeId,t.timesheet_date as timeSheetDate, "
			+ "concat(ee.frist_name,' ', ee.last_name) as employeeName,t.project_id as projectId, "
			+ "t.task_name as taskName,t.task_id as taskId,t.task_desc as taskDesc,t.approver_id as approverId, "
			+ "concat(ep.frist_name,' ', ep.last_name) as approverName,t.status as status,t.hours_spent as hoursSpent "
			+ ",t.task_comments as comments, "
			+ "t.reason as reason
		 */
		List<String> headers = Arrays.asList("ID","Date","Employee", "Project", "Task",  "Task ID",
				"Description","Hours Spent", "Comments", "Approver", "Status");
		List data = new ArrayList<>();

		for (Map story : storyDetails) {

			Map row = new HashMap<>();

			row.put("ID",story.get("timeSheetId") != null ? story.get("timeSheetId").toString(): "");
			row.put("Date", story.get("timeSheetDate") != null ? story.get("timeSheetDate").toString() : "");

			row.put("Employee",story.get("employeeName") != null ? story.get("employeeName").toString() : "");
			row.put("Project", story.get("projectName") != null ? story.get("projectName").toString() : "");
			row.put("Task", story.get("taskName") != null ? story.get("taskName").toString() : "");
			row.put("Task ID", story.get("taskId") != null ? story.get("taskId").toString() : "");
			row.put("Description", story.get("taskDesc") != null ? story.get("taskDesc").toString() : "");
			row.put("Hours Spent", story.get("hoursSpent") != null ? story.get("hoursSpent").toString() : "");

			row.put("Comments", story.get("comments") != null ? story.get("comments").toString() : "");
			row.put("Approver", story.get("approverName") != null ? story.get("approverName").toString() : "");

			row.put("Status", story.get("status") != null ? story.get("status").toString() : "");

			data.add(row);
		}

		Workbook workbook = ExcelUtil.createSingleSheetWorkbook(ExcelUtil.createSheet("Time Sheet Report", headers, data));

		return workbook;
	}
	
	@Override
	public ApiResponse getAllSheetsByDate(String sheetId, Float hoursSpent) {
		ApiResponse response = new ApiResponse(false);
		TimeSheet sheet = timesheetRepository.getById(sheetId);
	    if(sheet != null) {
	    	List<TimeSheet> timeSheetList = timesheetRepository.getAllSheetsByDate(sheet.getTimeSheetDate());
	    	if(timeSheetList != null) {
	    		Float totalHours = hoursSpent;
	    		for (final TimeSheet sheetEntity : timeSheetList) {
	    			if(sheetEntity.getTimeSheetId() != sheetId)
	    			totalHours += sheetEntity.getHoursSpent();
	    		}
	    		if(totalHours > 24) {
	    			response.setMessage("More than 24 Hours are not allowed per day");
	    			response.setSuccess(false);
	    		}else {
	    			response.setMessage("Success");
	    			response.setSuccess(true);
	    		}
	    	}
	    }else {
	    	response.setMessage("Sheet Id not exist");
			response.setSuccess(false);
	    }
		return response;
	}

 
}
