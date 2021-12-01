package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.PlatformRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Platform;
import com.xyram.ticketingTool.service.PlatformService;

@Service
@Transactional
public class PlatformServiceImpl implements PlatformService {

	@Autowired
	PlatformRepository platformRepository;

	@Override
	public Platform CreatePlatform(Platform platform) {

		return platformRepository.save(platform);

	}

	@Override
	public Platform updatePlatform(String id, Platform platformReq) {

		return platformRepository.findById(id).map(platform -> {

			if (platform.getPlatformName() != null) {
				platform.setPlatformName(platformReq.getPlatformName());
			}

			return platformRepository.save(platform);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "platform not found with id " + id));
	}

	@Override
	public Platform getPlatformbyId(String id) {
		Platform platform = platformRepository.getById(id);

		if (platform != null) {
			return platform;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "platform not found with this id");
		}

	}

	@Override
	public List<Platform> getAllPlatform() {
		// TODO Auto-generated method stub
		return platformRepository.findAll();
	}

	@Override
	public IssueTrackerResponse getStoryPlatformByProject(String projectId)

	{

		IssueTrackerResponse response = new IssueTrackerResponse();
		List<Map> platformList = platformRepository.getStoryPlatformByProject(projectId);

		response.setContent(platformList);

		response.setStatus("success");

		return response;

	}

}
