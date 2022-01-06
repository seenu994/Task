package com.xyram.ticketingTool.request;

import javax.persistence.Column;

public class ProjectFeatureRequest {


	@Column(name="feature_name")
	private String featureName;
	
	
	
	@Column(name="project_id")
	private String projectId;



	public String getFeatureName() {
		return featureName;
	}



	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}



	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	

}
