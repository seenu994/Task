package com.xyram.ticketingTool.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

import org.checkerframework.checker.guieffect.qual.UIEffect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.FeatureRepository;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.service.FeatureService;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	FeatureRepository featureRepository;

	@Override
	public Feature addDefaultFeature(Feature feature) {

		feature.setFeatureType("Default");
		feature.setFeatureStatus("ACTIVE");

		Integer totalFeature = featureRepository.getFeatureCount();

		feature.setFeatureId("f" + "" + Integer.toString(totalFeature + 1));

		if (feature.getFeatureName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature name is mandatory");
		}

		return featureRepository.save(feature);

	}

	@Override
	public Feature addFeature(Feature feature) {

		
		
		feature.setFeatureType("spec");
		feature.setFeatureStatus("ACTIVE");

		if (feature.getFeatureName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature name is mandatory");
		}
		Integer totalFeature = featureRepository.getFeatureCount();

		feature.setFeatureId("f" + "" + Integer.toString(totalFeature + 1));

		return featureRepository.save(feature);

	}

	@Override
	public IssueTrackerResponse getAllFeatureByDefault() {

		IssueTrackerResponse response = new IssueTrackerResponse();
		List<Map> featureList = featureRepository.getAllDefaultFeatures();
		response.setContent(featureList);

		response.setStatus("success");

		return response;

	}
	
	

	@Override
	public Feature updateFeatureByFeatureId(String featureId, Feature featureRequest)

	{
		Feature feature = getFeaturesByFeatureId(featureId);
		if (feature != null) {
			feature.setFeatureName(featureRequest.getFeatureName());
			return featureRepository.save(feature);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, " feature  not found with id" + featureId);
		}
	}

	@Override
	public Feature getFeaturesByFeatureId(String featureId) {

		return featureRepository.getFeaturesByFeatureId(featureId);
	}
}
