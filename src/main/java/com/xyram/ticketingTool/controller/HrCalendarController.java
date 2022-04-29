package com.xyram.ticketingTool.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HrCalendar;
import com.xyram.ticketingTool.service.HrCalendarService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class HrCalendarController {

	private final Logger logger = LoggerFactory.getLogger(HrCalendarController.class);
	
	@Autowired
	HrCalendarService hrCalrendarService;
	
	@PostMapping(value = { 
			AuthConstants.HR_ADMIN_BASEPATH + "/createScheduleInCalendar",
			AuthConstants.HR_BASEPATH + "/createScheduleInCalendar"			
			})
	public ApiResponse createScheduleInCalendar(@RequestBody HrCalendar schedule) {
		logger.info("Creating new Hr Schedule");
		return hrCalrendarService.createScheduleInCalendar(schedule);
	}
	
	@PostMapping(value = { 
			AuthConstants.HR_ADMIN_BASEPATH + "/editScheduleInCalendar",
			AuthConstants.HR_BASEPATH + "/editScheduleInCalendar"			
			})
	ApiResponse editScheduleInCalendar(@RequestBody HrCalendar schedule) {
		logger.info("Editing Hr Schedule");
		return hrCalrendarService.editScheduleInCalendar(schedule);
	}
	
	@DeleteMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/deleteScheduleInCalendar/{scheduleId}",
			AuthConstants.HR_BASEPATH + "/deleteScheduleInCalendar/{scheduleId}"			
			})
	ApiResponse deleteScheduleInCalendar(@PathVariable String scheduleId) {
		logger.info("Deleting Hr Schedule");
		return hrCalrendarService.deleteScheduleInCalendar(scheduleId);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/doReScheduleInCalendar/{scheduleId}/scheduleDate/{scheduleDate}",
			AuthConstants.HR_BASEPATH + "/doReScheduleInCalendar/{scheduleId}/scheduleDate/{scheduleDate}"			
			})
	ApiResponse doReScheduleInCalendar(@PathVariable String scheduleId, @PathVariable String comment) {
		logger.info("Re-Schedule Call");
		return hrCalrendarService.doReScheduleInCalendar(scheduleId,comment);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/changeScheduleStatus",
			AuthConstants.HR_BASEPATH + "/changeScheduleStatus"			
			})
	ApiResponse changeScheduleStatus(@RequestBody(required=false) Map<String,Object> filter) {
		logger.info("Changing Schedule Status");
	
		String scheduleId = filter.containsKey("scheduleId") ? ((String) filter.get("scheduleId")).toLowerCase()
			: null;
		String comment = filter.containsKey("comment") ? ((String) filter.get("comment")).toLowerCase()
				: null;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase()
				: null;
		String scheduleDate = filter.containsKey("scheduleDate") ? ((String) filter.get("scheduleDate")).toLowerCase()
				: null;
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt = null;
		if(scheduleDate == null) {
			
		}else {
			try {
				dt = sd1.parse(scheduleDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return hrCalrendarService.changeScheduleStatus(scheduleId,comment,status,dt);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/updateScheduleCallCounter/{scheduleId}",
			AuthConstants.HR_BASEPATH + "/updateScheduleCallCounter/{scheduleId}"			
			})
	ApiResponse updateScheduleCallCounter( @PathVariable String scheduleId) {
		logger.info("Updating Update Call COunter");
		return hrCalrendarService.updateScheduleCallCounter(scheduleId);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/addCommentToSchedule/{scheduleId}/comment/{comment}",
			AuthConstants.HR_BASEPATH + "/addCommentToSchedule/{scheduleId}/comment/{comment}"			
			})
	ApiResponse addCommentToSchedule( @PathVariable String scheduleId,  @PathVariable String comment) {
		logger.info("Adding comment to Schedule");
		return hrCalrendarService.addCommentToSchedule(scheduleId, comment);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/editCommentToSchedule/{commentId}/comment/{comment}",
			AuthConstants.HR_BASEPATH + "/editCommentToSchedule/{commentId}/comment/{comment}"			
			})
	ApiResponse editCommentToSchedule( @PathVariable String commentId, @PathVariable String comment) {
		logger.info("Editing Schedule Comment");
		return hrCalrendarService.editCommentToSchedule(commentId, comment);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/deleteCommentToSchedule/{commentId}",
			AuthConstants.HR_BASEPATH + "/deleteCommentToSchedule/{commentId}"			
			})
	ApiResponse deleteCommentToSchedule(@PathVariable String commentId) {
		logger.info("Deleting Schedule Comment");
		return hrCalrendarService.deleteCommentToSchedule(commentId);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllScheduleComments/{scheduleId}",
			AuthConstants.HR_BASEPATH + "/getAllScheduleComments/{scheduleId}"			
			})
	ApiResponse getAllScheduleComments(@PathVariable String scheduleId) {
		logger.info("Get all Schedule Comments");
		return hrCalrendarService.getAllScheduleComments(scheduleId);
	}
	
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllHrScheduleStatus",
			AuthConstants.HR_BASEPATH + "/getAllHrScheduleStatus"			
			})
	ApiResponse getAllHrScheduleStatus() {
		logger.info("Get all Schedule Comments");
		return hrCalrendarService.getAllHrScheduleStatus();
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/searchSchedulesInCalender/{searchString}",
			AuthConstants.HR_BASEPATH + "/searchSchedulesInCalender/{searchString}"			
			})
	ApiResponse searchSchedulesInCalender(@PathVariable String searchString) {
		logger.info("Get all getAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.searchSchedulesInCalender(searchString);
	} 
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/searchSchedulesInCalender/{mobileNo}",
			AuthConstants.HR_BASEPATH + "/searchSchedulesInCalender/{mobileNo}"			
			})
	ApiResponse getCandidateHistory(@PathVariable String mobileNo) {
		logger.info("Get all getCandidateHistory");
		return hrCalrendarService.getCandidateHistory(mobileNo);
	}
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllMySchedulesFromCalendarByStatus",
			AuthConstants.HR_BASEPATH + "/getAllMySchedulesFromCalendarByStatus"			
			})
	ApiResponse getAllMySchedulesFromCalendarByStatus(@RequestBody Map<String, Object>filter, Pageable pageable) {
		logger.info("Get all getAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.getAllMySchedulesFromCalendarByStatus(filter, pageable);
	}

	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllMyTeamSchedulesFromCalendarByStatus",
			AuthConstants.HR_BASEPATH + "/getAllMyTeamSchedulesFromCalendarByStatus"			
			})
	ApiResponse getAllMyTeamSchedulesFromCalendarByStatus(@RequestBody Map<String, Object>filter, Pageable pageable) {
		logger.info("Get all getAllMyTeamSchedulesFromCalendarByStatus");
		return hrCalrendarService.getAllMyTeamSchedulesFromCalendarByStatus(filter, pageable);
	}

	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/downloadAllMySchedulesFromCalendarByStatus",
			AuthConstants.HR_BASEPATH + "/downloadAllMySchedulesFromCalendarByStatus"			
			})
	ApiResponse downloadAllMySchedulesFromCalendarByStatus(@RequestBody Map<String, Object>filter) {
		logger.info("Get all downloadAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.downloadAllMySchedulesFromCalendarByStatus(filter);
	}

	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/downloadMyTeamSchedulesFromCalendarByStatus",
			AuthConstants.HR_BASEPATH + "/downloadMyTeamSchedulesFromCalendarByStatus"			
			})
	ApiResponse downloadMyTeamSchedulesFromCalendarByStatus(@RequestBody Map<String, Object>filter) {
		logger.info("Get all downloadMyTeamSchedulesFromCalendarByStatus");
		return hrCalrendarService.downloadMyTeamSchedulesFromCalendarByStatus(filter);
	}
	
	
	
}
