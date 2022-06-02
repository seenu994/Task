package com.xyram.ticketingTool.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

	@PostMapping("/createScheduleInCalendar")
	public ApiResponse createScheduleInCalendar(@RequestBody HrCalendar schedule) throws Exception {
		logger.info("Creating new Hr Schedule");
		return hrCalrendarService.createScheduleInCalendar(schedule);
	}

	@PostMapping("/editScheduleInCalendar/{validateDateTime}")
	ApiResponse editScheduleInCalendar(@PathVariable Boolean validateDateTime, @RequestBody HrCalendar schedule)
			throws Exception {
		logger.info("Editing Hr Schedule");
		return hrCalrendarService.editScheduleInCalendar(validateDateTime, schedule);
	}

	@DeleteMapping("/deleteScheduleInCalendar/{scheduleId}")
	ApiResponse deleteScheduleInCalendar(@PathVariable String scheduleId) throws Exception {
		logger.info("Deleting Hr Schedule");
		return hrCalrendarService.deleteScheduleInCalendar(scheduleId);
	}

	@GetMapping("/getScheduleDetail/{scheduleId}")
	ApiResponse getScheduleDetail(@PathVariable String scheduleId) {
		logger.info("Get Schedule Detail");
		return hrCalrendarService.getScheduleDetail(scheduleId);
	}

	@PostMapping("/doReScheduleInCalendar/{scheduleId}/comment/{comment}")
	ApiResponse doReScheduleInCalendar(@PathVariable String scheduleId, @PathVariable String comment) throws Exception {
		logger.info("Re-Schedule Call");
		return hrCalrendarService.doReScheduleInCalendar(scheduleId, comment);
	}

	@PostMapping("/changeScheduleStatus")
	ApiResponse changeScheduleStatus(@RequestBody(required = false) Map<String, Object> filter) {
		logger.info("Changing Schedule Status");

		String scheduleId = filter.containsKey("scheduleId") ? ((String) filter.get("scheduleId")).toLowerCase() : null;
		String comment = filter.containsKey("comment") ? ((String) filter.get("comment")).toLowerCase() : null;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String scheduleDate = filter.containsKey("scheduleDate") ? ((String) filter.get("scheduleDate")).toLowerCase()
				: null;
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt = null;
		if (scheduleDate == null) {

		} else {
			try {
				dt = sd1.parse(scheduleDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return hrCalrendarService.changeScheduleStatus(scheduleId, comment, status, dt);
	}

	@PostMapping("/updateScheduleCallCounter/{scheduleId}")
	ApiResponse updateScheduleCallCounter(@PathVariable String scheduleId) throws Exception {
		logger.info("Updating Update Call COunter");
		return hrCalrendarService.updateScheduleCallCounter(scheduleId);
	}

	@PostMapping("/addCommentToSchedule/{scheduleId}/comment/{comment}")
	ApiResponse addCommentToSchedule(@PathVariable String scheduleId, @PathVariable String comment) throws Exception {
		logger.info("Adding comment to Schedule");
		return hrCalrendarService.addCommentToSchedule(scheduleId, comment);
	}

	@PostMapping("/editCommentToSchedule/{commentId}/comment/{comment}")
	ApiResponse editCommentToSchedule(@PathVariable String commentId, @PathVariable String comment) throws Exception {
		logger.info("Editing Schedule Comment");
		return hrCalrendarService.editCommentToSchedule(commentId, comment);
	}

	@PostMapping("/deleteCommentToSchedule/{commentId}")
	ApiResponse deleteCommentToSchedule(@PathVariable String commentId) throws Exception {
		logger.info("Deleting Schedule Comment");
		return hrCalrendarService.deleteCommentToSchedule(commentId);
	}

	@GetMapping("/getAllScheduleComments/{scheduleId}")
	ApiResponse getAllScheduleComments(@PathVariable String scheduleId) throws Exception {
		logger.info("Get all Schedule Comments");
		return hrCalrendarService.getAllScheduleComments(scheduleId);
	}

	@GetMapping("/getAllHrScheduleStatus")
	ApiResponse getAllHrScheduleStatus() throws Exception {
		logger.info("Get all Schedule Comments");
		return hrCalrendarService.getAllHrScheduleStatus();
	}

	@GetMapping("/searchSchedulesInCalender/{searchString}")
	ApiResponse searchSchedulesInCalender(@PathVariable String searchString) {
		logger.info("Get all getAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.searchSchedulesInCalender(searchString);
	}

	@GetMapping("/searchMyTeamSchedulesInCalender/{searchString}")
	ApiResponse searchMyTeamSchedulesInCalender(@PathVariable String searchString) {
		logger.info("Get all searchMyTeamSchedulesInCalender");
		return hrCalrendarService.searchMyTeamSchedulesInCalender(searchString);
	}

	@GetMapping("/getCandidateHistory/{mobileNo}")
	ApiResponse getCandidateHistory(@PathVariable String mobileNo) throws Exception {
		logger.info("Get all getCandidateHistory");
		return hrCalrendarService.getCandidateHistory(mobileNo);
	}

	@PostMapping("/getAllMySchedulesFromCalendarByStatus")
	ApiResponse getAllMySchedulesFromCalendarByStatus(@RequestBody Map<String, Object> filter, Pageable pageable) throws Exception {
		logger.info("Get all getAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.getAllMySchedulesFromCalendarByStatus(filter, pageable);
	}

	@PostMapping("/getAllMyTeamSchedulesFromCalendarByStatus")
	ApiResponse getAllMyTeamSchedulesFromCalendarByStatus(@RequestBody Map<String, Object> filter, Pageable pageable) throws Exception {
		logger.info("Get all getAllMyTeamSchedulesFromCalendarByStatus");
		return hrCalrendarService.getAllMyTeamSchedulesFromCalendarByStatus(filter, pageable);
	}

	@PostMapping("/downloadAllMySchedulesFromCalendarByStatus")
	ApiResponse downloadAllMySchedulesFromCalendarByStatus(@RequestBody Map<String, Object> filter) throws Exception {
		logger.info("Get all downloadAllMySchedulesFromCalendarByStatus");
		return hrCalrendarService.downloadAllMySchedulesFromCalendarByStatus(filter);
	}

	@PostMapping("/downloadMyTeamSchedulesFromCalendarByStatus")
	ApiResponse downloadMyTeamSchedulesFromCalendarByStatus(@RequestBody Map<String, Object> filter) throws Exception {
		logger.info("Get all downloadMyTeamSchedulesFromCalendarByStatus");
		return hrCalrendarService.downloadMyTeamSchedulesFromCalendarByStatus(filter);
	}

	@GetMapping("/getAllhrCalender")
	public ApiResponse getAllhrCalender(@RequestBody Map<String, Object> filter, Pageable pageable) {
		logger.info("Received request to get all calender");
		return hrCalrendarService.getAllhrCalender(filter, pageable);
	}

	@GetMapping("/searchhrCalender/{searchString}")
	public ApiResponse searchhrCalender(@PathVariable String searchString) {
		logger.info("Received request to search hrCalender ");
		return hrCalrendarService.searchhrCalender(searchString);
	}

}
