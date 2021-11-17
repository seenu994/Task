package com.xyram.ticketingTool.service;

import java.util.List;

import com.xyram.ticketingTool.entity.StoryLabel;

public interface StoryLabelService {

	
	StoryLabel updateStoryLabel(String id, StoryLabel storylabel);
	
	List<StoryLabel> getStoryLabel();

	StoryLabel createStoryLabel(String id,StoryLabel storylabel);
	
}
