package com.xyram.ticketingTool.request;

import javax.persistence.Column;

public class ReminderRequest {

	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "reminder_date_time", nullable = false)
	private String reminderDateTime;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "is_host")
	private Boolean isHost;

	@Column(name = "references", nullable = false)
	private String[] references;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReminderDateTime() {
		return reminderDateTime;
	}

	public void setReminderDateTime(String reminderDateTime) {
		this.reminderDateTime = reminderDateTime;
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
