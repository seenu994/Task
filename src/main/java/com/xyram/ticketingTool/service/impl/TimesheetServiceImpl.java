package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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
import com.xyram.ticketingTool.service.TimesheetService;
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
				    sheet.setApproverId(employee.getUserCredientials().getId());
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
				for (String sheetId : timesheets) {
				   
				    TimeSheet sheet = timesheetRepository.getById(sheetId);
				    if(sheet != null) {
				    	sheet.setStatus(TimesheetStatus.APPROVED);
					    timesheetRepository.save(sheet);
				    }
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
	public ApiResponse rejectTimeSheets(List<String> timesheets,String reason) {
		ApiResponse response = new ApiResponse(false);
		
		if(timesheets != null) {
			if(timesheets.size() > 0) {
				for (String sheetId : timesheets) {
				   
				    TimeSheet sheet = timesheetRepository.getById(sheetId);
				    if(sheet != null) {
				    	sheet.setReason(reason);
				    	sheet.setStatus(TimesheetStatus.REJECTED);
					    timesheetRepository.save(sheet);
				    }
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
		// ApiResponse response = new ApiResponse(true);
		response.setMessage("List Retrieved");
		response.setSuccess(true);
		response.setContent(content);
		
		return response;
	}

	@Override
	public ApiResponse getAllMyTeamTimeSheets(Map<String, Object> filter, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

 
}
