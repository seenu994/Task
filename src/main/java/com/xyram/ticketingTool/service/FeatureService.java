package com.xyram.ticketingTool.service;

import java.util.List;

import com.xyram.ticketingTool.entity.Feature;

public interface FeatureService {

	Feature addDefaultFeature(Feature feature);

	Feature addFeature(Feature feature);

	List<Feature> getAllFeatureByDefault();

	Feature getFeaturesByFeatureId(String featureId);

}
