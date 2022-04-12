package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.VersionRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Version;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.VersionService;

@Service
@Transactional
public class VersionServiceImpl implements VersionService {

	@Autowired
	VersionRepository versionRepository;

	@Autowired
	CurrentUser currentUser;

	@Override
	public Version CreateVersion(Version versionBody) {

		if (versionBody.getVersionName() != null && versionBody.getProjectId() != null) {
			return versionRepository.save(versionBody);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version name and project id is mandatory ");
		}

	}

	@Override
	public Version updateVersion(String versionId, Version versionRequest) {

		return versionRepository.findById(versionId).map(version -> {

			version.setVersionName(versionRequest.getVersionName());
			version.setUpdatedBy(currentUser.getScopeId());

			return versionRepository.save(version);

		}).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "version not found with id " + versionId));
	}

	@Override
	public IssueTrackerResponse getVersionByProjectId(String Id) {
		IssueTrackerResponse response = new IssueTrackerResponse();
		if (Id != null) {

			List<Map> versionList = versionRepository.getByProjectId(Id);

			response.setContent(versionList);

			response.setStatus("success");

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project Id is required");
		}
		return response;

	}

	@Override
	public String deleteVersionByid(String Id) {
		String message = null;
		if (Id != null) {
			versionRepository.deleteById(Id);
			message = "Deleted Successfully";
			return message;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version Id is mandatory");
		}

	}

	@Override
	public Version getVersionById(String id) {
		if (id != null) {
			return versionRepository.getByVersionId(id);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version Id is mandatory");
		}

	}

}
