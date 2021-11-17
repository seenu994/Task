package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.Story;

public interface StoryService {

	Story createStory(Story story);

	Story changeStoryStatus(String storystatus, String storyId);

	List<Map> getAllStories(String projectId);

}
