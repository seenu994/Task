package com.xyram.ticketingTool.service;

import java.util.List;

import com.xyram.ticketingTool.entity.Platform;

public interface PlatformService {

	
	Platform CreatePlatform(Platform platform);

	Platform UpdatePlatform(String id, Platform platformReq);

	Platform getPlatformbyId(String id);
	
	List<Platform> getAllPlatform();
}
