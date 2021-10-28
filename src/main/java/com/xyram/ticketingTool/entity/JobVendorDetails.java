package com.xyram.ticketingTool.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.enumType.UserStatus;

@Entity
@Table(name = "job_vendor_details")
public class JobVendorDetails {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size(max = 8)
	@Column(name = "vendor_id")
	private String vId;

	@Column(name = "password")
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "role_id")
	private String roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "vendor_status")
	private UserStatus status = UserStatus.ACTIVE;

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "user_id")
    private User userCredientials;
	
	@Column(name="profile_pic_url")
	private String profileUrl;
	
	@Column(name="type_of_vendor")
	private String typeOfVendor;

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
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

	public String getTypeOfVendor() {
		return typeOfVendor;
	}

	public void setTypeOfVendor(String typeOfVendor) {
		this.typeOfVendor = typeOfVendor;
	}
	
	


}
