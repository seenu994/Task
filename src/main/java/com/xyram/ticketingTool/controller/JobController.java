package com.xyram.ticketingTool.controller;

import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class JobController {

	private final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	JobService jobService;

	@PostMapping("/editJob/{jobId}")
	public ApiResponse editJob(@PathVariable String jobId, @RequestBody JobOpenings jobObj) throws Exception {
		logger.info("Creating Job");
		return jobService.editJob(jobId, jobObj);
	}

	@PostMapping("/createJob")
	public ApiResponse createJob(@RequestBody JobOpenings jobObj) throws Exception {
		logger.info("Creating Job");
		return jobService.createJob(jobObj);
	}

	@GetMapping("/getAllJobs")
	public ApiResponse getAllJobs(@RequestParam Map<String, Object> filter, Pageable pageable) {
		logger.info("Get All Job");
		return jobService.getAllJobsOpening(filter, pageable);
	}

	@GetMapping("/getAllJobCodes")
	public ApiResponse getAllJobCodes() {
		logger.info("Get All Job");
		return jobService.getAllJobCodes();
	}

	@GetMapping("/searchJobOpenings/{searchString}")
	public ApiResponse searchJobOpenings(@PathVariable String searchString) throws Exception {
		logger.info("Search Job");
		return jobService.searchJobOpenings(searchString);
	}

//	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.HR_ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.DEVELOPER_BASEPATH+ "/getAllJobApplications", AuthConstants.HR_BASEPATH + "/getAllJobApplications",AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllJobApplications",AuthConstants.INFRA_USER_BASEPATH + "/getAllJobApplications", AuthConstants.JOB_VENDOR_BASEPATH + "/getAllJobApplications" })
//	public ApiResponse getAllJobApplications(@RequestParam Map<String, Object> filter,Pageable pageable) {
//		logger.info("Get All Job");
//		return jobService.getAllJobApplications(filter,pageable);
//	}
//	
//	
	@GetMapping("/getAllJobApplications")
	public ApiResponse getAllJobApplications(@RequestParam Map<String, Object> filter, Pageable pageable) {
		logger.info("Get All Job");
		return jobService.getAllJobApplication(filter, pageable);
	}

	@GetMapping("/getAllCompanyWingsAndSkills")
	public ApiResponse getAllCompanyWingsAndSkills() {
		logger.info("Get All Wings & Skills");
		return jobService.getAllCompanyWingsAndSkills();
	}

	/*
	 * @GetMapping(value = { "/getAllCompanyWingsAndSkills" }) public ApiResponse
	 * getAllCompanyWingsAndSkills() { logger.info("Get All Wings & Skills"); return
	 * jobService.getAllCompanyWingsAndSkills(); }
	 */
	@PostMapping("/createJobApplication")
	public ApiResponse createJobApplication(@RequestPart(name = "files", required = false) MultipartFile[] files,
			@RequestPart String jobAppObj) throws Exception {
		logger.info("Creating Job Application");
		return jobService.createJobApplication(files, jobAppObj);
	}

	@PostMapping("/editJobApplication/{jobAppId}")
	public ApiResponse editJobApplication(@RequestPart(name = "files", required = false) MultipartFile[] files,
			@RequestPart String jobAppObj, @PathVariable String jobAppId) throws Exception {
		logger.info("Creating Job Application");
		return jobService.editJobApplication(files, jobAppObj, jobAppId);
	}

	@PutMapping("/scheduleJobInterview/{applicationId}")
	public ApiResponse scheduleJobInterview(@RequestBody JobInterviews schedule, @PathVariable String applicationId) throws Exception {
		logger.info("Get All Job");
		return jobService.scheduleJobInterview(schedule, applicationId);
	}

	@GetMapping("/getJobInterview/{applicationId}")
	public ApiResponse getJobInterviewByAppId(@PathVariable String applicationId) throws Exception {
		logger.info("Get All Job");
		return jobService.getJobInterviewByAppId(applicationId);
	}

	@PutMapping("/editJobInterviewSchedule/{interviewId}")
	public ApiResponse editJobInterviewSchedule(@RequestBody JobInterviews jobInterviewRequest,
			@PathVariable String interviewId) throws Exception {
		logger.info("Edit Job Interview Schedule");
		return jobService.editJobInterview(jobInterviewRequest, interviewId);
	}

	@PutMapping("/changeJobInterviewStatus/{jobInerviewId}/{status}")
	public ApiResponse changeJobInterviewStatus(@PathVariable String jobInerviewId, @PathVariable String status,
			@RequestParam(required = false) Integer rating, @RequestParam(required = false) String feedback,
			@RequestParam(required = false) String comments) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.changeJobInterviewStatus(jobInerviewId, status, rating, feedback, comments);
	}

	@PutMapping("/updateJobInterviewFeedback/{jobInerviewId}")
	public ApiResponse changeJobInterviewStatus(@PathVariable String jobInerviewId,
			@RequestBody @Valid InterviewRoundReviewRequest request) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.updateInterviewRoundStatus(jobInerviewId, request);
	}

	@PutMapping("/changeJobApplicationStatus/{jobApplicationId}/{status}")
	public ApiResponse changeJobApplicatonStatus(@PathVariable String jobApplicationId,
			@PathVariable JobApplicationStatus status, @RequestParam(required = false) String comment) throws Exception {
		logger.info("change job application status");
		return jobService.changeJobApplicationStatus(jobApplicationId, status, comment);
	}

	@PutMapping("changeJobApplicationStatus/{jobApplicationId}")
	public ApiResponse updateJobApplicatonStatus(@PathVariable String jobApplicationId,
			@RequestBody JobApplicationStatusRequest request) throws Exception {
		logger.info("change job application status");
		return jobService.changeJobApplicationStatus(jobApplicationId, request);
	}

	@GetMapping("/getAllJobInterviews")
	public ApiResponse getAllJobInterviews(@RequestParam Map<String, Object> filter, Pageable pageable) throws Exception {
		logger.info("Get All Job");
		return jobService.getAllJobInterviews(filter, pageable);
	}

	@GetMapping("/getAllJobOpenings/{jobOpeningId}")
	public ApiResponse getAllJobOpeningsById(@PathVariable String jobOpeningId) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.getAllJobOpeningsById(jobOpeningId);
	}

	@GetMapping("/getAllApp/{jobAppId}")
	public ApiResponse getAllAppById(@PathVariable String jobAppId) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.getAllJobAppById(jobAppId);
	}

	@GetMapping("/getInterview/{jobInterviewId}")
	public ApiResponse getAllInterviewId(@PathVariable String jobInterviewId) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.getAllInterviewId(jobInterviewId);
	}

	@PutMapping("/changeJobOpeningStatus/{jobOpeningId}/{status}")
	public ApiResponse changeJobOpeningStatus(@PathVariable String jobOpeningId,
			@PathVariable JobOpeningStatus status) throws Exception {
		logger.info("Get JobOpening by id");
		return jobService.changeJobOpeningStatus(jobOpeningId, status);
	}

	@PostMapping("/createJobOffer/{jobAppId}")
	public ApiResponse createJobOffer(@RequestBody JobOffer jobObj, @PathVariable String jobAppId) throws Exception {
		logger.info("Creating Job Offers");
		return jobService.createJobOffer(jobObj, jobAppId);
	}

	@PutMapping("/editJobOffer/{jobOfferId}")
	public ApiResponse editJobOffer(@RequestBody JobOffer jobObj, @PathVariable String jobOfferId) throws Exception {
		logger.info("edit Job Offers");
		return jobService.editJobOffer(jobObj, jobOfferId);
	}

	@GetMapping("/getAllJobOffer")
	public ApiResponse getAllJobOffer(@RequestParam Map<String, Object> filter, Pageable pageable) throws Exception {
		logger.info("getall Job Offers");
		return jobService.getAllJobOffer(filter, pageable);
	}

	@GetMapping("/getAllJobOffer/{offerId}")
	public ApiResponse getAllJobOfferById(@PathVariable String offerId) throws Exception {
		logger.info("Creating Job Offers");
		return jobService.getAllJobOfferById(offerId);
	}

	@PutMapping("/changeOfferStatus/{jobOfferId}/{status}")
	public ApiResponse changeJobOfferStatus(@PathVariable String jobOfferId, @PathVariable JobOfferStatus status) throws Exception {
		logger.info("change job application status");
		return jobService.changeJobOfferStatus(jobOfferId, status);
	}

	@GetMapping("/getJobCodes/{jobCode}")
	public ApiResponse getJobCode(@PathVariable String jobCode) {
		return jobService.getJobCode(jobCode);
	}

	@GetMapping("/getApplicationList/{jobCodeId}")
	public ApiResponse getApplicationList(@PathVariable String jobCodeId) throws Exception {
		return jobService.getApplicationList(jobCodeId);
	}

	@GetMapping("/getRoundDetails/{appId}/{roundNo}")
	public ApiResponse getRoundDetails(@PathVariable String appId, @PathVariable Integer roundNo) throws Exception {
		return jobService.getRoundDetails(appId, roundNo);
	}

}
