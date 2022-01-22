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
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.JobInterviewStatus;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.request.InterviewRoundReviewRequest;
import com.xyram.ticketingTool.request.JobApplicationSearchRequest;
import com.xyram.ticketingTool.request.JobInterviewsRequest;
import com.xyram.ticketingTool.request.JobOpeningSearchRequest;

public interface JobService {

	ApiResponse createJob(JobOpenings jobObj);

	ApiResponse getAllJobs(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobApplications(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllCompanyWingsAndSkills();

	ApiResponse scheduleJobInterview(JobInterviews schedule, String applicationId);

	ApiResponse getAllJobInterviews(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobOpeningsById(String jobOpeningId);

	ApiResponse changeJobOpeningStatus(String jobOpeningId, JobOpeningStatus jobOpeningStatus);

	ApiResponse editJob(String jobId, JobOpenings jobObj);

	ApiResponse editJobInterview(JobInterviews jobInterviewRequest, String interviewId);

	ApiResponse changeJobApplicationStatus(String jobApplicationId, JobApplicationStatus jobStatus, String comment);

	ApiResponse editJobApplication(MultipartFile[] files, String jobAppObj, String jobAppId);

	ApiResponse getAllJobAppById(String jobAppId);

	ApiResponse getAllInterviewId(String jobInterviewId);

	ApiResponse createJobOffer(JobOffer jobObj, String jobAppId);

	ApiResponse editJobOffer(JobOffer jobObj, String jobOfferId);



	ApiResponse changeJobOfferStatus(String jobOfferId, JobOfferStatus status);

	ApiResponse getAllJobOfferById(String offerId);

	ApiResponse getAllJobCodes();

	ApiResponse getJobInterviewByAppId(String applicationId);

	ApiResponse getJobCode(String jobCode);

	ApiResponse getApplicationList(String jobCodeId);

	ApiResponse changeJobInterviewStatus(String jobInerviewId, String jobInterviewStatus, Integer rating,
			String feedback, String comments);

	ApiResponse updateInterviewRoundStatus(String jobInerviewId, InterviewRoundReviewRequest request);

	ApiResponse getRoundDetails(String appId, Integer roundNo);

	ApiResponse getAllJobApplication(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobsOpening(Map<String, Object> filter, Pageable pageable);

	ApiResponse getAllJobOffer(Map<String, Object> filter, Pageable pageable);

	ApiResponse createJobApplication(MultipartFile[] files, String jobAppString);

}
