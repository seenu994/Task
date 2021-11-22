package com.xyram.ticketingTool.vo;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StoryVo {

	@NotNull(message = "title  is mandatory")
	@NotEmpty(message = "title should not be  emplty")
	@Size(min = 5, max = 100)
	private String title;

//	@NotNull(message = "story Description  is mandatory")
//	@NotEmpty(message = "story Description should  not  be  empty")
	@Size(min = 5, max = 1000)
	private String storyDescription;

	
	
	private String platform;

	@NotNull(message = "project Id should is mandatory")
	@NotEmpty(message = "project Id should be not emplty")
	@Size(min = 3)
	private String projectId;

	@NotNull(message = "storyType should is mandatory")
	@NotEmpty(message = "storyType should be not emplty")
	@Size(min =2)
	private String storyType;
	
	

	private String storyLabel;

	@NotNull(message = "story Status should is mandatory")
	@NotEmpty(message = "story Status  should be not empty")
	@Size(min = 2)
	private String storyStatus;

	@NotNull(message = "assignTo should is mandatory")
	@NotEmpty(message = "assignTo should be not null")
	@Size(min = 5, max = 50)
	private String assignTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStoryDescription() {
		return storyDescription;
	}

	public void setStoryDescription(String storyDescription) {
		this.storyDescription = storyDescription;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStoryType() {
		return storyType;
	}

	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}

	public String getStoryLabel() {
		return storyLabel;
	}

	public void setStoryLabel(String storyLabel) {
		this.storyLabel = storyLabel;
	}

	public String getStoryStatus() {
		return storyStatus;
	}

	public void setStoryStatus(String storyStatus) {
		this.storyStatus = storyStatus;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	
	
}
