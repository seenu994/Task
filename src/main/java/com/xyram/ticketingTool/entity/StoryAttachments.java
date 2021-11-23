package com.xyram.ticketingTool.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "story_attachments")
public class StoryAttachments {

	@Id
	@IdPrefix(value = "STR")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_download_url")
	private String fileDownloadUrl;

	
	@Column(name="file_type")
	private String fileType;
	
	@Column(name = "story_id")
	private String  storyId;

	@Column(name = "project_id")
	private String projectId;

	@Column(name = "uploaded_by")
	private String uploadedBy;
	
	@JsonIgnore
	@OneToMany(mappedBy = "storyAttachment")
	private List<StoryComments> storyComment;
	
	
	@Column(name="uploaded_on")
	private Date uploadedOn;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	
	
	public String getFileDownloadUrl() {
		return fileDownloadUrl;
	}

	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<StoryComments> getStoryComment() {
		return storyComment;
	}

	public void setStoryComment(List<StoryComments> storyComment) {
		this.storyComment = storyComment;
	}

	
	

	
	

}
