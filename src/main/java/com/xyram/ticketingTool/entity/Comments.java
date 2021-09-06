package com.xyram.ticketingTool.entity;

//import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.TicketCommentsStatus;

//import org.hibernate.annotations.TypeDef;
//import org.hibernate.annotations.TypeDefs;
//
//import com.xyram.ticketingTool.baseData.model.AuditModel;
//import com.xyram.ticketingTool.enumType.ProjectMembersStatus;
//import com.xyram.ticketingTool.enumType.TicketAssigneeStatus;
//import com.xyram.ticketingTool.enumType.TicketCommentsStatus;
//import com.xyram.ticketingTool.enumType.TicketStatus;
//import com.xyram.ticketingTool.enumType.UserStatus;
//import com.xyram.ticketingTool.ticket.config.JSONObjectUserType;

@Entity
@Table(name = "ticket_comments")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class Comments extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="comments_id")
	private String Id;
	
	
	@Column(name = "ticket_id")
	private String ticketId;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ticketCommentsStatus")
	private TicketCommentsStatus status = TicketCommentsStatus.ACTIVE;
	
	@Column(name="Description")
	private String ticketCommentDescription;


	public String getTicketCommentDescription() {
		return ticketCommentDescription;
	}


	public void setTicketCommentDescription(String ticketCommentDescription) {
		this.ticketCommentDescription = ticketCommentDescription;
	}


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


	public TicketCommentsStatus getStatus() {
		return status;
	}


	public void setStatus(TicketCommentsStatus status) {
		this.status = status;
	}


	
}