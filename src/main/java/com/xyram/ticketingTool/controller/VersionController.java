package com.xyram.ticketingTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Version;
import com.xyram.ticketingTool.service.VersionService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class VersionController {

	@Autowired
	VersionService versionService;
	
	
	@PostMapping(value = {AuthConstants.ADMIN_BASEPATH + "/createVersion",
			AuthConstants.DEVELOPER_BASEPATH + "/createVersion",
			AuthConstants.HR_ADMIN_BASEPATH + "/createVersion",
			AuthConstants.HR_BASEPATH + "/createVersion",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createVersion",
			AuthConstants.INFRA_USER_BASEPATH + "/createVersion"})
	public Version CreateVersion(@RequestBody Version versionBody) {
		
		return versionService.CreateVersion(versionBody);
	} 
	
	@GetMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/getVersion/{id}",
			AuthConstants.DEVELOPER_BASEPATH + "/getVersion/{id}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getVersion/{id}",
			AuthConstants.HR_BASEPATH + "/getVersion/{id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getVersion/{id}",
			AuthConstants.INFRA_USER_BASEPATH + "/getVersion/{id}"})
	public Version getVersionById(@PathVariable String id) {
		
		return versionService.getVersionById(id);
	} 
	
	
	@PutMapping(value = { AuthConstants.DEVELOPER_BASEPATH + "/updateVersion/{id}",
			AuthConstants.DEVELOPER_BASEPATH + "/updateVersion/{id}",
			AuthConstants.HR_ADMIN_BASEPATH + "/updateVersion/{id}",
			AuthConstants.HR_BASEPATH + "/updateVersion/{id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/updateVersion/{id}",
			AuthConstants.INFRA_USER_BASEPATH + "/updateVersion/{id}"})
	public Version getVersionById(@PathVariable String id,@RequestBody Version versionBody) {
		
		return versionService.updateVersion(id,versionBody);
	} 
	
	@GetMapping(value = {AuthConstants.ADMIN_BASEPATH + "/getVersionByProjectId/{id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getVersionByProjectId/{id}", 
			AuthConstants.DEVELOPER_BASEPATH + "/getVersionByProjectId/{id}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getVersionByProjectId/{id}",
			AuthConstants.HR_BASEPATH + "/getVersionByProjectId/{id}",
			AuthConstants.INFRA_USER_BASEPATH + "/getVersionByProjectId/{id}"})
	public IssueTrackerResponse getVersionByProjectId(@PathVariable String id) {
		
		return versionService.getVersionByProjectId(id);
	} 
	
	@DeleteMapping (value = {AuthConstants.ADMIN_BASEPATH+"/delete/{Id}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/delete/{id}", 
			AuthConstants.DEVELOPER_BASEPATH + "/delete/{id}",
			AuthConstants.HR_ADMIN_BASEPATH + "/delete/{id}",
			AuthConstants.HR_BASEPATH + "/delete/{id}",
			AuthConstants.INFRA_USER_BASEPATH + "/delete/{id}"})
	public String deleteVersionByid(@PathVariable String Id) {
		
		return versionService.deleteVersionByid(Id);
	}
}
