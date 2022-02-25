package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.TimesheetRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.TimesheetStatus;
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
	
	@Override
	public ApiResponse createTimeSheets(List<TimeSheet> timesheets) {
		
		ApiResponse response = new ApiResponse(false);
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				for (TimeSheet sheet : timesheets) {
				    sheet.setCreatedAt(new Date());
				    sheet.setCreatedBy(currentUser.getUserId());
				    sheet.setUpdatedBy(currentUser.getUserId());
				    sheet.setLastUpdatedAt(new Date());	
				    
				    Employee employee = empRepository.getByEmpId(currentUser.getScopeId());
				    Employee reportor = empRepository.getByEmpId(employee.getReportingTo());
				    sheet.setApproverId(reportor.getUserCredientials().getId());
				    if(sheet.getEmployeeId() == null) {
				    	sheet.setEmployeeId(currentUser.getUserId());
				    }
				    
				    timesheetRepository.save(sheet);
				    response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_ADDED);
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
	public ApiResponse editTimeSheets(List<TimeSheet> timesheets) {
		ApiResponse response = new ApiResponse(false);
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				for (TimeSheet sheet : timesheets) {
				   
				    sheet.setUpdatedBy(currentUser.getUserId());
				    sheet.setLastUpdatedAt(new Date());	
				    sheet.setStatus(TimesheetStatus.PENDING);
				    
				    timesheetRepository.save(sheet);
				    response.setSuccess(true);
					response.setMessage(ResponseMessages.SHEETS_EDITED);
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
	public ApiResponse approveTimeSheets(List<String> timesheets) {
		ApiResponse response = new ApiResponse(false);
		
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				Boolean sheetHaveIssues = false;
				for (String sheetId : timesheets) {
				   
				    TimeSheet sheet = timesheetRepository.getById(sheetId);
				    if(sheet != null) {
				    	if(sheet.getApproverId() != currentUser.getUserId()) {
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
				    	if(sheet.getApproverId() != currentUser.getUserId()) {
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
		
		List<Map> timeSheetList = timesheetRepository.getAllMyTimeSheets(currentUser.getUserId(), projectId, fromDateStr, toDateStr, statusStr, pageable);
		Map content = new HashMap();
		content.put("timeSheetList", timeSheetList);
		content.put("totalCount", timesheetRepository.getAllMyTimeSheetsCount(currentUser.getUserId(), projectId, fromDateStr, toDateStr, statusStr));
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
		
		List<Map> timeSheetList = timesheetRepository.getAllMyTeamTimeSheets(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, statusStr, pageable);
		Map content = new HashMap();
		content.put("timeSheetList", timeSheetList);
		content.put("totalCount", timesheetRepository.getAllMyTeamTimeSheetsCount(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, statusStr));
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
		
		List<Map> timeSheetList = timesheetRepository.downloadAllMyTimeSheets(currentUser.getUserId(), projectId, fromDateStr, toDateStr, statusStr);
		
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
		
		List<Map> timeSheetList = timesheetRepository.downloadAllMyTeamTimeSheets(currentUser.getUserId(),employeeId, projectId, fromDateStr, toDateStr, statusStr);
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

 
}
