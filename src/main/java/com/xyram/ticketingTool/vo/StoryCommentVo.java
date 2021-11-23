package com.xyram.ticketingTool.vo;

import javax.mail.Multipart;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class StoryCommentVo {
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStoryId() {
		return storyId;
	}


	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public Multipart getAttachments() {
		return Attachments;
	}


	public void setAttachments(Multipart attachments) {
		Attachments = attachments;
	}


	@NotNull(message = "description is mandatory")
	@NotEmpty(message = "description should not be  empty")
	private String description;
	
	@NotNull(message = "storyId is mandatory")
	@NotEmpty(message = "storyId should not be  empty")
	private String  storyId;
	
	@NotNull(message = "projectId is mandatory")
	@NotEmpty(message = "projectId should not be  empty")
	private String projectId;
	
	
	private Multipart Attachments; 

}
