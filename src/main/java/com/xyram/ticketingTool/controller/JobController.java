package com.xyram.ticketingTool.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Comments;
import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.JobInterviewStatus;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.request.InterviewRoundReviewRequest;
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
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/editJob/{jobId}",AuthConstants.ADMIN_BASEPATH + "/editJob/{jobId}" })
	public ApiResponse editJob(@PathVariable String jobId,@RequestBody JobOpenings jobObj) {
		logger.info("Creating Job");
		return jobService.editJob(jobId,jobObj);
	} 
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/createJob",AuthConstants.ADMIN_BASEPATH + "/createJob" })
	public ApiResponse createJob(@RequestBody JobOpenings jobObj) {
		logger.info("Creating Job");
		return jobService.createJob(jobObj);
	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobs",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobs",AuthConstants.DEVELOPER_BASEPATH+ "/getAllJobs", AuthConstants.HR_BASEPATH + "/getAllJobs",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobs",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobs", AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobs" })
	public ApiResponse getAllJobs(@RequestParam Map<String, Object> filter,Pageable pageable) {
		logger.info("Get All Job");
		return jobService.getAllJobs(filter,pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobCodes",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobCodes",AuthConstants.DEVELOPER_BASEPATH+ "/getAllJobCodes", AuthConstants.HR_BASEPATH + "/getAllJobCodes",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobCodes",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobCodes", AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobCodes" })
	public ApiResponse getAllJobCodes() {
		logger.info("Get All Job");
		return jobService.getAllJobCodes();
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.DEVELOPER_BASEPATH+ "/getAllJobApplications", AuthConstants.HR_BASEPATH + "/getAllJobApplications",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobApplications", AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobApplications" })
	public ApiResponse getAllJobApplications(@RequestParam Map<String, Object> filter,Pageable pageable) {
		logger.info("Get All Job");
		return jobService.getAllJobApplications(filter,pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllCompanyWingsAndSkills",AuthConstants.HR_ADMIN_BASEPATH + "/getAllCompanyWingsAndSkills" })
	public ApiResponse getAllCompanyWingsAndSkills() {
		logger.info("Get All Wings & Skills");
		return jobService.getAllCompanyWingsAndSkills();
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createJobApplication/{jobCode}",AuthConstants.HR_ADMIN_BASEPATH + "/createJobApplication/{jobCode}",AuthConstants.DEVELOPER_BASEPATH+ "/createJobApplication/{jobCode}", AuthConstants.HR_BASEPATH + "/createJobApplication/{jobCode}",AuthConstants.INFRA_ADMIN_BASEPATH + "/createJobApplication/{jobCode}",AuthConstants.INFRA_USER_BASEPATH + "/createJobApplication/{jobCode}", AuthConstants.JOB_VENDOR_BASEPATH + "/createJobApplication/{jobCode}" })
	public ApiResponse createJobApplication(@RequestPart(name = "files", required = false) MultipartFile[] files,@RequestPart String jobAppObj,
			@PathVariable String jobCode) {
		logger.info("Creating Job Application");
		return jobService.createJobApplication(files,jobAppObj,jobCode);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editJobApplication/{jobAppId}",AuthConstants.HR_ADMIN_BASEPATH + "/editJobApplication/{jobAppId}",AuthConstants.DEVELOPER_BASEPATH+ "/editJobApplication/{jobAppId}", AuthConstants.HR_BASEPATH + "/editJobApplication/{jobAppId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/editJobApplication/{jobAppId}",AuthConstants.INFRA_USER_BASEPATH + "/createJobApplication/{jobCode}", AuthConstants.JOB_VENDOR_BASEPATH + "/createJobApplication/{jobCode}"  })
	public ApiResponse editJobApplication(@RequestPart(name = "files", required = false) MultipartFile[] files,@RequestPart String jobAppObj,
			@PathVariable String jobAppId) {
		logger.info("Creating Job Application");
		return jobService.editJobApplication(files,jobAppObj,jobAppId);
	}
	
	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/scheduleJobInterview/{applicationId}",AuthConstants.HR_ADMIN_BASEPATH + "/scheduleJobInterview/{applicationId}"  })
	public ApiResponse scheduleJobInterview(@RequestBody JobInterviews schedule,
			@PathVariable String applicationId) {
		logger.info("Get All Job");
		return jobService.scheduleJobInterview(schedule,applicationId);
	}

	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/scheduleJobInterview/{applicationId}",AuthConstants.HR_ADMIN_BASEPATH + "/scheduleJobInterview/{applicationId}"  })
	public ApiResponse getJobInterviewByAppId(@PathVariable String applicationId) {
		logger.info("Get All Job");
		return jobService.getJobInterviewByAppId(applicationId);
	}
	
	  @PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editJobInterviewSchedule/{interviewId}",AuthConstants.HR_ADMIN_BASEPATH + "/editJobInterviewSchedule/{interviewId}"  })
	  public ApiResponse editJobInterviewSchedule(@RequestBody JobInterviews
	  jobInterviewRequest,@PathVariable String interviewId) {
		  logger.info("Edit Job Interview Schedule");
	  return jobService.editJobInterview(jobInterviewRequest, interviewId); 
	  }
	 
	
	
	  @PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/changeJobInterviewStatus/{jobInerviewId}/{status}",
			  AuthConstants.HR_ADMIN_BASEPATH + "/changeJobInterviewStatus/{jobInerviewId}/{status}" ,
			  AuthConstants.HR_BASEPATH + "/changeJobInterviewStatus/{jobInerviewId}/{status}"})
	  public ApiResponse changeJobInterviewStatus(@PathVariable String jobInerviewId,@PathVariable String status,@RequestParam(required=false) Integer rating,@RequestParam(required=false) String feedback,@RequestParam(required=false) String comments) { 
		  logger.info("Get JobOpening by id");
	  return  jobService.changeJobInterviewStatus(jobInerviewId,status,rating,feedback,comments); 
	  }
	  
	  

	  @PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/updateJobInterviewFeedback/{jobInerviewId}",
			  AuthConstants.HR_ADMIN_BASEPATH + "/updateJobInterviewFeedback/{jobInerviewId}" ,
			  AuthConstants.HR_BASEPATH + "/updateJobInterviewFeedback/{jobInerviewId}"})
	  public ApiResponse changeJobInterviewStatus(@PathVariable String jobInerviewId,@RequestBody @Valid InterviewRoundReviewRequest request) { 
		  logger.info("Get JobOpening by id");
	  return  jobService.updateInterviewRoundStatus(jobInerviewId, request);
	  }
	 
	  
	  @PutMapping(value= {AuthConstants.ADMIN_BASEPATH+"/changeJobApplicationStatus/{jobApplicationId}/{status}",AuthConstants.HR_ADMIN_BASEPATH + "/changeJobApplicationStatus/{jobApplicationId}/{status}",AuthConstants.DEVELOPER_BASEPATH+ "/changeJobApplicationStatus/{jobApplicationId}/{status}", AuthConstants.HR_BASEPATH + "/changeJobApplicationStatus/{jobApplicationId}/{status}",AuthConstants.INFRA_ADMIN_BASEPATH + "/changeJobApplicationStatus/{jobApplicationId}/{status}",AuthConstants.INFRA_USER_BASEPATH + "/changeJobApplicationStatus/{jobApplicationId}/{status}", AuthConstants.JOB_VENDOR_BASEPATH + "/changeJobApplicationStatus/{jobApplicationId}/{status}"})
	  public ApiResponse changeJobApplicatonStatus(@PathVariable String jobApplicationId,@PathVariable JobApplicationStatus status,@RequestParam(required = false) String comment)
	  {
		  logger.info("change job application status");
		  return jobService.changeJobApplicationStatus(jobApplicationId, status,comment);
	  }
	
	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH+"/getAllJobInterviews",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobInterviews",AuthConstants.DEVELOPER_BASEPATH+ "/getAllJobInterviews", AuthConstants.HR_BASEPATH + "/getAllJobInterviews",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobInterviews",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobInterviews", AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobInterviews"})
	public ApiResponse getAllJobInterviews(@RequestParam Map<String, Object> filter,Pageable pageable) {
		logger.info("Get All Job");
		return jobService.getAllJobInterviews(filter,pageable);
	}
	
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.HR_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.DEVELOPER_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobOpenings/{jobOpeningId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobOpenings/{jobOpeningId}" })
	public ApiResponse getAllJobOpeningsById(@PathVariable String jobOpeningId) {
		logger.info("Get JobOpening by id");
		return jobService.getAllJobOpeningsById(jobOpeningId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.INFRA_USER_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.HR_ADMIN_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.HR_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.DEVELOPER_BASEPATH + "/getAllApp/{jobAppId}",AuthConstants.JOB_VENDOR_BASEPATH + "/getAllApp/{jobAppId}" })
	public ApiResponse getAllAppById(@PathVariable String jobAppId) {
		logger.info("Get JobOpening by id");
		return jobService.getAllJobAppById(jobAppId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.INFRA_ADMIN_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.INFRA_USER_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.HR_ADMIN_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.HR_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.DEVELOPER_BASEPATH + "/getInterview/{jobInterviewId}",AuthConstants.JOB_VENDOR_BASEPATH + "/getInterview/{jobInterviewId}" })
	public ApiResponse getAllInterviewId(@PathVariable String jobInterviewId) {
		logger.info("Get JobOpening by id");
		return jobService.getAllInterviewId(jobInterviewId);
	}
	
	@PutMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/changeJobOpeningStatus/{jobOpeningId}/{status}",AuthConstants.ADMIN_BASEPATH + "/changeJobOpeningStatus/{jobOpeningId}/{status}"})
	public ApiResponse changeJobOpeningStatus(@PathVariable String jobOpeningId,@PathVariable JobOpeningStatus status) {
		logger.info("Get JobOpening by id");
		return jobService.changeJobOpeningStatus(jobOpeningId,status);
	}
	
	
	@PostMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/createJobOffer/{jobAppId}",AuthConstants.ADMIN_BASEPATH + "/createJobOffer/{jobAppId}" })
	public ApiResponse createJobOffer(@RequestBody JobOffer jobObj,@PathVariable String jobAppId) {
		logger.info("Creating Job Offers");
		return jobService.createJobOffer(jobObj,jobAppId);
	}
	
	@PutMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/editJobOffer/{jobOfferId}",AuthConstants.ADMIN_BASEPATH + "/editJobOffer/{jobOfferId}" })
	public ApiResponse editJobOffer(@RequestBody JobOffer jobObj,@PathVariable String jobOfferId) {
		logger.info("Creating Job Offers");
		return jobService.editJobOffer(jobObj,jobOfferId);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobOffer",AuthConstants.ADMIN_BASEPATH + "/getAllJobOffer" })
	public ApiResponse getAllJobOffer(Pageable pageable) {
		logger.info("Creating Job Offers");
		return jobService.getAllJobOffer(pageable);
	}
	
	@GetMapping(value = { AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobOffer/{offerId}",AuthConstants.ADMIN_BASEPATH + "/getAllJobOffer/{offerId}" })
	public ApiResponse getAllJobOfferById(@PathVariable String offerId) {
		logger.info("Creating Job Offers");
		return jobService.getAllJobOfferById(offerId);
	}
	
	 @PutMapping(value= {AuthConstants.ADMIN_BASEPATH+"/changeOfferStatus/{jobOfferId}/{status}",AuthConstants.HR_ADMIN_BASEPATH + "/changeOfferStatus/{jobOfferId}/{status}"})
	  public ApiResponse changeJobOfferStatus(@PathVariable String jobOfferId,@PathVariable JobOfferStatus status)
	  {
		  logger.info("change job application status");
		  return jobService.changeJobOfferStatus(jobOfferId,status);
	  }
	 
	 @GetMapping( value = {AuthConstants.ADMIN_BASEPATH+"/getJobCodes/{jobCode}",AuthConstants.HR_ADMIN_BASEPATH
			 +"/getJobCodes/{jobCode}"})
	 public ApiResponse getJobCode(@PathVariable String jobCode) {
		 return jobService.getJobCode(jobCode);
	 }
	 
	 @GetMapping( value = {AuthConstants.ADMIN_BASEPATH+"/getApplicationList/{jobCodeId}",AuthConstants.HR_ADMIN_BASEPATH+"/getApplicationList/{jobCodeId}"})
	 public ApiResponse getApplicationList(@PathVariable String jobCodeId) {
		 return jobService.getApplicationList(jobCodeId);
	 }
	 
	 @GetMapping( value = {AuthConstants.HR_ADMIN_BASEPATH+"/getRoundDetails/{appId}/{roundNo}"})
	 public ApiResponse getRoundDetails(@PathVariable String appId,@PathVariable Integer roundNo) {
		 return jobService.getRoundDetails(appId,roundNo);
	 }
	 
	 
	 

}
