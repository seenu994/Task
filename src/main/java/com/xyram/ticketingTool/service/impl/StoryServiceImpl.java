package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTML;
import javax.transaction.Transactional;

import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.poi.hssf.record.OldCellRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.stringtemplate.v4.compiler.CodeGenerator.region_return;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.xyram.ticketingTool.Repository.StoryRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.ProjectMembers;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;
import com.xyram.ticketingTool.response.StoryDetailsResponse;
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
			return storyRepository.save(story);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " project not found " + story.getId());

		}

	}

//	public Story editStoryDetails(String storyId)
//	{
//		//return storyRepository.findAllById(storyId).map()
//	}

	@Override
	public IssueTrackerResponse getAllStories(String projectId) {

		IssueTrackerResponse response = new IssueTrackerResponse();
		List<Map> storyList = storyRepository.getAllStories(projectId);

		response.setContent(storyList);

		response.setStatus("success");

		return response;
	}

	@Override
	public Story changeStoryStatus(StoryChangeStatusRequest storyChangeStatusrequest) {
		Story story = storyRepository.getStoryById(storyChangeStatusrequest.getStoryId());
		if (story != null) {

			if (checkFeature(storyChangeStatusrequest.getStorystatus(), story.getProjectId()) != null) {

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
