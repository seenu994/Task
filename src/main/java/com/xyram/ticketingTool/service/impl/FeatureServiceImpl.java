package com.xyram.ticketingTool.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.Repository.FeatureRepository;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.service.FeatureService;

public class FeatureServiceImpl implements FeatureService {

	@Autowired
	FeatureRepository featureRepository;

	@Override
	public Feature addDefaultFeature(Feature feature) {

		feature.setFeatureType("Default");
		if (feature.getFeatureName() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature name is mandatory");
		}

		return featureRepository.save(feature);

	}

	@Override
	public Feature addFeature(Feature feature) {

		feature.setFeatureType("spec");
		if (feature.getFeatureName() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feature name is mandatory");
		}

		return featureRepository.save(feature);

	}

	
	@Override
	public List<Feature> getAllFeatureByDefault() {

		return   featureRepository.getAllDefaultFeatures();
		
	}
}
