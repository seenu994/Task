package com.xyram.ticketingTool.entity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.TicketStatus;

@Entity
@Table(name = "ticket_info")
//@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
public class Ticket extends AuditModel {

	@Id
	@GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size(max = 8)
	@Column(name = "ticket_id")
	private String Id;

	@Column(name = "ticket_description", length = 5000)
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

	@Column(name = "resolved_at")
	private Date resolvedAt;

	@Column(name = "cancelled_at")
	private Date cancelledAt;

//	@Column(name = "last_updated_at")
//	private Date lastUpdatedAt;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public void setResolvedAt(Date date) {
		resolvedAt = date;
	}

	public void setCancelledAt(Date date) {
		resolvedAt = date;
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

//	public Date getLast_updated_at() {
//		return lastUpdatedAt;
//	}
//
//	public void setLast_updated_at(Date last_updated_at) {
//		this.lastUpdatedAt = last_updated_at;
//	}

	public Date getResolvedOn() {
		return resolvedAt;
	}

	public Date getCancelledOn() {
		return cancelledAt;
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

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	
	
}
