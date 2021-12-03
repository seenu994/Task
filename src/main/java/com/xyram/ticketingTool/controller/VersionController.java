package com.xyram.ticketingTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.xyram.ticketingTool.entity.Version;
import com.xyram.ticketingTool.service.VersionService;
import com.xyram.ticketingTool.util.AuthConstants;
import com.xyram.ticketingTool.vo.PlatformVo;

@RestController
@CrossOrigin
public class VersionController {

	@Autowired
	VersionService versionService;
	
	
	@PostMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/createVersion"})
	public Version CreateVersion(@RequestBody Version versionBody) {
		
		return versionService.CreateVersion(versionBody);
	} 
	
	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/getVersion/{id}"})
	public Version getVersionById(@PathVariable String id) {
		
		return versionService.getVersionById(id);
	} 
}
