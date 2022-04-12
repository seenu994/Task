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
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "ticket_assignee")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class TicketAssignee extends AuditModel {

	@Id
	@IdPrefix(value = "TICA_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="ticketAssignee_id")
	private String Id;
	
	
//	@ManyToOne(cascade = { CascadeType.MERGE })
	@Column(name = "ticket_id")
	private String ticketId;
	
//	@ManyToOne(cascade = { CascadeType.MERGE })
	@Column(name = "employee_id")
	private String employeeId;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ticket_assignee_status")
	private TicketAssigneeStatus status = TicketAssigneeStatus.ACTIVE;


	public String getId() {
		return Id;
	}


	public void setId(String id) {
		Id = id;
	}


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public TicketAssigneeStatus getStatus() {
		return status;
	}


	public void setStatus(TicketAssigneeStatus status) {
		this.status = status;
	}


	
	
}
