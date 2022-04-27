package com.xyram.ticketingTool.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HrCalendar;

public interface HrCalendarService {
	
	ApiResponse createScheduleInCalendar(HrCalendar schedule);
	
	ApiResponse editScheduleInCalendar(HrCalendar schedule);
	
	ApiResponse deleteScheduleInCalendar(String scheduleId);
	
	ApiResponse doReScheduleInCalendar(String scheduleId, String comment);
	
	ApiResponse changeScheduleStatus(String scheduleId,String comment, String status, Date scheduleDate);
	
	ApiResponse updateScheduleCallCounter(String scheduleId);
	
	ApiResponse addCommentToSchedule(String scheduleId, String comment);
	
	ApiResponse editCommentToSchedule(String commentId, String comment);
	
	ApiResponse deleteCommentToSchedule(String commentId);
	
	ApiResponse getAllScheduleComments(String scheduleId);
	
	ApiResponse searchSchedulesInCalender(String searchString);
	
	ApiResponse getAllHrScheduleStatus();
	
	ApiResponse getAllMySchedulesFromCalendarByStatus(Map<String, Object>filter, Pageable pageable);
	
	ApiResponse getAllMyTeamSchedulesFromCalendarByStatus(Map<String, Object>filter, Pageable pageable);
	
	Map downloadAllMySchedulesFromCalendarByStatus(Map<String, Object>filter) throws ParseException, FileUploadException, IOException;
	
	Map downloadMyTeamSchedulesFromCalendarByStatus(Map<String, Object>filter) throws ParseException, FileUploadException, IOException;

}
