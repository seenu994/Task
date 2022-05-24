package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;


@Entity
@Table(name = "employee_permission")
public class EmployeePermission extends AuditModel{
	
	@Id
	@IdPrefix(value = "PER_")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String Id;
	
	@Column(name = "job_admin", nullable = false)
	private Boolean jobAdmin;
	
	@Column(name = "job_opening_add", nullable = false) //JOB-OPENINGS-ADD
	private Boolean jobOpeningAdd;
	
	@Column(name = "job_openings_view", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobOpeningsView;
	
	@Column(name = "job_app_upload", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobAppUpload;
	
	@Column(name = "job_app_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobAppViewAll;
	
	@Column(name = "job_off_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobOffViewAll;
	
	@Column(name = "job_int_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jobIntViewAll;
	
	@Column(name = "har_cal_schedule_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean harCalScheduleAdd;
	
	@Column(name = "hr_cal_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean hrCalViewAll;
	
	@Column(name = "ann_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean annViewAll;
	
	@Column(name = "ann_edit_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean annEditAll;
	
	@Column(name = "ann_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean annAdd;
	
	@Column(name = "art_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean artViewAll;
	
	@Column(name = "art_edit_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean artEditAll;
	
	@Column(name = "art_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean artAdd;
	
	@Column(name = "tkt_admin", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean tktAdmin;
	
	@Column(name = "tkt_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean tktAdd;
	
	@Column(name = "tkt_assign", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean tktAssign;
	
	@Column(name = "prj_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean prjViewAll;
	
	@Column(name = "prj_edit_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean prjEditAll;
	
	@Column(name = "prj_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean prjAdd;
	
	@Column(name = "timesheet_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean timesheetAdd;
	
	@Column(name = "timesheet_admin", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean timesheetAdmin;
	
	@Column(name = "asst_admin", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean asstAdmin;
	
	@Column(name = "asst_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean asstAdd;
	
	@Column(name = "asst_iss_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean asstIssAdd;
	
	@Column(name = "asst_bill_add", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean asstBillAdd;
	
	@Column(name = "jv_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean jvViewAll;
	
	@Column(name = "av_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean avViewAll;
	
	@Column(name = "iss_track_admin", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean issTrackAdmin;
	
	@Column(name = "iss_track_view_all", nullable = false)//JOB-OPENINGS-VIEW
	private Boolean issTrackViewAll;
	
	@Column(name = "emp_admin", nullable = false)
	private Boolean empAdmin;
	
	public String getEmpPermissionId() {
		return Id;
	}

	public void setEmpPermissionId(String empPermissionId) {
		this.Id = empPermissionId;
	}

	public Boolean getEmpAdmin() {
		return empAdmin;
	}

	public void setEmpAdmin(Boolean empAdmin) {
		this.empAdmin = empAdmin;
	}

	public Boolean getJobAdmin() {
		return jobAdmin;
	}

	public void setJobAdmin(Boolean jobAdmin) {
		this.jobAdmin = jobAdmin;
	}

	public Boolean getJobOpeningAdd() {
		return jobOpeningAdd;
	}

	public void setJobOpeningAdd(Boolean jobOpeningAdd) {
		this.jobOpeningAdd = jobOpeningAdd;
	}

	public Boolean getJobOpeningsView() {
		return jobOpeningsView;
	}

	public void setJobOpeningsView(Boolean jobOpeningsView) {
		this.jobOpeningsView = jobOpeningsView;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Boolean getJobAppUpload() {
		return jobAppUpload;
	}

	public void setJobAppUpload(Boolean jobAppUpload) {
		this.jobAppUpload = jobAppUpload;
	}

	public Boolean getJobAppViewAll() {
		return jobAppViewAll;
	}

	public void setJobAppViewAll(Boolean jobAppViewAll) {
		this.jobAppViewAll = jobAppViewAll;
	}

	public Boolean getJobOffViewAll() {
		return jobOffViewAll;
	}

	public void setJobOffViewAll(Boolean jobOffViewAll) {
		this.jobOffViewAll = jobOffViewAll;
	}

	public Boolean getJobIntViewAll() {
		return jobIntViewAll;
	}

	public void setJobIntViewAll(Boolean jobIntViewAll) {
		this.jobIntViewAll = jobIntViewAll;
	}

	public Boolean getHarCalScheduleAdd() {
		return harCalScheduleAdd;
	}

	public void setHarCalScheduleAdd(Boolean harCalScheduleAdd) {
		this.harCalScheduleAdd = harCalScheduleAdd;
	}

	public Boolean getHrCalViewAll() {
		return hrCalViewAll;
	}

	public void setHrCalViewAll(Boolean hrCalViewAll) {
		this.hrCalViewAll = hrCalViewAll;
	}

	public Boolean getAnnViewAll() {
		return annViewAll;
	}

	public void setAnnViewAll(Boolean annViewAll) {
		this.annViewAll = annViewAll;
	}

	public Boolean getAnnEditAll() {
		return annEditAll;
	}

	public void setAnnEditAll(Boolean annEditAll) {
		this.annEditAll = annEditAll;
	}

	public Boolean getAnnAdd() {
		return annAdd;
	}

	public void setAnnAdd(Boolean annAdd) {
		this.annAdd = annAdd;
	}

	public Boolean getArtViewAll() {
		return artViewAll;
	}

	public void setArtViewAll(Boolean artViewAll) {
		this.artViewAll = artViewAll;
	}

	public Boolean getArtEditAll() {
		return artEditAll;
	}

	public void setArtEditAll(Boolean artEditAll) {
		this.artEditAll = artEditAll;
	}

	public Boolean getArtAdd() {
		return artAdd;
	}

	public void setArtAdd(Boolean artAdd) {
		this.artAdd = artAdd;
	}

	public Boolean getTktAdmin() {
		return tktAdmin;
	}

	public void setTktAdmin(Boolean tktAdmin) {
		this.tktAdmin = tktAdmin;
	}

	public Boolean getTktAdd() {
		return tktAdd;
	}

	public void setTktAdd(Boolean tktAdd) {
		this.tktAdd = tktAdd;
	}

	public Boolean getTktAssign() {
		return tktAssign;
	}

	public void setTktAssign(Boolean tktAssign) {
		this.tktAssign = tktAssign;
	}

	public Boolean getPrjViewAll() {
		return prjViewAll;
	}

	public void setPrjViewAll(Boolean prjViewAll) {
		this.prjViewAll = prjViewAll;
	}

	public Boolean getPrjEditAll() {
		return prjEditAll;
	}

	public void setPrjEditAll(Boolean prjEditAll) {
		this.prjEditAll = prjEditAll;
	}

	public Boolean getPrjAdd() {
		return prjAdd;
	}

	public void setPrjAdd(Boolean prjAdd) {
		this.prjAdd = prjAdd;
	}

	public Boolean getTimesheetAdd() {
		return timesheetAdd;
	}

	public void setTimesheetAdd(Boolean timesheetAdd) {
		this.timesheetAdd = timesheetAdd;
	}

	public Boolean getTimesheetAdmin() {
		return timesheetAdmin;
	}

	public void setTimesheetAdmin(Boolean timesheetAdmin) {
		this.timesheetAdmin = timesheetAdmin;
	}

	public Boolean getAsstAdmin() {
		return asstAdmin;
	}

	public void setAsstAdmin(Boolean asstAdmin) {
		this.asstAdmin = asstAdmin;
	}

	public Boolean getAsstAdd() {
		return asstAdd;
	}

	public void setAsstAdd(Boolean asstAdd) {
		this.asstAdd = asstAdd;
	}

	public Boolean getAsstIssAdd() {
		return asstIssAdd;
	}

	public void setAsstIssAdd(Boolean asstIssAdd) {
		this.asstIssAdd = asstIssAdd;
	}

	public Boolean getAsstBillAdd() {
		return asstBillAdd;
	}

	public void setAsstBillAdd(Boolean asstBillAdd) {
		this.asstBillAdd = asstBillAdd;
	}

	public Boolean getJvViewAll() {
		return jvViewAll;
	}

	public void setJvViewAll(Boolean jvViewAll) {
		this.jvViewAll = jvViewAll;
	}

	public Boolean getAvViewAll() {
		return avViewAll;
	}

	public void setAvViewAll(Boolean avViewAll) {
		this.avViewAll = avViewAll;
	}

	public Boolean getIssTrackAdmin() {
		return issTrackAdmin;
	}

	public void setIssTrackAdmin(Boolean issTrackAdmin) {
		this.issTrackAdmin = issTrackAdmin;
	}

	public Boolean getIssTrackViewAll() {
		return issTrackViewAll;
	}

	public void setIssTrackViewAll(Boolean issTrackViewAll) {
		this.issTrackViewAll = issTrackViewAll;
	}

}
