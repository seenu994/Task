package com.xyram.ticketingTool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.stringtemplate.v4.compiler.CodeGenerator.list_return;

import com.xyram.ticketingTool.Repository.FeatureRepository;
import com.xyram.ticketingTool.Repository.ProjectFeatureRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.entity.ProjectFeature;
import com.xyram.ticketingTool.entity.Story;
import com.xyram.ticketingTool.request.AssignFeatureRequest;
import com.xyram.ticketingTool.request.ProjectFeatureRequest;
import com.xyram.ticketingTool.service.FeatureService;
import com.xyram.ticketingTool.service.ProjectFeatureService;

import ch.qos.logback.core.status.Status;

@Service
@Transactional
public class ProjectFeatureServiceImpl implements ProjectFeatureService {

	@Autowired
	ProjectFeatureRepository projectFeatureRepository;

	@Autowired
	FeatureService featureService;

	@Override
	public ProjectFeature addProjectFeature(ProjectFeature projectFeature) {

		return projectFeatureRepository.save(projectFeature);
	}

	@Override
	public IssueTrackerResponse getAllfeatureByProject(String projectId) {

		IssueTrackerResponse response = new IssueTrackerResponse();
		List<Map> featureList = projectFeatureRepository.getfeatureAvailableByproject(projectId);
		response.setContent(featureList);
		return response;
	}

	@Override
	public ProjectFeature addNewProjectFeature(ProjectFeatureRequest ProjectFeatureRequest) {

		Feature feature = new Feature();
		feature.setFeatureName(ProjectFeatureRequest.getFeatureName());

		Feature response = featureService.addFeature(feature);
		Object ProjectFeature;
		ProjectFeature projectFeature = null;
		if (response != null) {

			projectFeature = new ProjectFeature();

			projectFeature.setProjectId(ProjectFeatureRequest.getProjectId());
			projectFeature.setFeatureId(response.getId());
			projectFeature.setStatus("ACTIVE");

			return projectFeatureRepository.save(projectFeature);
		}
		return projectFeature;
	}

//	public ProjectFeature updateProjectFeature(String featureId, ProjectFeatureRequest projectFeatureRequest) {
//
//		
//		return projectFeatureRepository.getProjectFeatureByFeatureIdAndPrjId(featureId, projectFeatureRequest.getProjectId())
//				.map(projectFeature->{
//					fi
//					return projectFeature;
//				}).orElseThrow(()->throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"project feature not  with id"+featureId));
//		Feature feature = new Feature();
//		feature.setFeatureName(ProjectFeatureRequest.getFeatureName());

//		Feature response = featureService.addFeature(feature);
//		Object ProjectFeature;
//		ProjectFeature projectFeature =null;
//		if (response != null) {
//
//			projectFeature= new ProjectFeature();
//			
//			projectFeature.setProjectId(ProjectFeatureRequest.getProjectId());
//			projectFeature.setFeatureId(response.getId());
//			projectFeature.setStatus("ACTIVE");
//
//			return projectFeatureRepository.save(projectFeature);
//		}
//		return projectFeature;
	///

	@Override
	public Map getFeatureByProjectAndFeatureId(String projectId, String featureId) {
		return projectFeatureRepository.getFeatureAvailable(featureId, projectId);
	}

	@Override
	public List<ProjectFeature> assignFeatureToProject(AssignFeatureRequest request)

	{
		List<ProjectFeature> projectFeatures = new ArrayList<ProjectFeature>();
		for (String id : request.getStoryStatusIds()) {

			Feature feature = featureService.getFeaturesByFeatureId(id);
			if (feature != null) {
				ProjectFeature projectFeature = new ProjectFeature();
				projectFeature.setFeatureId(feature.getFeatureId());
				projectFeature.setProjectId(request.getProjectId());
				projectFeature.setStatus("ACTIVE");
				if (projectFeatureRepository.hasFeatureAlreadyExist(request.getProjectId(), id).isEmpty()) {
					projectFeatures.add(projectFeature);
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature id not found " + id);
			}

		}
		return projectFeatureRepository.saveAll(projectFeatures);
	}

	@Override
	public List<ProjectFeature> unAssignFeatureToProject(AssignFeatureRequest request)

	{
		List<ProjectFeature> projectFeatures = new ArrayList<ProjectFeature>();

		for (String id : request.getStoryStatusIds()) {

			ProjectFeature projectFeature = projectFeatureRepository
					.getProjectFeatureByFeatureIdAndProject(request.getProjectId(), id);

			if (projectFeature != null) {
				projectFeature.setStatus("INACTIVE");
				projectFeatures.add(projectFeature);

			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature id not found " + id);
			}

		}
		return projectFeatureRepository.saveAll(projectFeatures);
	}

}
