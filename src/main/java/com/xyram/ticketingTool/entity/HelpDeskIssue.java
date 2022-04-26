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
@Table(name = "Issues")

public class HelpDeskIssue {

	@Id
	@IdPrefix(value = "ISI")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "issue_Id" )
	private String issue_Id;
	
	

	@Column(name = "IssueName")
	private String issueName;



	public String getIssue_Id() {
		return issue_Id;
	}



	public void setIssue_Id(String issue_Id) {
		this.issue_Id = issue_Id;
	}



	public String getIssueName() {
		return issueName;
	}



	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	
}
