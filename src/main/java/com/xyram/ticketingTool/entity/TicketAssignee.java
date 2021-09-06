package com.xyram.ticketingTool.entity;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.ticket.config.JSONObjectUserType;

@Entity
@Table(name = "ticket_assignee")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class TicketAssignee extends AuditModel {

	@Id
	@Column(name = "ticketAssignee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	
//	@ManyToOne(cascade = { CascadeType.MERGE })
	@Column(name = "ticket_id")
	private Integer ticketId;
	
//	@ManyToOne(cascade = { CascadeType.MERGE })
	@Column(name = "employee_id")
	private Integer employeeId;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ticketAssigneeStatus")
	private TicketAssigneeStatus status = TicketAssigneeStatus.ACTIVE;


	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public Integer getTicket() {
		return ticketId;
	}


	public void setTicket(Integer ticket) {
		this.ticketId = ticket;
	}


	public TicketAssigneeStatus getStatus() {
		return status;
	}


	public void setStatus(TicketAssigneeStatus status) {
		this.status = status;
	}


	public Integer getEmployee() {
		return employeeId;
	}


	public void setEmployee(Integer employee) {
		this.employeeId = employee;
	}
	
}
