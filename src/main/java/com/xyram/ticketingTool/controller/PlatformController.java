package com.xyram.ticketingTool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.modelMapper.PlatformMapper;
import com.xyram.ticketingTool.service.PlatformService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.PlatformVo;

@RestController
@CrossOrigin
public class PlatformController {

	@Autowired
	PlatformService platformService;
	
	@Autowired
	PlatformMapper platformMapper;
	
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createPlatform", AuthConstants.ADMIN_BASEPATH + "/createPlatform"})
	public Platform CreatePlatform(@RequestBody PlatformVo platformVo) {
		
		return platformService.CreatePlatform(platformMapper.getEntityFromVo(platformVo));
	} 
	
	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/editPlatform/{Id}",AuthConstants.ADMIN_BASEPATH + "/editPlatform/{Id}"})
	public Platform UpdatePlatform(@PathVariable String Id,@RequestBody Platform platformObj) {
		return platformService.updatePlatform(Id,platformObj);
	} 

	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH+"/getAllplatform",AuthConstants.ADMIN_BASEPATH+"/getAllplatform"})
	public List<Platform> getAllPlatform() {
		return platformService.getAllPlatform();
	}
	
	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH+"/getplatformById/{Id}",AuthConstants.ADMIN_BASEPATH+"/getplatformById/{Id}"})
	public Platform getPlatformById(@PathVariable String Id) {
		return platformService.getPlatformById(Id);
	}
	
	

	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH+"/getAllplatforms/{projectId}"
			,AuthConstants.ADMIN_BASEPATH+"/getAllplatforms/{projectId}"})
	public IssueTrackerResponse getAllPlatformByProject(@PathVariable String projectId) {
		return platformService.getStoryPlatformByProject(projectId);
	}
}
