package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
/*import org.hibernate.mapping.Set;*/
import java.util.Set;
import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.enumType.ProjectStatus;
import com.xyram.ticketingTool.ticket.config.JSONObjectUserType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;



@Entity
@Table(name="project")
//@TypeDefs({@TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class)})
public class Projects extends AuditModel {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Size( max = 8)
	@Column(name="project_id")
	private String pId;
	
    @Column(name="project_name")
	private String projectName;
	
	@Column(name="project_description")
	private String projectDescritpion;
	
	
	@Column(name = "client_id")
	private String   clientId;

	
	@Column(name="inhouse")
	private String inHouse;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "projectstatus")
	private ProjectStatus status = ProjectStatus.INDEVELOPMENT;

	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescritpion() {
		return projectDescritpion;
	}

	public void setProjectDescritpion(String projectDescritpion) {
		this.projectDescritpion = projectDescritpion;
	}


	public String getInHouse() {
		return inHouse;
	}

	public void setInHouse(String inHouse) {
		this.inHouse = inHouse;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	
}
	