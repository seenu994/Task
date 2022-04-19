package com.xyram.ticketingTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.service.FeatureService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
public class FeatureController {

	
	
	@Autowired
	FeatureService featureService;
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createFeature"})
	public Feature createSprint(@RequestBody Feature feature) {
		
		return featureService.addDefaultFeature(feature);
	} 
	
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllFeatures",
			 AuthConstants.DEVELOPER_BASEPATH + "/getAllFeatures",	})
	public IssueTrackerResponse getAllDefaultFeatures() {
		
		return featureService.getAllFeatureByDefault();
	} 
}
