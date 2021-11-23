package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.request.StoryChangeStatusRequest;

public interface StoryService {

	Story createStory(Story story);


	IssueTrackerResponse getAllStories(String projectId);

	Story changeStoryStatus(StoryChangeStatusRequest storyChangeStatusrequest);

}
