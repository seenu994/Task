package com.xyram.ticketingTool.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.request.JobApplicationSearchRequest;
import com.xyram.ticketingTool.request.JobInterviewsRequest;
import com.xyram.ticketingTool.request.JobOpeningSearchRequest;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.entity.JobOpenings;

@RestController
@CrossOrigin

public class JobController {
	
	private final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	JobService jobService;
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/createJob" })
	public ApiResponse createJob(@RequestBody JobOpenings jobObj) {
		logger.info("Creating Job");
		return jobService.createJob(jobObj);
	} 
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobs" })
	public ApiResponse getAllJobs(@RequestBody JobOpeningSearchRequest jobOpeningObj) {
		logger.info("Get All Job");
		return jobService.getAllJobs(jobOpeningObj);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobApplications" })
	public ApiResponse getAllJobApplications(@RequestBody JobApplicationSearchRequest jobAppSearch) {
		logger.info("Get All Job");
		return jobService.getAllJobApplications(jobAppSearch);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllCompanyWingsAndSkills" })
	public ApiResponse getAllCompanyWingsAndSkills() {
		logger.info("Get All Wings & Skills");
		return jobService.getAllCompanyWingsAndSkills();
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createJobApplication/{jobCode}" })
	public ApiResponse createJobApplication(@RequestPart(name = "files", required = false) MultipartFile[] files,@RequestBody String jobAppObj,
			@PathVariable String jobCode) {
		logger.info("Creating Job Application");
		return jobService.createJobApplication(files,jobAppObj,jobCode);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/scheduleJobInterview/{applicationId}" })
	public ApiResponse scheduleJobInterview(@RequestBody JobInterviews schedule,
			@PathVariable String applicationId) {
		logger.info("Get All Job");
		return jobService.scheduleJobInterview(schedule,applicationId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobInterviews" })
	public ApiResponse getAllJobInterviews(@RequestBody JobInterviewsRequest serachObj) {
		logger.info("Get All Job");
		return jobService.getAllJobInterviews(serachObj);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobOpenings",AuthConstants.HR_BASEPATH + "/getAllJobOpenings" ,AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobOpenings",AuthConstants.DEVELOPER_BASEPATH + "/getAllJobOpenings" })
	public ApiResponse getAllJobOpenings(@RequestBody JobOpeningSearchRequest serachObj) {
		logger.info("Get All Job Openings");
		return jobService.getAllJobOpenings(serachObj);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.HR_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobOpenings/{jobOpeningId}" })
	public ApiResponse getAllJobOpeningsById(@PathVariable String jobOpeningId) {
		logger.info("Get JobOpening by id");
		return jobService.getAllJobOpeningsById(jobOpeningId);
	}

}
