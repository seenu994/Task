package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.ApplicationCommentsRepository;
import com.xyram.ticketingTool.Repository.CompanyWingsRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.JobApplicationRepository;
import com.xyram.ticketingTool.Repository.JobInterviewRepository;
import com.xyram.ticketingTool.Repository.JobOfferRepository;
import com.xyram.ticketingTool.Repository.JobRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.email.EmailService;
import com.xyram.ticketingTool.entity.ApplicationComments;
import com.xyram.ticketingTool.entity.CompanyWings;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.JobApplication;
import com.xyram.ticketingTool.entity.JobInterviews;
import com.xyram.ticketingTool.entity.JobOffer;
import com.xyram.ticketingTool.entity.JobOpenings;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.JobApplicationStatus;
import com.xyram.ticketingTool.enumType.JobOfferStatus;
import com.xyram.ticketingTool.enumType.JobOpeningStatus;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.InterviewRoundReviewRequest;
import com.xyram.ticketingTool.service.JobService;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class JobServiceImpl implements JobService {

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
	@Autowired
	PushNotificationCall pushNotificationCall;
	@Autowired
	PushNotificationRequest pushNotificationRequest;

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserRepository userRepo;
	@Autowired
	EmailService emailService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmpoloyeeServiceImpl employeeServiceImpl;
	@Autowired
	CompanyWingsRepository companyWingsRepository;

	@Value("${APPLICATION_URL}")
	private String application_url;

	static ChannelSftp channelSftp = null;
	static Session session = null;
	static Channel channel = null;
	static String PATHSEPARATOR = "/";

	@Override
	public ApiResponse createJob(JobOpenings jobObj) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		// Projects project = projectRepository.getById(ticketreq.getProjectId());
		if (jobObj.getWings() != null && jobObj.getWings().getId() != null) {
			CompanyWings wing = companyWingsRepository.getById(jobObj.getWings().getId());
			if (wing != null)

			{
				jobObj.setWings(wing);
			}

			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wing does not exist");
			}
		}
		jobObj.setUpdatedBy(userDetail.getUserId());
		jobObj.setCreatedAt(new Date());
		jobObj.setCreatedBy(userDetail.getUserId());
		jobObj.setJobStatus(JobOpeningStatus.VACANT);
		boolean jobCodeValidate = jobRepository.findb(jobObj.getJobCode());
		if (jobCodeValidate == false) {

			jobObj.setJobCode(jobObj.getJobCode());

		} else {
			response.setMessage("job code =" + jobObj.getJobCode() + " is already exists");

			return response;

		}
		if (jobRepository.save(jobObj) != null) {
			Employee emp = new Employee();

			List<Employee> EmployeeList = employeeRepository.getAllEmployeeNotification();
			for (Employee employeeNotification : EmployeeList) {
//				Map request = new HashMap<>();
//				request.put("eId", employeeNotification.geteId());
//
//				request.put("uid", employeeNotification.getUserCredientials().getUid());
//				request.put("title", "job CREATED");
//				request.put("body", " job Created - " + emp.getFirstName());
//				pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 12,
//						NotificationType.EMPLOYEE_CREATED.toString()));

				// inserting notification details
				Notifications notifications = new Notifications();
				notifications.setNotificationDesc("job created - " + emp.getFirstName());
				notifications.setNotificationType(NotificationType.JOB_CREATED);
				notifications.setSenderId(emp.getReportingTo());
				notifications.setReceiverId(userDetail.getUserId());
				notifications.setSeenStatus(false);
				notifications.setCreatedBy(userDetail.getUserId());
				notifications.setCreatedAt(new Date());
				notifications.setUpdatedBy(userDetail.getUserId());
				notifications.setLastUpdatedAt(new Date());

				notificationService.createNotification(notifications);
				UUID uuid = UUID.randomUUID();
				String uuidAsString = uuid.toString();
//				if (emp != null) {
//					String name = null;
//
//					HashMap mailDetails = new HashMap();
//					mailDetails.put("toEmail", employeeNotification.getEmail());
//					mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
//					mailDetails.put("message", "Hi " + name
//							+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
//							+ application_url + "/update-password" + "?key=" + uuidAsString
//							+ "\n\n Thanks for helping us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
//					emailService.sendMail(mailDetails);
//				}

				response.setSuccess(true);
				response.setMessage("New Job Opening Created");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("New Job Opening Not Created");
		}

		return response;
	}

	@Override
	public ApiResponse getAllJobs(Map<String, Object> filter, Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
		String status = filter.containsKey("status") ? ((String) filter.get("status")) : null;
		String searchQuery = filter.containsKey("searchstring") ? ((String) filter.get("searchstring")) : null;
		String wing = filter.containsKey("wing") ? ((String) filter.get("wing")) : null;
		JobOpeningStatus statusApp = null;
//		List<Map> allJobs = jobRepository.getAllJobOpenings();
//		List<JobOpenings> allList =  jobRepository.getList();
		Page<JobOpenings> allList = jobRepository.findAll(new Specification<JobOpenings>() {
			@Override
			public Predicate toPredicate(Root<JobOpenings> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				if (status != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("jobStatus"), statusApp.valueOf(status))));
				}

				if (wing != null) {
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.equal((root.get("wings").get("wingName")), wing)));
				}

				// criteriaBuilder.upper(itemRoot.get("code"), code.toUpperCase()
				if (searchQuery != null) {
//                	criteriaBuilder.like(root.get("title"), "%" + keyword + "%")
					// predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("jobTitle")),
					// "%"+searchQuery.toLowerCase()+"%"));
					predicates.add(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("jobTitle")), "%" + searchQuery + "%"));
					// predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("jobCode")),
					// "%"+searchQuery.toLowerCase()+"%"));

//					predicates.add(criteriaBuilder.like(root.get("jobDescription"), "%"+searchQuery+"%"));
//					predicates.add(criteriaBuilder.like(root.get("jobCode"), "%"+searchQuery+"%"));
//					predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("jobDescription"), "%"+searchQuery+"%")));
//					predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("jobCode"), "%"+searchQuery+"%")));
				}
				System.out.println(userDetail.getUserRole().equals("JOB_VENDOR"));
				if (userDetail.getUserRole().equals("JOB_VENDOR")) {

					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("vendor_view"), 1)));
				}
				query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		content.put("jobsList", allList);
		if (allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Jobs");
		} else {
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
		String status = filter.containsKey("status") ? ((String) filter.get("status")) : null;

		String searchQuery = filter.containsKey("searchstring") ? ((String) filter.get("searchstring")) : null;
		JobApplicationStatus statusApp = null;
		String vendor = filter.containsKey("vendor") ? ((String) filter.get("vendor")) : null;
		Page<JobApplication> allList = jobAppRepository.findAll(new Specification<JobApplication>() {
			@Override
			public Predicate toPredicate(Root<JobApplication> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				if (status != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("jobApplicationSatus"), statusApp.valueOf(status))));
				}
				if (vendor != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("referredVendor"), vendor)));
				}
				if (searchQuery != null) {
					predicates.add(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("candidateEmail")), "%" + searchQuery + "%"));
					
					/*
					 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(
					 * "candidateMobile"), searchQuery)));
					 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(
					 * "candidateEmail"), searchQuery)));
					 * 
					 * predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"),
					 * searchQuery)));
					 */
				}
				if (userDetail.getUserRole().equals("DEVELOPER") == true) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), userDetail.getUserId())));
				}
				if (userDetail.getUserRole().equals("INFRA_USER") == true) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), userDetail.getUserId())));
				}
				if (userDetail.getUserRole().equals("INFRA_ADMIN") == true) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), userDetail.getUserId())));
				}
				if (userDetail.getUserRole().equals("HR") == true) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("createdBy"), userDetail.getUserId())));
				}

				else {
					query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		content.put("jobAppList", allList);
		if (allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Jobs");
		} else {
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
	public ApiResponse createJobApplication(MultipartFile[] files, String jobAppString, String jobCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		JobOpenings jobOpening = jobRepository.getJobOpeningFromCode(jobCode);
		if (jobOpening != null) {
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (MultipartFile constentFile : files) {
				try {
					String fileextension = constentFile.getOriginalFilename()
							.substring(constentFile.getOriginalFilename().lastIndexOf("."));
					String filename = getRandomFileName() + fileextension;// constentFile.getOriginalFilename();
					if (addFileAdmin(constentFile, filename) != null) {
						jobAppObj.setResumePath(filename);
					}
				} catch (Exception e) {

				}
			}
			boolean Emailvalidate = jobAppRepository.findb(jobAppObj.getCandidateEmail());
			if (Emailvalidate == false) {

				//jobAppObj.setJobCode(jobCode);
				if (jobAppObj.getJobOpenings()!=null && jobAppObj.getJobOpenings().getId()!=null) {
					JobOpenings empObj = jobRepository.getJobOpeningsById(jobAppObj.getJobOpenings().getId());

					if (empObj != null) {

						jobAppObj.setJobOpenings(empObj);
					} else {
						response.setMessage("job oprning id not exsists");
						return response;
					}

				}
				else {
					  throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"job opening id is mandatory");
				}

				//jobAppObj.setJobOpenings(jobOpening);
				jobAppObj.setCreatedAt(new Date());
				jobAppObj.setCreatedBy(userDetail.getUserId());
				jobAppObj.setJobApplicationSatus(JobApplicationStatus.APPLIED);
				Employee empObi = new Employee();

				if (jobAppObj.getReferredEmployeeId() != null) {
					Employee empObj = employeeRepository.getEmployeeNameByScoleId(jobAppObj.getReferredEmployeeId());

					if (empObj != null) {

						jobAppObj.setReferredEmployee(empObj.getFirstName() + "" + empObj.getLastName());
					} else {
						response.setMessage("employee id not exsists");
						return response;
					}

				}

				if (jobAppRepository.save(jobAppObj) != null) {

					/*
					 * Employee empObj=new Employee(); List<Map> EmployeeByRole =
					 * employeeRepository.getEmployeeByRole();
					 * 
					 * 
					 * for (Map employeeNotification : EmployeeByRole) { Map request = new
					 * HashMap<>(); request.put("employeeId",
					 * employeeNotification.get("employeeId")); request.put("uid",
					 * employeeNotification.get("uid")); request.put("title", "EMPLOYEE CREATED");
					 * request.put("body", " employee Created - " + empObj.getRoleId());
					 * pushNotificationCall.restCallToNotification(pushNotificationRequest.
					 * PushNotification(request, 12, NotificationType.EMPLOYEE_CREATED.toString()));
					 * 
					 * } // inserting notification details Notifications notifications = new
					 * Notifications(); notifications.setNotificationDesc("employee created - " +
					 * empObj.getFirstName());
					 * notifications.setNotificationType(NotificationType.JOB_APPLOCATION_CREATED);
					 * notifications.setSenderId(empObj.getReportingTo());
					 * notifications.setReceiverId(userDetail.getUserId());
					 * notifications.setSeenStatus(false);
					 * notifications.setCreatedBy(userDetail.getUserId());
					 * notifications.setCreatedAt(new Date());
					 * notifications.setUpdatedBy(userDetail.getUserId());
					 * notifications.setLastUpdatedAt(new Date());
					 * 
					 * notificationService.createNotification(notifications); UUID uuid
					 * =UUID.randomUUID(); String uuidAsString = uuid.toString(); if(empObj!=null) {
					 * String name = null;
					 * 
					 * HashMap mailDetails = new HashMap(); mailDetails.put("toEmail",
					 * empObj.getEmail()); mailDetails.put("subject", name + ", " +
					 * "Here's your new PASSWORD"); mailDetails.put("message", "Hi " + name +
					 * ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
					 * + application_url + "/update-password" + "?key=" + uuidAsString +
					 * "\n\n Thanks for helping us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd."
					 * ); emailService.sendMail(mailDetails); }
					 */

					response.setSuccess(true);
					response.setMessage("New Job Application Created");
				} else {
					response.setSuccess(false);
					response.setMessage("New Job Application Not Created");
				}
			} else {
				response.setSuccess(false);
				response.setMessage("email already exists");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Job Code Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse scheduleJobInterview(JobInterviews schedule, String applicationId) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		JobApplication jobApp = jobAppRepository.getApplicationById(applicationId);
		List<JobInterviews> jobOpening = jobInterviewRepository.getInterviewByAppListId(applicationId);

		if (jobApp != null && jobOpening != null) {
			schedule.setCreatedAt(new Date());
			schedule.setCreatedBy(userDetail.getUserId());
			schedule.setJobInterviewStatus("SCHEDULED");
			schedule.setJobApplication(jobApp);
			Employee empObj = employeeRepository.getByEmpId(schedule.getInterviewer());
			if (empObj != null) {
				schedule.setInterviewerName(empObj.getFirstName());
			}
//			schedule.setInterviewer(emp);
			if (jobInterviewRepository.save(schedule) != null) {
				schedule.setJobInterviewStatus("SCHEDULED");
				jobAppRepository.save(jobApp);
				response.setSuccess(true);
				response.setMessage("Interview Scheduled");
			} else {
				response.setSuccess(false);
				response.setMessage("Interview Not Scheduled");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse getAllJobInterviews(Map<String, Object> filter, Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		Map content = new HashMap();
//		List<JobInterviews> allList =  jobInterviewRepository.getList();
		String status = filter.containsKey("status") ? ((String) filter.get("status")) : null;
		String searchQuery = filter.containsKey("searchstring") ? ((String) filter.get("searchstring")).toLowerCase()
				: null;
		JobInterviews statusApp = null;
		Page<JobInterviews> allList = jobInterviewRepository.findAll(new Specification<JobInterviews>() {
			@Override
			public Predicate toPredicate(Root<JobInterviews> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				if (status != null && !status.equalsIgnoreCase("ALL")) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobInterviewStatus"), status)));
				}
				if (userDetail.getUserRole().equals("DEVELOPER") || userDetail.getUserRole().equals("HR")
						|| userDetail.getUserRole().equals("INFRA_ADMIN")
						|| userDetail.getUserRole().equals("INFRA_USER")) {
					System.out.println(userDetail.getUserId());
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("interviewer"), userDetail.getScopeId())));
				}
				if (searchQuery != null) {
//	                	criteriaBuilder.like(root.get("title"), "%" + keyword + "%")
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateName"), searchQuery)));
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), searchQuery)));
				}
//	                if(searchObj.getSearchString() != null && !searchObj.getSearchString().equalsIgnoreCase("")) {
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateName"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateMobile"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("candidateEmail"), "%" + searchObj.getSearchString() + "%")));
//	                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobCode"), "%" + searchObj.getSearchString() + "%")));
//	                }
				query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);

		content.put("InterviewList", allList);
		if (allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Interviews");
		} else {
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

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public String addFileAdmin(MultipartFile file, String fileName) {
		System.out.println("bjsjsjn");
		String SFTPHOST = "13.229.182.200"; // SFTP Host Name or SFTP Host IP Address
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
			channelSftp.put(file.getInputStream(), fileName);
			System.out.println("added");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
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
		Map jobOpening = jobRepository.getJobOpeningById(jobOpeningId);
		if (jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		} else {
			response.setSuccess(false);
			response.setMessage("Job jobOpeningId Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse changeJobOpeningStatus(String jobOpeningId, JobOpeningStatus jobOpeningStatus) {
		ApiResponse response = new ApiResponse(false);
		JobOpenings status = jobRepository.getById(jobOpeningId);
		if (status != null) {
			status.setJobStatus(jobOpeningStatus);
			jobRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Opening Status Updated Sucessfully");
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Opening Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public ApiResponse editJobInterview(JobInterviews jobInterviewRequest, String interviewId) {

		ApiResponse response = new ApiResponse(false);

		System.out.println("InterviewId::" + interviewId);
		JobInterviews jbInterviews = jobInterviewRepository.getById(interviewId);

		System.out.println("feedBack::" + jobInterviewRequest.getFeedback());
		if (jbInterviews != null) {

			jbInterviews.setFeedback(jobInterviewRequest.getFeedback());
			jbInterviews.setInterviewType(jobInterviewRequest.getInterviewType());
			jbInterviews.setRoundDetails(jobInterviewRequest.getRoundDetails());
			jbInterviews.setRoundNo(jobInterviewRequest.getRoundNo());
			jbInterviews.setInterviewLink(jobInterviewRequest.getInterviewLink());
			jbInterviews.setInterviewer(jobInterviewRequest.getInterviewer());
			jbInterviews.setInterviewDate(jobInterviewRequest.getInterviewDate());
			jbInterviews.setRecommendation(jobInterviewRequest.getRecommendation());
			jbInterviews.setRating(jobInterviewRequest.getRateGiven());
			jobInterviewRepository.save(jbInterviews);
			response.setSuccess(true);
			String msg = "Job Interview Updated Successfully";
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
	public ApiResponse changeJobInterviewStatus(String jobInerviewId, String jobInterviewStatus, Integer rating,
			String feedback, String comments) {
		ApiResponse response = new ApiResponse(false);
		JobInterviews status = jobInterviewRepository.getById(jobInerviewId);
		JobApplication application = jobAppRepository.getApplicationById(status.getJobApplication().getId());
		if (status != null) {
			status.setJobInterviewStatus(jobInterviewStatus);
			status.setFeedback(feedback);
			status.setRateGiven(rating);
			if (comments != null) {
				status.setInterviewerComments(comments);
				if (jobInterviewStatus.equals("SELECTED")) {
					application.setJobApplicationSatus(JobApplicationStatus.SELECTED);
					jobAppRepository.save(application);
				}
			}
			jobInterviewRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Interview Status Updated Sucessfully");
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Interview Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public ApiResponse updateInterviewRoundStatus(String jobInerviewId, InterviewRoundReviewRequest request) {
		ApiResponse response = new ApiResponse(false);
		JobInterviews status = jobInterviewRepository.getById(jobInerviewId);
		JobApplication applicationdetails = jobAppRepository.getApplicationById(status.getJobApplication().getId());

		if (status != null) {
			applicationdetails.setJobApplicationSatus(JobApplicationStatus.SELECTED);
			jobAppRepository.save(applicationdetails);
			status.setJobInterviewStatus(request.getJobInterviewStatus());

			status.setFeedback(request.getFeedback());

			jobInterviewRepository.save(status);
			response.setSuccess(true);
			response.setMessage("Job Interview Status Updated Sucessfully");
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Interview Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	public ApiResponse editJob(String jobId, JobOpenings jobObj) {
		ApiResponse response = new ApiResponse(false);
		JobOpenings jobOpening = jobRepository.getById(jobId);
		if (jobOpening != null) {
			jobOpening.setUpdatedBy(userDetail.getUserId());
			jobOpening.setJobDescription(jobObj.getJobDescription());
			jobOpening.setJobCode(jobObj.getJobCode());
			jobOpening.setFilledPositions(jobObj.getFilledPositions());
			jobOpening.setJobSkills(jobObj.getJobSkills());
			jobOpening.setJobTitle(jobObj.getJobTitle());
			jobOpening.setLastUpdatedAt(new Date());
			jobOpening.setMaxExp(jobObj.getMaxExp());
			jobOpening.setMinExp(jobObj.getMinExp());
			jobOpening.setSalary(jobObj.getSalary());
			jobOpening.setTotalOpenings(jobObj.getTotalOpenings());

			if (jobObj.getWings() != null && jobObj.getWings().getId() != null) {
				CompanyWings wing = companyWingsRepository.getById(jobObj.getWings().getId());
				if (wing != null)

				{
					jobOpening.setWings(wing);
				}

				else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wing does not exist");
				}
			}

//			jobOpening.setWings(jobObj.getWings());

			jobOpening.setFilledPositions(jobObj.getFilledPositions());

//			jobOpening.setWings(jobObj.getWings());
			jobRepository.save(jobOpening);
			response.setSuccess(true);
			response.setMessage("Job Opening Updated Sucessfully");
			response.setContent(null);
		}

		else {
			response.setSuccess(false);
			response.setMessage("Job Opening Id does Not Exist");
		}

		return response;
	}

	@Override
	public ApiResponse changeJobApplicationStatus(String jobApplicationId, JobApplicationStatus jobStatus,
			String comment) {
		ApiResponse response = new ApiResponse(false);
		JobApplication status = jobAppRepository.getApplicationById(jobApplicationId);
		System.out.println(userDetail.getUserRole());
		if (status != null) {
			if (userDetail.getUserId().equals(status.getCreatedBy()) || userDetail.getUserRole() == "HR_ADMIN") {
				if (status != null) {
					ApplicationComments appComments = new ApplicationComments();
					appComments.setApplicationId(jobApplicationId);
					if (comment != null) {
						appComments.setGivenDescription(comment);
					} else {
						appComments.setAutoDescription(
								"Status changed to" + " " + jobStatus + "by" + status.getCreatedBy());
					}
					appRepository.save(appComments);
					status.setJobApplicationSatus(jobStatus);
					jobAppRepository.save(status);
					response.setSuccess(true);
					response.setMessage("Job Application Status Updated Sucessfully");
					response.setContent(null);
				}
			} else {
				response.setSuccess(false);
				response.setMessage("Job application Id does Not Exist");
			}
		} else {
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
		if (jobApp != null && userDetail.getUserId().equals(jobApp.getCreatedBy())
				|| userDetail.getUserRole().equals(UserRole.HR_ADMIN)) {
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (files != null) {
				for (MultipartFile constentFile : files) {
					try {
						String fileextension = constentFile.getOriginalFilename()
								.substring(constentFile.getOriginalFilename().lastIndexOf("."));
						String filename = getRandomFileName() + fileextension;// constentFile.getOriginalFilename();
						if (addFileAdmin(constentFile, filename) != null) {
							jobApp.setResumePath(filename);
						}
					} catch (Exception e) {

					}
				}
			}
			
			JobOpenings jobs=jobRepository.getJobCodeValidation(newJobAppObj.getJobCode());
				if(jobs!=null) {
			
			jobApp.setJobCode(jobs.getJobCode());
				}
				else {
					response.setMessage("job code not valid");
					return response;
				}
				
				if (newJobAppObj.getJobCode()!=null) {
					JobOpenings empObj = jobRepository.getJobCode(newJobAppObj.getJobCode());

					if (empObj != null) {

						jobApp.setJobOpenings(empObj);
					} else {
						response.setMessage("employee id not exsists");
						return response;
					}

				}
			
			
			jobApp.setCandidateEmail(newJobAppObj.getCandidateEmail());
			jobApp.setCandidateMobile(newJobAppObj.getCandidateMobile());
			jobApp.setCandidateName(newJobAppObj.getCandidateName());
			jobApp.setExpectedSalary(newJobAppObj.getExpectedSalary());

			if (jobAppRepository.save(jobApp) != null) {
				response.setSuccess(true);
				response.setMessage(" Job Application Updated");
			} else {
				response.setSuccess(false);
				response.setMessage(" Job Application Not Updated");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Job Application Id Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse getAllJobAppById(String jobAppId) {
		ApiResponse response = new ApiResponse(false);
		Map jobOpening = jobAppRepository.getAppById(jobAppId);
		if (jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;
	}

	@Override
	public ApiResponse getAllInterviewId(String jobInterviewId) {
		ApiResponse response = new ApiResponse(false);
		Map jobOpening = jobInterviewRepository.getInterviewById(jobInterviewId);
		if (jobOpening != null) {
			response.setSuccess(true);
			response.setMessage("Job Opening Detail");
			response.setContent(jobOpening);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Application Not Exist");
		}
		return response;

	}

	@Override
	public ApiResponse createJobOffer(JobOffer jobObj, String jobAppId) {
		ApiResponse response = new ApiResponse(false);
		JobApplication application = jobAppRepository.getApplicationById(jobAppId);
		if (application.getJobApplicationSatus().equals(JobApplicationStatus.SELECTED)) {
			jobObj.setCreatedAt(new Date());
			jobObj.setCreatedBy(userDetail.getUserId());
			jobObj.setApplicationId(application);
			if (offerRepository.save(jobObj) != null) {
				response.setSuccess(true);
				response.setMessage("New Job Offer Created");
			} else {
				response.setSuccess(false);
				response.setMessage("New Job Offer Not Created");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Cannot Create Job Offer Since Candidate Not selected");
		}
		return response;
	}

	@Override
	public ApiResponse editJobOffer(JobOffer jobObj, String jobOfferId) {
		ApiResponse response = new ApiResponse(false);
		JobOffer jobOffer = offerRepository.getById(jobOfferId);
		if (jobOffer != null) {
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

		else {
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
		Page<JobOffer> allList = offerRepository.findAll(new Specification<JobOffer>() {
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
				query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);

		content.put("OfferList", allList);
		if (allList != null) {
			response.setSuccess(true);
			response.setMessage("Succesfully retrieved Offers");
		} else {
			response.setSuccess(true);
			response.setMessage("No Records Found");
		}

		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse changeJobOfferStatus(String jobOfferId, JobOfferStatus status) {
		ApiResponse response = new ApiResponse(false);
		JobOffer offer = offerRepository.getById(jobOfferId);
		if (offer != null) {
			offer.setStatus(status);
			offerRepository.save(offer);
			response.setSuccess(true);
			response.setMessage("Job Offer Status Updated Sucessfully");
			response.setContent(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Offer Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public ApiResponse getAllJobOfferById(String offerId) {
		ApiResponse response = new ApiResponse(false);
		List<JobOffer> offer = offerRepository.getAllJobOfferById(offerId);
		if (offer != null) {
			Map content = new HashMap();
			content.put("offer", offer);
			response.setSuccess(true);
			response.setMessage(" Offer retrieved Sucessfully");
			response.setContent(content);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Offer Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public ApiResponse getAllJobCodes() {
		ApiResponse response = new ApiResponse(false);
		List<Map> offer = jobRepository.getAllJobCodes();
		Map res = new HashMap();
		if (offer != null) {
			res.put("jobcodes", offer);
			response.setSuccess(true);
			response.setMessage(" Offer retrieved Sucessfully");
			response.setContent(res);
		} else {
			response.setSuccess(false);
			response.setMessage("Job Offer Id does Not Exist");
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public ApiResponse getJobInterviewByAppId(String applicationId) {

		ApiResponse response = new ApiResponse(false);
		List<Predicate> predicates = new ArrayList<>();
		if (userDetail.getUserRole().equals("DEVELOPER") || userDetail.getUserRole().equals("INFRA_ADMIN")
				|| userDetail.getUserRole().equals("INFRA_USER")) {

			System.out.println(userDetail.getUserId());
			List<Map> jobInterviewsObj = jobInterviewRepository.getInterviwerByScopeID(userDetail.getScopeId());
			Map interviews = new HashMap();
			interviews.put("interviews", jobInterviewsObj);
			if (jobInterviewsObj.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("Job Application Not Exist");

			} else {
				response.setSuccess(true);
				response.setMessage("Job Opening Detail");
				response.setContent(interviews);
			}
			return response;

		} else {
			if (userDetail.getUserRole().equals("HR_ADMIN") || userDetail.getUserRole().equals("TICKETINGTOOL_ADMIN")) {

				List<Map> jobOpening = jobInterviewRepository.getInterviewByAppId(applicationId);
				Map interviews = new HashMap();
				interviews.put("interviews", jobOpening);
				if (jobOpening.isEmpty()) {
					response.setSuccess(false);
					response.setMessage("Job Application Not Exist");

				} else {
					response.setSuccess(true);
					response.setMessage("Job Opening Detail");
					response.setContent(interviews);
				}
				return response;
			}
		}
		return response;
	}

	@Override
	public ApiResponse getJobCode(String jobCode) {
		ApiResponse response = new ApiResponse(false);
		JobOpenings opening = jobRepository.getJobCode(jobCode);

		if (opening != null) {
			response.setSuccess(false);
			response.setMessage("Job code already exist");
		} else {
			response.setSuccess(true);
			response.setMessage("Job Code does not exist");
		}
		return response;
	}

	@Override
	public ApiResponse getApplicationList(String jobCodeId) {
		ApiResponse response = new ApiResponse(false);
		List<Map> jobOpening = jobAppRepository.getjobOpeningsById(jobCodeId);
		Map jobAppList = new HashMap();
		jobAppList.put("jobAppList", jobOpening);
		if (jobOpening != null) {
			response.setSuccess(true);
			response.setContent(jobAppList);
			response.setMessage("Job list");

		} else {
			response.setSuccess(false);
			response.setMessage("Job Code Id is Mandatory");
		}
		return response;
	}

	@Override
	public ApiResponse getRoundDetails(String appId, Integer roundNo) {
		ApiResponse response = new ApiResponse(false);
		JobInterviews interviewObj = jobInterviewRepository.getInterviewByAppIdRound(appId);
		if (interviewObj != null && interviewObj.getRoundNo().equals(roundNo)) {
			response.setSuccess(false);
			response.setMessage("Round Already Exists");
		}

		else {
			response.setSuccess(true);
			response.setMessage("Round does not exists");
		}

		return response;
	}

}
