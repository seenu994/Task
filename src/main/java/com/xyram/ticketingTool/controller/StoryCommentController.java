package com.xyram.ticketingTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.StoryCommentVo;

@RestController
@CrossOrigin
public class StoryCommentController {
	
	@Autowired
	StoryCommentService storyCommentservice;
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createComment"})
	public StoryComments CreateStoryComment(@ModelAttribute StoryCommentVo storyCommentVo) {
		
		return storyCommentservice.CreateStoryComment(storyCommentVo);
	} 

}
