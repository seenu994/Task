package com.xyram.ticketingTool.service;

import java.util.List;

import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Feature;

public interface FeatureService {

	Feature addDefaultFeature(Feature feature);

	Feature addFeature(Feature feature);

	IssueTrackerResponse getAllFeatureByDefault();

	Feature getFeaturesByFeatureId(String featureId);

}
