package com.xyram.ticketingTool.request;

import javax.persistence.Column;

public class ReminderRequest {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "reminderDate", nullable = false)
	private String reminderDate;

	@Column(name = "reminderTime", nullable = false)
	private String reminderTime;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "reference_id", nullable = false)
	private String referenceId;
	
	@Column(name = "is_host", nullable = false)
	private Boolean isHost;
	
	@Column(name = "references", nullable = false)
	private String[] references;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(String reminderDate) {
		this.reminderDate = reminderDate;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Boolean getIsHost() {
		return isHost;
	}

	public void setIsHost(Boolean isHost) {
		this.isHost = isHost;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}
	
	
	
}
