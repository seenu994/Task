package com.xyram.ticketingTool.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.UserStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "employee")
public class Employee extends AuditModel {

	/*
	 * @Id
	 * 
	 * @IdPrefix(value = "EMP_")
	 * 
	 * @GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	 * 
	 * @GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy =
	 * "com.xyram.ticketingTool.id.generator.IdGenerator")
	 */
	@Id
	@Column(name = "employee_id")
	private String eId;

	@Column(name = "password")
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "frist_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "role_id")
	private String roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "Employee_status")
	private UserStatus status = UserStatus.ACTIVE;

	@Column(name = "designation_id")
	private String designationId;

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "user_id")
    private User userCredientials;
	
	@Column(name="profile_pic_url")
	private String profileUrl;
	
	@Column(name="reporting_to")
	private String reportingTo;
	
	@Column(name="location")
	private String location;
	
	@Column(name="position")
	private String position;
	
	@OneToOne(cascade = {  CascadeType.ALL})
	@JoinColumn(name = "wing_id")
    private CompanyWings wings;
	
//	@OneToMany(mappedBy = "employee")
//    private Set<Asset> asset;
	
	
//	public Set<Asset> getAsset() {
//		return asset;
//	}
//
//	public void setAsset(Set<Asset> asset) {
//		this.asset = asset;
//	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public User getUserCredientials() {
		return userCredientials;
	}

	public void setUserCredientials(User userCredientials) {
		this.userCredientials = userCredientials;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
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

	public CompanyWings getWings() {
		return wings;
	}

	public void setWings(CompanyWings wings) {
		this.wings = wings;
	}

	
}
