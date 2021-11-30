package com.xyram.ticketingTool.service;

import java.util.List;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.StoryLabel;

public interface StoryLabelService {

	
	StoryLabel updateStoryLabel(String id, StoryLabel storylabel);
	
	List<StoryLabel> getStoryLabel();

	StoryLabel createStoryLabel(StoryLabel storylabel);

	IssueTrackerResponse getStoryLabelByProjectId(String projectId);
	
}
