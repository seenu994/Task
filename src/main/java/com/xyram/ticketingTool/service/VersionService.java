package com.xyram.ticketingTool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.entity.Version;

@Service
public interface VersionService {
	
	Version CreateVersion(Version versionBody);
	
	List<Version> getVersionByProjectId(String Id);
	
	String deleteVersionByid(String Id);
	
	Version getVersionById(String id);

	

}
