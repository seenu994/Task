package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xyram.ticketingTool.enumType.PermissionStatus;

@Entity
@Table(name = "permissions")
public class Permissions {
	
	@Id
	@Column(name = "permission_id")
	private Integer permissionId;

	@Column(name = "permission_name")
	private String permissionName;

	@Enumerated(EnumType.STRING)
	@Column(name = "permission_status")
	private PermissionStatus status;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public PermissionStatus getStatus() {
		return status;
	}

	public void setStatus(PermissionStatus status) {
		this.status = status;
	}

}
