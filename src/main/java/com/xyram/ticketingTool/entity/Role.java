package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.ticket.config.JSONObjectUserType;

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

}
