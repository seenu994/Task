package com.xyram.ticketingTool.service;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.HrCalendar;

public interface HrCalendarService {
	
	ApiResponse createScheduleInCalendar(HrCalendar schedule) throws Exception;
	
	ApiResponse editScheduleInCalendar(Boolean validateDateTime,HrCalendar schedule) throws Exception;
	
	ApiResponse deleteScheduleInCalendar(String scheduleId) throws Exception;
	
	ApiResponse doReScheduleInCalendar(String scheduleId, String comment) throws Exception ;
	
	ApiResponse changeScheduleStatus(String scheduleId,String comment, String status, Date scheduleDate);
	
	ApiResponse updateScheduleCallCounter(String scheduleId) throws Exception;
	
	ApiResponse addCommentToSchedule(String scheduleId, String comment) throws Exception;
	
	ApiResponse editCommentToSchedule(String commentId, String comment) throws Exception ;
	
	ApiResponse deleteCommentToSchedule(String commentId) throws Exception;
	
	ApiResponse getAllScheduleComments(String scheduleId) throws Exception;
	
	ApiResponse searchSchedulesInCalender(String searchString);
	
	ApiResponse searchMyTeamSchedulesInCalender(String searchString);
	
	ApiResponse getCandidateHistory(String mobileNo) throws Exception;
	
	ApiResponse getScheduleDetail(String scheduleId);
	
	ApiResponse getAllHrScheduleStatus() throws Exception;
	
	ApiResponse getAllMySchedulesFromCalendarByStatus(Map<String, Object>filter, Pageable pageable) throws Exception;
	
	ApiResponse getAllMyTeamSchedulesFromCalendarByStatus(Map<String, Object>filter, Pageable pageable) throws Exception;
	
	ApiResponse downloadAllMySchedulesFromCalendarByStatus(Map<String, Object>filter) throws Exception;
	
	ApiResponse downloadMyTeamSchedulesFromCalendarByStatus(Map<String, Object>filter) throws Exception;
	
	ApiResponse getAllhrCalender(Map<String, Object> filter , Pageable pageable);
	
	ApiResponse searchhrCalender(String searchString);
}
