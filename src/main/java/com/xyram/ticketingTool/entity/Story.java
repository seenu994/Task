package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name="story")
public class Story {
	
	@Id
	@IdPrefix(value = "STR")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name="story_no")
	private String storyNo;
	
	@Column(name="title")
	private String  title;
	
	@Column(name="story_description" ,length = 5000)
	private String storyDescription;
	
	
	@Column(name="platform")
	private String platform;
	
	@Column(name="project_id")
	private String projectId;
	
	
	@Column(name="story_type")
	private String storyType;
	
	
	@Column(name="story_label")
	private String storyLabel;
	
	
	@Column(name="story_status_id")
	private String storyStatus;
	
	
	@Column(name="sprint_id")
	private String  sprintId;
	
	@Column(name="owner")
	private String owner;
	

	
	@Column(name="assign_to")
	private String assignTo;
	
	
	@Column(name="version")
	private String version ;
	
	@Column(name="created_on")
	private Date createdOn;
	
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
		


	public String getPlatform() {
		return platform;
	}


	public void setPlateform(String plateform) {
		this.platform = plateform;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getAssignTo() {
		return assignTo;
	}


	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStoryNo() {
		return storyNo;
	}


	public void setStoryNo(String storyNo) {
		this.storyNo = storyNo;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getStoryDescription() {
		return storyDescription;
	}


	public void setStoryDescription(String storyDescription) {
		this.storyDescription = storyDescription;
	}


	public String getStoryType() {
		return storyType;
	}


	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}


	public String getStoryLabel() {
		return storyLabel;
	}


	public void setStoryLabel(String storyLabel) {
		this.storyLabel = storyLabel;
	}





	public String getSprintId() {
		return sprintId;
	}


	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getStoryStatus() {
		return storyStatus;
	}


	public void setStoryStatus(String storyStatus) {
		this.storyStatus = storyStatus;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	
	
	
	
	
	

}
