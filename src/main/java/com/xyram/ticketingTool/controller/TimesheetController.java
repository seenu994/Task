package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.TimeSheet;
import com.xyram.ticketingTool.response.ReportExportResponse;
import com.xyram.ticketingTool.service.TimesheetService;
import com.xyram.ticketingTool.util.AuthConstants;


@RestController
@CrossOrigin
public class TimesheetController {
	
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	TimesheetService timesheetService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/createTimeSheets",
			AuthConstants.HR_BASEPATH + "/createTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/createTimeSheets",
			})
	public ApiResponse createTimeSheets(@RequestBody List<TimeSheet> timesheets) {
		logger.info("Creating new Time sheets");
		return timesheetService.createTimeSheets(timesheets);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/editTimeSheets",
			AuthConstants.HR_BASEPATH + "/editTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/editTimeSheets",
			})
	public ApiResponse editTimeSheets(@RequestBody List<TimeSheet> timesheets) {
		logger.info("Editing Time sheets");
		return timesheetService.editTimeSheets(timesheets);
	} 
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/approveTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/approveTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/approveTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/approveTimeSheets",
			AuthConstants.HR_BASEPATH + "/approveTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/approveTimeSheets",
			})
	public 	ApiResponse approveTimeSheets(@RequestBody List<String> timesheets) {
		logger.info("Approving Time sheets");
		return timesheetService.approveTimeSheets(timesheets);

	} 
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/rejectTimeSheets/{reason}",
			AuthConstants.HR_ADMIN_BASEPATH + "/rejectTimeSheets/{reason}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/rejectTimeSheets/{reason}",
			AuthConstants.INFRA_USER_BASEPATH + "/rejectTimeSheets/{reason}",
			AuthConstants.HR_BASEPATH + "/rejectTimeSheets/{reason}",
			AuthConstants.DEVELOPER_BASEPATH + "/rejectTimeSheets/{reason}",
			})
	public 	ApiResponse rejectTimeSheets(@RequestBody List<String> timesheets, @PathVariable String reason) {
		logger.info("Rejecting Time sheets");
		return timesheetService.rejectTimeSheets(timesheets,reason);

	} 
	
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllMyTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllMyTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllMyTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllMyTimeSheets",
			AuthConstants.HR_BASEPATH + "/getAllMyTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllMyTimeSheets",
			})
	public 	ApiResponse getAllMyTimeSheets(@RequestBody Map<String, Object> filter, Pageable pageable) {
		logger.info("Get all My Time sheets");
		return timesheetService.getAllMyTimeSheets(filter,pageable);

	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/downloadAllMyTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/downloadAllMyTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/downloadAllMyTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/downloadAllMyTimeSheets",
			AuthConstants.HR_BASEPATH + "/downloadAllMyTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/downloadAllMyTimeSheets",
			})
	public 	ReportExportResponse downloadAllMyTimeSheets(@RequestBody Map<String, Object> filter) {
		logger.info("Download all My Time sheets");
		return timesheetService.downloadAllMyTimeSheets(filter);

	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllMyTeamTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllMyTeamTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllMyTeamTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllMyTeamTimeSheets",
			AuthConstants.HR_BASEPATH + "/getAllMyTeamTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllMyTeamTimeSheets",
			})
	public 	ApiResponse getAllMyTeamTimeSheets(@RequestBody Map<String, Object> filter, Pageable pageable) {
		logger.info("Get all My Team Time sheets");
		return timesheetService.getAllMyTeamTimeSheets(filter,pageable);

	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/downloadAllMyTeamTimeSheets",
			AuthConstants.HR_ADMIN_BASEPATH + "/downloadAllMyTeamTimeSheets",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/downloadAllMyTeamTimeSheets",
			AuthConstants.INFRA_USER_BASEPATH + "/downloadAllMyTeamTimeSheets",
			AuthConstants.HR_BASEPATH + "/downloadAllMyTeamTimeSheets",
			AuthConstants.DEVELOPER_BASEPATH + "/downloadAllMyTeamTimeSheets",
			})
	public 	ReportExportResponse downloadAllMyTeamTimeSheets(@RequestBody Map<String, Object> filter) {
		logger.info("Download all My Team Time sheets");
		return timesheetService.downloadAllMyTeamTimeSheets(filter);

	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			AuthConstants.HR_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllSheetsByDate/{sheetId}/{hoursSpent}",
			})
	public 	ApiResponse checkHoursSpentById(@PathVariable String sheetId, @PathVariable Float hoursSpent) {
		logger.info("getAllSheetsByDate");
		return timesheetService.getAllSheetsByDate(sheetId,hoursSpent);

	}
	
	@DeleteMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		AuthConstants.HR_ADMIN_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		AuthConstants.INFRA_USER_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		AuthConstants.HR_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		AuthConstants.DEVELOPER_BASEPATH + "/deleteTimeSheet/{timeSheetId}",
		})
	public ApiResponse deleteTimeSheet(@PathVariable String timeSheetId) {
		logger.info("Received request to deleting time sheet:");
		return timesheetService.deleteTimeSheet(timeSheetId);
	}


}
