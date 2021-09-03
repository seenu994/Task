package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.ClientStatus;

@Entity
@Table(name = "client")
/*
 * @TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass =
 * JSONObjectUserType.class) })
 */
public class Client extends AuditModel {

@Id
@Column(name = "client_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer Id;

@Column(name="client_name")
	private String clientName;

@Enumerated(EnumType.STRING)
@Column(name = "clientstatus")
private ClientStatus status = ClientStatus.INACTIVE;

public Integer getId() {
	return Id;
}

public void setId(Integer id) {
	Id = id;
}

public String getClientName() {
	return clientName;
}

public void setClientName(String clientName) {
	this.clientName = clientName;
}

public ClientStatus getStatus() {
	return status;
}

public void setStatus(ClientStatus status) {
	this.status = status;
}
}

