package com.xyram.ticketingTool.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.xyram.ticketingTool.baseData.model.IBaseData;
import com.xyram.ticketingTool.enumType.ProjectStatus;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.ticket.config.JSONObjectUserType;
import javax.persistence.Lob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;   

@Entity
@Table(name = "user")
@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class User extends IBaseData {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size(max = 8)
	@Column(name = "user_id")
	private String id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "uid")
	private String uid;

	@Enumerated(EnumType.STRING)
	@Column(name = "userStatus")
	private UserStatus status;
	
	@Column(name = "accesskey")
	private String accesskey;
	
	
	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	@JsonIgnore
	private byte[] image;

	@JsonIgnore
	@Column(name = "mime_type")
	private String mimeType;

	@Column(name = "file_name")
	@JsonIgnore
	private String fileName;
	
	
	

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "TICKETINGTOOL_ADMIN")
	private UserRole userRole;
	

	public User() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;

	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Object map(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
