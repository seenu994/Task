package com.xyram.ticketingTool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Platform;

@Service
public interface PlatformService {

	
	Platform CreatePlatform(Platform platform);

	Platform updatePlatform(String id, Platform platformReq);

	Platform getPlatformById(String id);
	
	List<Platform> getAllPlatform();

	IssueTrackerResponse getStoryPlatformByProject(String projectId);
}
