package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.vo.StoryCommentVo;

public interface StoryCommentService {

	StoryComments CreateStoryComment(StoryCommentVo storyComment);

	List<Map> getStoryCommentbyprojectId(String Id);

	List<Map> getStoryCommentbyprojectIdandStoryId(String Id,String sId);

}
