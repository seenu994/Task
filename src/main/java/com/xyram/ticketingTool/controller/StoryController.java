package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Sprint;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.modelMapper.StoryMapper;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;
import com.xyram.ticketingTool.response.StoryDetailsResponse;
import com.xyram.ticketingTool.service.SprintService;
import com.xyram.ticketingTool.service.StoryService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.StoryVo;

@RestController
public class StoryController {

	@Autowired
	private StoryService storyService;

	@Autowired
	private StoryMapper storyMapper;

	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/CreateStory",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/CreateStory", AuthConstants.INFRA_USER_BASEPATH + "/CreateStory",
			AuthConstants.ACCOUNTANT_BASEPATH + "/CreateStory", AuthConstants.DEVELOPER_BASEPATH + "/CreateStory",
			AuthConstants.HR_ADMIN_BASEPATH + "/CreateStory", AuthConstants.JOB_VENDOR_BASEPATH + "/CreateStory" })
	public Story createStory(@Valid @RequestBody StoryVo storyVo) {

		return storyService.createStory(storyMapper.getEntityFromVo(storyVo));
	}

	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.ADMIN_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.INFRA_USER_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.ACCOUNTANT_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllStories/{projectId}",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getAllStories/{projectId}"})
	public IssueTrackerResponse createStory(@PathVariable String projectId, @RequestParam Map<String, Object> filter) {

		return storyService.getAllStories(projectId, filter);
	}

	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/getStoryDetails",
			AuthConstants.ADMIN_BASEPATH + "/getStoryDetails", AuthConstants.INFRA_ADMIN_BASEPATH + "/getStoryDetails",
			AuthConstants.INFRA_USER_BASEPATH + "/getStoryDetails",
			AuthConstants.ACCOUNTANT_BASEPATH + "/getStoryDetails",
			AuthConstants.DEVELOPER_BASEPATH + "/getStoryDetails", AuthConstants.HR_ADMIN_BASEPATH + "/getStoryDetails",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getStoryDetails" })
	public StoryDetailsResponse getStoryDetails(@RequestParam String projectId, @RequestParam String storyId) {

		return storyService.getStoryDetailsById(projectId, storyId);

	}

	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.ADMIN_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.INFRA_USER_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.ACCOUNTANT_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.DEVELOPER_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/storySearch/{projectId}",
			AuthConstants.JOB_VENDOR_BASEPATH + "/storySearch/{projectId}"})
	public IssueTrackerResponse getStoryDetails(@PathVariable String projectId,
			@RequestParam Map<String, Object> filter) {

		return storyService.storySearch(projectId, filter);

	}

	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.ADMIN_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.INFRA_USER_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.ACCOUNTANT_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.DEVELOPER_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/updateStory/{storyId}",
			AuthConstants.JOB_VENDOR_BASEPATH + "/updateStory/{storyId}"})
	public Story updateStory(@PathVariable String storyId, @RequestBody Story story) {

		return storyService.editStoryDetails(storyId, story);
	}

	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/getAllStories",
			AuthConstants.ADMIN_BASEPATH + "/getAllStories", AuthConstants.INFRA_USER_BASEPATH + "/getAllStories",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllStories", AuthConstants.ACCOUNTANT_BASEPATH + "/getAllStories",
			AuthConstants.DEVELOPER_BASEPATH + "/getAllStories", AuthConstants.HR_ADMIN_BASEPATH + "/getAllStories",
			AuthConstants.JOB_VENDOR_BASEPATH + "/getAllStories" })
	public IssueTrackerResponse getStoryDetailsByStoryStatus(@RequestParam String projectId,
			@RequestParam String storyStatusId) {

		return storyService.getAllStoriesBystatus(projectId, storyStatusId);

	}

	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/changeStoryStatus",
			AuthConstants.ADMIN_BASEPATH + "/changeStoryStatus",
			AuthConstants.INFRA_USER_BASEPATH + "/changeStoryStatus",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/changeStoryStatus",
			AuthConstants.ACCOUNTANT_BASEPATH + "/changeStoryStatus",
			AuthConstants.DEVELOPER_BASEPATH + "/changeStoryStatus",
			AuthConstants.HR_ADMIN_BASEPATH + "/changeStoryStatus",
			AuthConstants.JOB_VENDOR_BASEPATH + "/changeStoryStatus"})
	public Story changeStoryStatus(StoryChangeStatusRequest storyChangeStatusrequest) {

		return storyService.changeStoryStatus(storyChangeStatusrequest);
	}
}
