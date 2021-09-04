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
	@Column(name = "ticketCommentse_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	
	@Column(name = "ticket_id")
	private Integer ticketId;
	
	
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


	public Integer getId() {
		return Id;
	}


	public Integer getTicketId() {
		return ticketId;
	}


	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}


	public void setId(Integer id) {
		Id = id;
	}

	public TicketCommentsStatus getStatus() {
		return status;
	}


	public void setStatus(TicketCommentsStatus status) {
		this.status = status;
	}
	
}
	
