package com.xyram.ticketingTool.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.StoryRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.email.EmailService;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;
import com.xyram.ticketingTool.response.ReportExportResponse;
import com.xyram.ticketingTool.response.StoryDetailsResponse;
import com.xyram.ticketingTool.service.NotificationService;
import com.xyram.ticketingTool.service.PlatformService;
import com.xyram.ticketingTool.service.ProjectFeatureService;
import com.xyram.ticketingTool.service.ProjectMemberService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.service.StoryAttachmentsService;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.service.StoryLabelService;
import com.xyram.ticketingTool.service.StoryService;
import com.xyram.ticketingTool.util.ExcelUtil;

@Service
@Transactional
public class StoryServiceImpl implements StoryService {

	private static final Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Autowired
	StoryRepository storyRepository;

	@Autowired
	ProjectService projectService;

	@Autowired
	StoryAttachmentsService storyAttachmentsService;

	@Autowired
	StoryCommentService storyCommentService;

	@Autowired
	ProjectFeatureService projectFeatureService;

	@Autowired
	StoryLabelService storyLabelService;

	@Autowired
	PlatformService platformService;

	@Autowired
	ProjectMemberService projectMemberService;

	@Autowired
	CurrentUser currentUser;
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PushNotificationCall pushNotificationCall;
	@Autowired
	PushNotificationRequest pushNotificationRequest;

	@Autowired
	NotificationService notificationService;

	@Autowired
	EmailService emailService;

	@Value("${APPLICATION_URL}")
	private String application_url;

	@Override
	public Story createStory(Story story) throws Exception{
		Projects projects = projectService.getprojectById(story.getProjectId());

		if (projects != null) {

			checkProjectMemberInProject(story.getProjectId(), story.getAssignTo());
			CheckplatformExist(story.getPlatform());
			checkLabel(story.getStoryLabel());
			if(story.getStoryStatus()!=null)
			{
			Map projectFeature = projectFeatureService.getFeatureByProjectAndFeatureId(story.getProjectId(),
					story.getStoryStatus());
			
			}
			else {
		     story.setStoryStatus("f1");
			}

			story.setOwner(currentUser.getScopeId());
			Integer storyNo = storyRepository.getTotalTicketByprojectId(story.getProjectId()) + 1;
			story.setStoryNo(storyNo.toString());
			story.setCreatedOn(new Date());
			story.setUpdatedOn(new Date());
			story.setLastUpdatedBy(currentUser.getScopeId());
			return storyRepository.save(story);
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invaild project Reference");
		}
		
	}

	@Override
	public Story editStoryDetails(String storyId, Story storyRequest) {
		return storyRepository.findById(storyId).map(story -> {
			if (storyRequest.getStoryDescription() != null) {
				story.setStoryDescription(storyRequest.getStoryDescription());

			}
			if (storyRequest.getStoryLabel() != null) {
				checkLabel(story.getStoryLabel());
				story.setStoryLabel(storyRequest.getStoryLabel());
			}
			if (storyRequest.getStoryType() != null) {
				story.setStoryType(storyRequest.getStoryType());
			}
			if (storyRequest.getAssignTo() != null) {
				checkProjectMemberInProject(story.getProjectId(), story.getAssignTo());
				story.setAssignTo(storyRequest.getAssignTo());
			}
			if (storyRequest.getPlatform() != null) {
				CheckplatformExist(story.getPlatform());
				story.setPlatform(storyRequest.getPlatform());
			}
			story.setUpdatedOn(new Date());
			story.setLastUpdatedBy(currentUser.getScopeId());
			return storyRepository.save(story);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "story not found with " + storyId));

	}

	@Override
	public IssueTrackerResponse getAllStories(String projectId, Map<String, Object> filter) {

		IssueTrackerResponse response = new IssueTrackerResponse();
		String issue = null;
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		String assignTo = filter.containsKey("assignTo") ? ((String) filter.get("assignTo")).toLowerCase() : null;
		String platform = filter.containsKey("platform") ? ((String) filter.get("platform")).toLowerCase() : null;
		String storyStatus = filter.containsKey("storyStatus") ? ((String) filter.get("storyStatus")).toLowerCase()
				: null;
		String storyType = filter.containsKey("storyType") ? ((String) filter.get("storyType")).toLowerCase() : null;

		List<Map> storyList = storyRepository.getAllStories(projectId, searchString);

		response.setContent(storyList);

		response.setStatus("success");

		return response;
	}

	@Override
	public Story changeStoryStatus(StoryChangeStatusRequest storyChangeStatusrequest) {
		Story story = storyRepository.getStoryById(storyChangeStatusrequest.getStoryId());
		if (story != null) {

			if (checkFeature(storyChangeStatusrequest.getStorystatus(), story.getProjectId()) != null) {
				story.setStoryStatus(storyChangeStatusrequest.getStorystatus());
				story.setUpdatedOn(new Date());
				story.setLastUpdatedBy(currentUser.getScopeId());

				return storyRepository.save(story);
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					" story not found " + storyChangeStatusrequest.getStoryId());

		}
		return story;

	}

	public boolean checkProjectMemberInProject(String projectId, String employeeId) {
		ProjectMembers projectMembers = projectMemberService.getProjectMembersInProject(employeeId, projectId,null);
		if (projectMembers != null) {
			if (projectMembers.getStatus().equals("INACTIVE")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "  project member is inactive state");
			} else {
				return true;
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you can't assign non project member");
		}
	}

	public boolean CheckplatformExist(String platformId) {
		if (platformId != null) {
			if (platformService.getPlatformById(platformId) != null) {
				return true;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "platform not found with id");
			}

		} else {

			return true;
		}
	}

	@Override
	public IssueTrackerResponse storySearch(String projectId, Map<String, Object> filter) {
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		String assignTo = filter.containsKey("assignTo") ? ((String) filter.get("assignTo")).toLowerCase() : null;
		String platform = filter.containsKey("platform") ? ((String) filter.get("platform")).toLowerCase() : null;
		String storyStatus = filter.containsKey("storyStatus") ? ((String) filter.get("storyStatus")).toLowerCase()
				: null;
		String storyType = filter.containsKey("storyType") ? ((String) filter.get("storyType")).toLowerCase() : null;
		String storyLabel = filter.containsKey("storyLabel") ? ((String) filter.get("storyLabel")).toLowerCase() : null;

		IssueTrackerResponse response = new IssueTrackerResponse();

		List<Map> stories = storyRepository.getStoryTesting(projectId, searchString, assignTo, platform, storyStatus,
				storyType, storyLabel);
		response.setContent(stories);

		response.setStatus("success");
		;
		return response;
	}

	@Override
	public IssueTrackerResponse getStoryDetailsForReport(String projectId, Map request) {
		
		Map filter =(Map)request;
		
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		String assignTo = filter.containsKey("assignTo") ? ((String) filter.get("assignTo")).toLowerCase() : null;
		String platform = filter.containsKey("platform") ? ((String) filter.get("platform")).toLowerCase() : null;
		String storyStatus = filter.containsKey("storyStatus") ? ((String) filter.get("storyStatus")).toLowerCase()
				: null;
		String storyType = filter.containsKey("storyType") ? ((String) filter.get("storyType")).toLowerCase() : null;
		String storyLabel = filter.containsKey("storyLabel") ? ((String) filter.get("storyLabel")).toLowerCase() : null;

		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
			parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

		} catch (ParseException e) {
			logger.error("Invalid date format date should be yyyy-MM-dd");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format date should be yyyy-MM-dd");
		}

		IssueTrackerResponse response = new IssueTrackerResponse();

		List<Map> stories = storyRepository.getStoryFilterForReport(projectId, searchString, assignTo, platform,
				storyStatus, storyType, storyLabel, fromDate, toDate);

		response.setContent(stories);

		response.setStatus("success");
		;
		return response;
	}

	@Override
	public ReportExportResponse getStoryDetailsForReportDownload(String projectId, Map request) {
Map filter =(Map)request;
		
		String searchString = filter.containsKey("searchString") ? ((String) filter.get("searchString")).toLowerCase()
				: null;
		String assignTo = filter.containsKey("assignTo") ? ((String) filter.get("assignTo")).toLowerCase() : null;
		String platform = filter.containsKey("platform") ? ((String) filter.get("platform")).toLowerCase() : null;
		String storyStatus = filter.containsKey("storyStatus") ? ((String) filter.get("storyStatus")).toLowerCase()
				: null;
		String storyType = filter.containsKey("storyType") ? ((String) filter.get("storyType")).toLowerCase() : null;
		String storyLabel = filter.containsKey("storyLabel") ? ((String) filter.get("storyLabel")).toLowerCase() : null;

		String fromDate = filter.containsKey("fromDate") ? filter.get("fromDate").toString() : null;
		String toDate = filter.containsKey("toDate") ? filter.get("toDate").toString() : null;

		Date parsedfromDate = null;
		Date parsedtoDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			parsedfromDate = fromDate != null ? dateFormat.parse(fromDate) : null;
			parsedtoDate = toDate != null ? dateFormat.parse(toDate) : null;

		} catch (ParseException e) {
			logger.error("Invalid date format date should be yyyy-MM-dd");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format date should be yyyy-MM-dd");
		}

		ReportExportResponse response = new ReportExportResponse();
		Map<String, Object> fileResponse = new HashMap<>();

		List<Map> stories = storyRepository.getStoryFilterForReport(projectId, searchString, assignTo, platform,
				storyStatus, storyType, storyLabel, fromDate, toDate);

	
		Workbook workbook = prepareExcelWorkBook(stories);
		
		byte[] blob = ExcelUtil.toBlob(workbook);

		fileResponse.put("fileName", "issue-report.xlsx");
		fileResponse.put("type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileResponse.put("blob", blob);
		response.setFileDetails(fileResponse);
		response.setStatus("success");
		response.setMessage("report exported Successfully");

		return response;
	}

	public boolean checkLabel(String labelId) {
		if (labelId != null) {

			if (storyLabelService.getStoryLabelById(labelId) != null) {
				return true;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Story Label not found with id" + labelId);
			}

		} else {

			return true;
		}
	}

	public Map checkFeature(String featureId, String projectId) {

		if (featureId != null) {
			Map projectFeature = projectFeatureService.getFeatureByProjectAndFeatureId(projectId, featureId);
			if (projectFeature.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature Not Found  with " + featureId);
			} else {

				return projectFeature;
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you can't assign ");
		}

	}

	public Story getStoryId(String id) {
		return storyRepository.findById(id).map(story -> {

			return story;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "story not found with " + id));
	}

	@Override
	public StoryDetailsResponse getStoryDetailsById(String projectId, String storyId) {
		StoryDetailsResponse storyDetailsResponse = new StoryDetailsResponse();
		Map storyDetails = storyRepository.getAllStoriesByStoryId(projectId, storyId);
		List<Map> storyAttachments = storyAttachmentsService.getStoryAttachmentsListByStoryId(storyId);
		List<Map> storyComments = storyCommentService.getStoryCommentsListByStoryId(storyId);
		storyDetailsResponse.setStoryAttachments(storyAttachments);
		storyDetailsResponse.setStoryDetails(storyDetails);
		storyDetailsResponse.setStoryComments(storyComments);
		return storyDetailsResponse;
	}

	@Override
	public IssueTrackerResponse getAllStoriesBystatus(String projectId, String storyStatusId) {
		IssueTrackerResponse response = new IssueTrackerResponse();

		List<Map> storyList = storyRepository.getAllStoriesByStoryStaus(projectId, storyStatusId);

		response.setContent(storyList);

		response.setStatus("success");

		return response;
	}

	private Workbook prepareExcelWorkBook(List<Map> storyDetails) {

		List<String> headers = Arrays.asList("Story NO","Created on ", "Title", "Description",  "Issue Type",
				"Issue Status", "Reporter", "Assignee", "Module", "Version", "Sprint");
		List data = new ArrayList<>();

		for (Map story : storyDetails) {

			Map row = new HashMap<>();

			row.put("Story NO",story.get("storyNo") != null ? story.get("storyNo").toString(): "");
			row.put("Title", story.get("title") != null ? story.get("title").toString() : "");

			row.put("Description",
					story.get("storyDescription") != null ? story.get("storyDescription").toString() : "");
			row.put("Created on ", story.get("createdOn") != null ? story.get("createdOn").toString() : "");
			row.put("Issue Type", story.get("storyType") != null ? story.get("storyType").toString() : "");
			row.put("Issue Status", story.get("storyStatus") != null ? story.get("storyStatus").toString() : "");
			row.put("Reporter", story.get("owner") != null ? story.get("owner").toString() : "");
			row.put("Assignee", story.get("assignedTo") != null ? story.get("title").toString() : "");

			row.put("Module", story.get("storyLabel") != null ? story.get("storyLabel").toString() : "");
			row.put("Version", story.get("versionName") != null ? story.get("versionName").toString() : "");

			row.put("Sprint", story.get("sprintName") != null ? story.get("sprintName").toString() : "");

			data.add(row);
		}

		Workbook workbook = ExcelUtil.createSingleSheetWorkbook(ExcelUtil.createSheet("Claim Report", headers, data));

//		To create workbook with multiple sheets
//		List sheets = new ArrayList<>();
//		
//		sheets.add(ExcelUtil.createSheet("Claim Report", headers, data));
//		sheets.add(ExcelUtil.createSheet(headers, data));
//		sheets.add(ExcelUtil.createSheet("no data", headers, null));
//		sheets.add(ExcelUtil.createSheet(headers, data, 2, 1));
//		sheets.add(ExcelUtil.createSheet("Row Init Col Init", headers, data, 2, 1));
//		
//		Workbook workbook = ExcelUtil.createWorkbook(sheets);

		return workbook;
	}

}
