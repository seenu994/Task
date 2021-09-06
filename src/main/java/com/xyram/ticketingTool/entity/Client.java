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

import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="client_id")
    private String Id;

@Column(name="client_name")
	private String clientName;

@Enumerated(EnumType.STRING)
@Column(name = "clientstatus")
private ClientStatus status = ClientStatus.ACTIVE;


public String getId() {
	return Id;
}

public void setId(String id) {
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

