package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.compiler.CodeGenerator.list_return;

import com.xyram.ticketingTool.Repository.ProjectFeatureRepository;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.entity.ProjectFeature;
import com.xyram.ticketingTool.service.ProjectFeatureService;

@Service
@Transactional
public class ProjectFeatureServiceImpl implements ProjectFeatureService {

	@Autowired
	ProjectFeatureRepository projectFeatureRepository;

	@Override
	public ProjectFeature addProjectFeature(ProjectFeature projectFeature) {

		return projectFeatureRepository.save(projectFeature);
	}

	@Override
	public List<Map> getAllfeatureByProject(String projectId) {
		return projectFeatureRepository.getfeatureAvailableByproject(projectId);
	}

	@Override
	public Map getFeatureByProjectAndFeatureId(String projectId, String featureId) {
		return projectFeatureRepository.getFeatureAvailable(featureId, projectId);
	}

}
