package com.xyram.ticketingTool.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class StoryAttachmentVo {

	
	@NotNull(message="Attachment is mandatory ")
	private MultipartFile[] storyAttachment;
	
	@NotNull(message = "storyId is mandatory")
	@NotEmpty(message = "storyId should not be  empty")
	private String storyId;

	public MultipartFile[] getStoryAttachment() {
		return storyAttachment;
	}

	public void setStoryAttachment(MultipartFile[] storyAttachment) {
		this.storyAttachment = storyAttachment;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	
	
	
}


