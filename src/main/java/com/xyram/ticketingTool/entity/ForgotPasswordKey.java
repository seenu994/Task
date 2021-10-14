package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "passwordtokendetails")
public class ForgotPasswordKey {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="key_id")
	private String Id;
	
	@Column(name="reset_password_token")
	private String resetPasswordToken;
	
	@Column(name = "password_updated_time")
	private Date passwordUpdatedTime;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "created_at")
	private Date createdAt;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Date getPasswordUpdatedTime() {
		return passwordUpdatedTime;
	}

	public void setPasswordUpdatedTime(Date passwordUpdatedTime) {
		this.passwordUpdatedTime = passwordUpdatedTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
