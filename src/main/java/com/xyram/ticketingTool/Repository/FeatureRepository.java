package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, String> {

	
	
	@Query("select new map ( f.featureName as featureName ,f.featureId as featureId) from Feature f where f.featureType='Default' ")
	public List<Map> getAllDefaultFeatures();
	
	
	@Query("select f from Feature f where f.featureType='Default' ")
	public List<Feature> getDefaultFeatures();

	@Query("select f from Feature f where f.featureId=:featureId")
	public Feature getFeaturesByFeatureId(String featureId);
	
	@Query("select count(f.id) from Feature f ")
	public Integer getFeatureCount();
	
	
	
	
	
}
