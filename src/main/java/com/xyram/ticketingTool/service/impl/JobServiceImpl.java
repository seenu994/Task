package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.criterion.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sun.mail.iap.Response;
import com.xyram.ticketingTool.Repository.ApplicationCommentsRepository;
import com.xyram.ticketingTool.Repository.JobApplicationRepository;
import com.xyram.ticketingTool.Repository.JobInterviewRepository;
import com.xyram.ticketingTool.Repository.JobOfferRepository;
import com.xyram.ticketingTool.Repository.JobRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.ApplicationComments;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.JobInterviewStatus;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.JobApplicationSearchRequest;
import com.xyram.ticketingTool.request.JobInterviewsRequest;
import com.xyram.ticketingTool.request.JobOpeningSearchRequest;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.util.ResponseMessages;


@Service
@Transactional
public class JobServiceImpl implements JobService{
	
	
	@Autowired
	CurrentUser userDetail;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	JobApplicationRepository jobAppRepository;
	
	@Autowired
	JobInterviewRepository jobInterviewRepository;
	
	@Autowired
	ApplicationCommentsRepository appRepository;
	
	@Autowired
	JobOfferRepository offerRepository;
	
	static ChannelSftp channelSftp = null;
	static Session session = null;
	static Channel channel = null;
	static String PATHSEPARATOR = "/";
		
	@Override
	public ApiResponse createJob(JobOpenings jobObj) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		jobObj.setCreatedAt(new Date());
		jobObj.setCreatedBy(userDetail.getUserId());
		jobObj.setJobStatus(JobOpeningStatus.VACANT);
		if(jobRepository.save(jobObj) != null) {
			response.setSuccess(true);
			response.setMessage("New Job Opening Created");
		}else {
			response.setSuccess(false);
			response.setMessage("New Job Opening Not Created");
		}
		return response;
	} 

	@Override
	public ApiResponse getAllJobs(Map<String, Object>filter,Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String searchQuery = filter.containsKey("searchstring") ? ((String) filter.get("searchstring")).toLowerCase()
				: null;
//		List<Map> allJobs = jobRepository.getAllJobOpenings();
//		List<JobOpenings> allList =  jobRepository.getList();
		Page<JobOpenings> allList =  jobRepository.findAll(new Specification<JobOpenings>() {
			@Override
			public Predicate toPredicate(Root<JobOpenings> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
                if(status != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobStatus"),status)));
                }
//                if(jobOpeningObj.getWing() != null && !jobOpeningObj.getWing().equalsIgnoreCase("ALL")) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal((root.get("wings").get("Id")), jobOpeningObj.getWing())));
//                }
                //criteriaBuilder.upper(itemRoot.get("code"), code.toUpperCase()
                if(searchQuery != null) {
//                	criteriaBuilder.like(root.get("title"), "%" + keyword + "%")
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobTitle"), "%" + searchQuery + "%")));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobDescription"), "%" + searchQuery + "%")));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), "%" + searchQuery + "%")));
                }
                if(userDetail.getUserRole() == "JOB_VENDOR" ) {
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), "%" + userDetail.getUserId() + "%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
        },pageable);
		content.put("jobsList",allList);
		if(allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Jobs");
		}
		else {
			response.setSuccess(true);
			response.setMessage("No Records Found");
		}
		
		response.setContent(content);
		return response;
	} 
	
	@Override
	public ApiResponse getAllJobApplications(Map<String, Object> filter, Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String searchQuery = filter.containsKey("searchstring") ? ((String) filter.get("searchstring")).toLowerCase()
				: null;
		Page<JobApplication> allList =  jobAppRepository.findAll(new Specification<JobApplication>() {
				@Override
				public Predicate toPredicate(Root<JobApplication> root, javax.persistence.criteria.CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					// TODO Auto-generated method stub
					List<Predicate> predicates = new ArrayList<>();
	                if(status != null) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
	                }
//	                if(jobAppSearch.getVendor() != null && !jobAppSearch.getVendor().equalsIgnoreCase("ALL")) {
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("referredVendor"), jobAppSearch.getVendor())));
//	                }
	                if(searchQuery != null) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateName"), "%" + searchQuery + "%")));
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateMobile"), "%" + searchQuery + "%")));
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateEmail"), "%" + searchQuery + "%")));
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), "%" + searchQuery + "%")));
	                }
	                if(userDetail.getUserRole() != "HR_ADMIN" && userDetail.getUserRole() != "TICKETINGTOOL_ADMIN") {
	                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), "%" + userDetail.getUserId() + "%")));
	                }
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
	        }, pageable);
		content.put("jobAppList",allList);
		if(allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Jobs");
		}
		else {
			response.setSuccess(true);
			response.setMessage("No Records Found");
		}
		
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse getAllCompanyWingsAndSkills() {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map<String, List<Map>> content = new HashMap<String, List<Map>>();
		List<Map> allWingsList = jobRepository.getAllCompanyWings();
		List<Map> skillsList = jobRepository.getAllTechSkills();
		
		content.put("wings", allWingsList);
		content.put("skills", skillsList);
		response.setSuccess(true);
		response.setMessage("No Records Found");
		response.setContent(content);
		
		return response;
	}

	@Override
	public ApiResponse createJobApplication(MultipartFile[] files, String jobAppString,String jobCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		JobOpenings jobOpening = jobRepository.getJobOpeningFromCode(jobCode);
		if(jobOpening != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			JobApplication jobAppObj = null;
			try {
				jobAppObj = objectMapper.readValue(jobAppString, JobApplication.class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (MultipartFile constentFile : files) {
				try {
				      String fileextension = constentFile.getOriginalFilename().substring(constentFile.getOriginalFilename().lastIndexOf("."));
				      String filename = getRandomFileName()+fileextension;//constentFile.getOriginalFilename();
				      if(addFileAdmin(constentFile,filename)!= null) {
				    	  jobAppObj.setResumePath(filename);
				      }
				}catch(Exception e) {
					
				}
			}
			jobAppObj.setJobCode(jobCode);
			jobAppObj.setJobOpenings(jobOpening);
			jobAppObj.setCreatedAt(new Date());
			jobAppObj.setCreatedBy(userDetail.getUserId());
			jobAppObj.setJobApplicationSatus(JobApplicationStatus.APPLIED);
			if(jobAppRepository.save(jobAppObj) != null) {
				response.setSuccess(true);
				response.setMessage("New Job Application Created");
			}else {
				response.setSuccess(false);
				response.setMessage("New Job Application Not Created");
			}
		}else {
			response.setSuccess(false);
			response.setMessage("Job Code Not Exist");
		}
		return response;
	}
	
	@Override
	public ApiResponse scheduleJobInterview(JobInterviews schedule, String applicationId) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		JobApplication jobApp  = jobAppRepository.getApplicationById(applicationId);
		if(jobApp != null) {
			schedule.setCreatedAt(new Date());
			schedule.setCreatedBy(userDetail.getUserId());
			schedule.setJobInterviewStatus(JobInterviewStatus.SCHEDULED);
			schedule.setJobApplication(jobApp);
			if(jobInterviewRepository.save(schedule) != null) {
				schedule.setJobInterviewStatus(JobInterviewStatus.SCHEDULED);
				jobAppRepository.save(jobApp);
				response.setSuccess(true);
				response.setMessage("Interview Scheduled");
			}else {
				response.setSuccess(false);
				response.setMessage("Interview Not Scheduled");
			}
		}else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
	}
	
	@Override
	public ApiResponse getAllJobInterviews(Map<String, Object> filter,Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
//		List<JobInterviews> allList =  jobInterviewRepository.getList();
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		Page<JobInterviews> allList =  jobInterviewRepository.findAll(new Specification<JobInterviews>(){
				@Override
				public Predicate toPredicate(Root<JobInterviews> root, javax.persistence.criteria.CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					// TODO Auto-generated method stub
					List<Predicate> predicates = new ArrayList<>();
	                if(status != null && !status.equalsIgnoreCase("ALL")) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
	                }
	                if(userDetail.getUserId()!=null && userDetail.getUserRole() != "HR_ADMIN" && userDetail.getUserRole() != "TICKETINGTOOL_ADMIN") {
	                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), "%" + userDetail.getUserId() + "%")));
	                }
//	                if(searchObj.getSearchString() != null && !searchObj.getSearchString().equalsIgnoreCase("")) {
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateName"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateMobile"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateEmail"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), "%" + searchObj.getSearchString() + "%")));
//	                }
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
	        },pageable);
		
		content.put("InterviewList",allList);
		if(allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Interviews");
		}
		else {
			response.setSuccess(true);
			response.setMessage("No Records Found");
		}
		
		response.setContent(content);
		return response;
	}
		
	public String getRandomFileName() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}
	public String addFileAdmin(MultipartFile file, String fileName){
		System.out.println("bjsjsjn");
	    String SFTPHOST = "13.229.55.43"; // SFTP Host Name or SFTP Host IP Address
	    int SFTPPORT = 22; // SFTP Port Number
	    String SFTPUSER = "ubuntu"; // User Name
	    String SFTPPASS = ""; // Password
	    String SFTPKEY = "/home/ubuntu/tomcat/webapps/Ticket_tool-0.0.1-SNAPSHOT/WEB-INF/classes/Covid-Phast-Prod.ppk";
	    String SFTPWORKINGDIRAADMIN = "/home/ubuntu/tomcat/webapps/image/resumes";// Source Directory on SFTP server
	    String fileNameOriginal = fileName;
	    try {
	          JSch jsch = new JSch();
	        if (SFTPKEY != null && !SFTPKEY.isEmpty()) {
				jsch.addIdentity(SFTPKEY);
			}
	        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//	        session.setPassword(SFTPPASS);
	        java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	        session.connect(); // Create SFTP Session
	        channel = session.openChannel("sftp"); // Open SFTP Channel
	        channel.connect();
	        channelSftp = (ChannelSftp) channel;
	        channelSftp.cd(SFTPWORKINGDIRAADMIN);// Change Directory on SFTP Server
	        channelSftp.put(file.getInputStream(),fileName);
	        System.out.println("added");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } 
	        finally {
	        if (channelSftp != null)
	            channelSftp.disconnect();
	        if (channel != null)
	            channel.disconnect();
	        if (session != null)
	            session.disconnect();
	    }
		return fileNameOriginal;
	}

	
	@Override
	public ApiResponse getAllJobOpeningsById(String jobOpeningId) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map jobOpening  = jobRepository.getJobOpeningById(jobOpeningId);
		if(jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse changeJobOpeningStatus(String jobOpeningId,JobOpeningStatus jobOpeningStatus) {
		ApiResponse response = new ApiResponse(false);
		JobOpenings status= jobRepository.getById(jobOpeningId);
		if(status!= null) {
			status.setJobStatus(jobOpeningStatus);
			jobRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Opening Status Updated Sucessfully");
			response.setContent(null);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Opening Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override

	public ApiResponse editJobInterview( JobInterviews jobInterviewRequest, String interviewId ) {
		
		ApiResponse response = new ApiResponse(false);
		
		  System.out.println("InterviewId::"+interviewId);
		JobInterviews jbInterviews = jobInterviewRepository.getById(interviewId);
	
		System.out.println("feedBack::"+jobInterviewRequest.getFeedback());
			if (jbInterviews != null) {
			
				jbInterviews.setFeedback(jobInterviewRequest.getFeedback());
				jbInterviews.setInterviewType(jobInterviewRequest.getInterviewType());
				jbInterviews.setRoundDetails(jobInterviewRequest.getRoundDetails());
				jbInterviews.setInterviewLink(jobInterviewRequest.getInterviewLink());
				jbInterviews.setInterviewer(jobInterviewRequest.getInterviewer());
				jbInterviews.setInterviewDate(jobInterviewRequest.getInterviewDate());
				jbInterviews.setRecommendation(jobInterviewRequest.getRecommendation());
				jbInterviews.setRating(jobInterviewRequest.getRateGiven());
				
				
				jobInterviewRepository.save(jbInterviews);
				response.setSuccess(true);
				String msg="Job Interview Updated Successfully";
				response.setMessage(ResponseMessages.SCHEDULEINIERVIEW_UPDATED);
				response.setContent(null);
			}

			else {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.SCHEDULEINIERVIEW_INVALID);
				response.setContent(null);
			}
		return response;
	}

	@Override
	public ApiResponse changeJobInterviewStatus(String jobInerviewId, JobInterviewStatus jobInterviewStatus) {
		ApiResponse response = new ApiResponse(false);
		JobInterviews status= jobInterviewRepository.getById(jobInerviewId);
		if(status!= null && status.getInterviewer() == userDetail.getUserId()) {
			status.setJobInterviewStatus(jobInterviewStatus);
			jobInterviewRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Interview Status Updated Sucessfully");
			response.setContent(null);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Interview Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}
	public ApiResponse editJob(String jobId, JobOpenings jobObj) {
		ApiResponse response = new ApiResponse(false);
		JobOpenings jobOpening = jobRepository.getById(jobId);
		if(jobOpening != null) {
			jobOpening.setJobCode(jobObj.getJobCode());
			jobOpening.setJobDescription(jobObj.getJobDescription());
			jobOpening.setJobSkills(jobObj.getJobSkills());
			jobOpening.setJobTitle(jobObj.getJobTitle());
			jobOpening.setLastUpdatedAt(new Date());
			jobOpening.setMaxExp(jobObj.getMaxExp());
			jobOpening.setMinExp(jobObj.getMinExp());
			jobOpening.setSalary(jobObj.getSalary());
			jobOpening.setTotalOpenings(jobObj.getTotalOpenings());
			jobOpening.setWings(jobObj.getWings());
			jobRepository.save(jobOpening);
			response.setSuccess(true);
			response.setMessage("Job Opening Updated Sucessfully");
			response.setContent(null);
		}
		
		else
		{
			response.setSuccess(false);
			response.setMessage("Job Opening Id does Not Exist");
		}
		

		return response;
	}

	@Override
	public ApiResponse changeJobApplicationStatus(String jobApplicationId, JobApplicationStatus jobStatus,String comment) {
		ApiResponse response = new ApiResponse(false);
		JobApplication status= jobAppRepository.getApplicationById(jobApplicationId);
		System.out.println(userDetail.getUserRole());
		if(status != null) {
			if(userDetail.getUserId().equals(status.getCreatedBy()) || userDetail.getUserRole() == "HR_ADMIN") {
		if(status!= null) {
			ApplicationComments appComments = new ApplicationComments();
			appComments.setApplicationId(jobApplicationId);
			if(comment != null) {
				appComments.setGivenDescription(comment);
			}
			else
			{
				appComments.setAutoDescription("Status changed to"+" "+jobStatus+"by"+status.getCreatedBy());
			}
			appRepository.save(appComments);
			status.setJobApplicationSatus(jobStatus);
			jobAppRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Application Status Updated Sucessfully");
			response.setContent(null);
		}
			}
		else {
			response.setSuccess(false);
			response.setMessage("Job application Id does Not Exist");
		}
		}
		else
		{
			response.setSuccess(false);
			response.setMessage("Job application Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
		
		
	}

	@Override
	public ApiResponse editJobApplication(MultipartFile[] files, String jobAppObj, String jobAppId) {
		ApiResponse response = new ApiResponse(false);
		JobApplication jobApp = jobAppRepository.getApplicationById(jobAppId);
		if(jobApp != null && userDetail.getUserId().equals(jobApp.getCreatedBy()) || userDetail.getUserRole().equals(UserRole.HR_ADMIN)) {
			ObjectMapper objectMapper = new ObjectMapper();
			JobApplication newJobAppObj = null;
			try {
				newJobAppObj = objectMapper.readValue(jobAppObj, JobApplication.class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (MultipartFile constentFile : files) {
				try {
				      String fileextension = constentFile.getOriginalFilename().substring(constentFile.getOriginalFilename().lastIndexOf("."));
				      String filename = getRandomFileName()+fileextension;//constentFile.getOriginalFilename();
				      if(addFileAdmin(constentFile,filename)!= null) {
				    	  jobApp.setResumePath(filename);
				      }
				}catch(Exception e) {
					
				}
			}
			jobApp.setJobCode(newJobAppObj.getJobCode());
			jobApp.setJobOpenings(newJobAppObj.getJobOpenings());
			jobApp.setCandidateEmail(newJobAppObj.getCandidateEmail());
			jobApp.setCandidateMobile(newJobAppObj.getCandidateMobile());
			jobApp.setCandidateName(newJobAppObj.getCandidateName());
			jobApp.setExpectedSalary(newJobAppObj.getExpectedSalary());
			if(jobAppRepository.save(jobApp) != null) {
				response.setSuccess(true);
				response.setMessage(" Job Application Updated");
			}else {
				response.setSuccess(false);
				response.setMessage(" Job Application Not Updated");
			}
		}
		else {
			response.setSuccess(false);
			response.setMessage("Job Application Id Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse getAllJobAppById(String jobAppId) {
		ApiResponse response = new ApiResponse(false);
		Map jobOpening  = jobAppRepository.getAppById(jobAppId);
		if(jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse getAllInterviewId(String jobInterviewId) {
		ApiResponse response = new ApiResponse(false);
		Map jobOpening  = jobInterviewRepository.getInterviewById(jobInterviewId);
		if(jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
		
	}

	@Override
	public ApiResponse createJobOffer(JobOffer jobObj,String jobAppId) {
       ApiResponse response = new ApiResponse(false);
		JobApplication application = jobAppRepository.getApplicationById(jobAppId);
		if(application.getJobApplicationSatus().equals(JobApplicationStatus.SELECTED)) {
		jobObj.setCreatedAt(new Date());
		jobObj.setCreatedBy(userDetail.getUserId());
		if(offerRepository.save(jobObj) != null) {
			response.setSuccess(true);
			response.setMessage("New Job Offer Created");
		}else {
			response.setSuccess(false);
			response.setMessage("New Job Offer Not Created");
		}
		}
		else
		{
			response.setSuccess(false);
			response.setMessage("Cannot Create Job Offer Since Candidate Not selected");
		}
		return response;
	}

	@Override
	public ApiResponse editJobOffer(JobOffer jobObj, String jobOfferId) {
		ApiResponse response = new ApiResponse(false);
		JobOffer jobOffer = offerRepository.getById(jobOfferId);
		if(jobOffer != null) {
			jobOffer.setCandidateEmail(jobObj.getCandidateEmail());
			jobOffer.setCandidateMobile(jobObj.getCandidateMobile());
			jobOffer.setCandidateName(jobObj.getCandidateName());
			jobOffer.setDoj(jobObj.getDoj());
			jobOffer.setJobTitle(jobObj.getJobTitle());
			jobOffer.setSalary(jobObj.getSalary());
			jobOffer.setTotalExp(jobObj.getTotalExp());
			offerRepository.save(jobOffer);
			response.setSuccess(true);
			response.setMessage("Job Offers Updated Sucessfully");
			response.setContent(null);
		}
		
		else
		{
			response.setSuccess(false);
			response.setMessage("Job Offers Id does Not Exist");
		}
		

		return response;
	}

	@Override
	public ApiResponse getAllJobOffer(Pageable pageable) {
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
//		List<JobInterviews> allList =  jobInterviewRepository.getList();
		Page<JobOffer> allList =  offerRepository.findAll(new Specification<JobOffer>(){
				@Override
				public Predicate toPredicate(Root<JobOffer> root, javax.persistence.criteria.CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					// TODO Auto-generated method stub
					List<Predicate> predicates = new ArrayList<>();
//	                if(status != null && !status.equalsIgnoreCase("ALL")) {
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
//	                }
//	                if(userDetail.getUserId()!=null && userDetail.getUserRole() != "HR_ADMIN" && userDetail.getUserRole() != "TICKETINGTOOL_ADMIN") {
//	                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), "%" + userDetail.getUserId() + "%")));
//	                }
//	                if(searchObj.getSearchString() != null && !searchObj.getSearchString().equalsIgnoreCase("")) {
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateName"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateMobile"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateEmail"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), "%" + searchObj.getSearchString() + "%")));
//	                }
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
	        },pageable);
		
		content.put("OfferList",allList);
		if(allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Offers");
		}
		else {
			response.setSuccess(true);
			response.setMessage("No Records Found");
		}
		
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse changeJobOfferStatus(String jobOfferId, JobOfferStatus status) {
		ApiResponse response = new ApiResponse(false);
		JobOffer offer= offerRepository.getById(jobOfferId);
		if(offer!= null) {
			offer.setStatus(status);
			offerRepository.save(offer);
			response.setSuccess(true);
			response.setMessage("Job Offer Status Updated Sucessfully");
			response.setContent(null);
		}else {
			response.setSuccess(false);
			response.setMessage("Job Offer Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}


	

	

}
