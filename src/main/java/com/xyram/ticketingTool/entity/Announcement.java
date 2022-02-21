
package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.AnnouncementStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "Announcement")
public class Announcement extends AuditModel {

	@Id
	@IdPrefix(value = "ANN_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "announcement_id")
	private String announcementId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description",length = 5000, nullable = false)
	private String description;

	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AnnouncementStatus getStatus() {
		return status;
	}

	public void setStatus(AnnouncementStatus status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchLabels() {
		return searchLabels;
	}

	public void setSearchLabels(String searchLabels) {
		this.searchLabels = searchLabels;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private AnnouncementStatus status = AnnouncementStatus.ACTIVE;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "search_labels")
	private String searchLabels;

	
	
}


