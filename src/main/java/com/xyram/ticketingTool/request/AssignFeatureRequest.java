package com.xyram.ticketingTool.request;

import java.util.List;

public class AssignFeatureRequest {

	
	private List<String> StoryStatusIds;
	
	private String projectId;
	

	public List<String> getStoryStatusIds() {
		return StoryStatusIds;
	}

	public void setStoryStatusIds(List<String> storyStatusIds) {
		StoryStatusIds = storyStatusIds;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	
	
	
	

	
}
