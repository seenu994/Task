package com.xyram.ticketingTool.helper;


import com.poiji.annotation.ExcelCellName;
import com.xyram.ticketingTool.baseData.model.AuditModel;

public class EmployeePojo extends AuditModel  {


	@ExcelCellName("eId")
	private String eId;
	
	@ExcelCellName("password")
	private String password;
	@ExcelCellName("email")
	private String email;

	@ExcelCellName("firstName")
	private String firstName;

	@ExcelCellName("lastName")
	private String lastName;

	@ExcelCellName("middleName")
	private String middleName;
	
	@ExcelCellName("roleId")
	private String roleId;
	
	@ExcelCellName("mobile_number")
	private String mobileNumber;
	
	@ExcelCellName("status")
	private String status;
	
	@ExcelCellName("profileUrl")
	private String profileUrl;
	
	
	@ExcelCellName("designationId")
	private String designationId;
	
	
	
	
	@ExcelCellName("reportingTo")
	private String reportingTo;
	
	
	@ExcelCellName("location")
	private String location;
	
	
	@ExcelCellName("position")
	private String position;
	
	@ExcelCellName("wing_id")
	private String wing_id;

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWing_id() {
		return wing_id;
	}

	public void setWing_id(String wing_id) {
		this.wing_id = wing_id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
}