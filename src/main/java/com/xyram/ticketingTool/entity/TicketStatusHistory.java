package com.xyram.ticketingTool.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.TicketStatus;

@Entity
@Table(name = "ticket_status_history")
public class TicketStatusHistory extends AuditModel {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 4)
	@Column(name="Ticket_Hist_id")
	private String TicketHistID;

	@Column(name = "ticket_id")
	private String ticketId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ticket_Status")
	private TicketStatus ticketStatus = TicketStatus.INITIATED;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

}
