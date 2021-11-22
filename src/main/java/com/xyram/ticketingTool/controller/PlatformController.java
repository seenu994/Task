package com.xyram.ticketingTool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.service.PlatformService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class PlatformController {

	@Autowired
	PlatformService platformService;
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createPlatform", AuthConstants.ADMIN_BASEPATH + "/createPlatform"})
	public Platform CreatePlatform(@RequestBody Platform platform) {
		
		return platformService.CreatePlatform(platform);
	} 
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/editPlatform/{Id}",AuthConstants.ADMIN_BASEPATH + "/editPlatform/{Id}"})
	public Platform UpdatePlatform(@PathVariable String Id,@RequestBody Platform platformObj) {
		return platformService.UpdatePlatform(Id,platformObj);
	} 

	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH+"/getAllplatform",AuthConstants.ADMIN_BASEPATH+"/getAllplatform"})
	public List<Platform> getAllPlatform() {
		return platformService.getAllPlatform();
	}
	
	@GetMapping(value= {AuthConstants.DEVELOPER_BASEPATH+"/getplatformById/{Id}",AuthConstants.ADMIN_BASEPATH+"/getplatformById/{Id}"})
	public Platform getPlatformById(@PathVariable String Id) {
		return platformService.getPlatformbyId(Id);
	}
}
