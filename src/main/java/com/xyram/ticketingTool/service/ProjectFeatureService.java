package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.ProjectFeature;
import com.xyram.ticketingTool.request.AssignFeatureRequest;
import com.xyram.ticketingTool.request.ProjectFeatureRequest;

public interface ProjectFeatureService {

	ProjectFeature addProjectFeature(ProjectFeature projectFeature);

	Map getFeatureByProjectAndFeatureId(String projectId, String featureId);
	
    IssueTrackerResponse getAllfeatureByProject(String projectId);

	List<ProjectFeature> assignFeatureToProject(AssignFeatureRequest request);

	List<ProjectFeature> unAssignFeatureToProject(AssignFeatureRequest request);

	ProjectFeature addNewProjectFeature(ProjectFeatureRequest ProjectFeatureRequest);

	ProjectFeature updateProjectFeature(String featureId, ProjectFeature projectFeatureRequest);

}
