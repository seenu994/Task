package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "client")
/*
 * @TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass =
 * JSONObjectUserType.class) })
 */
public class Client extends AuditModel {

	@Id
	@IdPrefix(value = "CLI_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="client_id")
    private String Id;

	@Column(name="client_name", length = 30)
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
	
