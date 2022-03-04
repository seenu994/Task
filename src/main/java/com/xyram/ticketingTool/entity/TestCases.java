package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.TestCaseComponent;
import com.xyram.ticketingTool.enumType.TestCaseStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "testcases_details")
public class TestCases extends AuditModel {
	@Id
	@IdPrefix(value = "TSDET_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name="project_testcase_id")
	private String ptId;
	
	
	@Column(name = "project_id")
	private String projectId;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "tcpm_id")
    private TCProjectModules tCPrjModule;
	
	@Column(name = "name")
	private String testCaseName;
	
	@Column(name = "objective", length = 1000)
	private String testObjective;
	
	@Column(name = "precondition", length = 10000)
	private String precondition;
	
	@Column(name = "label")
	private String label;
	
	@Column(name = "priority_id")
	private String priorityId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "testCaseStatus")
	private TestCaseStatus status;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "testCaseComponent")
	private TestCaseComponent component;
	
	@Column(name = "owner")
	private String owner;
	
	@Column(name = "reviewer1")
	private String reviewer1;
	
	@Column(name = "reviewer2")
	private String reviewer2;

	public String getPtId() {
		return ptId;
	}

	public void setPtId(String ptId) {
		this.ptId = ptId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getTestObjective() {
		return testObjective;
	}

	public void setTestObjective(String testObjective) {
		this.testObjective = testObjective;
	}

	public String getPrecondition() {
		return precondition;
	}

	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}

	public TestCaseStatus getStatus() {
		return status;
	}

	public void setStatus(TestCaseStatus status) {
		this.status = status;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public TestCaseComponent getComponent() {
		return component;
	}

	public void setComponent(TestCaseComponent component) {
		this.component = component;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getReviewer1() {
		return reviewer1;
	}

	public void setReviewer1(String reviewer1) {
		this.reviewer1 = reviewer1;
	}

	public String getReviewer2() {
		return reviewer2;
	}

	public void setReviewer2(String reviewer2) {
		this.reviewer2 = reviewer2;
	}

	public TCProjectModules gettCPrjModule() {
		return tCPrjModule;
	}

	public void settCPrjModule(TCProjectModules tCPrjModule) {
		this.tCPrjModule = tCPrjModule;
	}
}
