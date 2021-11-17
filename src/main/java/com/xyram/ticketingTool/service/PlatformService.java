package com.xyram.ticketingTool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.entity.Platform;

@Service
public interface PlatformService {

	
	Platform CreatePlatform(Platform platform);

	Platform UpdatePlatform(String id, Platform platformReq);

	Platform getPlatformbyId(String id);
	
	List<Platform> getAllPlatform();
}
