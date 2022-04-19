package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.service.StoryAttachmentsService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.StoryAttachmentVo;

@RestController
public class StoryAttachmentController {

	@Autowired
	StoryAttachmentsService storyAttachmentsService;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/uplaodStoryAttachment",
			AuthConstants.HR_BASEPATH + "/uplaodStoryAttachment",
			AuthConstants.HR_ADMIN_BASEPATH + "/uplaodStoryAttachment",
			AuthConstants.INFRA_USER_BASEPATH + "/uplaodStoryAttachment",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/uplaodStoryAttachment",
			AuthConstants.DEVELOPER_BASEPATH + "/uplaodStoryAttachment" })
	public IssueTrackerResponse CreateStoryAttachment(@ModelAttribute StoryAttachmentVo storyAttachmentVo) {

		return storyAttachmentsService.uploadStoryAttachemet(storyAttachmentVo);
	}
//	
//	@GetMapping(value = {AuthConstants.DEVELOPER_BASEPATH + "/getListByProjectId/{projectId}"})
//	public List<Map> getStoryCommentbyprojectId(@PathVariable String projectId) {
//		return storyCommentservice.getStoryCommentsById(projectId);
//	}
//	
//	@GetMapping(value = {AuthConstants.DEVELOPER_BASEPATH + "/getListByProjectIdAndStoryId/{projectId}/{storyId}"})
//	public List<Map> getStoryCommentbyprojectIdandStoryId(@PathVariable String projectId,String storyId) {
//		return storyCommentservice.getStoryCommentbyprojectIdandStoryId(projectId,storyId);
//

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}",
			AuthConstants.HR_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}",
			AuthConstants.INFRA_USER_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}",
			AuthConstants.DEVELOPER_BASEPATH + "/deleteStoryAttachment{storyAttachmentId}" })
	public Map<String, Object> deleteStoryComments(String storyAttachmentId) {
		return storyAttachmentsService.deleteStoryAttachmentById(storyAttachmentId);
	}

}
