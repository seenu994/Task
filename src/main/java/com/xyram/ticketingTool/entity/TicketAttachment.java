package com.xyram.ticketingTool.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;

@Entity
@Table(name = "ticket_attachment")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class TicketAttachment extends AuditModel {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="ticketAttachment_id")
	private String Id;
	
	
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	@Column(name="image_path")
	private String imagePath;

	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;

}
}