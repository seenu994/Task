package com.xyram.ticketingTool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "hrcalendar")
public class HrCalendar extends AuditModel{

	@Id
	@IdPrefix(value = "HRC_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String Id;
	
	@Column(name = "candidate_mobile")
	private String candidateMobile;
	
	@Column(name = "candidate_name")
	private String candidateName;
	
	@Column(name = "schedule_date")
	private Date scheduleDate;
	
	@Column(name = "searched_source")
	private String searchedSource;
	
	@Column(name = "job_id")
	private String jobId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "closed")
	private Boolean closed = false;
	
	@Column(name = "call_count")
	private Integer callCount = 0;
	
	@Column(name = "reporting_to")
	private String reportingTo;
	
	@Column(name = "is_scheduled")
	private Boolean is_scheduled;

	public Boolean getIs_scheduled() {
		return is_scheduled;
	}

	public void setIs_scheduled(Boolean is_scheduled) {
		this.is_scheduled = is_scheduled;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCandidateMobile() {
		return candidateMobile;
	}

	public void setCandidateMobile(String candidateMobile) {
		this.candidateMobile = candidateMobile;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getSearchedSource() {
		return searchedSource;
	}

	public void setSearchedSource(String searchedSource) {
		this.searchedSource = searchedSource;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	
}
