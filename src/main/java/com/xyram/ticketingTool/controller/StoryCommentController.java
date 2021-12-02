package com.xyram.ticketingTool.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Platform;
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
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createStoryComment"})
	public StoryComments CreateStoryComment(@RequestBody StoryCommentVo storyCommentVo) {
		
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

	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/updateStoryComment{storyCommentId}"})
	public StoryComments updateStoryComments(String storyCommentId,StoryComments storyComments)
	{
		return storyCommentservice.updateStoryComment(storyCommentId, storyComments);
	}
	
	
	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/deleteStoryComment{storyCommentId}"})
	public Map<String, Object> deleteStoryComments(String storyCommentId)
	{
		return storyCommentservice.deleteStoryCommentById(storyCommentId);
	}
	
	
	
	
}
