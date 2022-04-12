package com.xyram.ticketingTool.response;

import java.util.List;
import java.util.Map;

public class StoryDetailsResponse {

	private Map  storyDetails;
	private List<Map> storyComments;
	private List<Map> storyAttachments;
	
	
	public Map getStoryDetails() {
		return storyDetails;
	}
	public void setStoryDetails(Map storyDetails) {
		this.storyDetails = storyDetails;
	}
	public List<Map> getStoryComments() {
		return storyComments;
	}
	public void setStoryComments(List<Map> storyComments) {
		this.storyComments = storyComments;
	}
	public List<Map> getStoryAttachments() {
		return storyAttachments;
	}
	public void setStoryAttachments(List<Map> storyAttachments) {
		this.storyAttachments = storyAttachments;
	}
	
	
	
	
}
