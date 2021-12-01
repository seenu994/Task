package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.xyram.ticketingTool.Repository.StoryAttachmentsRespostiory;
import com.xyram.ticketingTool.Repository.StoryCommentRepository;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.entity.StoryComments;
import com.xyram.ticketingTool.service.StoryCommentService;
import com.xyram.ticketingTool.vo.StoryCommentVo;

@Service
public class StoryCommentServiceImpl implements StoryCommentService {

	@Autowired
	StoryCommentRepository storyCommentRepository;
	
	@Autowired
	CurrentUser currentUser;

	@Override
	public StoryComments createStoryComment(StoryComments storyComment) {

	    storyComment.setCommentedBy(currentUser.getScopeId());
	    storyComment.setCommentedDate(new Date());
		return storyCommentRepository.save(storyComment);
	}

	
	@Override
	public StoryComments updateStoryComment(String id, StoryComments storyCommentRequest) {
		
		return storyCommentRepository.findById(id).map(storyComment->{
			
			if(storyComment.getCommentedBy()!=null && storyComment.getCommentedBy().equalsIgnoreCase(currentUser.getScopeId())){
			if(storyComment.getDescription()!=null)
			{
				storyComment.setDescription(storyCommentRequest.getDescription());
			}
			
			
			
			}else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"You Don't Have permission to perform this Operation");
			}
			return storyCommentRepository.save(storyComment);
			
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Story Comment not found with id " + id));
	}
	
	
	

	@Override
	public StoryComments getStoryCommentsById(String id)

	{

		return storyCommentRepository.findById(id).map(storyComments -> {
			return storyComments;
		}).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "story Comments not found  with id " + id));

	}
	
	@Override	
	public List<Map> getStoryCommentsListByStoryId(String storyId){
	
		return storyCommentRepository.getStoryCommentsByStory(storyId);
	}
	
	
	@Override
	public Map<String, Object> deleteStoryCommentById(String id) {
		Map<String, Object> reponse = new HashMap<>();
		StoryComments storyComments = getStoryCommentsById(id);
		if (storyComments != null) {
			if (storyComments.getCommentedBy().equals(currentUser.getScopeId())) {
				

				Integer status = storyCommentRepository.deleteStoryCommentsById(id);

				if (status > 0) {
					
					reponse.put("message", "Story comments  succesfully deleted for id" + id);

				} else {
					reponse.put("message", "unable to delete for Story comments  id" + id);

				}
			} else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"You Don't Have permission to perform this Operation");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "story comments not found with id " + id);
		}

		return reponse;

	}
}
