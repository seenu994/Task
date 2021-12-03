package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.entity.ProjectFeature;


public interface ProjectFeatureRepository extends JpaRepository<ProjectFeature,String> {
	
	@Query("select new map ( f.featureName as featureName ,f.featureId as featureId) from ProjectFeature p inner join Feature f on f.featureId=p.featureId  where p.featureId=:featureId "
			+ " and p.projectId=:projectId ")
	Map getFeatureAvailable(String featureId,String projectId);
	
	@Query("select new map ( f.featureName as featureName ,f.featureId as featureId)  from ProjectFeature p inner join Feature f on f.featureId=p.featureId  where "
			+ " p.projectId=:projectId order by f.featureId asc  ")
	List<Map> getfeatureAvailableByproject(String projectId );
	
	

	@Query("select p from ProjectFeature p  where "
			+ " p.projectId=:projectId and p.featureId=:featureId ")
	List<ProjectFeature> hasFeatureAlreadyExist(String projectId ,String featureId);
	
	
	@Query("select p from ProjectFeature p  where "
			+ " p.projectId=:projectId and p.featureId=:featureId ")
	ProjectFeature getProjectFeatureByFeatureIdAndProject(String projectId ,String featureId);
	

}
