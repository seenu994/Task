package com.xyram.ticketingTool.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

		if(versionBody.getVersionName()!=null && versionBody.getProjectId()!=null) {
			return 	versionRepository.save(versionBody);
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version name and project id is mandatory ");
		}
		
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
	public Version getVersionById(String id) {
		if(id!=null) {
			return versionRepository.getById(id);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Version Id is mandatory");
		}
		
	}

	


}
