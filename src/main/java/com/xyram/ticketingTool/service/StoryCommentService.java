package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.vo.StoryCommentVo;

public interface StoryCommentService {


	Map<String, Object> deleteStoryCommentById(String id);

	List<Map> getStoryCommentsListByStoryId(String storyId);

	StoryComments getStoryCommentsById(String id);

	StoryComments createStoryComment(StoryComments storyComment);

	StoryComments updateStoryComment(String id, StoryComments storyCommentRequest);

}
