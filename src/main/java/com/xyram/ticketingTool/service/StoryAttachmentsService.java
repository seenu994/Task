package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xyram.ticketingTool.entity.StoryAttachments;
import com.xyram.ticketingTool.vo.StoryAttachmentVo;

public interface StoryAttachmentsService {


	List<Map> getStoryAttachmentsListByStoryId(String storyId);

	StoryAttachments getStoryAttachmentsById(String storyAttachmentId);

	Map<String, Object> deleteStoryAttachmentById(String storyAttachmentId);

	List<Map> uploadStoryAttachemet(StoryAttachmentVo storyAttachmentVo);

}
