package com.xyram.ticketingTool.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.HrCalendarCommentRepository;
import com.xyram.ticketingTool.Repository.HrCalendarRepository;
import com.xyram.ticketingTool.Repository.JobRepository;
import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.controller.HrCalendarController;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.HrCalendar;
import com.xyram.ticketingTool.entity.HrCalendarComment;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.HrCalendarService;
import com.xyram.ticketingTool.ticket.config.EmployeePermissionConfig;
import com.xyram.ticketingTool.util.ExcelUtil;
import com.xyram.ticketingTool.util.HrCalendarTimeZone;

@Service
@Transactional
public class HrCalendarServiceImpl implements HrCalendarService {

	@Autowired
	CurrentUser currentUser;

	@Autowired
	HrCalendarServiceImpl HrCalendarServiceImpl;

	@Autowired
	HrCalendarRepository hrCalendarRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	HrCalendarCommentRepository cmtRepository;

	@Autowired
	EmployeePermissionConfig empPerConfig;

	private final Logger logger = LoggerFactory.getLogger(HrCalendarController.class);

	@Override
	public ApiResponse createScheduleInCalendar(HrCalendar schedule) throws Exception {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create Hrcalendar");
			return response;
		}
		if (schedule != null) {
			if (validateSchedule(schedule)) {
				if (schedule.getJobId() != null) {
					Map job = jobRepository.getJobById(schedule.getJobId());
					if (job == null) {
						response.setSuccess(false);
						response.setMessage("Job Id not found.");
						return response;
					}
				} else {
					response.setSuccess(false);
					response.setMessage("Job Id not found.");
					return response;
				}
				if (schedule.getIs_scheduled()) {

					Date toDateTime = new Date();
					long diff = schedule.getScheduleDate().getTime() - toDateTime.getTime();// as given
					if (schedule.getScheduleDate().getDate() == toDateTime.getDate()) {
						long diffMinutes = diff / (60 * 1000);
						if (diffMinutes < 15) {
							response.setSuccess(false);
							response.setMessage(
									"A future date is permitted, and minimum 15 minutes prior to the current time is required.");
							return response;
						}
					}
				}

				Employee employee = employeeRepository.getByEmpId(currentUser.getScopeId());
				Employee reportor = employeeRepository.getByEmpId(employee.getReportingTo());
				if (reportor != null) {
					schedule.setCreatedAt(new Date());
					schedule.setCreatedBy(currentUser.getUserId());
					schedule.setUpdatedBy(currentUser.getUserId());
					schedule.setLastUpdatedAt(new Date());
					schedule.setReportingTo(reportor.getUserCredientials().getId());
					if (schedule.getCallCount() == null) {
						schedule.setCallCount(0);
					}
					HrCalendar savedObj = hrCalendarRepository.save(schedule);

					schedule.setStatus("SCHEDULED");
					response.setSuccess(true);
					response.setMessage("Schedule created successfully.");
					Map content = new HashMap();
					content.put("scheduleId", savedObj.getId());
					response.setContent(content);
				} else {
					response.setSuccess(false);
					response.setMessage("Employee reporter not found.");
				}
			} else {
				response.setSuccess(false);
				response.setMessage("Mandatory fields are not encountered.");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse editScheduleInCalendar(Boolean validateDateTime, HrCalendar schedule) throws Exception {

		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to edit Hrcalendar");
			return response;
		}

		HrCalendar scheduleObj = hrCalendarRepository.findById(schedule.getId()).get();
		if (schedule != null && scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this schedule.");
			}
			if (validateSchedule(schedule)) {
				Employee employee = employeeRepository.getByEmpId(currentUser.getScopeId());
				Employee reportor = employeeRepository.getByEmpId(employee.getReportingTo());
				if (schedule.getJobId() != null) {
					Map job = jobRepository.getJobById(schedule.getJobId());
					if (job == null) {
						response.setSuccess(false);
						response.setMessage("Job Id not found.");
						return response;
					}
				} else {
					response.setSuccess(false);
					response.setMessage("Job Id not found.");
					return response;
				}
				if (schedule.getIs_scheduled() && validateDateTime) {
					Date toDateTime = new Date();
					long diff = schedule.getScheduleDate().getTime() - toDateTime.getTime();// as given

					if (schedule.getScheduleDate().getDate() == toDateTime.getDate()) {
						long diffMinutes = diff / (60 * 1000);
						if (diffMinutes < 15) {
							response.setSuccess(false);
							response.setMessage(
									"A future date is permitted, and minimum 15 minutes prior to the current time is required.");
							return response;
						}
					}
				}

				if (reportor != null) {
					scheduleObj.setUpdatedBy(currentUser.getUserId());
					scheduleObj.setLastUpdatedAt(new Date());
					scheduleObj.setReportingTo(reportor.getUserCredientials().getId());

					scheduleObj.setCandidateMobile(schedule.getCandidateMobile());
					scheduleObj.setCandidateName(schedule.getCandidateName());
					scheduleObj.setJobId(schedule.getJobId());
					scheduleObj.setIs_scheduled(schedule.getIs_scheduled());
					scheduleObj.setScheduleDate(schedule.getScheduleDate());
					if (schedule.getSearchedSource() != null)
						scheduleObj.setSearchedSource(schedule.getSearchedSource());

					hrCalendarRepository.save(scheduleObj);
					response.setSuccess(true);
					response.setMessage("Schedule edited successfully.");
				} else {
					response.setSuccess(false);
					response.setMessage("Employee reporter not found.");
				}
			} else {
				response.setSuccess(false);
				response.setMessage("Mandatory fields are not encountered.");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse deleteScheduleInCalendar(String scheduleId) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to deleteScheduleInCalendar");
			return response;
		}

		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this schedule.");
			}
			hrCalendarRepository.delete(scheduleObj);
			response.setSuccess(true);
			response.setMessage("Schedule deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@SuppressWarnings("deprecation")
	@Override

	public ApiResponse changeScheduleStatus(String scheduleId, String comment, String status, Date scheduleDate) {
		ApiResponse response = new ApiResponse(false);

		// "CANDIDATE-NOT-INTERESTED","CANDIDATE-NOT-PICKED","CANDIDATE-NOT-SUITS"
		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this schedule.");
			}
			if (status.equalsIgnoreCase("RE-SCHEDULED")) {
				Date toDateTime = new Date();
				long diff = scheduleDate.getTime() - toDateTime.getTime();// as given

				if (scheduleDate.getDate() == toDateTime.getDate()) {
					long diffMinutes = diff / (60 * 1000);
					if (diffMinutes < 15) {
						response.setSuccess(false);
						response.setMessage(
								"A future date is permitted, and minimum 15 minutes prior to the current time is required. "
										+ scheduleObj.getScheduleDate().getDate() + "-" + toDateTime.getDate());
						return response;
					}
				}
//				else {
//					response.setSuccess(false);
//					response.setMessage("Only future date is allowed and Min 15 minutes prior to the present time is required.");
//					return response;
//				}
				scheduleObj.setScheduleDate(scheduleDate);
				scheduleObj.setClosed(false);
			} else if (status.equalsIgnoreCase("CANCELLED")) {
				scheduleObj.setClosed(true);

			} else if (status.equalsIgnoreCase("CANDIDATE-NOT-SUITS")) {
				scheduleObj.setClosed(true);

			} else if (status.equalsIgnoreCase("CANDIDATE-NOT-INTERESTED")) {
				scheduleObj.setClosed(true);

			} else if (status.equalsIgnoreCase("CANDIDATE-INTERESTED")) {
				scheduleObj.setClosed(true);

			} else {
				response.setSuccess(false);
				response.setMessage("Status is not proper.");
				return response;
			}
			HrCalendarComment cmt = new HrCalendarComment();
			cmt.setScheduleId(scheduleId);
			cmt.setDescription(comment);
			cmt.setCreatedAt(new Date());
			cmt.setCreatedBy(currentUser.getUserId());
			cmt.setUpdatedBy(currentUser.getUserId());
			cmt.setLastUpdatedAt(new Date());
			cmtRepository.save(cmt);

			scheduleObj.setStatus(status.toUpperCase());
			scheduleObj.setLastUpdatedAt(new Date());

			hrCalendarRepository.save(scheduleObj);
			response.setSuccess(true);
			response.setMessage("Status changed successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}
		return response;
	}

	@Override
	public ApiResponse updateScheduleCallCounter(String scheduleId) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to updatecallcounter Hrcalendar");
			return response;
		}

		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this schedule.");
			}
			scheduleObj.setCallCount(scheduleObj.getCallCount() + 1);
			scheduleObj.setLastUpdatedAt(new Date());
			hrCalendarRepository.save(scheduleObj);
			response.setSuccess(true);
			response.setMessage("Updated schedule call counter successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse addCommentToSchedule(String scheduleId, String comment) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to addCommentsTo Hrcalendar");
			return response;
		}

		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this schedule.");
			}
			scheduleObj.setLastUpdatedAt(new Date());
			hrCalendarRepository.save(scheduleObj);
			HrCalendarComment cmt = new HrCalendarComment();
			cmt.setScheduleId(scheduleId);
			cmt.setDescription(comment);
			cmt.setCreatedAt(new Date());
			cmt.setCreatedBy(currentUser.getUserId());
			cmt.setUpdatedBy(currentUser.getUserId());
			cmt.setLastUpdatedAt(new Date());
			cmtRepository.save(cmt);

			response.setSuccess(true);
			response.setMessage("Comment added successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse getAllHrScheduleStatus() throws Exception {
		ApiResponse response = new ApiResponse(true);

		if (!empPerConfig.isHavingpersmission("hrCalViewAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to updatecallcounter Hrcalendar");
			return response;
		}

		String statusArr[] = { "SCHEDULED", "RE-SCHEDULED", "CANDIDATE-NOT-INTERESTED", "CANDIDATE-INTERESTED",
				"CANDIDATE-NOT-SUITS", "CANCELLED" };
		Map content = new HashMap();
		content.put("Statuses", statusArr);
		response.setContent(content);
		response.setMessage("Successfully retrieved");
		return response;
	}

	@Override
	public ApiResponse getCandidateHistory(String mobileNo) throws Exception {
		ApiResponse response = new ApiResponse(false);
		
		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create Hrcalendar");
			return response;
		}
		if (mobileNo.length() != 10) {
			response.setSuccess(false);
			response.setMessage("Not a Valid Mobile No.");
			return response;
		}
		List<Map> shceduleList = hrCalendarRepository.getCandidateHistory(mobileNo);

		if (shceduleList.size() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}

		return response;
	}

	@Override
	public ApiResponse getScheduleDetail(String scheduleId) {
		ApiResponse response = new ApiResponse(false);

		Map scheduleObj = hrCalendarRepository.getScheduleById(scheduleId);
		if (scheduleObj != null) {
			Map content = new HashMap();
			content.put("schedule", scheduleObj);
//			ObjectMapper oMapper = new ObjectMapper();
//			Map<String, Object> map = oMapper.convertValue(scheduleObj, Map.class);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("Retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Schedule not found.");
		}
		return response;
	}

	@Override
	public ApiResponse searchMyTeamSchedulesInCalender(String searchString) {
		ApiResponse response = new ApiResponse(false);
		if (searchString.length() == 0) {
			response.setSuccess(false);
			response.setMessage("Search String is empty.");
			return response;
		}
//		Employee reportor = employeeRepository.getByEmpId(currentUser.getUserId());
//		if(reportor == null) {
//			response.setSuccess(false);
//			response.setMessage("Reporter Not Found.");
//			return response;
//		}
		List<Map> shceduleList = hrCalendarRepository.searchInMyTeamShedule(currentUser.getUserId(), searchString);

		if (shceduleList.size() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}

		return response;
	}

	@Override
	public ApiResponse searchSchedulesInCalender(String searchString) {
		ApiResponse response = new ApiResponse(false);
		if (searchString.length() == 0) {
			response.setSuccess(false);
			response.setMessage("Search String is empty.");
			return response;
		}
		List<Map> shceduleList = hrCalendarRepository.searchInMyShedule(currentUser.getUserId(), searchString);

		if (shceduleList.size() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}

		return response;
	}

	@Override
	public ApiResponse getAllMySchedulesFromCalendarByStatus(Map<String, Object> filter, Pageable pageable) throws Exception {
		ApiResponse response = new ApiResponse(false);
		
		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create Hrcalendar");
			return response;
		}

		String jobId = filter.containsKey("jobId") ? ((String) filter.get("jobId")) : null;
		Boolean closed = filter.containsKey("closed") ? ((Boolean) filter.get("closed")) : false;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDate != null && toDate != null) {
			try {

				parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

			} catch (ParseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");
			}
		}

		Page<Map> shceduleList = hrCalendarRepository.getAllMySchedulesFromCalendarByStatus(currentUser.getUserId(),
				jobId, fromDate, toDate, status, closed, pageable);

		if (shceduleList.getSize() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retreived successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}

		return response;
	}

	@Override
	public ApiResponse getAllMyTeamSchedulesFromCalendarByStatus(Map<String, Object> filter, Pageable pageable) throws Exception {
		ApiResponse response = new ApiResponse(false);
		
		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create Hrcalendar");
			return response;
		}

		String jobId = filter.containsKey("jobId") ? ((String) filter.get("jobId")) : null;
		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId")) : null;
		Boolean closed = filter.containsKey("closed") ? ((Boolean) filter.get("closed")) : false;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDate != null && toDate != null) {
			try {

				parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

			} catch (ParseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");
			}
		}

		Page<Map> shceduleList = hrCalendarRepository.getAllMyTeamSchedulesFromCalendarByStatus(currentUser.getUserId(),
				employeeId, jobId, fromDate, toDate, status, closed, pageable);

		if (shceduleList.getSize() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retrieved successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}
		return response;
	}

	/*
	 * @Override public Map downloadAllMySchedulesFromCalendarByStatus(Map<String,
	 * Object> filter) throws ParseException, FileUploadException, IOException{ Map
	 * response = new HashMap();
	 * 
	 * // String jobId = filter.containsKey("jobId") ? ((String)
	 * filter.get("jobId")) // : null; Boolean closed = filter.containsKey("closed")
	 * ? ((Boolean) filter.get("closed")) : false; String status =
	 * filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase()
	 * : null; String fromDate = filter.containsKey("fromDate") ?
	 * filter.get("fromDate").toString() : null; String toDate =
	 * filter.containsKey("toDate") ? filter.get("toDate").toString() : null;
	 * 
	 * Date parsedfromDate = null; Date parsedtoDate = null; SimpleDateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * if(fromDate != null && toDate != null) { try {
	 * 
	 * parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
	 * parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;
	 * 
	 * } catch (ParseException e) { throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST,
	 * "Invalid date format date should be yyyy-MM-dd"); } }
	 * 
	 * List<HrCalendar> schedule =
	 * hrCalendarRepository.downloadAllMySchedulesFromCalendarByStatus(fromDate,
	 * toDate, status, closed);
	 * 
	 * if(schedule.size() > 0) { List excelHeaders = Arrays.asList("Name",
	 * "Job code", "Date & Time", "Source", "Status"); List excelData = new
	 * ArrayList<>(); int index = 1;
	 * 
	 * for (HrCalendar scheduleList : schedule) { Map row = new HashMap(); String
	 * getJobCode = jobRepository.getJobCodeById(scheduleList.getJobId());
	 * 
	 * row.put("Name", scheduleList.getCandidateName()); row.put("Job Code",
	 * getJobCode); row.put("Date & Time", scheduleList.getScheduleDate());
	 * row.put("Source", scheduleList.getSearchedSource()); row.put("Status",
	 * scheduleList.getStatus());
	 * 
	 * excelData.add(row); index++; }
	 * 
	 * 
	 * XSSFWorkbook workbook = ExcelWriter.writeToExcel(excelHeaders, excelData,
	 * "My Schedule Details", null, "My Schedule Details", 1, 0);
	 * 
	 * 
	 * String filename = new
	 * SimpleDateFormat("'MySchedule_details_'yyyyMMddHHmmss'.xlsx'").format(new
	 * Date());
	 * 
	 * Path fileStorageLocation = Paths.get(ResponseMessages.BASE_DIRECTORY +
	 * ResponseMessages.HR_CALENDAR_DIRECTORY );
	 * Files.createDirectories(fileStorageLocation);
	 * 
	 * try { FileOutputStream out = new FileOutputStream( new
	 * File(ResponseMessages.BASE_DIRECTORY + ResponseMessages.HR_CALENDAR_DIRECTORY
	 * + filename)); workbook.write(out); out.close(); // logger.info(filename +
	 * " written successfully on disk."); } catch (Exception e) { //
	 * logger.error("Exception occured while saving pincode details" +
	 * e.getCause()); throw e; } response.put("fileLocation",
	 * ResponseMessages.HR_CALENDAR_DIRECTORY + filename);
	 * 
	 * return response; } else { throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST, "No Records Found!"); } }
	 */
	/*
	 * @Override public Map downloadMyTeamSchedulesFromCalendarByStatus(Map<String,
	 * Object> filter) throws ParseException, FileUploadException, IOException{ Map
	 * response = new HashMap();
	 * 
	 * // String jobId = filter.containsKey("jobId") ? ((String)
	 * filter.get("jobId")) // : null; Boolean closed = filter.containsKey("closed")
	 * ? ((Boolean) filter.get("closed")) : false; String status =
	 * filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase()
	 * : null; String fromDate = filter.containsKey("fromDate") ?
	 * filter.get("fromDate").toString() : null; String toDate =
	 * filter.containsKey("toDate") ? filter.get("toDate").toString() : null;
	 * 
	 * Date parsedfromDate = null; Date parsedtoDate = null; SimpleDateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * if(fromDate != null && toDate != null) { try {
	 * 
	 * parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
	 * parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;
	 * 
	 * } catch (ParseException e) { throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST,
	 * "Invalid date format date should be yyyy-MM-dd"); } }
	 * 
	 * List<HrCalendar> schedule =
	 * hrCalendarRepository.downloadAllMyTeamSchedulesFromCalendarByStatus(fromDate,
	 * toDate, status, closed);
	 * 
	 * if(schedule.size() > 0) { List excelHeaders = Arrays.asList("Name",
	 * "Job code", "Date & Time", "Scheduled By", "Source", "Status"); List
	 * excelData = new ArrayList<>(); int index = 1;
	 * 
	 * for (HrCalendar scheduleList : schedule) { Map row = new HashMap(); String
	 * getJobCode = jobRepository.getJobCodeById(scheduleList.getJobId());
	 * row.put("Name", scheduleList.getCandidateName()); row.put("Job code",
	 * getJobCode); row.put("Date", scheduleList.getScheduleDate()); //
	 * row.put("Time", scheduleList.get); row.put("Scheduled By",
	 * scheduleList.getCreatedBy()); row.put("Source",
	 * scheduleList.getSearchedSource()); row.put("Status",
	 * scheduleList.getStatus());
	 * 
	 * excelData.add(row); index++; }
	 * 
	 * 
	 * XSSFWorkbook workbook = ExcelWriter.writeToExcel(excelHeaders, excelData,
	 * "My Team Schedule Details", null, "My Team Schedule Details", 1, 0);
	 * 
	 * 
	 * String filename = new
	 * SimpleDateFormat("'MyTeamSchhedule_details_'yyyyMMddHHmmss'.xlsx'").format(
	 * new Date());
	 * 
	 * Path fileStorageLocation = Paths.get(ResponseMessages.BASE_DIRECTORY +
	 * ResponseMessages.HR_DIRECTORY );
	 * Files.createDirectories(fileStorageLocation);
	 * 
	 * try { FileOutputStream out = new FileOutputStream( new
	 * File(ResponseMessages.BASE_DIRECTORY + ResponseMessages.HR_DIRECTORY +
	 * filename)); workbook.write(out); out.close(); // logger.info(filename +
	 * " written successfully on disk."); } catch (Exception e) { //
	 * logger.error("Exception occured while saving pincode details" +
	 * e.getCause()); throw e; } response.put("fileLocation",
	 * ResponseMessages.HR_DIRECTORY + filename);
	 * 
	 * return response; } else { throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST, "No Records Found!"); } }
	 */
	public static boolean isValidMobileNo(String str) {
		// (0/91): number starts with (0/91)
		// [7-9]: starting of the number may contain a digit between 0 to 9
		// [0-9]: then contains digits 0 to 9
		Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		// the matcher() method creates a matcher that will match the given input
		// against this pattern
		Matcher match = ptrn.matcher(str);
		// returns a boolean value
		return (match.find() && match.group().equals(str));
	}

	private Boolean validateSchedule(HrCalendar schedule) {
		logger.info("" + schedule.getCandidateMobile() + " " + schedule.getCandidateName() + " "
				+ schedule.getScheduleDate());
		if (schedule.getCandidateMobile() != null && schedule.getCandidateName() != null
				&& schedule.getScheduleDate() != null) {
			if (schedule.getCandidateMobile().length() != 10 && !isValidMobileNo(schedule.getCandidateMobile())) {
				logger.info("Mobile validation failed");
				return false;
			}
			if (schedule.getCandidateName().length() < 3) {
				logger.info("Name validation failed");
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public ApiResponse doReScheduleInCalendar(String scheduleId, String comment) throws Exception {
		ApiResponse response = new ApiResponse(false);
		
		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to deleteScheduleInCalendar");
			return response;
		}

		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to ReSchedule Calendar.");
			}

			HrCalendarComment cmt = new HrCalendarComment();
			cmt.setScheduleId(scheduleId);
			cmt.setDescription(comment);
			cmt.setCreatedAt(new Date());
			// cmt.setCreatedBy(currentUser.getUserId());
			// cmt.setUpdatedBy(currentUser.getUserId());
			cmt.setLastUpdatedAt(new Date());
			cmtRepository.save(cmt);

			response.setSuccess(true);
			response.setMessage("Comment added successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse editCommentToSchedule(String commentId, String comment) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to updatecallcounter Hrcalendar");
			return response;
		}

		HrCalendarComment cmt = cmtRepository.getById(commentId);
		if (cmt == null) {
			response.setSuccess(false);
			response.setMessage("Comment not exist.");
			return response;
		}
		HrCalendar scheduleObj = hrCalendarRepository.findById(cmt.getScheduleId()).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to edit this comment.");
			}
			scheduleObj.setLastUpdatedAt(new Date());
			hrCalendarRepository.save(scheduleObj);
			cmt.setDescription(comment);
			cmt.setUpdatedBy(currentUser.getUserId());
			cmt.setLastUpdatedAt(new Date());

			response.setSuccess(true);
			response.setMessage("Comment edited successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse deleteCommentToSchedule(String commentId) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to updatecallcounter Hrcalendar");
			return response;
		}

		HrCalendarComment cmt = cmtRepository.getById(commentId);
		if (cmt == null) {
			response.setSuccess(false);
			response.setMessage("Comment not exist.");
			return response;
		}
		HrCalendar scheduleObj = hrCalendarRepository.findById(cmt.getScheduleId()).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to delete this comment.");
			}
			cmtRepository.delete(cmt);
			response.setSuccess(true);
			response.setMessage("Comment deleted successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse getAllScheduleComments(String scheduleId) throws Exception {
		ApiResponse response = new ApiResponse(false);

		if (!empPerConfig.isHavingpersmission("hrCalViewAll")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to updatecallcounter Hrcalendar");
			return response;
		}
		HrCalendar scheduleObj = hrCalendarRepository.findById(scheduleId).get();
		if (scheduleObj != null) {
			if (!scheduleObj.getCreatedBy().equals(currentUser.getUserId())) {
				response.setSuccess(false);
				response.setMessage("Not authorised to delete this comment.");
			}
			List<Map> commentList = cmtRepository.getAllScheduleComments(scheduleId);
			Map content = new HashMap();
			content.put("commentList", commentList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retrieved successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("Input Object is not valid.");
		}

		return response;
	}

	@Override
	public ApiResponse downloadAllMySchedulesFromCalendarByStatus(Map<String, Object> filter) throws Exception {
		ApiResponse response = new ApiResponse();
		
		if (!empPerConfig.isHavingpersmission("harCalScheduleAdd")) {
			response.setSuccess(false);
			response.setMessage("Not authorised to create Hrcalendar");
			return response;
		}
		
		String jobId = filter.containsKey("jobId") ? ((String) filter.get("jobId")) : null;
//		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId"))
//				: null;
		Boolean closed = filter.containsKey("closed") ? ((Boolean) filter.get("closed")) : false;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;
		String userZone = filter.containsKey("userZone") ? filter.get("userZone").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDate != null && toDate != null) {
			try {
				parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

			} catch (ParseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");
			}
		}

		List<Map> myScheduleList = hrCalendarRepository.downloadAllMySchedulesFromCalendarByStatus(
				currentUser.getUserId(), fromDate, jobId, toDate, status, closed, userZone);

		Map<String, Object> fileResponse = new HashMap<>();
		Workbook workbook = prepareExcelWorkBook(myScheduleList, userZone);
//	String input = GMTWithNormalFormat(myScheduleList.get("scheduleDate") != null ? myScheduleList.get("scheduleDate").toString(): "");
//	System.out.println(input);

		byte[] blob = ExcelUtil.toBlob(workbook);

		try {
			ExcelUtil.saveWorkbook(workbook, "MySchedulereports.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileResponse.put("fileName", "MyScheduleDetails-report.xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		// System.out.println(fileResponse);
		response.setSuccess(true);
		response.setMessage("report exported Successfully");

		return response;
	}

//	public static String getScheduleDate(String input) {
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            inputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
//            Date date = inputFormat.parse(input);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            return outputFormat.format(date);
//        } catch (Exception ignore) {
//            //cannot happen in this example
//            //Log.e("Exception", ignore.toString());
//        }
//        return "";
//    }
	private Workbook prepareExcelWorkBook(List<Map> myScheduleList, String userZone) {
		List<String> headers = Arrays.asList("Name", "Job code", "Job Title", "Date & Time", "Source", "Status");

		List data = new ArrayList<>();

		for (Map mySchedule : myScheduleList) {
			Map row = new HashMap<>();
			Date scheduleDate = null;
			Calendar cal = null;
			try {
				cal = Calendar.getInstance();
				cal.setTimeZone(TimeZone.getTimeZone("GMT"));

				scheduleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.parse(mySchedule.get("scheduleDate").toString());

				cal.setTime(scheduleDate);
//		HrCalendarTimeZone.getDate(userZone);
				cal.set(Calendar.SECOND, HrCalendarTimeZone.getDate(userZone));

				// System.out.println(cal.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			row.put("Name", mySchedule.get("candidateName") != null ? mySchedule.get("candidateName").toString() : "");
			row.put("Job code", mySchedule.get("jobCode") != null ? mySchedule.get("jobCode").toString() : "");
			row.put("Job Title", mySchedule.get("jobTitle") != null ? mySchedule.get("jobTitle").toString() : "");
			row.put("Date & Time",
					scheduleDate != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime()) : "");
			row.put("Source",
					mySchedule.get("searchedSource") != null ? mySchedule.get("searchedSource").toString() : "");
			row.put("Status", mySchedule.get("status") != null ? mySchedule.get("status").toString() : "");

			data.add(row);

		}
		Workbook workbook = ExcelUtil
				.createSingleSheetWorkbook(ExcelUtil.createSheet("My Schedule report", headers, data));

		return workbook;

	}

	@Override
	public ApiResponse downloadMyTeamSchedulesFromCalendarByStatus(Map<String, Object> filter) throws Exception {
		ApiResponse response = new ApiResponse();
		String jobId = filter.containsKey("jobId") ? ((String) filter.get("jobId")) : null;
		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId")) : null;
		Boolean closed = filter.containsKey("closed") ? ((Boolean) filter.get("closed")) : false;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;
		String userZone = filter.containsKey("userZone") ? filter.get("userZone").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDate != null && toDate != null) {
			try {
				parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

			} catch (ParseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");
			}
		}
		List<Map> myTeamScheduleList = null;

		if (currentUser.getUserId().equals("USR_JAbUrXk611")) {
			myTeamScheduleList = hrCalendarRepository.downloadAllMyTeamSchedulesFromCalendarByStatusForAdmin(employeeId,
					fromDate, toDate, jobId, status, closed, userZone);
		} else if (empPerConfig.isHavingpersmission("hrCalViewAll")) {
			myTeamScheduleList = hrCalendarRepository.downloadAllMyTeamSchedulesFromCalendarByStatusForAdmin(employeeId,
					fromDate, toDate, jobId, status, closed, userZone);
		} else {
			myTeamScheduleList = hrCalendarRepository.downloadAllMyTeamSchedulesFromCalendarByStatus(
					currentUser.getUserId(), employeeId, fromDate, toDate, jobId, status, closed, userZone);
		}

		Map<String, Object> fileResponse = new HashMap<>();

		Workbook workbook = prepareExcelWorkBookTeam(myTeamScheduleList, userZone);

		byte[] blob = ExcelUtil.toBlob(workbook);

		try {
			ExcelUtil.saveWorkbook(workbook, "MyTeamSchedulereports.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileResponse.put("fileName", "MyTeamSchedule-report.xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		// System.out.println(fileResponse);
		response.setSuccess(true);
		response.setMessage("report exported Successfully");

		return response;
	}
//	public static String GMTWithNormalTimeFormat(String input) {
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            Date date = inputFormat.parse(input);
//            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            return outputFormat.format(date);
//        } catch (Exception ignore) {
//         //   Log.e("Exception", ignore.toString());
//        }
//        return "";
//    }
//	public static String getTime(String input) {
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//          //  inputFormat.setTimeZone(TimeZone.getTimeZone("));
//            Date date = inputFormat.parse(input);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            return outputFormat.format(date);
//        } catch (Exception ignore) {
//            //cannot happen in this example
//            //Log.e("Exception", ignore.toString());
//        }
//        return "";
//    }

	private Workbook prepareExcelWorkBookTeam(List<Map> myTeamScheduleList, String userZone) {
		List<String> headers = Arrays.asList("Name", "Job code", "Job Title", "Date & Time", "Scheduled By", "Source",
				"Status");

		List data = new ArrayList<>();

		for (Map myTeamSchedule : myTeamScheduleList) {
			Map row = new HashMap<>();
			Date scheduleDate = null;
			Calendar cal = null;
			try {
				cal = Calendar.getInstance();
				cal.setTimeZone(TimeZone.getTimeZone("GMT"));

				scheduleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.parse(myTeamSchedule.get("scheduleDate").toString());

				cal.setTime(scheduleDate);
				cal.set(Calendar.SECOND, HrCalendarTimeZone.getDate(userZone));

//				System.out.println(cal.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			row.put("job Id",myTeamSchedule.get("jobId") != null ? myTeamSchedule.get("jobId").toString(): "");
			row.put("Name",
					myTeamSchedule.get("candidateName") != null ? myTeamSchedule.get("candidateName").toString() : "");
			row.put("Job code", myTeamSchedule.get("jobCode") != null ? myTeamSchedule.get("jobCode").toString() : "");
			row.put("Job Title",
					myTeamSchedule.get("jobTitle") != null ? myTeamSchedule.get("jobTitle").toString() : "");
			row.put("Date & Time",
					scheduleDate != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime()) : "");
			row.put("Scheduled By",
					myTeamSchedule.get("scheduledBy") != null ? myTeamSchedule.get("scheduledBy").toString() : "");
			row.put("Source",
					myTeamSchedule.get("searchedSource") != null ? myTeamSchedule.get("searchedSource").toString()
							: "");
			row.put("Status", myTeamSchedule.get("status") != null ? myTeamSchedule.get("status").toString() : "");

			data.add(row);

		}
		Workbook workbook = ExcelUtil
				.createSingleSheetWorkbook(ExcelUtil.createSheet("My Team Schedule report", headers, data));

		return workbook;

	}

	@Override
	public ApiResponse getAllhrCalender(Map<String, Object> filter, Pageable pageable) {
		ApiResponse response = new ApiResponse(false);

		String jobId = filter.containsKey("jobId") ? ((String) filter.get("jobId")) : null;
		String employeeId = filter.containsKey("employeeId") ? ((String) filter.get("employeeId")) : null;
		Boolean closed = filter.containsKey("closed") ? ((Boolean) filter.get("closed")) : false;
		String status = filter.containsKey("status") ? ((String) filter.get("status")).toLowerCase() : null;
		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDate != null && toDate != null) {
			try {

				parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
				parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

			} catch (ParseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");
			}
		}

		Page<Map> shceduleList = hrCalendarRepository.getAllMyTeamSchedulesFromCalendarByStatusForAdmin(employeeId,
				jobId, fromDate, toDate, status, closed, pageable);

		if (shceduleList.getSize() > 0) {
			Map content = new HashMap();
			content.put("shceduleList", shceduleList);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("List retrieved successfully.");
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty.");
		}
		return response;
	}

	@Override
	public ApiResponse searchhrCalender(String searchString) {
		ApiResponse response = new ApiResponse();
		List<Map> hrCalender = hrCalendarRepository.searchhrCalender(currentUser.getUserId(), searchString);

		Map content = new HashMap();
		content.put("hrCalender", hrCalender);
		if (content != null) {

			response.setSuccess(true);
			response.setMessage("hr calender details retrived successfully");
			response.setContent(content);
		} else {

			response.setSuccess(false);
			// response.setContent(content);
			response.setMessage("Not retrived the data");
		}

		return response;

	}
}
