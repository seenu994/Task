package com.xyram.ticketingTool.service;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.entity.Version;

@Service
public interface VersionService {
	
	Version CreateVersion(Version versionBody);
	
	Version editVersion(String id, Version versionBody);
	
	Version getVersionByProjectId(String Id);
	
	Version deleteVersionByid(String Id);
	
	Version getVersionById(String id);

	

}
