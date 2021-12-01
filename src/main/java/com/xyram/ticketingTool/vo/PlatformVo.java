package com.xyram.ticketingTool.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlatformVo {

	

	@NotNull(message = "story Description  is mandatory")
	@NotEmpty(message = "story Description should  not  be  empty")
	@Size(min = 5, max = 1000)
	private String platformName;


	@NotNull(message = "project Id should is mandatory")
	@NotEmpty(message = "project Id should be not empty")
	@Size(min = 3)
	private String projectId;


	public String getPlatformName() {
		return platformName;
	}


	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
	
	
}

