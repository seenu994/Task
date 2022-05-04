package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "helpdesk_issues")

public class HelpDeskIssueTypes {

	@Id
	@IdPrefix(value = "ISI")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "issueType_Id" )
	private String issueTypeId;
	
	@Column(name = "helpdesk_status")
	private boolean helpdeskStatus;
	
	@Column(name = "helpdeskIssueType")
	private String helpdeskIssueType;

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public boolean isHelpdeskStatus() {
		return helpdeskStatus;
	}

	public void setHelpdeskStatus(boolean helpdeskStatus) {
		this.helpdeskStatus = helpdeskStatus;
	}

	public String getHelpdeskIssueType() {
		return helpdeskIssueType;
	}

	public void setHelpdeskIssueType(String helpdeskIssueType) {
		this.helpdeskIssueType = helpdeskIssueType;
	}

	
	
    
}

	