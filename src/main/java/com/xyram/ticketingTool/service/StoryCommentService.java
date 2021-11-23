package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.vo.StoryCommentVo;

public interface StoryCommentService {

	StoryComments CreateStoryComment(StoryCommentVo storyComment);

}
