package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.entity.ProjectFeature;

public interface ProjectFeatureService {

	ProjectFeature addProjectFeature(ProjectFeature projectFeature);

	Map getFeatureByProjectAndFeatureId(String projectId, String featureId);
	
    List<Map> getAllfeatureByProject(String projectId);

}
