package com.xyram.ticketingTool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.Repository.StoryLabelRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.entity.StoryLabel;
import com.xyram.ticketingTool.service.StoryLabelService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class StoryLabelController {

	@Autowired 
	StoryLabelService storyLabelService;
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createStoryLabel",
			AuthConstants.ADMIN_BASEPATH + "/createStoryLabel",
			 AuthConstants.INFRA_ADMIN_BASEPATH + "/createStoryLabel",
			 AuthConstants.INFRA_USER_BASEPATH + "/createStoryLabel"})
	public StoryLabel createStoryLabel(@RequestBody StoryLabel storylabel) {
		return storyLabelService.createStoryLabel(storylabel);
	} 
	
	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/editStoryLabel/{id}",AuthConstants.ADMIN_BASEPATH + "/editStoryLabel/{id}"})
	public StoryLabel updateStoryLabel(@PathVariable String id,@RequestBody StoryLabel storylabel) {
		return storyLabelService.updateStoryLabel(id,storylabel);
	} 

	
	
	
	@GetMapping(value= {AuthConstants.ADMIN_BASEPATH+"/getAllStoryLabel/{projectId}",
			AuthConstants.DEVELOPER_BASEPATH+"/getAllStoryLabel/{projectId}",
			 AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllStoryLabel/{projectId}",
			 AuthConstants.INFRA_USER_BASEPATH + "/getAllStoryLabel/{projectId}"})
	public IssueTrackerResponse getAllStoryLabelByProject(@PathVariable String projectId) {
		return storyLabelService.getStoryLabelByProjectId(projectId);
	}
}
