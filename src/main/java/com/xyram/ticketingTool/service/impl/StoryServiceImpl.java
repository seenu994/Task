package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.text.html.HTML;
import javax.transaction.Transactional;

import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.poi.hssf.record.OldCellRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.stringtemplate.v4.compiler.CodeGenerator.region_return;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.microsoft.schemas.office.excel.STObjectType;
import com.xyram.ticketingTool.Communication.PushNotificationCall;
import com.xyram.ticketingTool.Communication.PushNotificationRequest;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.StoryRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.email.EmailService;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Notifications;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.enumType.NotificationType;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;
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

@Service
@Transactional
public class StoryServiceImpl implements StoryService {

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
	public Story createStory(Story story) {
		Projects projects = projectService.getprojectById(story.getProjectId());

		if (projects != null) {

			checkProjectMemberInProject(story.getProjectId(), story.getAssignTo());
			CheckplatformExist(story.getPlatform());
			checkLabel(story.getStoryLabel());
			Map projectFeature = projectFeatureService.getFeatureByProjectAndFeatureId(story.getProjectId(),
					story.getStoryStatus());

			
			story.setOwner(currentUser.getScopeId());
			Integer storyNo = storyRepository.getTotalTicketByprojectId(story.getProjectId()) + 1;
			story.setStoryNo(storyNo.toString());
			story.setCreatedOn(new Date());

		if( storyRepository.save(story) != null) {


	Employee empObj = new Employee();
	
	List<Map> EmployeeByRole = storyRepository.getVendorNotification(story.getAssignTo());

	for (Map employeeNotification : EmployeeByRole) {
		Map request = new HashMap<>();
		request.put("id", employeeNotification.get("eId"));
		request.put("uid", employeeNotification.get("uid"));
		request.put("title", "JOB_VENDOR_EDITED");
		request.put("body", "JOB_VENDOR_EDITED - " );
		pushNotificationCall.restCallToNotification(pushNotificationRequest.PushNotification(request, 12,
				NotificationType.JOB_VENDOR_CREATED.toString()));

	 // inserting notification details	
	Notifications notifications = new Notifications();
	notifications.setNotificationDesc("JOB_VENDOR_CREATED - " + employeeNotification.get("firstName"));
	notifications.setNotificationType(NotificationType.JOB_VENDOR_EDITED);
	notifications.setSenderId(empObj.getReportingTo());
	notifications.setReceiverId(currentUser.getUserId());
	notifications.setSeenStatus(false);
	notifications.setCreatedBy(currentUser.getUserId());
	notifications.setCreatedAt(new Date());
	notifications.setUpdatedBy(currentUser.getUserId());
	notifications.setLastUpdatedAt(new Date());

	notificationService.createNotification(notifications);
	UUID uuid = UUID.randomUUID();
	String uuidAsString = uuid.toString();
	if (employeeNotification != null) {
		String name = null;

		HashMap mailDetails = new HashMap();
		mailDetails.put("toEmail", employeeNotification.get("email"));
		mailDetails.put("subject", name + ", " + "Here's your new PASSWORD");
		mailDetails.put("message", "Hi " + name
				+ ", \n\n We received a request to reset the password for your Account. \n\n Here's your new PASSWORD Link is: "
				+ application_url + "/update-password" + "?key=" + uuidAsString
				+ "\n\n Thanks for helpRing us keep your account secure.\n\n Xyram Software Solutions Pvt Ltd.");
		emailService.sendMail(mailDetails);
	}
	}}

else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " project not found " + story.getId());
}
		}
		return story;}
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

				return storyRepository.save(story);
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					" story not found " + storyChangeStatusrequest.getStoryId());

		}
		return story;

	}

	public boolean checkProjectMemberInProject(String projectId, String employeeId) {
		ProjectMembers projectMembers = projectMemberService.getProjectMembersInProject(employeeId, projectId);
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

}
