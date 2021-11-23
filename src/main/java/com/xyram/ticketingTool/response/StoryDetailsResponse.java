package com.xyram.ticketingTool.response;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.stringtemplate.v4.compiler.CodeGenerator.list_return;

import com.xyram.ticketingTool.entity.StoryAttachments;
import com.xyram.ticketingTool.entity.StoryComments;

public class StoryDetailsResponse {

	private List<Map>  storyDetails;
	private List<Map> storyComments;
	private List<Map> storyAttachments;
	public List<Map> getStoryDetails() {
		return storyDetails;
	}
	public void setStoryDetails(List<Map> storyDetails) {
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
