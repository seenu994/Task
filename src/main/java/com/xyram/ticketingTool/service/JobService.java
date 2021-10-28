package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.JobInterviewStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.request.JobApplicationSearchRequest;
import com.xyram.ticketingTool.request.JobInterviewsRequest;
import com.xyram.ticketingTool.request.JobOpeningSearchRequest;

public interface JobService {
	
	ApiResponse createJob( JobOpenings jobObj); 
	
	ApiResponse getAllJobs(JobOpeningSearchRequest jobOpeningObj); 
	
	ApiResponse getAllJobApplications(JobApplicationSearchRequest jobAppSearch);
	
	ApiResponse getAllCompanyWingsAndSkills();
	
	ApiResponse createJobApplication(MultipartFile[] files, String jobAppObj,String jobCode);
	
	ApiResponse scheduleJobInterview(JobInterviews schedule, String applicationId); 
	
	ApiResponse getAllJobInterviews(JobInterviewsRequest searchObj);

	ApiResponse getAllJobOpeningsById(String jobOpeningId);

	ApiResponse changeJobOpeningStatus(String jobOpeningId,JobOpeningStatus jobOpeningStatus);




	ApiResponse changeJobInterviewStatus(String jobInerviewId, JobInterviews status);

	ApiResponse editJob(String jobId, JobOpenings jobObj);

	ApiResponse editJobApplication(MultipartFile[] files, String jobAppObj, String jobAppId);

	ApiResponse editJobInterview(JobInterviews jobInterviewRequest, String interviewId);

	ApiResponse changeJobInterviewStatus(String jobInerviewId, JobInterviewStatus status);


}
