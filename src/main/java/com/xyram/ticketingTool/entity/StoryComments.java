package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "story_comments")
public class StoryComments {

	@Id
	@IdPrefix(value = "STC")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String id;

	@Column(name = "description")
	private String description;

	@Column(name = "mention_to")
	private String mentionTo;

	@Column(name = "commented_date")
	private Date commentedDate;

	@Column(name = "commented_by")
	private String commentedBy;

	@Column(name = "story_id")
	private String  storyId;

	@Column(name = "project_id")
	private String projectId;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="story_attachement_id")
	private StoryAttachments storyAttachment;

	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMentionTo() {
		return mentionTo;
	}

	public void setMentionTo(String mentionTo) {
		this.mentionTo = mentionTo;
	}

	public Date getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(Date commentedDate) {
		this.commentedDate = commentedDate;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
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

	public StoryAttachments getStoryAttachment() {
		return storyAttachment;
	}

	public void setStoryAttachment(StoryAttachments storyAttachment) {
		this.storyAttachment = storyAttachment;
	}
	
	

}
