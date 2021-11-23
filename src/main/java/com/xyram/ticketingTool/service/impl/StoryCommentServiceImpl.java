package com.xyram.ticketingTool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.xyram.ticketingTool.Repository.StoryCommentRepository;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.vo.StoryCommentVo;

@Service
public class StoryCommentServiceImpl implements StoryCommentService{
	
	@Autowired
	StoryCommentRepository serviceCommentRepository;
	
	@Override
	public StoryComments CreateStoryComment(StoryCommentVo storyCommentVo) {
		
		StoryComments storyComment=	new StoryComments();
		storyComment.setDescription(storyCommentVo.getDescription());
		storyComment.setStoryId(storyCommentVo.getStoryId());
		storyComment.setProjectId(storyCommentVo.getProjectId());
		return storyComment;
	}
	

}
