package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.UserStatus;

@Entity
@Table(name = "role")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class Role extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Size( max = 8)
	@Column(name="role_id")
	private String Id;

	@Column(name = "role_name")
	private String roleName;

	@Enumerated(EnumType.STRING)
	@Column(name = "roleStatus")
	private UserStatus status = UserStatus.INACTIVE;
	
	@Column(name="displayable_name")
	private String displayableName;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getDisplayableName() {
		return displayableName;
	}

	public void setDisplayableName(String displayableName) {
		this.displayableName = displayableName;
	}
	
	

}
