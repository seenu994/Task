package com.xyram.ticketingTool.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.VersionRepository;
import com.xyram.ticketingTool.entity.Version;
import com.xyram.ticketingTool.service.VersionService;

@Service
@Transactional
public class VersionServiceImpl implements VersionService{

	@Autowired
	VersionRepository versionRepository;
	
	@Override
	public Version CreateVersion(Version versionBody) {

//		if(versionBody.)
		return null;
	}

	@Override
	public Version editVersion(String id, Version versionBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersionByProjectId(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version deleteVersionByid(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersionById(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
