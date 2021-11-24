package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.StoryCommentRepository;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.vo.StoryCommentVo;

@Service
public class StoryCommentServiceImpl implements StoryCommentService{
	
	@Autowired
	StoryCommentRepository storyCommentRepository;
	
	@Autowired
	CurrentUser currentUser;
	
	@Override
	public StoryComments CreateStoryComment(StoryCommentVo storyCommentVo) {
		
		StoryComments storyComment=	new StoryComments();
		if(storyCommentVo.getStoryId()!=null && storyCommentVo.getProjectId()!=null) {
			storyComment.setDescription(storyCommentVo.getDescription());
			storyComment.setStoryId(storyCommentVo.getStoryId());
			storyComment.setProjectId(storyCommentVo.getProjectId());
			storyComment.setMentionTo(storyCommentVo.getMentionTo());
			storyComment.setCommentedDate(new Date());
			storyComment.setCommentedBy(currentUser.getScopeId());
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"storyId and projectId are mandatory");
		}
		return storyCommentRepository.save(storyComment);
		
	}

	@Override
	public List<Map> getStoryCommentbyprojectId(String Id) {
		List<Map> getList = storyCommentRepository.getCommentsByProjectId(Id);	
		return getList;
	}

	@Override
	public List<Map> getStoryCommentbyprojectIdandStoryId(String Id,String sId) {
		List<Map> getList = storyCommentRepository.getStoryCommentbyprojectIdandStoryId(Id,sId);	
		return getList;
	}

}
