package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;
import com.xyram.ticketingTool.response.StoryDetailsResponse;

public interface StoryService {

	Story createStory(Story story);


	IssueTrackerResponse getAllStories(String projectId );
	IssueTrackerResponse getAllStoriesBystatus(String projectId ,String storyStatusId );

	Story changeStoryStatus(StoryChangeStatusRequest storyChangeStatusrequest);


	StoryDetailsResponse getStoryDetailsById(String projectId, String storyId);

}
