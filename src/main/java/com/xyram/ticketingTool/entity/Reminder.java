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
@Table(name = "Reminders")
public class Reminder extends AuditModel {
	@Id
	@IdPrefix(value = "REM")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String ReminderId;

	@Column(name = "user_name", nullable = false)
	private String userName;
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "reminderDate", nullable = false)
	private String reminderDate;

	@Column(name = "reminderTime", nullable = false)
	private String reminderTime;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "notify_members", nullable = false)
	private String notifyMembers;

	public String getNotifyMembers() {
		return notifyMembers;
	}

	public void setNotifyMembers(String notifyMembers) {
		this.notifyMembers = notifyMembers;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReminderId() {
		return ReminderId;
	}

	public void setReminderId(String ReminderId) {
		this.ReminderId = ReminderId;
	}

}
