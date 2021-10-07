package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.TicketStatus;

@Entity
@Table(name = "ticket")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class Ticket extends AuditModel {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="ticket_id")
	private String Id;
	@Column(name="ticket_description")
	private String ticketDescription;
	
//	@ManyToOne(cascade = { CascadeType.ALL })
//	@JoinColumn(name = "project_id")
//	private Projects project;
	
	@Column(name = "project_id")
	private String projectId;
	
	@Column(name = "created_by")
	private String createdBy;
	
//	@OneToOne(cascade = { CascadeType.MERGE})
	
	@Column(name = "priority_id")
	private String priorityId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ticketStatus")
	private TicketStatus status = TicketStatus.INITIATED;

	
	

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

//	public Projects getProject() {
//		return project;
//	}
//sssss
//	public void setProject(Projects project) {
//		this.project = project;
//	}

	

	public String getCreatedBy() {
		return createdBy;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	
}
	