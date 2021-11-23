package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.entity.StoryAttachments;

public interface StoryAttachmentsService {

	List<Map> uploadStoryAttachemet(MultipartFile[] file, String storyId);

	List<Map> getStoryAttachmentsListByStoryId(String storyId);

	StoryAttachments getStoryAttachmentsById(String storyAttachmentId);

	Map<String, Object> deleteStoryAttachmentById(String storyAttachmentId);

}
