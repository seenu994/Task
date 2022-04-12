package com.xyram.ticketingTool.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.modelMapper.StoryCommentMapper;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.StoryCommentVo;

@RestController
@CrossOrigin
public class StoryCommentController {

	@Autowired
	StoryCommentService storyCommentservice;

	@Autowired
	StoryCommentMapper storyCommentMapper;

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createStoryComment",
			AuthConstants.HR_BASEPATH + "/createStoryComment", AuthConstants.HR_ADMIN_BASEPATH + "/createStoryComment",
			AuthConstants.INFRA_USER_BASEPATH + "/createStoryComment",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createStoryComment",
			AuthConstants.DEVELOPER_BASEPATH + "/createStoryComment" })
	public StoryComments CreateStoryComment(@RequestBody @Valid StoryCommentVo storyCommentVo) {

		return storyCommentservice.createStoryComment(storyCommentMapper.getEntityFromVo(storyCommentVo));
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
//	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/updateStoryComment/{storyCommentId}",
			AuthConstants.HR_BASEPATH + "/updateStoryComment/{storyCommentId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/updateStoryComment/{storyCommentId}",
			AuthConstants.INFRA_USER_BASEPATH + "/updateStoryComment/{storyCommentId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/updateStoryComment/{storyCommentId}",
			AuthConstants.DEVELOPER_BASEPATH + "/updateStoryComment/{storyCommentId}" })
	public StoryComments updateStoryComments(@PathVariable String storyCommentId,
			@RequestBody StoryComments storyComments) {
		return storyCommentservice.updateStoryComment(storyCommentId, storyComments);
	}

	@PutMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteStoryComment/{storyCommentId}",
			AuthConstants.HR_BASEPATH + "/deleteStoryComment/{storyCommentId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/deleteStoryComment/{storyCommentId}",
			AuthConstants.INFRA_USER_BASEPATH + "/deleteStoryComment/{storyCommentId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteStoryComment/{storyCommentId}",
			AuthConstants.DEVELOPER_BASEPATH + "/deleteStoryComment/{storyCommentId}" })
	public Map<String, Object> deleteStoryComments(@PathVariable String storyCommentId) {
		return storyCommentservice.deleteStoryCommentById(storyCommentId);
	}

}
