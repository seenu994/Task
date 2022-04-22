package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.response.ReportExportResponse;

public interface TimesheetService {
	
	ApiResponse createTimeSheets(List<TimeSheet> timesheets);

	ApiResponse editTimeSheets(List<TimeSheet> timesheets);
	
	ApiResponse approveTimeSheets(List<String> timesheets);
	
	ApiResponse rejectTimeSheets(List<String> timesheets, String reason);
	
	ApiResponse getAllMyTimeSheets(Map<String, Object>filter,Pageable pageable);
	
	ReportExportResponse downloadAllMyTimeSheets(Map<String, Object>filter);
	
	ApiResponse getAllMyTeamTimeSheets(Map<String, Object>filter,Pageable pageable);
	
	ReportExportResponse downloadAllMyTeamTimeSheets(Map<String, Object>filter);
	
	ApiResponse getAllSheetsByDate(String sheetId,Float hoursSpent);

	ApiResponse deleteTimeSheet(String timeSheetId);



}
