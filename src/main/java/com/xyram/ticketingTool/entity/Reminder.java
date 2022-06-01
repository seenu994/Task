package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "reminders")
public class Reminder extends AuditModel {
	@Id
	@IdPrefix(value = "REM")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String reminderId;

//	@Column(name = "user_name", nullable = false)
//	private String userName;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "reminder_date_time", nullable = false)
	private String reminderDateTime;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "is_host")
	private Boolean isHost;

	@Column(name = "reference")
	private String references;

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getReminderId() {
		return reminderId;
	}

	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

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
}
