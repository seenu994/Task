package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.request.InterviewRoundReviewRequest;
import com.xyram.ticketingTool.request.JobApplicationStatusRequest;

public interface JobService {

	ApiResponse createJob(JobOpenings jobObj) throws Exception;

	ApiResponse getAllJobs(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobApplications(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllCompanyWingsAndSkills();

	ApiResponse scheduleJobInterview(JobInterviews schedule, String applicationId) throws Exception;

	ApiResponse getAllJobInterviews(Map<String, Object> filter, Pageable pageable) throws Exception;

	ApiResponse getAllJobOpeningsById(String jobOpeningId) throws Exception;

	ApiResponse changeJobOpeningStatus(String jobOpeningId, JobOpeningStatus jobOpeningStatus) throws Exception;

	ApiResponse editJob(String jobId, JobOpenings jobObj) throws Exception;

	ApiResponse editJobInterview(JobInterviews jobInterviewRequest, String interviewId) throws Exception;

	ApiResponse changeJobApplicationStatus(String jobApplicationId, JobApplicationStatus jobStatus, String comment) throws Exception;

	ApiResponse editJobApplication(MultipartFile[] files, String jobAppObj, String jobAppId) throws Exception;

	ApiResponse getAllJobAppById(String jobAppId) throws Exception;

	ApiResponse getAllInterviewId(String jobInterviewId) throws Exception;

	ApiResponse createJobOffer(JobOffer jobObj, String jobAppId) throws Exception;

	ApiResponse editJobOffer(JobOffer jobObj, String jobOfferId) throws Exception;



	ApiResponse changeJobOfferStatus(String jobOfferId, JobOfferStatus status) throws Exception;

	ApiResponse getAllJobOfferById(String offerId) throws Exception;

	ApiResponse getAllJobCodes();
	
	ApiResponse searchJobOpenings(String searchString) throws Exception;

	ApiResponse getJobInterviewByAppId(String applicationId) throws Exception;

	ApiResponse getJobCode(String jobCode);

	ApiResponse getApplicationList(String jobCodeId) throws Exception;

	ApiResponse changeJobInterviewStatus(String jobInerviewId, String jobInterviewStatus, Integer rating,
			String feedback, String comments) throws Exception;

	ApiResponse updateInterviewRoundStatus(String jobInerviewId, InterviewRoundReviewRequest request) throws Exception;

	ApiResponse getRoundDetails(String appId, Integer roundNo) throws Exception;

	ApiResponse getAllJobApplication(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobsOpening(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobOffer(Map<String, Object> filter, Pageable pageable) throws Exception;

	ApiResponse createJobApplication(MultipartFile[] files, String jobAppString) throws Exception;

	ApiResponse changeJobApplicationStatus(String jobApplicationId, JobApplicationStatusRequest request) throws Exception;

}
