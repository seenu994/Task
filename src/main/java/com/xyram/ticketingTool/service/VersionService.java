package com.xyram.ticketingTool.service;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Version;

@Service
public interface VersionService {
	
	Version CreateVersion(Version versionBody);
	
	IssueTrackerResponse getVersionByProjectId(String Id);
	
	String deleteVersionByid(String Id);
	
	Version getVersionById(String id);

	Version updateVersion(String versionId, Version versionRequest);

	

}
